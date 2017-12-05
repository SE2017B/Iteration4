package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;

import java.awt.event.ActionEvent;
import java.util.Observer;

public class AboutController implements ControllableScreen{ //the other ones implement Observer... what's that all about?
    @FXML
    private JFXButton returnButton;

    private ScreenController parent;

    public void init(){

    }

    public void onShow(){

    }

    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }

    public void returnPressed(ActionEvent e){
        System.out.println("Return Pressed");
        parent.setScreen(ScreenController.MainID, "LEFT");
    }


}
