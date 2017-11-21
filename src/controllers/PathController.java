/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik, Oluchukwu Okafor
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXTextField;
import exceptions.InvalidNodeException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import map.Path;
import service.ServiceRequest;
import service.Staff;

import java.util.ArrayList;

public class PathController implements ControllableScreen{
    private ScreenController parent;
    private ArrayList<Node> path;

    private ArrayList<Line> lines;


    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    @FXML
    private Button btnenter;

    @FXML
    private Button ntncancel;

    @FXML
    private CheckBox chkstairs;

    @FXML
    private Label lblstart;

    @FXML
    private Label lblend;

    @FXML
    private Label lblstairs;

    @FXML
    private Label lbldir;

    @FXML
    private JFXTextField startChoice;

    @FXML
    private JFXTextField endChoice;

    @FXML
    private Pane mapPane;

    @FXML
    private Label failLabel;

    @FXML
    private Button startFloor;

    @FXML
    private Button endFloor;

    private proxyImagePane backImage = new proxyImagePane();

    private FloorNumber currentFloor;// the current floor where the kiosk is.

    private ArrayList<FloorNumber> floors; //list of floors available

    private Path testpath;

    //Methods start here
    public void init()
    {
        path = new ArrayList<Node>();
        lines = new ArrayList<Line>();
        failLabel = new Label();
        onShow();
        currentFloor = FloorNumber.fromDbMapping("1");
        //set up floor variables
        floors = new ArrayList<FloorNumber>();
        floors.add(FloorNumber.fromDbMapping("1"));
        floors.add(FloorNumber.fromDbMapping("2"));
        //add the test background image
        switchImage(currentFloor);

        //create test path
        testpath = new Path();
        testpath.addToPath(new Node(100,100));
        testpath.addToPath(new Node(100,105));
        testpath.addToPath(new Node(105,105));
        testpath.addToPath(new Node(110,115));
        testpath.addToPath(new Node(130,120));
        testpath.addToPath(new Node(1100,800));


    }

    public void onShow(){
        //Update the nodes in the map

        //update the items in the checklist


        //remove any previous paths from the display
        for (Line line: lines) {
            line.setVisible(false);
            mapPane.getChildren().remove(line);
        }
        lines = new ArrayList<>();
    }

    public void diplayPath(Path path){
        if( path.getPath().size() <= 1){
            System.out.println("NO PATH FOUND");
            failLabel.setVisible(true);
        } else if(path.getPath().size() > 1) {
            failLabel.setVisible(false);
            for (int i = 0; i < path.getPath().size() - 1; i++) {
                Line line = new Line();
                Node start = path.getPath().get(i);
                Node end = path.getPath().get(i + 1);
                line.setLayoutX((start.getX())/2);
                line.setLayoutY((start.getY())/2);


                line.setEndX((end.getX() - start.getX())/2);
                line.setEndY((end.getY() - start.getY())/2);

                line.setVisible(true);
                line.setStrokeWidth(2);
                mapPane.getChildren().add(line);
                lines.add(line);
                //

            }
        }
        else{
            System.out.println("ERROR: No Path Found");
        }
    }
    public void clearPaths(){
        for(Line line : lines){
            line.setVisible(false);
            mapPane.getChildren().remove(line);
        }
    }
    //button methods
    public void startPressed(ActionEvent e){
        //show the first screen when clicked on
        if(floors.size()>0){
            switchImage(floors.get(0));
        }

    }

    public void endPressed(ActionEvent e){
        if(floors.size()>1){
            switchImage(floors.get(1));
        }
    }
    private Path getPath(){
        //calculate the path an return it
        return testpath; //for now
    }


    public void enterPressed(ActionEvent e) throws InvalidNodeException
    {
        //add background image
        switchImage(currentFloor);


        //draw path
        System.out.println("Enter Pressed");
        //Remove last path from screen
        clearPaths();
        //test get path
        diplayPath(getPath());



    }
    //some methods for switching between screens to be edited in the future
    private void switchImage(FloorNumber floor){
        //clear lines first
        clearPaths();
        //delete background image if any
        if(mapPane.getChildren().size()>0){
            mapPane.getChildren().remove(0);
        }
        //now add background image
        mapPane.getChildren().add(backImage.getImagePane(floor));
    }

    public void cancelPressed(ActionEvent e)
    {
        System.out.println("Cancel Pressed");
        clearPaths();
        parent.setScreen(ScreenController.MainID);
    }

    public void stairsPressed(ActionEvent e)
    {

        System.out.println("Checked off stairs");
    }

}
