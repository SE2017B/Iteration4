package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ui.AnimatedCircle;
import ui.TransitionCircle;
import java.util.ArrayList;
import javafx.scene.control.Label;

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
    private ArrayList<ArrayList<Double>> positions = new ArrayList<>();
    private TransitionCircle pointer = new TransitionCircle(500,500);
    private int something = 0;

    @Override
    public void init(){
        pointer.setRadius(20);
        pointer.setOpacity(0.9);
        helpPane.getChildren().add(pointer);
    }

    //reset everything to the start
    @Override
    public void onShow() {
        something = 0;
        stepLabel.setText("");
        positions = new ArrayList<>();
        pointer.moveTo(helpTextArea.getLayoutX(),helpTextArea.getLayoutY());
        helpTextArea.setText("Welcome to Brigham & Women’s hospital kiosk application! Click next to start the tutorial for the Main Screen.");
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
    private Pane helpPane;

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
            helpProgress.setProgress(.14);
            stepLabel.setText("Step 1/7");
        }
        else if(stat == 2) {
            helpProgress.setProgress(.28);
            stepLabel.setText("Step 2/7");
        }
        else if(stat == 3) {
            helpProgress.setProgress(.42);
            stepLabel.setText("Step 3/7");
        }
        else if(stat == 4) {
            helpProgress.setProgress(.56);
            stepLabel.setText("Step 4/7");
        }
        else if(stat == 5) {
            helpProgress.setProgress(.70);
            stepLabel.setText("Step 5/7");
        }

        else if(stat == 6) {
            helpProgress.setProgress(.84);
            stepLabel.setText("Step 6/7");
        }

        else if(stat == 7) {
            helpProgress.setProgress(1);
            stepLabel.setText("Step 7/7");
        }
    }

    private void setImageFromList(boolean next){
        //If we move forward, we check bounds
        if(next){
            if((this.status) >= 7) {
                this.status = 7;
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
        AnimatedHelpController.CustomPair currentLables = this.descriptions.get(this.status);
        this.helpTextArea.setText(currentLables.getDescription());
        //animate pointer
        this.pointer.moveTo(positions.get(status).get(0),positions.get(status).get(1));
    }

    private void populateLists(){
        Double width =50.0;
        AnimatedHelpController.CustomPair main = new AnimatedHelpController.CustomPair("Welcome! Click next to start the tutorial for the Main Screen.");
        this.descriptions.add(main);
        this.ImageMap.add("1");
        ArrayList<Double> current = new ArrayList();
        current.add(helpTextArea.getLayoutX()+helpTextArea.getWidth());
        current.add(helpTextArea.getLayoutY()+helpTextArea.getHeight());
        this.positions.add(current);

        AnimatedHelpController.CustomPair FindNearest = new AnimatedHelpController.CustomPair("Near Me shows the closest type of location that you click on.");
        this.descriptions.add(FindNearest);
        this.ImageMap.add("2");
        current = new ArrayList();
        current.add(width);
        current.add(50.0);
        this.positions.add(current);

        AnimatedHelpController.CustomPair Filter = new AnimatedHelpController.CustomPair("Filter shows locations by type.");
        this.descriptions.add(Filter);
        this.ImageMap.add("3");
        current = new ArrayList();
        current.add(width);
        current.add(150.0);
        this.positions.add(current);

        AnimatedHelpController.CustomPair Emergency = new AnimatedHelpController.CustomPair("The emergency button shows the closest exit to you.");
        this.descriptions.add(Emergency);
        this.ImageMap.add("4");
        current = new ArrayList();
        current.add(width);
        current.add(500.0);
        this.positions.add(current);

        AnimatedHelpController.CustomPair Floor = new AnimatedHelpController.CustomPair("Click to change which floor you are viewing!");
        this.descriptions.add(Floor);
        this.ImageMap.add("5");
        current = new ArrayList();
        current.add(parent.getWidth()/2.0);
        current.add(parent.getHeight() - 80);
        this.positions.add(current);

        AnimatedHelpController.CustomPair Zoom = new AnimatedHelpController.CustomPair("Click the plus or minus to zoom in and out.");
        this.descriptions.add(Zoom);
        this.ImageMap.add("6");
        current = new ArrayList();
        current.add(parent.getWidth()/2.0);
        current.add(parent.getHeight() - 20);
        this.positions.add(current);

        AnimatedHelpController.CustomPair Login = new AnimatedHelpController.CustomPair("Login for hospital staff.");
        this.descriptions.add(Login);
        this.ImageMap.add("7");
        current = new ArrayList();
        current.add(width);
        current.add(600.0);
        this.positions.add(current);

        AnimatedHelpController.CustomPair Navigation = new AnimatedHelpController.CustomPair("Click Next and then Navigation to learn more about finding paths in this application!");
        this.descriptions.add(Navigation);
        this.ImageMap.add("8");
        current = new ArrayList();
        current.add(width);
        current.add(275.0);
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


