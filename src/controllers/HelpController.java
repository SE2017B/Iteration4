package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class HelpController implements ControllableScreen {

    private int status;
    private ArrayList<String> ImageMap;
    private ArrayList<CustomPair> descriptions;
    private ScreenController parent;

    @FXML
    private Label HelpDescription;

    @FXML
    private Label HelpTitle;

    @FXML
    private ImageView ImageViewer;

    @FXML
    private Button Exit;

    @FXML
    private Button Next;

    @FXML
    private Button Previous;

    public HelpController() {
        this.status = 0;
        this.ImageMap = new ArrayList<>();
        this.descriptions = new ArrayList<>();
    }

    @Override
    public void init() {
        populateLists();
        //Sets the current image as the first one,  and sets the index accordingly
        setImageFromList(false);
    }

    @Override
    public void onShow() {
        //TODO:What do
    }

    @Override
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }

    public void pressEnter(){
        setImageFromList(true);
        setLables();
    }

    public void pressPrevious(){
        setImageFromList(false);
        setLables();
    }

    //////////////////////////
    // HELPER METHODS BELOW //
    //////////////////////////
    private void setImageFromList(boolean next){
        //If we move forward, we check bounds
        if(next){
            if((this.status) >= this.ImageMap.size())
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
        //This shows the image, depending on the index we want
        String imageURL = this.ImageMap.get(this.status);
        this.ImageViewer.setImage(new Image(imageURL));
    }

    private void setLables() {
        CustomPair currentLables = this.descriptions.get(this.status);
        this.HelpTitle.setText(currentLables.getTitle());
        this.HelpDescription.setText(currentLables.getDescription());
    }

    private void populateLists(){
        CustomPair main = new CustomPair("Main Menu", "");
        this.descriptions.add(main);
        this.ImageMap.add("Tutorial_Pictures/MainMenu.png");

        CustomPair filter = new CustomPair("Main Menu", "");
        this.descriptions.add(filter);
        this.ImageMap.add("Tutorial_Pictures/FilterOpen.png");

        CustomPair FindNearest = new CustomPair("Main Menu", "");
        this.descriptions.add(FindNearest);
        this.ImageMap.add("Tutorial_Pictures/FindNearestOpen.png");

        CustomPair NodeToNodeBefore = new CustomPair("Main Menu", "");
        this.descriptions.add(NodeToNodeBefore);
        this.ImageMap.add("Tutorial_Pictures/PathFindingBefore.png");

        CustomPair NodeToNodeAfter = new CustomPair("Main Menu", "");
        this.descriptions.add(NodeToNodeAfter);
        this.ImageMap.add("Tutorial_Pictures/PathFindingAfter.png");
    }

    class CustomPair{
        private String title;
        private String description;

        public CustomPair(String t,  String d){
            this.title = t;
            this.description = d;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}
