/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXSlider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import map.FloorNumber;
import map.HospitalMap;
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
