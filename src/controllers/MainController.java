package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

public class MainController implements ControllableScreen{
    private ScreenController parent;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }
    @FXML
    private Button btnlogin;

    @FXML
    private Button btnzout;

    @FXML
    private Slider slideBarZoom;

    @FXML
    private Button btnzin;

    @FXML
    private Button btndirection;

    @FXML
    private Button btnsearch;

    @FXML
    private Button btnfilter;

    public void init(){}

    public void onShow(){}

    public void loginPressed(ActionEvent e){
        System.out.println("Login Pressed");
        parent.setScreen(ScreenController.LoginID);
    }
    public void directionPressed(ActionEvent e){
        System.out.println("Direction Pressed");
        parent.setScreen(ScreenController.PathID);
    }
    public void searchPressed(ActionEvent e){
        System.out.println("Search Pressed");

    }
    public void filterPressed(ActionEvent e){
        System.out.println("Filter Pressed");
        parent.setScreen(ScreenController.FilterID);
    }
    public void zinPressed(ActionEvent e){
        System.out.println("Zoom In Pressed");
 
    }
    public void zoutPressed(ActionEvent e){
        System.out.println("Zoom Out Pressed");

    }

}
