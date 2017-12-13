package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ui.AnimatedCircle;
import ui.TransitionCircle;

import java.util.ArrayList;

public class DirectionHelpController implements ControllableScreen{

    public DirectionHelpController() {
        this.status = 0;
        this.descriptions = new ArrayList<>();
    }

    ScreenController parent;
    private ArrayList<DirectionHelpController.CustomPair> descriptions;
    private int status;
    private ArrayList<ArrayList<Double>> positions = new ArrayList<>();
    private TransitionCircle pointer=new TransitionCircle(500,500);
    private int something = 0;

    @Override
    public void init(){
        //set up animated pointer
        pointer.setRadius(20);
        pointer.setOpacity(0.9);
        aPane.getChildren().add(pointer);
    }

    //reset everything to the start
    @Override
    public void onShow() {
        something = 0;
        stepLabel.setText("");
        positions = new ArrayList<>();
        pointer.moveTo(helpTextArea.getLayoutX(),helpTextArea.getLayoutY());
        helpTextArea.setText("Welcome! Click next to start the tutorial for the Navigation Screen.");
        this.setImageFromList(false);
        helpProgress.setProgress(0.0);
        status = 0;
        populateLists();
    }

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Pane aPane;
    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton previousButton;

    @FXML
    private JFXTextArea helpTextArea;

    @FXML
    private JFXButton returnButton;

    private AnimatedCircle circle;

    @FXML
    private JFXProgressBar helpProgress;

    @FXML
    private Label stepLabel;

    @FXML
    void nextPressed(ActionEvent event) {
        setImageFromList(true);
        setLables();
        progressBarStatus(status);
        System.out.println(helpProgress.getProgress());
        if(something == 1) {
            returnPressed(event);
        }
    }

    @FXML
    void previousPressed(ActionEvent event) {
        setImageFromList(false);
        setLables();
        progressBarStatus(status);
        System.out.println(helpProgress.getProgress());
    }

    @FXML
    void returnPressed(ActionEvent event) {
        setImageFromList(true);
        parent.setScreen(ScreenController.LoginID,"HELP_OUT");
        System.out.println("Return Pressed");
    }

    //////////////////////////
    // HELPER METHODS BELOW //
    //////////////////////////

    private void progressBarStatus(int stat) {
        stat = this.status;
        if(stat == 0) {
            helpProgress.setProgress(0);
            stepLabel.setText("");
        }
        else if(stat == 1) {
            helpProgress.setProgress(.5);
            stepLabel.setText("Step 1/2");
        }
        else if(stat == 2) {
            helpProgress.setProgress(1);
            stepLabel.setText("Step 2/2");
        }
    }
    private void setImageFromList(boolean next){
        //If we move forward, we check bounds
        if(next){
            if((this.status) >= 2) {
                this.status = 2;
                something++;
            }
            else {
                this.status += 1;
            }
        }
        //If we move backwards, we check bounds
        else{
            if((this.status) <= 0)
                this.status = 0;
            else
                this.status -= 1;
            something--;
            if (something <=0) {
                something = 0;
            }
        }
    }

    private void setLables() {
        DirectionHelpController.CustomPair currentLables = this.descriptions.get(this.status);
        this.helpTextArea.setText(currentLables.getDescription());
        //animate pointer
        this.pointer.moveTo(positions.get(status).get(0),positions.get(status).get(1));
    }

    private void populateLists(){
        ArrayList<Double> current = new ArrayList();
        DirectionHelpController.CustomPair main = new DirectionHelpController.CustomPair("Welcome! Click next to start the tutorial for the Navigation Screen.");
        this.descriptions.add(main);
        current.add(helpTextArea.getLayoutX()+helpTextArea.getWidth());
        current.add(helpTextArea.getLayoutY()+helpTextArea.getHeight());
        this.positions.add(current);

        DirectionHelpController.CustomPair Search = new DirectionHelpController.CustomPair("Search by text or search by type to find a path from one location to another!");
        this.descriptions.add(Search);
        current = new ArrayList();
        current.add(200.0); //width
        current.add(20.0); //height
        this.positions.add(current);

        DirectionHelpController.CustomPair Go = new DirectionHelpController.CustomPair("Press 'Go!' To find the path!");
        this.descriptions.add(Go);
        current = new ArrayList();
        current.add(parent.getWidth() - 40.0); //width
        current.add(40.0); //height
        this.positions.add(current);
    }

    class CustomPair{
        private String description;
        public CustomPair(String d){
            this.description = d;
        }
        public String getDescription() {
            return description;
        }
    }

}