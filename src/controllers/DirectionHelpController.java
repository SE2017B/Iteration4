package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ui.AnimatedCircle;


import java.util.ArrayList;

public class DirectionHelpController implements ControllableScreen{

    public DirectionHelpController() {
        this.status = 0;
        this.descriptions = new ArrayList<>();
        this.ImageMap = new ArrayList<>();
    }

    ScreenController parent;
    private ArrayList<DirectionHelpController.CustomPair> descriptions;
    private ArrayList<String> ImageMap;
    private int status;

    @Override
    public void init(){
        helpTextArea.setText("Welcome to Brigham & Women’s hospital kiosk application! Click next to start the tutorial for the Navigation Screen.");
        populateLists();
        this.setImageFromList(false);
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
    private JFXTextArea helpTextArea;

    @FXML
    private JFXButton returnButton;

    private AnimatedCircle circle;

    @FXML
    private JFXProgressBar helpProgress;

    @FXML
    void nextPressed(ActionEvent event) {
        setImageFromList(true);
        setLables();
        progressBarStatus(status);
        System.out.println(helpProgress.getProgress());
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
        }
        else if(stat == 1) {
            helpProgress.setProgress(.25);
        }
        else if(stat == 2) {
            helpProgress.setProgress(.5);
        }
        else if(stat == 3) {
            helpProgress.setProgress(.75);
        }
        else if(stat == 4) {
            helpProgress.setProgress(1);
        }
    }
    private void setImageFromList(boolean next){
        //If we move forward, we check bounds
        if(next){
            if((this.status) >= 4)
                this.status = this.ImageMap.size()-1;
            else
                this.status += 1;
        }
        //If we move backwards, we check bounds
        else{
            if((this.status) <= 0)
                this.status = 0;
            else
                this.status -= 1;
        }
    }

    private void setLables() {
        DirectionHelpController.CustomPair currentLables = this.descriptions.get(this.status);
        this.helpTextArea.setText(currentLables.getDescription());
    }

    private void populateLists(){
        DirectionHelpController.CustomPair main = new DirectionHelpController.CustomPair("Welcome to Brigham & Women’s hospital kiosk application! Click next to start the tutorial for the Main Screen.");
        this.descriptions.add(main);
        this.ImageMap.add("1");

        DirectionHelpController.CustomPair Search = new DirectionHelpController.CustomPair("Here you can search for the start and end locations. You have the option to type with the keyboard and search, or click Search By Type to search by types of places!");
        this.descriptions.add(Search);
        this.ImageMap.add("2");

        DirectionHelpController.CustomPair Go = new DirectionHelpController.CustomPair("Press 'Go!' To find the path!");
        this.descriptions.add(Go);
        this.ImageMap.add("3");

        DirectionHelpController.CustomPair Floors = new DirectionHelpController.CustomPair("This window shows the floors in the hospital. Click them to change which floor you are viewing!");
        this.descriptions.add(Floors);
        this.ImageMap.add("4");

        DirectionHelpController.CustomPair Zoom = new DirectionHelpController.CustomPair("Click the plus or minus to zoom in and out.\n");
        this.descriptions.add(Zoom);
        this.ImageMap.add("5");
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