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
import javafx.scene.control.CheckBox;

public class FilterController implements ControllableScreen{
    private ScreenController parent;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    @FXML
    private CheckBox checkBoxBathrooms;

    @FXML
    private CheckBox checkBoxExits;

    @FXML
    private Button btncancel;

    public void cancelPressed(ActionEvent e)
    {
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.MainID);
    }
}
