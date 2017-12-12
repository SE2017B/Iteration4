/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration3
* Original author(s): Travis Norris, Erika Snow, Nick Fajardo, Leo Grande
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.skins.JFXSliderSkin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class FeedbackController implements ControllableScreen{
    public FeedbackController(){
    }

    private ScreenController parent;

    @FXML
    private JFXSlider starSlider;
    @FXML
    private JFXTextArea HelpDescription;
    @FXML
    private Label HelpTitle;
    @FXML
    private ImageView ImageViewer;
    @FXML
    private JFXButton Next;
    @FXML
    private JFXButton Previous;

    @Override
    public void init() {
        starSlider.setValue(0.0);
    }

    @Override
    public void onShow() {
        starSlider.setValue(0.0);
    }

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }


    public void returnPressed(ActionEvent e){
        parent.setScreen(ScreenController.MainID,"LEFT");
    }

    public void enterPressed(ActionEvent e) {}

}
