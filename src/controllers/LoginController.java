package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController implements ControllableScreen{
    public LoginController(){}
    ScreenController parent;

    @FXML
    private JFXTextField usernameField;


    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXButton enterButton;

    @FXML
    private JFXButton returnButton;

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }

    @Override
    public void init() {

    }

    @Override
    public void onShow() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void returnPressed(ActionEvent e){
        System.out.println("Return Pressed");
        parent.setScreen(ScreenController.MainID);

    }

    public void enterPressed(ActionEvent e){
        System.out.println("Enter Pressed");
        parent.setScreen(ScreenController.RequestID);
    }
}
