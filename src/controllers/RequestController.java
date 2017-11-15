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
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;

public class RequestController implements ControllableScreen{
    private ScreenController parent;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    @FXML
    private Button btncreate;

    @FXML
    private Button btncancel;

    @FXML
    private MenuButton serviceDropDown;

    @FXML
    private MenuButton staffDropDown;

    @FXML
    private MenuButton timeDropDown;

    @FXML
    private DatePicker dateMenu;

    public void createPressed(ActionEvent e){
        System.out.println("Create Pressed");
        parent.setScreen(ScreenController.AdminMenuID);
    }
    public void cancelPressed(ActionEvent e){
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.AdminMenuID);
    }

    public void serviceSelected(ActionEvent e){
        System.out.println("Service Selected");
    }

    public void staffSelected(ActionEvent e){
        System.out.println("Staff Selected");
    }

    public void timeSelected(ActionEvent e){
        System.out.println("Time Selected");
    }

    public void dateSelected(ActionEvent e){
        System.out.println("Date Selected");
    }
}