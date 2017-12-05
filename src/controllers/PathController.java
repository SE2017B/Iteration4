/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik, Oluchukwu Okafor
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import exceptions.InvalidNodeException;
import javafx.animation.Transition;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.util.Duration;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import map.Path;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.animation.PathTransition;

import ui.*;

import java.util.*;

public class PathController implements ControllableScreen, Observer{
    private ScreenController parent;
    private HospitalMap map;
    private String startType = "";
    private String startFloor = "";
    private String endType =  "";
    private String endFloor = "";

    private MapViewer mapViewer;
    private FloorNumber currentFloor;// the current floor where the kiosk is.
    private PathViewer currentPath;
    private ArrayList<FloorNumber> floors; //list of floors available
    private ArrayList<PathViewer> paths;
    private ArrayList<Shape> shapes;
    private Pane arrow;
    private PathTransition pathTransition;
    private Pane mapPane;
    private Path thePath;

    @FXML
    private ChoiceBox<Node> startNodeChoice;
    @FXML
    private ChoiceBox<Node> endNodeChoice;

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TitledPane textDirectionsPane;

    private ScrollPane mapScrollPane;
    @FXML
    private JFXSlider slideBarZoom;
    @FXML
    private AnchorPane buttonHolderPane;
    @FXML
    private MenuButton startTypeMenu;
    @FXML
    private MenuButton startFloorMenu;
    @FXML
    private MenuButton endTypeMenu;
    @FXML
    private MenuButton endFloorMenu;
    @FXML
    private JFXListView<String> directionsList;
    @FXML
    private JFXComboBox<Node> startTextSearch;
    @FXML
    private JFXComboBox<Node> endTextSearch;
    @FXML
    private JFXButton btnReverse;

    //Methods start here
    public void init() {
        map = HospitalMap.getMap();
        shapes = new ArrayList<Shape>();
        paths = new ArrayList<PathViewer>();
        currentFloor = FloorNumber.FLOOR_ONE;

        mapViewer = new MapViewer(this, parent);
        mapPane = mapViewer.getMapPane();
        mapScrollPane = mapViewer.getMapScrollPane();
        mapScrollPane.setPannable(true);
        //set up floor variables
        floors = new ArrayList<FloorNumber>();

        mainAnchorPane.getChildren().add(0, mapViewer.getMapViewerPane());

        int arrowSize = 20;
        arrow = new Pane();
        arrow.setPrefSize(arrowSize, arrowSize);
        Image arrowImage = new Image("images/arrow.png");
        ImageView arrowView = new ImageView(arrowImage);
        arrowView.setFitHeight(arrowSize);
        arrowView.setFitWidth(arrowSize);
        arrow.setVisible(true);
        arrow.getChildren().add(arrowView);

        pathTransition = new PathTransition();

        //add listeners
        startTextSearch.getEditor().textProperty().addListener((obs, oldText, newText) -> searchText(startTextSearch, newText));
        endTextSearch.getEditor().textProperty().addListener((obs, oldText, newText) -> searchText(endTextSearch, newText));
    }

        private void searchText(ComboBox<Node> textSearch, String text){
        textSearch.getItems().clear();//remove all previous items
        List<Node> ans = map.getNodesByText(text);
        if(ans.size()==1){
            textSearch.setValue(ans.get(0));//set that to the answer if their is one possible value
            //endTextSearch.hide();//hide the options
        }
        else if(ans.size()>1){
            int nos =0; //to remove annoying error message
            textSearch.getItems().addAll(ans);
            textSearch.show();
        }
        else{
            //endTextSearch.hide();
        }

    }


    public void onShow(){
        startNodeChoice.setItems(FXCollections.observableArrayList(
                map.getKioskLocation()));
        //set the default start location to be the kiosk
        startNodeChoice.setValue(map.getKioskLocation());
        //remove any previous paths from the display
        clearPaths();

        startNodeChoice.setValue(map.getKioskLocation());
        startNodeChoice.setDisable(true);
        startFloorMenu.setText(map.getKioskLocation().getFloor().getDbMapping());
        startFloorMenu.setDisable(true);
        startTypeMenu.setText("Type");

        endNodeChoice.setDisable(true);
        endNodeChoice.setValue(null);
        endFloorMenu.setDisable(true);
        endFloorMenu.setText("Floor");
        endTypeMenu.setText("Type");

        directionsList.setItems(FXCollections.observableArrayList()); //function implementation


        mapViewer.resetView();
        btnReverse.setVisible(false);//hide button because there is no path
        //reset search boxes
        startTextSearch.setValue(null);
        endTextSearch.setValue(null);
        startTextSearch.hide();
        endTextSearch.hide();
    }

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    private Path getPath(){

        Node s= startTextSearch.getValue();
        Node e = endTextSearch.getValue();
        if(s==null){
            s=startNodeChoice.getValue();
        }
        if(e==null){
            endNodeChoice.getValue();
        }
        return map.findPath(s,e);
    }


