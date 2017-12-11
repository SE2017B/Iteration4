/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik, Oluchukwu Okafor
* The following code
*/

package controllers;

import QRCode.NonValidQRCodeMessageException;
import QRCode.QRCodeGenerator;
import com.jfoenix.controls.*;
import exceptions.InvalidNodeException;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.util.Duration;
import javafx.util.StringConverter;
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

import java.io.File;
import java.util.*;

public class PathController implements ControllableScreen, Observer{
    private ScreenController parent;
    private HospitalMap map;
    private String startType = "";
    private String startFloor = "";
    private String endType =  "";
    private String endFloor = "";
    private QRCodeGenerator qr;

    private final double LINE_STROKE = 4;
    private final double ARROW_SIZE = 30;

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

    //animation variables
    private ArrayList<Integer> Center;
    private boolean isAnimating;
    private int animationCount;

    //path search booleans
    private boolean isSearching;
    private boolean startSearching;
    private boolean endSearching;
    private int searchCount=0;
    //start node
    private Shape startPoint;
    private FloorNumber startPointFloor;
    //end node
    private Shape endPoint;
    private FloorNumber endPointFloor;

    Node startNode;
    Node endNode;

    @FXML
    private ChoiceBox<Node> startNodeChoice;
    @FXML
    private ChoiceBox<Node> endNodeChoice;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private TitledPane textDirectionsPane;
    @FXML
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
    private JFXTextField startTextField;
    @FXML
    private JFXTextField endTextField;
    @FXML
    private JFXListView<Node> startNodeOptionList;
    @FXML
    private JFXListView<Node> endNodeOptionList;
    @FXML
    private JFXButton btnReverse;
    @FXML
    private JFXTabPane startTabPane;
    @FXML
    private JFXTabPane endTabPane;
    @FXML
    private Tab startTextTab;
    @FXML
    private Tab endTextTab;
    @FXML
    private Tab startTypeTab;
    @FXML
    private Tab endTypeTab;
    @FXML
    private ImageView qrImageView;

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

        animationCount=0;

        arrow = new Pane();
        Image arrowImage = new Image("images/arrow.png");
        ImageView arrowView = new ImageView(arrowImage);
        arrowView.setFitHeight(ARROW_SIZE);
        arrowView.setFitWidth(ARROW_SIZE);
        arrow.setVisible(true);
        arrow.getChildren().add(arrowView);

        pathTransition = new PathTransition();

        //add listeners
        startTextField.setOnKeyPressed( e -> searchText(e, startTextField, startNodeOptionList));
        endTextField.setOnKeyPressed(e -> searchText(e, endTextField, endNodeOptionList));
        startNodeOptionList.prefWidthProperty().bind(startTextField.widthProperty());
        endNodeOptionList.prefWidthProperty().bind(endTextField.widthProperty());
        startNodeOptionList.setOnMouseClicked( e -> suggestionPressed(e, startTextField, startNodeOptionList));
        endNodeOptionList.setOnMouseClicked( e -> suggestionPressed(e, endTextField, endNodeOptionList));

        startTextTab.setOnSelectionChanged(e -> {
            startNodeOptionList.setVisible(false);
            startNode = null;
            startTextField.setText("");
        });
        endTextTab.setOnSelectionChanged(e -> {
            endNodeOptionList.setVisible(false);
            endNode = null;
            endTextField.setText("");
        });

        qr = new QRCodeGenerator();

        //position map
        Center= new ArrayList<>();
        Center.add(1500);
        Center.add(850);

        //init start and end nodes
        startPoint=getPoint(0,0);
        startPoint.setVisible(false);
        mapPane.getChildren().add(startPoint);//add to mapPane
        startPointFloor=currentFloor;

        endPoint=getPoint(0,0);
        endPoint.setVisible(false);
        mapPane.getChildren().add(endPoint);//add to mapPane
        endPointFloor=currentFloor;

