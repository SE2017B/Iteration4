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
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import map.Path;
import ui.AnimatedCircle;
import ui.MapViewer;

import java.util.Observable;
import java.util.Observer;


public class MainController implements ControllableScreen, Observer{
    private ScreenController parent;


    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    @FXML
    private Pane mapPane;

    @FXML
    private JFXSlider slideBarZoom;


    private MapViewer mapButtons;

    private AnimatedCircle kioskIndicator;

    private FloorNumber curerntFloor;

    @FXML
    private AnchorPane buttonHolderPane;

    private HospitalMap map;

    @FXML
    private JFXButton btnbath;

    @FXML
    private JFXButton btnexit;

    @FXML
    private JFXButton btnelev;

    @FXML
    private JFXButton btnvend;

    @FXML
    private JFXButton btnstairs;

    public void init() {
        curerntFloor = FloorNumber.FLOOR_ONE;
        map = HospitalMap.getMap();
        mapButtons = new MapViewer(this);
        mapButtons.setFloor(curerntFloor);
        kioskIndicator = new AnimatedCircle();
        kioskIndicator.setCenterX(map.getKioskLocation().getX()/mapButtons.getScale());
        kioskIndicator.setCenterY(map.getKioskLocation().getY()/mapButtons.getScale());
        kioskIndicator.setVisible(false);
        kioskIndicator.setFill(Color.rgb(0,84,153));
        kioskIndicator.setStroke(Color.rgb(40,40,60));
        kioskIndicator.setStrokeWidth(3);
        System.out.println("Kiosk Location: " + kioskIndicator.getCenterX() + " " +  kioskIndicator.getCenterY());
        buttonHolderPane.getChildren().add(mapButtons.getPane());
        mapPane.getChildren().addAll(mapButtons.getMapImage(),kioskIndicator);

    }

    //circle helper function for nodeTypePressed
    public void makeCircle(Node node){
        //make a new AnimatedCircle + initialize it
        AnimatedCircle newIndicator = new AnimatedCircle();
        newIndicator.setCenterX(node.getX()/mapButtons.getScale());
        newIndicator.setCenterY(node.getY()/mapButtons.getScale());
        newIndicator.setVisible(true);
        newIndicator.setFill(Color.rgb(153, 63, 62)); //not sure what color this should be
        newIndicator.setStroke(Color.rgb(60, 26, 26));
        newIndicator.setStrokeWidth(3);
    }

    //what if two buttons are pressed? Or if a button is pressed twice?
    //nodeTypePressed
    public void nodeTypePressed(ActionEvent e, String type){
        System.out.println("Button Pressed: " + type);
        switch (type){
            case "Bathroom":
                bathTypePressed(e);
                break;
            case "Exit":
                exitTypePressed(e);
                break;
            case "Elevator":
                elevTypePressed(e);
                break;
            case "Vending Machine":
                vendTypePressed(e);
                break;
            case "Stairs":
                stairsTypePressed(e);
        }
    }

    //bathroom type
    public void bathTypePressed(ActionEvent e){
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "Bathroom");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //make a new AnimatedCircle + initialize it
        makeCircle(node);
    }

    //Exit type
    public void exitTypePressed(ActionEvent e){
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "Exit");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //make a new AnimatedCircle + initialize it
        makeCircle(node);
    }

    //Elevator type
    public void elevTypePressed(ActionEvent e){
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "Elevator");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //make a new AnimatedCircle + initialize it
        makeCircle(node);
    }

    //Vending Machine type
    public void vendTypePressed(ActionEvent e){
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "Vending Machine");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //make a new AnimatedCircle + initialize it
        makeCircle(node);
    }

    //stairs type
    public void stairsTypePressed(ActionEvent e){
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "Stairs");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //make a new AnimatedCircle + initialize it
        makeCircle(node);
    }

    public void onShow(){
        kioskIndicator.setCenterX(map.getKioskLocation().getX()/mapButtons.getScale());
        kioskIndicator.setCenterY(map.getKioskLocation().getY()/mapButtons.getScale());
        setFloor(curerntFloor);
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
    //when filter button is pressed go to filter screen


    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        slideBarZoom.setValue(slideBarZoom.getValue()+0.2);
        setZoom(slideBarZoom.getValue());

    }

    //Pass in a value from 0-3. 0 is smallest, 3 is largest
    public void setZoom(double zoom){
        mapButtons.setScale(4-zoom);
        kioskIndicator.setCenterX(map.getKioskLocation().getX()/mapButtons.getScale());
        kioskIndicator.setCenterY(map.getKioskLocation().getY()/mapButtons.getScale());
    }
    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){
        slideBarZoom.setValue(slideBarZoom.getValue()-0.2);
        setZoom(slideBarZoom.getValue());
    }

    //adjusts map zoom through slider
    public void sliderChanged(MouseEvent e){
        mapButtons.setScale(4-slideBarZoom.getValue());
    }

    private void setFloor(FloorNumber floor){
        curerntFloor = floor;
        if (curerntFloor.equals(map.getKioskLocation().getFloor())){
            kioskIndicator.setVisible(true);
        }
        else{
            kioskIndicator.setVisible(false);
        }
    }

    public void update(Observable o, Object arg) {
        if(arg instanceof FloorNumber){
            setFloor((FloorNumber) arg);
        }

    }
}
