package controllers;

import DepartmentSubsystem.*;
import DepartmentSubsystem.Exceptions.PasswordException;
import DepartmentSubsystem.Exceptions.UsernameException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import ui.ShakeTransition;

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

    @Override
    public void init() {
        depSub = DepartmentSubsystem.getSubsystem();
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

    public void enterPressed(ActionEvent e) throws UsernameException, PasswordException {
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
        try{
            Staff person = depSub.login(login, passWord);
            parent.setScreen(ScreenController.RequestID,"UP");
        }
        catch (UsernameException ex){

        }
        catch (PasswordException ex){

        }


    }
}
