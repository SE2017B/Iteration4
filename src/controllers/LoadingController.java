package controllers;

import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import ui.hospitalBackground;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements  Initializable {


    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private JFXProgressBar progressBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
