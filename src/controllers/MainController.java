/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import map.Node;



public class MainController implements ControllableScreen{
    private ScreenController parent;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }



    public void init(){
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
    public void filterPressed(ActionEvent e){
        System.out.println("Filter Pressed");
        parent.setScreen(ScreenController.FilterID);
    }
    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        System.out.println("Zoom In Pressed");


    }

    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){



    }

    //adjusts map zoom through slider
    public void sliderChanged(MouseEvent e){

    }

    //map scale set up
    public void setMapScale(double scale){

    }



}
