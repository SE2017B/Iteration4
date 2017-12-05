package controllers;

import com.jfoenix.controls.JFXSlider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FeedbackController implements ControllableScreen{
    public FeedbackController(){}
    ScreenController parent;

    @FXML
    private Label numberOfStars;

    @FXML
    private JFXSlider starSlider;

    @Override
    public void init() {

    }

    @Override
    public void onShow() {

    }

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }

    public void setStarLabel() {
        String labelNum = Double.toString(starSlider.getValue());
        numberOfStars.setText(labelNum);
    }

    public void returnPressed(ActionEvent e){
        System.out.println("Return Pressed");
        parent.setScreen(ScreenController.MainID,"LEFT");
    }
}
