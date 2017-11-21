/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik, Oluchukwu Okafor
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import map.Path;
import DepartmentSubsystem.*;


import java.util.ArrayList;
import java.util.HashMap;

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
    private ChoiceBox<Node> startNodeChoice;

    @FXML
    private ChoiceBox<Node> endNodeChoice;

    @FXML
    private Pane mapPane;


    @FXML
    private JFXButton floorL2Button;
    @FXML
    private JFXButton floorL1Button;
    @FXML
    private JFXButton floorGButton;
    @FXML
    private JFXButton floor1Button;
    @FXML
    private JFXButton floor2Button;
    @FXML
    private JFXButton floor3Button;



    private proxyImagePane mapImage = new proxyImagePane();

    private FloorNumber currentFloor;// the current floor where the kiosk is.

    private ArrayList<FloorNumber> floors; //list of floors available

    private Path testpath;

    private HashMap<FloorNumber,ArrayList<Line>> pathLines;

    //Methods start here
    public void init()
    {
        path = new ArrayList<Node>();
        lines = new ArrayList<Line>();
        pathLines = new HashMap<FloorNumber,ArrayList<Line>>(); //hash map to lines for each floor
        onShow();
        currentFloor = FloorNumber.fromDbMapping("1");
        //set up floor variables
        floors = new ArrayList<FloorNumber>();
        floors.add(FloorNumber.fromDbMapping("1"));
        floors.add(FloorNumber.fromDbMapping("2"));
        //add the test background image
        mapImage.setImage(currentFloor);

        //create test path
        testpath = new Path();
        testpath.addToPath(new Node(0,0,FloorNumber.fromDbMapping("L1")));
        testpath.addToPath(new Node(1000,400,FloorNumber.fromDbMapping("L1")));
        testpath.addToPath(new Node(1000,400,FloorNumber.fromDbMapping("L2")));
        testpath.addToPath(new Node(0,1000,FloorNumber.fromDbMapping("L2")));

        mapPane.getChildren().add(mapImage);
    }

    public void onShow(){
        //Todo: update the items in the choice boxes


        //remove any previous paths from the display
        for (Line line: lines) {
            line.setVisible(false);
            mapPane.getChildren().remove(line);
        }
        lines = new ArrayList<>();
    }
    private void setLines(Path path){
        //clear all lines and paths
        pathLines.clear();
        floors.clear();
        lines.clear();
        FloorNumber current=null; // pointer to the current node of the floor
        for(int i=0;i<path.getPath().size();i++){
            //get first floor
            if(path.getPath().get(i).getFloor()!=current){
                current = path.getPath().get(i).getFloor();
                floors.add(current);
                //create new hashmap element
                pathLines.put(current,new ArrayList<Line>());
                if(i==0){
                    currentFloor=current;
                }
            }
            else if(path.getPath().get(i).getFloor()==current){
                //create new floor and add it
                Line line = new Line();
                Node start = path.getPath().get(i-1);//the last node is the start node
                Node end = path.getPath().get(i);
                line.setLayoutX((start.getX())/mapImage.getScale());
                line.setLayoutY((start.getY())/mapImage.getScale());

                line.setEndX((end.getX() - start.getX())/mapImage.getScale());
                line.setEndY((end.getY() - start.getY())/mapImage.getScale());

                line.setVisible(true);
                line.setStrokeWidth(4/mapImage.getScale());// make the line width vary with the scale
                pathLines.get(current).add(line);
                mapPane.getChildren().add(line);//add all lines to mapPane
                lines.add(line);
            }
        }
    }
    //method to switch between paths when toggling between floors
    private void switchPath(FloorNumber floor){
        hidePaths(lines);
        if(pathLines.containsKey(floor)){
            System.out.println("Switching to path " + floor);
            //add all in to mapPane
            showPaths(pathLines.get(floor));
        }
    }

    public void diplayPath(Path path){
        if( path.getPath().size() <= 1){
            System.out.println("NO PATH FOUND");
        } else if(path.getPath().size() > 1) {
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
    public void hidePaths(ArrayList<Line> thislines){
        for(Line line : thislines){
            line.setVisible(false);
            System.out.println("Line has been hidden");
        }

    }
    public void showPaths(ArrayList<Line> thislines){
        for(Line line : thislines){
            line.setVisible(true);
            System.out.println("Line has been shown");
        }
    }
    //button methods
    public void startPressed(ActionEvent e){
        //show the first screen when clicked on
        if(floors.size()>0){
            mapImage.setImage(floors.get(0));
        }

    }

    public void endPressed(ActionEvent e){
        if(floors.size()>1){
            mapImage.setImage(floors.get(1));
        }
    }
    private Path getPath(){
        //HospitalMap.findPath(startNodeChoice.getValue(),endNodeChoice.getValue());
        //calculate the path an return it
        return testpath; //for now
    }


    public void enterPressed(ActionEvent e) throws InvalidNodeException
    {
        setLines(getPath());
        //add background image
        mapImage.setImage(currentFloor);

        switchPath(currentFloor);
        System.out.println("Enter Pressed");

        //todo: draw path on this floor
        //todo: disable floor buttons not in the path


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

    public void floorButtonPressed(ActionEvent e){
        //todo: display the lines form the path on the floor that was pressed
        //todo: hide the lines from the other floors
        FloorNumber floor = FloorNumber.fromDbMapping(((JFXButton)e.getSource()).getText());
        System.out.println("Floor Pressed: " + floor);
        mapImage.setImage(floor);
        switchPath(floor);
    }



}
