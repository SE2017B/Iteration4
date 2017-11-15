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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements ControllableScreen{
    private ScreenController parent;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }
    @FXML
    private Button btnenter;

    @FXML
    private Button btncancel;

    @FXML
    private TextField txtfldLogin;

    @FXML
    private PasswordField passwordFieldPassword;

    public void enterPressed(ActionEvent e){
        System.out.println("Enter Pressed");
        parent.setScreen(ScreenController.AdminMenuID);
    }

    public void cancelPressed(ActionEvent e){
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.MainID);
    }



}
