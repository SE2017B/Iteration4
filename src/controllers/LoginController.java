package controllers;

import DepartmentSubsystem.*;
import DepartmentSubsystem.Exceptions.PasswordException;
import DepartmentSubsystem.Exceptions.UsernameException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

        passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    enterPressed(new ActionEvent());
                }
            }
        });
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

    public void enterPressed(ActionEvent e) {
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
            s.shake(usernameField);
        }
        catch (PasswordException ex){
            s.shake(passwordField);
        }
    }
}
