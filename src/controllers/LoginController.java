package controllers;

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
    }

    //Verify the username and password, then set up the admin menu for that user
    public void enterPressed(ActionEvent e){
        //Look for the user in the master list of staff members
        //todo

        //if the password matches
        if(true) {
            parent.setScreen(ScreenController.AdminMenuID);

            ((AdminMenuController) parent.getController(ScreenController.AdminMenuID))
                    .setForStaff(new Staff("jsmith", "password", "Food", "John Smith", 1234, new FoodService()));
        }
        else{
            failLabel.setVisible(true);
        }
    }

    public void cancelPressed(ActionEvent e){
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.MainID);
    }



}