    private void controlScroller(PathViewer p){
        double x = p.getCenter().get(0);
        double y = p.getCenter().get(1);
        //height
        mapViewer.centerView((int)x,(int)y);

    }

    private void SetPaths(Path path){
        clearPaths();
        Node node = null;
        FloorNumber current=null; // pointer to the current node of the floor
        Path pathToAdd = new Path();
        for(int i=0;i<path.getPath().size();i++){
            //get first floor
            node = path.getPath().get(i);
            if(node.getFloor()!=current){
                //savePaths.add(new Path());//create new path for floor
                //create a new path for floors
                current = node.getFloor();//get new floor
                floors.add(current);
                if(i==0){
                    currentFloor=current;
                }
                else{
                    paths.add(new PathViewer(pathToAdd));
                    pathToAdd = new Path();
                }
                //add to path
               pathToAdd.addToPath(node);
                //add point for current node
            }
            else{
                //add to path
                pathToAdd.addToPath(node);

            }
        }
        paths.add(new PathViewer(pathToAdd));
        //set current Path
        if(paths.size()>0){
            currentPath=paths.get(0);
            currentFloor=floors.get(0);
        }
    }

    //-----------------------ANIMATIONS START--------------------------//
    private Circle getPoint(int x, int y){
        Circle c = new AnimatedCircle();
        c.setCenterX(x);
        c.setCenterY(y);
        c.setVisible(true);
        c.setRadius(7);
        return c;
    }

    private void clearShapes(){
        for(Shape s : shapes){
            s.setVisible(false);
            mapPane.getChildren().remove(s);
        }
        pathTransition.stop();
        arrow.setVisible(false);
        mapPane.getChildren().remove(arrow);
        shapes = new ArrayList<>();
    }

    private void displayPath(PathViewer path){
        //clear path
        //display an entire path if available
        if(path.getNodes().size()>0){
            animatePath(path);
        }
    }

    private void animatePath(PathViewer path){
        //represent first and last nodes with animated circles
        Circle newp = getPoint(path.getNodes().get(0).getX(),path.getNodes().get(0).getY());
        newp.setFill(Color.RED);
        mapPane.getChildren().add(newp);
        path.addShape(newp);
        shapes.add(newp);
        //add to last node
        Circle lastp = getPoint(path.getNodes().get(path.getNodes().size()-1).getX(),path.getNodes().get(path.getNodes().size()-1).getY());
        mapPane.getChildren().add(lastp);
        path.addShape(lastp);
        shapes.add(lastp);

        if(path.getNodes().size() > 1) {
            //animation that moves the indicator
            pathTransition = new PathTransition();
            //path to follow
            javafx.scene.shape.Path p = new javafx.scene.shape.Path();
            p.setStroke(Color.NAVY);
            p.setStrokeWidth(4);
            //add shapes currentPath and shapes
            path.addShape(p);
            mapPane.getChildren().addAll(p, arrow);
            shapes.add(p);
            arrow.setVisible(true);

            //starting point defined by MoveTo
            p.getElements().add(new MoveTo(path.getNodes().get(0).getX(), path.getNodes().get(0).getY()));

            //line movements along drawn lines
            for (int i = 1; i < path.getNodes().size(); i++) {
                p.getElements().add(new LineTo(path.getNodes().get(i).getX(), path.getNodes().get(i).getY()));
            }
            //define the animation actions
            pathTransition.setDuration(Duration.millis(p.getElements().size() * 800));//make speed constant
            pathTransition.setNode(arrow);
            pathTransition.setPath(p);
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setCycleCount(Transition.INDEFINITE);
            pathTransition.play();
        }
    }

    //method to switch between paths when toggling between floors
    private void switchPath(PathViewer path){
        //mapViewer.setScale(1);//test set scale to 1
        clearShapes();
        currentFloor=path.getFloor();
        setScale(path);
        //set zoom level here

        displayPath(path);
        controlScroller(path);//reposition map

    }

