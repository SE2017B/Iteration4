package controllers;

import com.jfoenix.controls.JFXSlider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class FeedbackController implements ControllableScreen{
    public FeedbackController(){}
    private ScreenController parent;


    @FXML
    private JFXSlider starSlider;

    @Override
    public void init() {
        starSlider.setValue(0.0);
    }

    @Override
    public void onShow() {
    }

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }

    public void returnPressed(ActionEvent e){
        System.out.println("Return Pressed");
        parent.setScreen(ScreenController.MainID,"LEFT");
    }

    public void enterPressed(ActionEvent e) {
        System.out.println("Enter Pressed");
    }
}
