/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik, Oluchukwu Okafor
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import exceptions.InvalidNodeException;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.shape.Circle;

import map.Path;
import DepartmentSubsystem.*;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathController implements ControllableScreen{
    private ScreenController parent;
    private ArrayList<Node> path;
    private HospitalMap map;
    private ArrayList<Line> lines;
    private ArrayList<Circle> points;


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
    private ChoiceBox<Node> startNodeChoice;

    @FXML
    private ChoiceBox<Node> endNodeChoice;

    @FXML
    private Pane mapPane;

    @FXML
    private TitledPane textDirectionsPane;


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
    @FXML
    private ScrollPane floorScrollPane;



    private proxyImagePane mapImage = new proxyImagePane();

    private FloorNumber currentFloor;// the current floor where the kiosk is.

    private ArrayList<FloorNumber> floors; //list of floors available

    //private Path testpath;

    private HashMap<FloorNumber,ArrayList<Line>> pathLines;

    private HashMap<FloorNumber, ArrayList<Circle>> pathPoints;

    @FXML
    private JFXListView<String> directionsList;
    //Methods start here
    public void init()
    {
        map = HospitalMap.getMap();
        path = new ArrayList<Node>();
        lines = new ArrayList<Line>();
        points = new ArrayList<Circle>();
        pathLines = new HashMap<FloorNumber,ArrayList<Line>>(); //hash map to lines for each floor
        pathPoints = new HashMap<FloorNumber, ArrayList<Circle>>();
        onShow();
        currentFloor = FloorNumber.fromDbMapping("1");
        //set up floor variables
        floors = new ArrayList<FloorNumber>();
        //floors.add(FloorNumber.fromDbMapping("1"));
        //floors.add(FloorNumber.fromDbMapping("2"));
        //add the test background image
        mapImage.setImage(currentFloor);



        mapPane.getChildren().add(mapImage);
    }

    public void onShow(){
        startNodeChoice.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));
        endNodeChoice.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));


        //remove any previous paths from the display
        clearPaths();
        //lines = new ArrayList<>();
    }
    private Circle getPoint(int x, int y){
        Circle c = new Circle();
        c.setCenterX(x/mapImage.getScale());
        c.setCenterY(y/mapImage.getScale());
        c.setVisible(true);
        c.setRadius(10/mapImage.getScale());
        return c;
    }
    private void setLines(Path path){
        clearPaths();
        FloorNumber current=null; // pointer to the current node of the floor
        for(int i=0;i<path.getPath().size();i++){
            //get first floor
            if(path.getPath().get(i).getFloor()!=current){
                current = path.getPath().get(i).getFloor();
                floors.add(current);
                //create new hashmap elements
                pathLines.put(current,new ArrayList<Line>());
                pathPoints.put(current,new ArrayList<Circle>());
                if(i==0){
                    currentFloor=current;
                }
                //add point for current node
                Circle newp = getPoint(path.getPath().get(i).getX(),path.getPath().get(i).getY());
                pathPoints.get(current).add(newp);
                mapPane.getChildren().add(newp);
                points.add(newp);
                //add last point if it exists
                if(i>0){
                    Circle newp1 = getPoint(path.getPath().get(i-1).getX(),path.getPath().get(i-1).getY());
                    pathPoints.get(path.getPath().get(i-1).getFloor()).add(newp1);
                    mapPane.getChildren().add(newp1);
                    points.add(newp1);
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
                //line.setStroke();
                line.setStrokeWidth(4/mapImage.getScale());// make the line width vary with the scale
                pathLines.get(current).add(line);
                mapPane.getChildren().add(line);//add all lines to mapPane
                lines.add(line);
            }
            if(i==path.getPath().size()-1 && i>0){
                Circle newP2 = getPoint(path.getPath().get(i).getX(),path.getPath().get(i).getY());
                pathPoints.get(current).add(newP2);
                mapPane.getChildren().add(newP2);
                points.add(newP2);
            }
            //Todo: add points at nodes with a steep change in direction
        }
    }
    //method to switch between paths when toggling between floors
    private void switchPath(FloorNumber floor){
        hidePaths(lines);
        hidePoints(points);
        if(pathLines.containsKey(floor)){
            System.out.println("Switching to path " + floor);
            //add all in to mapPane
            showPaths(pathLines.get(floor));
            showPoints(pathPoints.get(floor));
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
        for(Circle c: points){
            c.setVisible(false);
            mapPane.getChildren().remove(c);
        }
        //clear all lines and paths
        pathLines = new HashMap<>();
        pathPoints = new HashMap<>();
        floors= new ArrayList<>();
        lines=new ArrayList<>();
        points= new ArrayList<>();
        System.out.println("All entities cleared");
    }

    public void hidePaths(ArrayList<Line> thislines){
        for(Line line : thislines){
            line.setVisible(false);
            System.out.println("Line has been hidden");
        }

    }
    public void hidePoints(ArrayList<Circle> thispoints){
        for(Circle c: thispoints){
            c.setVisible(false);
            System.out.println("Point has been hidden");
        }

    }
    public void showPaths(ArrayList<Line> thislines){
        for(Line line : thislines){
            line.setVisible(true);
            System.out.println("Line has been shown");
        }
    }
    public void showPoints(ArrayList<Circle> thispoints){
        for(Circle c: thispoints){
            c.setVisible(true);
            System.out.println("Point has been shown");
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
        return map.findPath(startNodeChoice.getValue(),endNodeChoice.getValue());
    }


    public void enterPressed(ActionEvent e) throws InvalidNodeException
    {
        Path thePath = getPath();
        setLines(thePath);
        //add background image
        System.out.println(thePath.getDirections());
        mapImage.setImage(currentFloor);
        directionsList.setItems(FXCollections.observableList(thePath.getDirections()));
        textDirectionsPane.setVisible(true);
        textDirectionsPane.setExpanded(false);
        switchPath(currentFloor);
        clearScreen(); //remove other screens
        System.out.println("Enter Pressed");

        //todo: draw path on this floor
        //todo: disable floor buttons not in the path


    }

    private void clearScreen(){
        //check each button
        checkScreen(floorL2Button);
        checkScreen(floorL1Button);
        checkScreen(floorGButton);
        checkScreen(floor1Button);
        checkScreen(floor2Button);
        checkScreen(floor3Button);

    }
    private void checkScreen(JFXButton b){
        if(floors.contains(FloorNumber.fromDbMapping(b.getText()))){
            b.setDisable(false);
        }
        else{
            b.setDisable(true);
        }
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
        floorScrollPane.setHvalue((((JFXButton) e.getSource()).getLayoutX()-415)/1000);
        mapImage.setImage(floor);
        switchPath(floor);
    }



}