    public void clearPaths(){
        for(Shape s: shapes){
            s.setVisible(false);
            mapPane.getChildren().remove(s);
        }
        arrow.setVisible(false);
        mapPane.getChildren().remove(arrow);
        //clear all lines and paths
        floors= new ArrayList<>();
        shapes=new ArrayList<>();
        paths=new ArrayList<>();
        pathTransition.stop();
        arrow.setVisible(false);
        mapPane.getChildren().removeAll(arrow);
    }
    //-----------------------ANIMATIONS END--------------------------//
    public void setScale(PathViewer path){
        double scale = path.getScale();
        System.out.println("Scale: "+scale);
        mapViewer.setScale(scale);
        slideBarZoom.setValue(scale);
    }



    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        System.out.println("Zoom In Pressed");
        slideBarZoom.setValue(slideBarZoom.getValue()+0.2);
        mapViewer.setScale(slideBarZoom.getValue());
    }

    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){
        slideBarZoom.setValue(slideBarZoom.getValue()-0.2);
        mapViewer.setScale(slideBarZoom.getValue());
    }
    //-------------------------MAP SCALE START--------------------------//
    private void displayPaths(Path thePath){
        SetPaths(thePath);
        mapViewer.setButtonsByFloor(floors);
        directionsList.setItems(FXCollections.observableList(thePath.findDirections()));
        textDirectionsPane.setVisible(true);
        textDirectionsPane.setExpanded(false);
        currentFloor=paths.get(0).getFloor();//set the current floor
        switchPath(paths.get(0));
        System.out.println("Intermediate  " + (mapScrollPane.getVvalue()));
    }

    public void enterPressed(ActionEvent e) throws InvalidNodeException {

        if((startNodeChoice.getValue() instanceof Node || startTextSearch.getValue() instanceof Node) &&
                (endNodeChoice.getValue() instanceof Node || endTextSearch.getValue() instanceof Node)) {
            btnReverse.setVisible(true);//make reverse button visible
            thePath = getPath();
            displayPaths(thePath);
            System.out.println("Enter Pressed");
        }
    }

    public void reversePressed(ActionEvent e){
        if(thePath!=null){
            displayPaths(thePath.getReverse());
        }
        System.out.println("Reverse Pressed");

    }

    public void cancelPressed(ActionEvent e) {
        System.out.println("Cancel Pressed");
        clearPaths();
        parent.setScreen(ScreenController.MainID,"RIGHT");
    }

    public void update(Observable o, Object arg){
        if(arg instanceof PathID){
            PathID ID = (PathID) arg;
            if(ID.getID() != -1) {;
                currentPath = paths.get(ID.getID());
                currentFloor = currentPath.getFloor();
                switchPath(currentPath);
            }
        }
    }
    //-----------------------NODE SELECT END--------------------------//
    public void startTypeSelected(ActionEvent e){
        startType = ((MenuItem)e.getSource()).getText();
        startTypeMenu.setText(startType);
        startFloorMenu.setDisable(false);
        if(!startFloor.equals("")){
            startChosen();
        }
    }

    public void startFloorSelected(ActionEvent e){
        startFloor = ((MenuItem)e.getSource()).getText();
        startFloorMenu.setText(startFloor);
        startChosen();
    }

    private void startChosen(){
        String filter = "";
        if(startType.equals("Restroom")){
            filter = "REST";
        }
        else if (startType.equals("Retail")){
            filter = "RETL";
        }
        else if (startType.equals("Exits")){
            filter = "EXIT";
        }
        else if (startType.equals("Stairs")){
            filter = "STAI";
        }
        else if (startType.equals("Elevators")){
            filter = "ELEV";
        }
        else if (startType.equals("Department")){
            filter = "DEPT";
        }
        else if (startType.equals("Services")){
            filter = "SERV";
        }
        else {
            filter = "INFO";
        }
        final String f = filter.toString();
        if(startFloor.equals("ALL")){
            startNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f))));
        }
        else{
            FloorNumber floor = FloorNumber.fromDbMapping(startFloor);
            startNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f) && n.getFloor().equals(floor))));
        }
        startNodeChoice.setDisable(false);
    }

    public void endTypeSelected(ActionEvent e){
        endType = ((MenuItem)e.getSource()).getText();
        endTypeMenu.setText(endType);
        endFloorMenu.setDisable(false);
        if(!endFloor.equals("")){
            endChosen();
        }
    }

    public void endFloorSelected(ActionEvent e){
        endFloor = ((MenuItem)e.getSource()).getText();
        endFloorMenu.setText(endFloor);
        endChosen();
    }

    private void endChosen(){
        String filter = "";
        if(endType.equals("Restroom")){
            filter = "REST";
        }
        else if (endType.equals("Retail")){
            filter = "RETL";
        }
        else if (endType.equals("Exits")){
            filter = "EXIT";
        }
        else if (endType.equals("Stairs")){
            filter = "STAI";
        }
        else if (endType.equals("Elevators")){
            filter = "ELEV";
        }
        else if (endType.equals("Department")){
            filter = "DEPT";
        }
        else if (endType.equals("Services")){
            filter = "SERV";
        }
        else {
            filter = "INFO";
        }
        final String f = filter.toString();
        if(endFloor.equals("ALL")){
            endNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f))));
        }
        else{
            FloorNumber floor = FloorNumber.fromDbMapping(endFloor);
            endNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f) && n.getFloor().equals(floor))));
        }
        endNodeChoice.setDisable(false);
    }
    //-----------------------NODE SELECT END--------------------------//
}
