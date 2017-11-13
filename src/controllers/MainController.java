package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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

    @FXML
    private Pane mapPane;

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
        slideBarZoom.adjustValue(slideBarZoom.getValue()+0.1);
        setMapScale(slideBarZoom.getValue());
 
    }
    public void zoutPressed(ActionEvent e){

        slideBarZoom.adjustValue(slideBarZoom.getValue()-0.1);
        setMapScale(slideBarZoom.getValue());

    }

    public void sliderChanged(MouseEvent e){
        System.out.println("Slider Moved");
        setMapScale(slideBarZoom.getValue());
    }

    public void setMapScale(double scale){
        mapPane.setScaleX(scale);
        mapPane.setScaleY(scale);
    }



}
