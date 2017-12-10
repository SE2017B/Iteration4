package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ui.AnimatedCircle;

import java.util.ArrayList;

public class AnimatedHelpController implements ControllableScreen{

    ScreenController parent;

    @Override
    public void init(){

    }

    //reset everything to the start
    @Override
    public void onShow() {

    }

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton previousButton;

    @FXML
    private Label helpTextLabel;

    @FXML
    private JFXButton returnButton;

    private AnimatedCircle circle;

    @FXML
    private JFXProgressBar helpProgress;

    @FXML
    void nextPressed(ActionEvent event) {

    }

    @FXML
    void previousPressed(ActionEvent event) {

    }

    @FXML
    void returnPressed(ActionEvent event) {
        boolean test = parent.setScreen(ScreenController.LoginID, "HELP_OUT");
        System.out.println("Return Pressed " + test );
    }

}
