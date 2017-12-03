/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import map.Path;
import ui.AnimatedCircle;
import ui.MapViewer;
import ui.Mouse;
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
    private JFXButton bathFilterButton;
    @FXML
    private JFXButton exitFilterButton;
    @FXML
    private JFXButton elevatorFilterButton;
    @FXML
    private JFXButton retailFilterButton;
    @FXML
    private JFXButton stairsFilterButton;

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
        kioskIndicator.setCenterX(map.getKioskLocation().getX()/mapViewer.getScale());
        kioskIndicator.setCenterY(map.getKioskLocation().getY()/mapViewer.getScale());
        kioskIndicator.setVisible(false);
        kioskIndicator.setFill(Color.rgb(0,84,153));
        kioskIndicator.setStroke(Color.rgb(40,40,60));
        kioskIndicator.setStrokeWidth(3);
        System.out.println("Kiosk Location: " + kioskIndicator.getCenterX() + " " +  kioskIndicator.getCenterY());
        mapPane.getChildren().add(kioskIndicator);
        mainAnchorPane.getChildren().add(0,mapViewer.getMapViewerPane());
    }

    public void onShow(){
        kioskIndicator.setCenterX(map.getKioskLocation().getX()/mapViewer.getScale());
        kioskIndicator.setCenterY(map.getKioskLocation().getY()/mapViewer.getScale());
        setFloor(curerntFloor);

        System.out.println(mapViewer.getMapViewerPane().getChildren().toString());
        System.out.println(mainAnchorPane.getChildren().toString());
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
        newIndicator.setCenterX(node.getX()/mapViewer.getScale());
        newIndicator.setCenterY(node.getY()/mapViewer.getScale());
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
        mapViewer.setScale(4-slideBarZoom.getValue());
    }

    Mouse mouse = new Mouse();

    public void handlePanMap(MouseEvent e){
    //probably use the mapScrollPane
        //double sceneX, sceneY;
        //double translateX, translateY;
        mapPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouse.setX(event.getX());
                mouse.setY(event.getY());

            }
        });
        mapPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mapPane.getScene().getWindow().setX(event.getScreenX() - mouse.getX());
                mapPane.getScene().getWindow().setX(event.getScreenY() - mouse.getY());
            }
        });
    }

    //-------------------findNearest Button Actions Start-----------------//
    //clear button
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
            //make a new AnimatedCircle + initialize it
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
            //make a new AnimatedCircle + initialize it
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
            //make a new AnimatedCircle + initialize it
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
            //make a new AnimatedCircle + initialize it
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
            //make a new AnimatedCircle + initialize it
            Circle c = makeCircle(node);
            mapPane.getChildren().add(c);
        }
    }
    //-------------------findNearest Button Actions End-----------------//

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
        System.out.println("Login Pressed");
        parent.setScreen(ScreenController.LoginID,"RIGHT");
    }

    //when direction button is pressed go to directions screen
    public void directionPressed(ActionEvent e){
        System.out.println("Direction Pressed");
        parent.setScreen(ScreenController.PathID,"LEFT");
    }

    //when search button is pressed go to search screen
    public void searchPressed(ActionEvent e){
        System.out.println("Search Pressed");
    }

    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        slideBarZoom.setValue(slideBarZoom.getValue()+0.2);
        setZoom(slideBarZoom.getValue());
    }

    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){
        slideBarZoom.setValue(slideBarZoom.getValue()-0.2);
        setZoom(slideBarZoom.getValue());
    }
    //-----------------------HANDLE ACTIONS END----------------------------//

    //Pass in a value from 0-3. 0 is smallest, 3 is largest
    public void setZoom(double zoom){
        double oldScale = mapViewer.getScale();
        mapViewer.setScale(4-zoom);
        kioskIndicator.setCenterX(map.getKioskLocation().getX()/mapViewer.getScale());
        kioskIndicator.setCenterY(map.getKioskLocation().getY()/mapViewer.getScale());
        for(Circle c : indicators){
            c.setCenterX(c.getCenterX()*oldScale/mapViewer.getScale());
            c.setCenterY(c.getCenterY()*oldScale/mapViewer.getScale());
        }
    }
}
