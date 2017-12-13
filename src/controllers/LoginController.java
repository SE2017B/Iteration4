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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ui.ShakeTransition;
import ui.hospitalBackground;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.Toolkit;
import java.awt.KeyEventDispatcher;

import static java.awt.event.KeyEvent.VK_CAPS_LOCK;
import static javafx.scene.input.KeyEvent.*;


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
    private Label errorLbl;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private ImageView capsLock;
    @FXML
    private GridPane screen;



    @Override
    public void init() {
        depSub = DepartmentSubsystem.getSubsystem();

        //Add the pretty hospital picture to the background
        //it takes care of all the resizing
        hospitalBackground hospitalImage = new hospitalBackground(parent);
        mainAnchorPane.getChildren().add(0,hospitalImage);
        AnchorPane.setBottomAnchor(hospitalImage,0.0);

        capsLock.setOpacity(0.0);

        screen.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(Toolkit.getDefaultToolkit().getLockingKeyState(VK_CAPS_LOCK)){
                    capsLock.setOpacity(1.0);
                }
                else{
                    capsLock.setOpacity(0.0);
                }
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
        Toolkit.getDefaultToolkit().getLockingKeyState(VK_CAPS_LOCK);
    }

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }

    public void returnPressed(ActionEvent e){
        parent.setScreen(ScreenController.MainID,"LEFT");
    }


    public void enterPressedkey(KeyEvent e){
        if(e.getCode().toString().equals("ENTER")) {
            enterPressed(new ActionEvent());
        }
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
            errorLbl.setText("Login is Incorrect");
            s.shake(usernameField);
        }
        catch (PasswordException ex){
            errorLbl.setText("Password is Incorrect");
            s.shake(passwordField);
        }


    }
}
