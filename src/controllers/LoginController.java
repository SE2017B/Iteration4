/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import exceptions.InvalidLoginException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.FoodService;
import service.Staff;

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
    private Label failLabel;

    @FXML
    private TextField txtfldLogin;

    @FXML
    private PasswordField passwordFieldPassword;

    public void init(){}

    public void onShow(){

        failLabel.setVisible(false);
        //txtfldLogin.setText("");
        txtfldLogin.setText("test");   //Auto-fill for testing todo remove before demo
        passwordFieldPassword.setText("");
    }

    //Verify the username and password, then set up the admin menu for that user
    public void enterPressed(ActionEvent e){
        //Look for the user in the master list of staff members
        try{
            if(parent.getEngine().login(txtfldLogin.getText(),passwordFieldPassword.getText())){
                parent.setScreen(ScreenController.AdminMenuID);
                ((AdminMenuController) parent.getController(ScreenController.AdminMenuID))
                        .setForStaff(parent.getEngine().getStaff(txtfldLogin.getText()));
            }
            else
                failLabel.setVisible(true);
        }
        catch (InvalidLoginException exc){
            failLabel.setVisible(true);
        }
    }

    //returns to the main screen when cancel is pressed
    public void cancelPressed(ActionEvent e){
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.MainID);
    }



}
