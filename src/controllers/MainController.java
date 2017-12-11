/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXSlider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import map.Path;
import ui.AnimatedCircle;
import ui.MapViewer;
import ui.PathID;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainController implements ControllableScreen, Observer{
    private ScreenController parent;
    private MapViewer mapViewer;
    private AnimatedCircle kioskIndicator;
    private FloorNumber curerntFloor;
    private HospitalMap map;
    private ArrayList<Circle> indicators;
    private Pane mapPane;

    @FXML
    private JFXSlider slideBarZoom;

    @FXML
    private HBox dropDownBox;

    private JFXButton filterHeaderButton;

    private JFXButton nearestHeaderButton;

    private JFXButton bathFilterButton;

    private JFXButton exitFilterButton;

    private JFXButton elevatorFilterButton;

    private JFXButton retailFilterButton;

    private JFXButton stairsFilterButton;

    private JFXButton bathNearestButton;

    private JFXButton exitNearestButton;

    private JFXButton elevatorNearestButton;

    private JFXButton retailNearestButton;

    private JFXButton stairsNearestButton;

    private JFXNodesList filterList;

    private JFXNodesList nearestList;

    @FXML
    private AnchorPane mainAnchorPane;

    public void init() {
        curerntFloor = FloorNumber.FLOOR_ONE;
        map = HospitalMap.getMap();
        mapViewer = new MapViewer(this, parent);
        mapViewer.setFloor(curerntFloor);
        mapPane = mapViewer.getMapPane();
        indicators = new ArrayList<>();
        kioskIndicator = new AnimatedCircle();
        kioskIndicator.setCenterX(map.getKioskLocation().getX()*mapViewer.getScale());
        kioskIndicator.setCenterY(map.getKioskLocation().getY()*mapViewer.getScale());
        kioskIndicator.setVisible(false);
        kioskIndicator.setFill(Color.rgb(0,84,153));
        kioskIndicator.setStroke(Color.rgb(40,40,60));
        kioskIndicator.setStrokeWidth(3);
        initButtons();

        mapPane.getChildren().add(kioskIndicator);
        mainAnchorPane.getChildren().add(0,mapViewer.getMapViewerPane());
        mapViewer.getMapScrollPane().setPannable(true);

        mapViewer.centerView((int)kioskIndicator.getCenterX(), (int)kioskIndicator.getCenterY());

        mainAnchorPane.prefWidthProperty().bind(parent.prefWidthProperty());
        mainAnchorPane.prefHeightProperty().bind(parent.prefHeightProperty());
    }

    private void initButtons(){
        filterList = new JFXNodesList();
        nearestList = new JFXNodesList();
        ArrayList<JFXButton> buttons = new ArrayList<>();
        bathFilterButton = new JFXButton();
        exitFilterButton = new JFXButton();
        elevatorFilterButton = new JFXButton();
        retailFilterButton = new JFXButton();
        stairsFilterButton = new JFXButton();
        bathNearestButton = new JFXButton();
        exitNearestButton = new JFXButton();
        elevatorNearestButton = new JFXButton();
        retailNearestButton = new JFXButton();
        stairsNearestButton = new JFXButton();
        filterHeaderButton = new JFXButton("Filter");
        nearestHeaderButton = new JFXButton("Nearest");

        filterHeaderButton.setMinSize(150,50);
        nearestHeaderButton.setMinSize(150,50);
        filterHeaderButton.setMaxWidth(2000);
        nearestHeaderButton.setMaxWidth(2000);

        buttons.add(bathFilterButton);
        buttons.add(exitFilterButton);
        buttons.add(elevatorFilterButton);
        buttons.add(retailFilterButton);
        buttons.add(stairsFilterButton);
        buttons.add(bathNearestButton);
        buttons.add(exitNearestButton);
        buttons.add(elevatorNearestButton);
        buttons.add(retailNearestButton);
        buttons.add(stairsNearestButton);
        filterList.addAnimatedNode(filterHeaderButton);
        nearestList.addAnimatedNode(nearestHeaderButton);
        for(int i = 0; i< 10; i++){
            JFXButton b  = buttons.get(i);
            b.setPrefSize(150,50);
            if(i < 5){
                b.setOnAction(e -> filterButtonPressed(e));
                filterList.addAnimatedNode(b);
            }
            else{
                b.setOnAction(e -> nearestPressed(e));
                nearestList.addAnimatedNode(b);
            }

        }
        filterList.setSpacing(20);
        nearestList.setSpacing(20);

        mainAnchorPane.getChildren().addAll(filterList,nearestList);
        AnchorPane.setLeftAnchor(filterList,200.0);
        AnchorPane.setRightAnchor(nearestList, 200.0);
        filterHeaderButton.prefWidthProperty().bind(mainAnchorPane.widthProperty().divide(2).subtract(230));
        nearestHeaderButton.prefWidthProperty().bind(mainAnchorPane.widthProperty().divide(2).subtract(230));

        System.out.println(mainAnchorPane.getWidth());
        System.out.println(mainAnchorPane.getBoundsInLocal().getWidth());
        System.out.println(mainAnchorPane.getBoundsInParent().getWidth());



        bathFilterButton.setText("Restroom");
        exitFilterButton.setText("Exit");
        elevatorFilterButton.setText("Elevator");
        retailFilterButton.setText("Retail");
        stairsFilterButton.setText("Stairs");
        bathNearestButton.setText("Restroom");
        exitNearestButton.setText("Exit");
        elevatorNearestButton.setText("Elevator");
        retailNearestButton.setText("Retail");
        stairsNearestButton.setText("Stairs");

    }

    public void onShow(){

        kioskIndicator.setCenterX(map.getKioskLocation().getX());
        kioskIndicator.setCenterY(map.getKioskLocation().getY());
        setFloor(curerntFloor);
        mapViewer.centerView((int)kioskIndicator.getCenterX(), (int)kioskIndicator.getCenterY());
        setZoom(0.8);
    }

    //Setters
    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    private void setFloor(FloorNumber floor){
        curerntFloor = floor;
        clearPressed(new ActionEvent());
        if (curerntFloor.equals(map.getKioskLocation().getFloor())){
            kioskIndicator.setVisible(true);
        }
        else{
            kioskIndicator.setVisible(false);
        }
    }

    //circle helper function for nodeTypePressed
    public Circle makeCircle(Node node){
        AnimatedCircle newIndicator = new AnimatedCircle();
        newIndicator.setCenterX(node.getX());
        newIndicator.setCenterY(node.getY());
        newIndicator.setVisible(true);
        newIndicator.setFill(Color.rgb(153, 63, 62)); //not sure what color this should be
        newIndicator.setStroke(Color.rgb(60, 26, 26));
        newIndicator.setStrokeWidth(3);
        indicators.add(newIndicator);
        newIndicator.getTimeline().stop();
        newIndicator.getTimeline().setCycleCount(5);
        newIndicator.getTimeline().setOnFinished(e -> {
            newIndicator.setVisible(false);
            mapPane.getChildren().remove(newIndicator);
            indicators.remove(newIndicator);
        });

        newIndicator.getTimeline().play();

        return newIndicator;
    }

    public Circle makeClearCircle(Node node){
        Circle newIndicator = makeCircle(node);
        newIndicator.setFill(Color.TRANSPARENT);
        return newIndicator;
    }

    public void update(Observable o, Object arg) {
        if(arg instanceof PathID){
            setFloor(((PathID) arg).getFloor());
        }
    }

    //-----------------------HANDLE ACTIONS START--------------------------//
    public void clearMousePressed(MouseEvent e){
        clearPressed(new ActionEvent());
    }

    //adjusts map zoom through slider
    public void sliderChanged(MouseEvent e){
        mapViewer.setScale(slideBarZoom.getValue());
    }

    ////////////////////////////////////////////////////////////
    /////////////           Find Nearest
    ////////////////////////////////////////////////////////////
    public void clearPressed(ActionEvent e){
        int size = mapPane.getChildren().size();
        mapPane.getChildren().remove(0, size);
        mapPane.getChildren().addAll(mapViewer.getMapImage(), kioskIndicator);
        indicators.clear();
    }

    public void bathTypePressed(ActionEvent e){
        //switch to kiosk floor
        mapViewer.setFloor(map.getKioskLocation().getFloor());
        kioskIndicator.setVisible(true);
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "REST");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //if nearest node is on same floor as kiosk, make a circle
        if(node.getFloor() == map.getKioskLocation().getFloor()){
            Circle c = makeCircle(node);
            mapPane.getChildren().add(c);
        }
    }

    public void exitTypePressed(ActionEvent e){
        //switch to kiosk floor
        mapViewer.setFloor(map.getKioskLocation().getFloor());
        kioskIndicator.setVisible(true);
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "EXIT");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //if nearest node is on same floor as kiosk, make a circle
        if(node.getFloor() == map.getKioskLocation().getFloor()){
            Circle c = makeCircle(node);
            mapPane.getChildren().add(c);
        }
    }

    public void elevTypePressed(ActionEvent e){
        //switch to kiosk floor
        mapViewer.setFloor(map.getKioskLocation().getFloor());
        kioskIndicator.setVisible(true);
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "ELEV");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //if nearest node is on same floor as kiosk, make a circle
        if(node.getFloor() == map.getKioskLocation().getFloor()){
            Circle c = makeCircle(node);
            mapPane.getChildren().add(c);
        }
    }

    public void retailTypePressed(ActionEvent e){
        //switch to kiosk floor
        mapViewer.setFloor(map.getKioskLocation().getFloor());
        kioskIndicator.setVisible(true);
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "RETL");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //if nearest node is on same floor as kiosk, make a circle
        if(node.getFloor() == map.getKioskLocation().getFloor()){
            Circle c = makeCircle(node);
            mapPane.getChildren().add(c);
        }
    }

    public void stairsTypePressed(ActionEvent e){
        //switch to kiosk floor
        mapViewer.setFloor(map.getKioskLocation().getFloor());
        kioskIndicator.setVisible(true);
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "STAI");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //if nearest node is on same floor as kiosk, make a circle
        if(node.getFloor() == map.getKioskLocation().getFloor()){
            Circle c = makeCircle(node);
            mapPane.getChildren().add(c);
        }
    }

    public void nearestPressed(ActionEvent e){
        //switch to kiosk floor
        mapViewer.setFloor(map.getKioskLocation().getFloor());
        kioskIndicator.setVisible(true);
        //find nearest node of given type
        Path path;
        if(e.getSource().equals(bathNearestButton)){
            path = map.findNearest(map.getKioskLocation(), "REST");
        }
        else if(e.getSource().equals(elevatorNearestButton)){
            path = map.findNearest(map.getKioskLocation(), "ELEV");
        }
        else if(e.getSource().equals(exitNearestButton)){
            path = map.findNearest(map.getKioskLocation(), "EXIT");
        }
        else if(e.getSource().equals(retailNearestButton)){
            path = map.findNearest(map.getKioskLocation(), "RETL");
        }
        else {
            path = map.findNearest(map.getKioskLocation(), "STAI");
        }

        System.out.println("Nearest Pressed");

        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //if nearest node is on same floor as kiosk, make a circle
        if(node.getFloor() == map.getKioskLocation().getFloor()){
            Circle c = makeCircle(node);
            mapPane.getChildren().add(c);
        }

    }



    ////////////////////////////////////////////////////////////
    /////////////           Filter
    ////////////////////////////////////////////////////////////
    public void filterButtonPressed(ActionEvent e) {
        clearPressed(new ActionEvent());
        System.out.println("Filter Pressed");
        JFXButton pressed = (JFXButton) e.getSource();
        ArrayList<Node> filteredNodes = new ArrayList<Node>();
        if (pressed.equals(bathFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(curerntFloor) && n.getType().equals("REST")));
        } else if (pressed.equals(exitFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(curerntFloor) && n.getType().equals("EXIT")));
        } else if (pressed.equals(elevatorFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(curerntFloor) && n.getType().equals("ELEV")));
        } else if (pressed.equals(retailFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(curerntFloor) && n.getType().equals("RETL")));
        } else if (pressed.equals(stairsFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(curerntFloor) && n.getType().equals("STAI")));
        }
        for (Node n: filteredNodes)
        {
            Circle c = makeClearCircle(n);
            mapPane.getChildren().add(c);
        }
    }

    //when login button is pressed go to login screen
    public void loginPressed(ActionEvent e){
        parent.setScreen(ScreenController.LoginID,"RIGHT");
    }

    //when direction button is pressed go to directions screen
    public void directionPressed(ActionEvent e){
        parent.setScreen(ScreenController.PathID,"LEFT");
    }

    public void questionPressed(ActionEvent e) {
        parent.setScreen(ScreenController.HelpID, "HELP_IN");
    }

    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        setZoom(slideBarZoom.getValue()+0.2);
    }

    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){
        setZoom(slideBarZoom.getValue()-0.2);
    }
    //-----------------------HANDLE ACTIONS END----------------------------//

    public void setZoom(double zoom){
        slideBarZoom.setValue(zoom);
        mapViewer.setScale(zoom);
    }
}