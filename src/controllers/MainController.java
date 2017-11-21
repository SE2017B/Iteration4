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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import map.FloorNumber;
import map.Node;
import DepartmentSubsystem.Staff;


public class MainController implements ControllableScreen{
    private ScreenController parent;


    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    @FXML
    private Pane mapPane;

    @FXML
    private ScrollPane floorScrollPane;

    @FXML
    private JFXSlider slideBarZoom;

    private proxyImagePane mapImage;

    public void init() {
        mapImage = new proxyImagePane();
        mapImage.setImage(FloorNumber.FLOOR_G);
        mapPane.getChildren().add(mapImage);

    }

    public void onShow(){

    }

    //when login button is pressed go to login screen
    public void loginPressed(ActionEvent e){
        System.out.println("Login Pressed");
        parent.setScreen(ScreenController.LoginID);
    }
    //when direction button is pressed go to directions screen
    public void directionPressed(ActionEvent e){
        System.out.println("Direction Pressed");
        parent.setScreen(ScreenController.PathID);
    }
    //when search button is pressed go to search screen
    public void searchPressed(ActionEvent e){
        System.out.println("Search Pressed");
    }
    //when filter button is pressed go to filter screen


    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        System.out.println("Zoom In Pressed");
        slideBarZoom.setValue(slideBarZoom.getValue()+0.2);
        mapImage.setScale(4-slideBarZoom.getValue());

    }

    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){
        slideBarZoom.setValue(slideBarZoom.getValue()-0.2);
        mapImage.setScale(4-slideBarZoom.getValue());
    }

    //adjusts map zoom through slider
    public void sliderChanged(MouseEvent e){
        mapImage.setScale(4-slideBarZoom.getValue());
    }


    public void floorButtonPressed(ActionEvent e){
       String floor =  ((JFXButton)e.getSource()).getText();
       System.out.println(((JFXButton) e.getSource()).getLayoutX());
       floorScrollPane.setHvalue((((JFXButton) e.getSource()).getLayoutX()-415)/1000);
       System.out.println("Floor Pressed: " + floor);
       mapImage.setImage(FloorNumber.fromDbMapping(floor));
    }




}
