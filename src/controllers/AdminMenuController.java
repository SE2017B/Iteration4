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

public class AdminMenuController implements ControllableScreen{
    private ScreenController parent;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }
    @FXML
    private Button btnService;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnCancel;

    public void servicePressed(ActionEvent e){
        System.out.println("Service Pressed");
        parent.setScreen(ScreenController.RequestID);
    }

    public void editPressed(ActionEvent e){
        System.out.println("Edit Pressed");
        parent.setScreen(ScreenController.AddNodeID);
    }

    public void cancelPressed(ActionEvent e){
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.MainID);
    }

}
