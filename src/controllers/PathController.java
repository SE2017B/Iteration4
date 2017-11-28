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
import exceptions.InvalidNodeException;
import javafx.animation.Transition;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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


import ui.AnimatedCircle;
import ui.MapViewer;
import ui.proxyImagePane;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class PathController implements ControllableScreen, Observer{
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
    private ScrollPane mapScrollPane;

    @FXML
    private JFXSlider slideBarZoom;

    @FXML
    private AnchorPane buttonHolderPane;



    private MapViewer mapViewer;

    private FloorNumber currentFloor;// the current floor where the kiosk is.

    private ArrayList<FloorNumber> floors; //list of floors available


    private HashMap<FloorNumber,ArrayList<Line>> pathLines;

    private HashMap<FloorNumber, ArrayList<Circle>> pathPoints;

    private HashMap<FloorNumber, ArrayList<Shape>> pathShapes;

    private HashMap<FloorNumber,ArrayList<Integer>> EdgeNodes;

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
        EdgeNodes= new HashMap<FloorNumber,ArrayList<Integer>>();
        currentFloor = FloorNumber.FLOOR_ONE;
        mapViewer = new MapViewer(this);
        //set up floor variables
        floors = new ArrayList<FloorNumber>();
        //floors.add(FloorNumber.fromDbMapping("1"));
        //floors.add(FloorNumber.fromDbMapping("2"));
        //add the test background image




        mapPane.getChildren().add(mapViewer.getMapImage());
        buttonHolderPane.getChildren().add(mapViewer.getPane());
        mapViewer.setScale(2);
    }

    public void onShow(){
        startNodeChoice.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));
        endNodeChoice.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));
        startNodeChoice.setValue(map.getKioskLocation());
        //remove any previous paths from the display
        clearPaths();
        //lines = new ArrayList<>();
    }
    private Circle getPoint(int x, int y){
        Circle c = new AnimatedCircle();
        c.setCenterX(x/mapViewer.getScale());
        c.setCenterY(y/mapViewer.getScale());
        c.setVisible(true);
        c.setRadius(7/mapViewer.getScale());
        return c;
    }
    private void getVars(FloorNumber floor,Node n){
        //get actual values
        int X = n.getX()/(int)mapViewer.getScale();
        int Y = n.getY()/(int)mapViewer.getScale();
        ArrayList<Integer> ans = new ArrayList<Integer>();
        if(EdgeNodes.containsKey(floor)){
            if(EdgeNodes.get(floor).get(0)<X){
                EdgeNodes.get(floor).set(0,X);
            }
            if(EdgeNodes.get(floor).get(1)<Y){
                EdgeNodes.get(floor).set(1,Y);
            }
            if(EdgeNodes.get(floor).get(2)>X){
                EdgeNodes.get(floor).set(2,X);
            }
            if(EdgeNodes.get(floor).get(3)>Y){
                EdgeNodes.get(floor).set(3,Y);
            }
        }
        else{
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(X);
            temp.add(Y);
            temp.add(X);
            temp.add(Y);
            EdgeNodes.put(floor,temp);
        }
    }
    private double toScale(int v){
        return v*mapViewer.getScale();

    }
    private void controlScroller(FloorNumber floor){
        if(EdgeNodes.containsKey(floor)){
            //find the average distance between min and max
            double x = (EdgeNodes.get(floor).get(0)+EdgeNodes.get(floor).get(2))/2;
            double y = (EdgeNodes.get(floor).get(1)+EdgeNodes.get(floor).get(3))/2;
            System.out.println("X is at "+x);
            System.out.println("Y is at "+y);
            /**
             * //Todo: Resize map to fit path if needed
            double sx = positionVars.get(floor).get(0)-positionVars.get(floor).get(2);
            double sy = positionVars.get(floor).get(1)-positionVars.get(floor).get(3);
            focustopath(sx,sy);//focus to path
             **/
            mapScrollPane.setHvalue(x/5000);
            mapScrollPane.setVvalue(y/3500);
            System.out.println("Screen Adjusted");
        }
    }
    private void focustopath(double sx,double sy){
        //resize map to fit path
        double x =Math.abs(sx);
        double y =Math.abs(sy);
        if(y>2){
            mapViewer.setScale(4);
            System.out.println("hahahaddhahaiofefjisejfonfo;wiefjw");
        }
        //if(y>x){
            //mapImage.setScale(y/5000);
        //}
        //else{
            //mapImage.setScale(x/3500);
        //}
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
                //getVars(current, newp);
                mapPane.getChildren().add(newp);
                points.add(newp);
                newp.setFill(Color.RED);
                //shapes.add(newp);
                //DON'T TAKE THIS OUT
                //add last point if it exists
              if(i>0){
                    Circle newp1 = getPoint(lastNode.getX(),lastNode.getY());
                    pathPoints.get(lastNode.getFloor()).add(newp1);
                    //getVars(current, newp1);
                    mapPane.getChildren().add(newp1);
                    points.add(newp1);
                }
            }
            else if(path.getPath().get(i).getFloor()==current){
                //create new floor and add it
                Line line = new Line();
                Node start = path.getPath().get(i-1);//the last node is the start node
                Node end = path.getPath().get(i);
                line.setLayoutX((start.getX())/mapViewer.getScale());
                line.setLayoutY((start.getY())/mapViewer.getScale());

                line.setEndX((end.getX() - start.getX())/mapViewer.getScale());
                line.setEndY((end.getY() - start.getY())/mapViewer.getScale());

                line.setVisible(true);
                //line.setStroke();
                line.setStrokeWidth(4/mapViewer.getScale());// make the line width vary with the scale
                pathLines.get(current).add(line);
                mapPane.getChildren().add(line);//add all lines to mapPane
                lines.add(line);
                //shapes.add(line);
            }
            if(i==path.getPath().size()-1 && i>0){
                Circle newP2 = getPoint(path.getPath().get(i).getX(),path.getPath().get(i).getY());
                pathPoints.get(current).add(newP2);
                //getVars(current, newP2);
                mapPane.getChildren().add(newP2);
                points.add(newP2);
            }
            //Todo: add points at nodes with a steep change in direction
        }
    }
    private void setPaths(Path path){
        clearPaths();
        Node node = null;
        FloorNumber current=null; // pointer to the current node of the floor
        ArrayList<Path> Paths = new ArrayList<>();
        for(int i=0;i<path.getPath().size();i++){
            //get first floor
            node = path.getPath().get(i);
            getVars(node.getFloor(),node);//check if the node is an edge node
            if(node.getFloor()!=current){
                Paths.add(new Path());//create new path for floor
                //create a new path for floors
                current = node.getFloor();//get new floor
                System.out.println("New Current Floor "+current);
                floors.add(current);
                if(i==0){
                    currentFloor=current;
                }
                //add to path
                Paths.get(Paths.size()-1).addToPath(node);
                //add point for current node
            }
            else if(path.getPath().get(i).getFloor()==current){
                //add to path
                Paths.get(Paths.size()-1).addToPath(node);

            }
        }
        //now animate all paths
        for(Path p: Paths){
            animateFloor(p.getPath().get(0).getFloor(),p);
        }

    }
    private void animateFloor(FloorNumber floor,Path path){
        System.out.println("Animating floor "+path.toString());
        //create new hashMap element for floor if none existes
        if(!pathShapes.containsKey(floor)){
            pathShapes.put(floor,new ArrayList<Shape>());
        }

        //represent first and last nodes with animated circles
        Circle newp = getPoint(path.getPath().get(0).getX(),path.getPath().get(0).getY());
        newp.setFill(Color.RED);
        pathShapes.get(floor).add(newp);
        //getVars(current, newp);
        mapPane.getChildren().add(newp);
        shapes.add(newp);
        //add to last node
        Circle lastp = getPoint(path.getPath().get(path.getPath().size()-1).getX(),path.getPath().get(path.getPath().size()-1).getY());
        pathShapes.get(floor).add(lastp);
        //getVars(current, newp);
        mapPane.getChildren().add(lastp);
        shapes.add(lastp);


        //indicator to follow the path
        Rectangle rect = new Rectangle (0,0, 10, 10);
        rect.setVisible(true);
        rect.setFill(Color.DODGERBLUE);

        //animation that moves the indicator
        PathTransition pathTransition = new PathTransition();

        //path to follow
        javafx.scene.shape.Path p = new javafx.scene.shape.Path();
        p.setStroke(Color.RED);
        //p.setVisible(false);//let animation move along our line
        mapPane.getChildren().addAll(rect,p);
        //add path and rect to shape hash map
        pathShapes.get(floor).add(rect);
        pathShapes.get(floor).add(p);
        //add all shapes to shape
        shapes.add(rect);
        shapes.add(p);
        //to remove the red line, remove p ^

        //starting point defined by MoveTo
        p.getElements().add(new MoveTo(path.getPath().get(0).getX()/mapViewer.getScale(), path.getPath().get(0).getY()/mapViewer.getScale()));

        //line movements along drawn lines
        for(int i=1;i<path.getPath().size();i++){
            p.getElements().add(new LineTo(path.getPath().get(i).getX()/mapViewer.getScale(), path.getPath().get(i).getY()/mapViewer.getScale()));
        }
        //define the animation actions
        System.out.println("The set distance is "+ path.getDistance());
        pathTransition.setDuration(Duration.millis(path.getDistance()/0.1));//make speed constant
        pathTransition.setNode(rect);
        pathTransition.setPath(p);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Transition.INDEFINITE);
        pathTransition.play();

    }
    //method to switch between paths when toggling between floors
    private void switchPath(FloorNumber floor){
        hidePaths(lines);
        hidePoints(points);
        hideShapes(shapes);
        if(pathShapes.containsKey(floor)){
            System.out.println("Switching to path " + floor);
            //add all in to mapPane
            System.out.println("Shapes are " + pathShapes.get(floor));
            showShapes(pathShapes.get(floor));
            //showPaths(pathLines.get(floor));
            //showPoints(pathPoints.get(floor));
            //animatePath(floor,pathLines.get(floor));
            //adjust screen
            controlScroller(floor);
            //Update Current floor
            currentFloor=floor;
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
        EdgeNodes = new HashMap<>();
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
        double oldScale = mapViewer.getScale();
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
        mapViewer.setScale(scale);
    }

    public void calVars(){
        //function to help reposition the screen on zoom
        for(FloorNumber f : floors){
            for(Circle p: points){
                if(EdgeNodes.get(f).contains(p)){
                    //getVars(f,p);
                }
            }
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
        p.getElements().add(new MoveTo(ls.get(0).getLayoutX(), ls.get(0).getLayoutY()));

        //line movements along drawn lines
        for(Line l : ls){
            //add to line end so that animation gets to the end
            p.getElements().add(new LineTo(l.getLayoutX()+l.getEndX(),l.getLayoutY()+l.getEndY()));
        }
        //define the animation actions
        pathTransition.setDuration(Duration.millis(8000));
        pathTransition.setNode(rect);
        pathTransition.setPath(p);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Transition.INDEFINITE);
        pathTransition.play();

    }


    public void enterPressed(ActionEvent e) throws InvalidNodeException
    {
        Path thePath = getPath();

        System.out.println(thePath.toString());
        //setLines(thePath);
        setPaths(thePath);
        System.out.println(floors);
        mapViewer.setButtonsByFloor(floors);
        //add background image
        System.out.println(thePath.getDirections());
        directionsList.setItems(FXCollections.observableList(thePath.findDirections()));
        textDirectionsPane.setVisible(true);
        textDirectionsPane.setExpanded(false);
        switchPath(currentFloor);
        System.out.println("Enter Pressed");

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

    public void update(Observable o, Object arg){
        if(arg instanceof FloorNumber) {
            FloorNumber floor = (FloorNumber) arg;
            currentFloor = floor;//update Current floor
            System.out.println("Updating");
            switchPath(floor);
        }
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
    //when left is pressed
    public void rightPressed(ActionEvent e){
        for(int i=0;i<floors.size();i++){
            if(floors.get(i)==currentFloor){
                if(i<floors.size()-1){
                    switchPath(floors.get(i+1));
                }
                break;
            }
        }

    }
    //when right is pressed
    public void leftPressed(ActionEvent e){
        for(int i=0;i<floors.size();i++){
            if(floors.get(i)==currentFloor){
                if(i>0){
                    switchPath(floors.get(i-1));
                }
                break;
            }
        }
    }



}
