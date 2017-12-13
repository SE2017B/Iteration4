/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration3
* Original author(s): Travis Norris, Erika Snow, Nick Fajardo, Leo Grande
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import ui.feedbackBackground;


public class FeedbackController implements ControllableScreen{
    public FeedbackController(){
    }

    private ScreenController parent;

    @FXML
    private JFXSlider starSlider;


    @FXML
    private AnchorPane mainAnchorPane;

    @Override
    public void init() {
        starSlider.setValue(0.0);

        feedbackBackground hospitalImage = new feedbackBackground(parent);
        mainAnchorPane.getChildren().add(0,hospitalImage);
        AnchorPane.setBottomAnchor(hospitalImage,0.0);
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
