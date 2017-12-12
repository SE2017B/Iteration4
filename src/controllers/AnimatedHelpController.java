package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import ui.AnimatedCircle;


import java.util.ArrayList;

public class AnimatedHelpController implements ControllableScreen{

    public AnimatedHelpController() {
        this.status = 0;
        this.descriptions = new ArrayList<>();
        this.ImageMap = new ArrayList<>();
    }

    ScreenController parent;
    private ArrayList<AnimatedHelpController.CustomPair> descriptions;
    private ArrayList<String> ImageMap;
    private int status;

    @Override
    public void init(){
        helpTextArea.setText("Welcome to Brigham & Women’s hospital kiosk application! Click next to start the tutorial for the Main Screen.");
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
            helpProgress.setProgress(.15);
        }
        else if(stat == 2) {
            helpProgress.setProgress(.3);
        }
        else if(stat == 3) {
            helpProgress.setProgress(.45);
        }
        else if(stat == 4) {
            helpProgress.setProgress(.6);
        }
        else if(stat == 5) {
            helpProgress.setProgress(.75);
        }
        else if(stat == 6) {
            helpProgress.setProgress(.9);
        }
        else if(stat == 7) {
            helpProgress.setProgress(1);
        }
    }
    private void setImageFromList(boolean next){
        //If we move forward, we check bounds
        if(next){
            if((this.status) >= 7)
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
        AnimatedHelpController.CustomPair currentLables = this.descriptions.get(this.status);
        this.helpTextArea.setText(currentLables.getDescription());
    }

    private void populateLists(){
        AnimatedHelpController.CustomPair main = new AnimatedHelpController.CustomPair("Welcome to Brigham & Women’s hospital kiosk application! Click next to start the tutorial for the Main Screen.");
        this.descriptions.add(main);
        this.ImageMap.add("1");

        AnimatedHelpController.CustomPair Login = new AnimatedHelpController.CustomPair("Hospital Staff login here. As a patient/visitor you do not have to worry about this!");
        this.descriptions.add(Login);
        this.ImageMap.add("2");

        AnimatedHelpController.CustomPair Emergency = new AnimatedHelpController.CustomPair("The emergency button shows you the closest exit to you for you to get out safely in case of an emergency.");
        this.descriptions.add(Emergency);
        this.ImageMap.add("5");

        AnimatedHelpController.CustomPair Filter = new AnimatedHelpController.CustomPair("Filter allows you to toggle the display of different types of areas on the map.");
        this.descriptions.add(Filter);
        this.ImageMap.add("3");

        AnimatedHelpController.CustomPair FindNearest = new AnimatedHelpController.CustomPair("Near Me finds a path to the nearest location that you click on.");
        this.descriptions.add(FindNearest);
        this.ImageMap.add("4");

        AnimatedHelpController.CustomPair Floors = new AnimatedHelpController.CustomPair("This window shows the floors in the hospital. Click them to change which floor you are viewing!");
        this.descriptions.add(Floors);
        this.ImageMap.add("6");

        AnimatedHelpController.CustomPair Zoom = new AnimatedHelpController.CustomPair("Click the plus or minus to zoom in and out.");
        this.descriptions.add(Zoom);
        this.ImageMap.add("7");

        AnimatedHelpController.CustomPair Navigation = new AnimatedHelpController.CustomPair("Click Navigation to learn more about finding paths in this application!");
        this.descriptions.add(Navigation);
        this.ImageMap.add("8");

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


