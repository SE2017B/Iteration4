package controllers;

import DepartmentSubsystem.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ui.ShakeTransition;
import ui.hospitalBackground;



public class LoginController implements ControllableScreen{
    public LoginController(){}
    ScreenController parent;
    private DepartmentSubsystem depSub;

    ShakeTransition s = new ShakeTransition();

    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private AnchorPane mainAnchorPane;



    @Override
    public void init() {
        depSub = DepartmentSubsystem.getSubsystem();

        //Add the pretty hospital picture to the background
        //it takes care of all the resizing
        hospitalBackground hospitalImage = new hospitalBackground(parent);
        mainAnchorPane.getChildren().add(0,hospitalImage);
        AnchorPane.setBottomAnchor(hospitalImage,0.0);
    }

    @Override
    public void onShow() {
        usernameField.setText("");
        passwordField.setText("");
    }

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }

    public void returnPressed(ActionEvent e){
        parent.setScreen(ScreenController.MainID,"LEFT");
    }

    public void enterPressed(ActionEvent e){
        String login = usernameField.getText();
        String passWord = passwordField.getText();
        if(login.equals("") || passWord.equals("")){
            if(login.equals("")){
                s.shake(usernameField);
            }
            if(passWord.equals("")){
                s.shake(passwordField);
            }
            return;
        }
        if((depSub.login(login, passWord)))
        {
            parent.setScreen(ScreenController.RequestID,"UP");
        }
        else{
            System.out.println("Wrong Pass/Login");
        }
    }
}
