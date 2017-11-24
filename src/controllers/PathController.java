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
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import exceptions.InvalidNodeException;
import javafx.animation.Transition;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.MoveTo;
import javafx.util.Duration;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import map.Path;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.animation.PathTransition;


import DepartmentSubsystem.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathController implements ControllableScreen{
    private ScreenController parent;
    private ArrayList<Node> path;
    private HospitalMap map;
    private ArrayList<Line> lines;
    private ArrayList<Circle> points;
    private ArrayList<Shape> shapes;


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

    @FXML
    private ScrollPane mapScrollPane;

    @FXML
    private JFXSlider slideBarZoom;



    private proxyImagePane mapImage = new proxyImagePane();

    private FloorNumber currentFloor;// the current floor where the kiosk is.

    private ArrayList<FloorNumber> floors; //list of floors available


    private HashMap<FloorNumber,ArrayList<Line>> pathLines;

    private HashMap<FloorNumber, ArrayList<Circle>> pathPoints;

    private HashMap<FloorNumber, ArrayList<Shape>> pathShapes;

    private HashMap<FloorNumber,ArrayList<Integer>> positionVars;

    @FXML
    private JFXListView<String> directionsList;
    //Methods start here
    public void init()
    {
        map = HospitalMap.getMap();
        path = new ArrayList<Node>();
        lines = new ArrayList<Line>();
        points = new ArrayList<Circle>();
        shapes = new ArrayList<Shape>();
        pathShapes = new HashMap<FloorNumber,ArrayList<Shape>>();
        pathLines = new HashMap<FloorNumber,ArrayList<Line>>(); //hash map to lines for each floor
        pathPoints = new HashMap<FloorNumber, ArrayList<Circle>>();
        positionVars= new HashMap<FloorNumber,ArrayList<Integer>>();
        currentFloor = FloorNumber.FLOOR_ONE;
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
        mapImage.setImage(currentFloor);
        mapImage.slideButtons(floorScrollPane,currentFloor);
        startNodeChoice.setValue(map.getKioskLocation());
        //remove any previous paths from the display
        clearPaths();
        //lines = new ArrayList<>();
    }
    private Circle getPoint(int x, int y){
        Circle c = new AnimatedCircle();
        c.setCenterX(x/mapImage.getScale());
        c.setCenterY(y/mapImage.getScale());
        c.setVisible(true);
        c.setRadius(10/mapImage.getScale());
        return c;
    }
    private void getVars(FloorNumber floor,Circle c){
        ArrayList<Integer> ans = new ArrayList<Integer>();
        if(positionVars.containsKey(floor)){
            if(positionVars.get(floor).get(0)<c.getCenterX()){
                positionVars.get(floor).set(0,(int)c.getCenterX());
            }
            if(positionVars.get(floor).get(1)<c.getCenterY()){
                positionVars.get(floor).set(1,(int)c.getCenterY());
            }
        }
        else{
            ArrayList<Integer> temp = new ArrayList<Integer>();
            temp.add((int)c.getCenterX());
            temp.add((int)c.getCenterY());
            positionVars.put(floor,temp);
        }
    }
    private void controlScroller(FloorNumber floor){
        if(positionVars.containsKey(floor)){
            mapScrollPane.setHvalue(positionVars.get(floor).get(0)*mapImage.getScale()/5000);
            mapScrollPane.setVvalue(positionVars.get(floor).get(1)*mapImage.getScale()/3500);
            System.out.println("Screen Adjusted");
        }
    }

    private void setLines(Path path){
        clearPaths();
        Node node = null;
        FloorNumber current=null; // pointer to the current node of the floor
        for(int i=0;i<path.getPath().size();i++){
            //get first floor
            Node lastNode = node;
            node = path.getPath().get(i);
            if(node.getFloor()!=current){
                current = node.getFloor();
                floors.add(current);
                //create new hashmap elements
                pathLines.put(current,new ArrayList<Line>());
                pathPoints.put(current,new ArrayList<Circle>());
                if(i==0){
                    currentFloor=current;
                }
                //add point for current node
                Circle newp = getPoint(node.getX(),node.getY());
                pathPoints.get(current).add(newp);
                getVars(current, newp);
                mapPane.getChildren().add(newp);
                points.add(newp);
                newp.setFill(Color.RED);
                //shapes.add(newp);
                //add last point if it exists
//                if(i>0){
//                    Circle newp1 = getPoint(lastNode.getX(),lastNode.getY());
//                    pathPoints.get(lastNode.getFloor()).add(newp1);
//                    getVars(current, newp1);
//                    mapPane.getChildren().add(newp1);
//                    points.add(newp1);
//                }
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
                //shapes.add(line);
            }
            if(i==path.getPath().size()-1 && i>0){
                Circle newP2 = getPoint(path.getPath().get(i).getX(),path.getPath().get(i).getY());
                pathPoints.get(current).add(newP2);
                getVars(current, newP2);
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
        hideShapes(shapes);
        if(pathLines.containsKey(floor)){
            System.out.println("Switching to path " + floor);
            //add all in to mapPane
            showPaths(pathLines.get(floor));
            showPoints(pathPoints.get(floor));
            animatePath(floor,pathLines.get(floor));
            //adjust screen
            controlScroller(floor);
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
        for(Shape s: shapes){
            s.setVisible(false);
            mapPane.getChildren().remove(s);
        }
        //clear all lines and paths
        positionVars = new HashMap<>();
        pathLines = new HashMap<>();
        pathPoints = new HashMap<>();
        pathShapes = new HashMap<>();
        floors= new ArrayList<>();
        lines=new ArrayList<>();
        points= new ArrayList<>();
        shapes=new ArrayList<>();
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
    public void hideShapes(ArrayList<Shape> thisshapes){
        for(Shape s: thisshapes){
            s.setVisible(false);
        }
        System.out.println("Shape has been hidden");

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
    public void showShapes(ArrayList<Shape> thisshapes){
        for(Shape s: thisshapes){
            s.setVisible(true);
        }
        System.out.println("Shape has been shown");

    }

    public void setMapScale(double scale){
        double oldScale = mapImage.getScale();
        for(Line l : lines){
            l.setLayoutX((l.getLayoutX())*oldScale/scale);
            l.setLayoutY((l.getLayoutY())*oldScale/scale);

            l.setEndX(l.getEndX()*oldScale/scale);
            l.setEndY(l.getEndY()*oldScale/scale);
        }
        for(Circle c : points){
            c.setCenterX(c.getCenterX()*oldScale/scale);
            c.setCenterY(c.getCenterY()*oldScale/scale);
        }
        calVars();
        //reposition map
        mapImage.setScale(scale);
    }

    public void calVars(){
        //function to help reposition the screen on zoom
        for(FloorNumber f : floors){
            for(Circle p: points){
                if(positionVars.get(f).contains(p)){
                    getVars(f,p);
                }
            }
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
        return map.findPath(startNodeChoice.getValue(),endNodeChoice.getValue());
    }

    private void animatePath(FloorNumber floor,ArrayList<Line> ls){
        //create new hashMap element for floor if none existes
        if(!pathShapes.containsKey(floor)){
            pathShapes.put(floor,new ArrayList<Shape>());
        }

        //indicator to follow the path
        Rectangle rect = new Rectangle (0,0, 10, 10);
        rect.setVisible(true);
        rect.setFill(Color.DODGERBLUE);

        //animation that moves the indicator
        PathTransition pathTransition = new PathTransition();

        //path to follow
        javafx.scene.shape.Path p = new javafx.scene.shape.Path();
        p.setStroke(Color.RED);
        p.setVisible(false);//let animation move along our line
        mapPane.getChildren().addAll(rect,p);
        //add path and rect to shape hash map
        pathShapes.get(floor).add(rect);
        pathShapes.get(floor).add(p);
        //add all shapes to shape
        shapes.add(rect);
        shapes.add(p);
        //to remove the red line, remove p ^

        //starting point defined by MoveTo
        p.getElements().add(new MoveTo(ls.get(0).getLayoutX()-5, ls.get(0).getLayoutY()-5));

        //line movements along drawn lines
        for(Line l : ls){
            p.getElements().add(new LineTo(l.getLayoutX(),l.getLayoutY()));
            //Todo: Add the last line to the path
        }
        //define the animation actions
        pathTransition.setDuration(Duration.millis(10000));
        pathTransition.setNode(rect);
        pathTransition.setPath(p);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Transition.INDEFINITE);
        pathTransition.play();

    }


    public void enterPressed(ActionEvent e) throws InvalidNodeException
    {
        Path thePath = getPath();
        setLines(thePath);
        //add background image
        System.out.println(thePath.getDirections());
        mapImage.setImage(currentFloor);
        mapImage.slideButtons(floorScrollPane,currentFloor);
        directionsList.setItems(FXCollections.observableList(thePath.findDirections()));
        textDirectionsPane.setVisible(true);
        textDirectionsPane.setExpanded(false);
        switchPath(currentFloor);
        disableUnusedButtons(); //remove other screens
        System.out.println("Enter Pressed");

    }

    private void disableUnusedButtons(){
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
        parent.setScreen(ScreenController.MainID,"RIGHT");
    }

    public void stairsPressed(ActionEvent e)
    {
        System.out.println("Checked off stairs");
    }

    public void floorButtonPressed(ActionEvent e){
        FloorNumber floor = FloorNumber.fromDbMapping(((JFXButton)e.getSource()).getText());
        mapImage.slideButtons(floorScrollPane,floor);
        mapImage.setImage(floor);
        currentFloor=floor;//update Current floor
        switchPath(floor);
    }

    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        System.out.println("Zoom In Pressed");
        slideBarZoom.setValue(slideBarZoom.getValue()+0.2);
        setMapScale(4-slideBarZoom.getValue());
        //redraw animation o ensure that it is well positioned
        switchPath(currentFloor);
        controlScroller(currentFloor);
    }

    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){
        slideBarZoom.setValue(slideBarZoom.getValue()-0.2);
        setMapScale(4-slideBarZoom.getValue());
        //redraw animation to ensure that it is well positioned
        switchPath(currentFloor);
        controlScroller(currentFloor);
    }



}