        //using animation to update position
        AnimationTimer zoomPath= new AnimationTimer(){
            @Override
            public void handle(long now) {
                /**
                if(animationCount>0) {
                    mapViewer.centerView(Center.get(0), Center.get(1));
                    animationCount--;
                }
                 **/

                if(currentPath!=null){
                  if(currentPath.isAnimating){
                      ArrayList<Double> pos = currentPath.getPos();
                      mapViewer.centerView(pos.get(0), pos.get(1));
                  }

                  if(currentPath.isScaling){
                      double scale =currentPath.getAnimatedScale();
                      scaleMap(scale);
                  }

                }
                //search things
                if(searchCount>0){
                    if(searchCount==1){
                        endSearching=true;
                    }
                    searchCount--;
                }
            }
        };
        zoomPath.start();
        //getting node position on mouse click
        initSearch();
        mapPane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                double sX=event.getX();
                double sY=event.getY();
                System.out.println("X is: "+sX+" Y is: "+sY);
                if(currentFloor!=null && isSearching){
                    List<Node> v = map.getNodesInArea((int)sX,(int)sY,currentFloor);
                    if(v.size()>0){
                        //set up search up searching things
                        Node selected = v.get(0);
                        if(startSearching){
                            startTabPane.getSelectionModel().select(0);
                            startNode = v.get(0);
                            startTextField.setText(startNode.toString());
                            startSearching=false;
                            //endSearching=true;
                            searchCount=5;
                            //add shape representing
                            Circle newp = getPoint(selected.getX(),selected.getY());
                            newp.setFill(Color.RED);
                            startPoint=newp;
                            startPoint.setVisible(true);
                            mapPane.getChildren().add(startPoint);
                            startPointFloor=currentFloor;
                        }
                        if(endSearching){
                            startTabPane.getSelectionModel().select(0);
                            endNode = v.get(0);
                            endTextField.setText(endNode.toString());
                            endSearching=false;
                            isSearching=false;
                            //do the same thing
                            Circle newp = getPoint(selected.getX(),selected.getY());
                            endPoint=newp;
                            endPoint.setVisible(true);
                            mapPane.getChildren().add(endPoint);
                            endPointFloor=currentFloor;
                        }

                    }
                }
            }
        });

    }

    public void onShow(){
        mapViewer.setScale(1);
        startNodeChoice.setItems(FXCollections.observableArrayList(
                map.getKioskLocation()));
        //set the default start location to be the kiosk
        startNodeChoice.setValue(map.getKioskLocation());
        //remove any previous paths from the display
        clearPaths();

        startType = "Information";
        startTypeMenu.setText(startType);
        startFloor = map.getKioskLocation().getFloor().getDbMapping();
        startFloorMenu.setText(startFloor);
        startFloorMenu.setDisable(false); //make this false

        endNodeChoice.setDisable(true);
        endNodeChoice.setValue(null);
        endFloorMenu.setDisable(true);
        endFloorMenu.setText("Floor");
        endTypeMenu.setText("Type");

        directionsList.setItems(FXCollections.observableArrayList()); //function implementation

        mapViewer.resetView();
        btnReverse.setVisible(false);//hide button because there is no path
        startNodeOptionList.setVisible(false);
        endNodeOptionList.setVisible(false);
        //reset search boxes

        startNodeChoice.setValue(map.getKioskLocation()); //redundant
        startNodeChoice.setDisable(false);

        startNode = map.getKioskLocation();
        startTextField.setText(startNode.toString());

        textDirectionsPane.setVisible(false);
        initSearch();
        //handle emergencies
        if(map.searchNodes.size()>1){
            startNode=map.searchNodes.get(0);
            endNode=map.searchNodes.get(1);
            thePath=getPath();
            displayPaths(thePath);
            map.searchNodes=new ArrayList<>();//clear search nodes
        }
    }

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    private void initSearch(){
        //setup search variables
        isSearching=true;
        startSearching=true;
        endSearching=false;
        //move tab to search by text
        startTabPane.getSelectionModel().select(0);

    }

    private void searchText(KeyEvent keyEvent, JFXTextField textField, JFXListView<Node> listView){
            KeyCode code = keyEvent.getCode();
            if(code.equals(KeyCode.ENTER)) {
                Node node;
                if(listView.getSelectionModel().selectedItemProperty().isNull().get()){
                    node = listView.getItems().get(0);
                }
                else{
                    node = listView.getSelectionModel().getSelectedItem();
                }
                if(textField.equals(startTextField)){
                    startNode = node;
                }
                else{
                    endNode = node;
                }
                    textField.setText(node.toString());
                    listView.setVisible(false);
                }
            else if(code.equals(KeyCode.DOWN)){
                if(listView.getSelectionModel().getSelectedIndex() == -1) {
                    listView.getSelectionModel().select(0);
                }
                else if(listView.getSelectionModel().getSelectedIndex() <= listView.getItems().size()-1){
                    listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);
                }
                textField.setText(listView.getSelectionModel().getSelectedItem().toString());
            }
            else if(code.equals(KeyCode.UP)){
                if(listView.getSelectionModel().getSelectedIndex() == -1) {
                    listView.getSelectionModel().select(0);
                }
                else if(listView.getSelectionModel().getSelectedIndex() >= 0){
                    listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() - 1);
                }
                textField.setText(listView.getSelectionModel().getSelectedItem().toString());
            }
            else if(code.isLetterKey() || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                String text = ((JFXTextField) keyEvent.getSource()).getText();
                if(text.equals("")){
                    listView.setVisible(false);
                }
                else {
                    List<Node> ans = map.getNodesByText(text);
                    if (ans.size() > 10) {
                        listView.getItems().setAll(ans.subList(0, 5));
                        listView.setVisible(true);
                    }
                    if (textField.equals(startTextField)) {
                        startNode = null;
                    } else {
                        endNode = null;
                    }
                }
            }
    }

    private void suggestionPressed(MouseEvent e, JFXTextField textField, JFXListView<Node> listView) {
        Node selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Node node = listView.getSelectionModel().getSelectedItem();
            if (textField.equals(startTextField)) {
                startNode = node;
            } else {
                endNode = node;
            }
            textField.setText(selected.toString());
            listView.setVisible(false);
        }
    }

    private Path getPath(){
        Node s;
        Node e;
        if(startTabPane.getSelectionModel().getSelectedIndex()==0){
            s= startNode;
        }
        else{
            s=startNodeChoice.getValue();
        }
        if(endTabPane.getSelectionModel().getSelectedIndex()==0){
            e= endNode;
        }
        else{
            e=endNodeChoice.getValue();
        }
        return map.findPath(s,e);
    }

    private void controlScroller(PathViewer p){
        double x = p.getCenter().get(0);
        double y = p.getCenter().get(1);
        double cx =mapViewer.getCenter().get(0);
        double cy = mapViewer.getCenter().get(1);
        if(currentPath.hasAnimated){
            cx=x;
            cy=y;
        }

        //height
        Center.set(0,(int)x);
        Center.set(1,(int)y);
        p.initAnimation(cx,cy,x,y);
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
            p.setStrokeWidth(LINE_STROKE);

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
        clearShapes();
        currentFloor=path.getFloor();
        setScale(path);
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
        scale = mapViewer.checkScale(scale);
        path.initScaling(mapViewer.getScale(),scale); //animate the scaling process
    }

    public void scaleMap(double scale){
        mapViewer.setScale(scale);
        slideBarZoom.setValue(scale);
        for(Shape s : shapes){
            s.setStrokeWidth(LINE_STROKE/scale);
            if(s instanceof AnimatedCircle){
                s.setScaleX(1/scale);
                s.setScaleY(1/scale);
            }
        }
        arrow.setScaleX(1/scale);
        arrow.setScaleY(1/scale);
    }

    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        scaleMap(slideBarZoom.getValue()+0.2);
        currentPath.hasAnimated=false;
    }

    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){
        scaleMap(slideBarZoom.getValue()-0.2);
        currentPath.hasAnimated=false;
    }

    //-------------------------MAP SCALE START--------------------------//
    private void displayPaths(Path thePath){
        SetPaths(thePath);
        mapViewer.setButtonsByFloor(floors);
        ArrayList<String> directions = thePath.findDirections();
        directionsList.setItems(FXCollections.observableList(directions));
        textDirectionsPane.setVisible(true);
        textDirectionsPane.setExpanded(false);
        try {
            qr.writeQRList(directions, "src/images/qr");
            while(!qr.isComplete()){
                System.out.println("Waiting on qr");
            }
            File f = new File("/image/qr/jpg");
            qrImageView.setImage(new Image("file:src/images/qr.jpg"));
        }
        catch (NonValidQRCodeMessageException e){
            System.out.println("QR too long");
        }
        currentFloor=paths.get(0).getFloor();//set the current floor
        switchPath(paths.get(0));
    }

    public void enterPressed(ActionEvent e) throws InvalidNodeException {
        if(((startNodeChoice.getValue()!= null && startTypeTab.isSelected()) || (startNode != null && startTextTab.isSelected()) ) &&
                ((endNodeChoice.getValue() != null && endTypeTab.isSelected()) || (endNode != null && endTextTab.isSelected()) )) {
            btnReverse.setVisible(true);//make reverse button visible
            thePath = getPath();
            displayPaths(thePath);
        }
        else{
            ShakeTransition shake = new ShakeTransition();
            if(startNodeChoice.getValue() == null){
                shake.shake(startNodeChoice);
            }
            if(startNode == null){
                shake.shake(startTextField);
            }
            if(endFloorMenu.getText().equals("Floor")){
                shake.shake(endFloorMenu);
            }
            if(endTypeMenu.getText().equals("Type")){
                shake.shake(endTypeMenu);
            }
            if(endNodeChoice.getValue() == null){
                shake.shake(endNodeChoice);
            }
            if(endNode == null){
                shake.shake(endTextField);
            }
        }
    }

    public void reversePressed(ActionEvent e){
        if(thePath!=null){
            thePath = thePath.getReverse();
            displayPaths(thePath);
        }
    }

    public void cancelPressed(ActionEvent e) {
        clearPaths();
        parent.setScreen(ScreenController.MainID,"RIGHT");
    }

    public void update(Observable o, Object arg){
        if(arg instanceof PathID){
            PathID ID = (PathID) arg;
            if(ID.getID() != -1) {
                if(paths.get(ID.getID())!=currentPath){//if the path is changes
                    currentPath.hasAnimated=false; //it is probably not well positioned
                }
                currentPath = paths.get(ID.getID());
                
                currentFloor = currentPath.getFloor();
                switchPath(currentPath);
            }
            //change current floor tho.
            else{
                currentFloor=ID.getFloor();
            }
            //adjust start nodes
            if(startPointFloor==currentFloor){
                System.out.println("Setting start and end points");
                startPoint.setVisible(true);
            }
            else{
                startPoint.setVisible(false);
            }
            //adjust end nodes
            if(endPointFloor==currentFloor){
                endPoint.setVisible(true);
            }
            else{
                endPoint.setVisible(false);
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
