/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik, Oluchukwu Okafor
* The following code
*/

package controllers;

import QRCode.NonValidQRCodeMessageException;
import QRCode.QRCodeGenerator;
import com.jfoenix.controls.*;
import exceptions.InvalidNodeException;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.util.Duration;
import javafx.util.StringConverter;
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

import ui.*;

import java.io.File;
import java.util.*;

public class PathController implements ControllableScreen, Observer{
    private ScreenController parent;
    private HospitalMap map;
    private String startType = "";
    private String startFloor = "";
    private String endType =  "";
    private String endFloor = "";
    private QRCodeGenerator qr;

    private final double LINE_STROKE = 6;
    private final double ARROW_SIZE = 30;
    private final double BUTTON_SIZE = 35;

    private MapViewer mapViewer;
    private FloorNumber currentFloor;// the current floor where the kiosk is.
    private PathViewer currentPath;
    private PathViewer wholePath;
    private ArrayList<FloorNumber> floors; //list of floors available
    private ArrayList<PathViewer> paths;
    private ArrayList<Shape> shapes;
    private Pane arrow;
    private PathTransition pathTransition;
    private Pane mapPane;
    private Path thePath;
    private int currentFloorIndex = 0;
    private int scaling;

    //animation variables
    private ArrayList<Double> Center;
    private boolean isAnimating;
    private int animationCount;

    //path search booleans
    private boolean isSearching;
    private boolean startSearching;
    private boolean endSearching;
    private int searchCount=0;
    private boolean isNavigating;
    //start node
    private Shape startPoint;
    private FloorNumber startPointFloor;
    //end node
    private Shape endPoint;
    private FloorNumber endPointFloor;

    Node startNode;
    Node endNode;
    //direction button
    private JFXButton UP;
    private JFXButton DOWN;
    //the floor they point to
    private PathViewer upPath;
    private PathViewer downPath;

    //Buttons to link next and previous floors
    private JFXButton NEXT;
    private JFXButton PREV;


    @FXML
    private ChoiceBox<Node> startNodeChoice;
    @FXML
    private ChoiceBox<Node> endNodeChoice;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private JFXButton textDirectionButton;
    @FXML
    private ScrollPane mapScrollPane;
    @FXML
    private JFXSlider slideBarZoom;
    @FXML
    private AnchorPane buttonHolderPane;
    @FXML
    private MenuButton startTypeMenu;
    @FXML
    private MenuButton startFloorMenu;
    @FXML
    private MenuButton endTypeMenu;
    @FXML
    private MenuButton endFloorMenu;
    @FXML
    private JFXListView<String> directionsList;
    @FXML
    private JFXTextField startTextField;
    @FXML
    private JFXTextField endTextField;
    @FXML
    private JFXListView<Node> startNodeOptionList;
    @FXML
    private JFXListView<Node> endNodeOptionList;
    @FXML
    private JFXButton btnReverse;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXTabPane startTabPane;
    @FXML
    private JFXTabPane endTabPane;
    @FXML
    private Tab startTextTab;
    @FXML
    private Tab endTextTab;
    @FXML
    private Tab startTypeTab;
    @FXML
    private Tab endTypeTab;
    @FXML
    private ImageView qrImageView;
    private Pane qrPane;

    private JFXNodesList textDirectionDropDown;


    //Methods start here
    public void init() {
        map = HospitalMap.getMap();
        shapes = new ArrayList<Shape>();
        paths = new ArrayList<PathViewer>();
        currentFloor = FloorNumber.FLOOR_ONE;

        mapViewer = new MapViewer(this, parent);
        mapPane = mapViewer.getMapPane();
        mapScrollPane = mapViewer.getMapScrollPane();
        mapScrollPane.setPannable(true);
        //set up floor variables
        floors = new ArrayList<FloorNumber>();

        mainAnchorPane.getChildren().add(0, mapViewer.getMapViewerPane());

        animationCount=0;

        //set up mapPane buttons.
        NEXT=new JFXButton();
        PREV=new JFXButton();
        NEXT.setVisible(false);
        PREV.setVisible(false);
        mapPane.getChildren().add(NEXT);
        mapPane.getChildren().add(PREV);


        arrow = new Pane();
        Image arrowImage = new Image("images/arrow.png");
        ImageView arrowView = new ImageView(arrowImage);
        arrowView.setFitHeight(ARROW_SIZE);
        arrowView.setFitWidth(ARROW_SIZE);
        arrow.setVisible(true);
        arrow.getChildren().add(arrowView);

        pathTransition = new PathTransition();

        //add listeners
        startTextField.setOnKeyPressed( e -> searchText(e, startTextField, startNodeOptionList));
        endTextField.setOnKeyPressed(e -> searchText(e, endTextField, endNodeOptionList));
        startNodeOptionList.prefWidthProperty().bind(startTextField.widthProperty());
        endNodeOptionList.prefWidthProperty().bind(endTextField.widthProperty());
        startNodeOptionList.setOnMouseClicked( e -> suggestionPressed(e, startTextField, startNodeOptionList));
        endNodeOptionList.setOnMouseClicked( e -> suggestionPressed(e, endTextField, endNodeOptionList));


        directionsList.setOnMouseClicked( event  -> doStuffForTextDirections(currentPath,((ListView) event.getSource()).getSelectionModel().getSelectedIndex(), floors.get(currentFloorIndex)));

        startTextTab.setOnSelectionChanged(e -> {
            startNodeOptionList.setVisible(false);
            startNode = null;
            startTextField.setText("");
        });
        endTextTab.setOnSelectionChanged(e -> {
            endNodeOptionList.setVisible(false);
            endNode = null;
            endTextField.setText("");
        });

        qr = new QRCodeGenerator();

        //position map
        Center= new ArrayList<>();
        Center.add(1500.0);
        Center.add(850.0);

        //init start and end nodes
        startPoint=getPoint(0,0);
        startPoint.setVisible(false);
        mapPane.getChildren().add(startPoint);//add to mapPane
        startPointFloor=currentFloor;

        endPoint=getPoint(0,0);
        endPoint.setVisible(false);
        mapPane.getChildren().add(endPoint);//add to mapPane
        endPointFloor=currentFloor;

        //using animation to update position
        AnimationTimer zoomPath= new AnimationTimer(){
            @Override
            public void handle(long now) {
                /**
                if(mapViewer.scaleChanged==true){
                    mapViewer.scaleChanged=false;
                    animationCount=5;
                    Center=mapViewer.getCenter();
                }
                 **/
                if(animationCount>0) {
                    mapViewer.centerView(Center.get(0), Center.get(1));
                    animationCount--;
                }

                if(currentPath!=null){
                  if(currentPath.isAnimating){
                      ArrayList<Double> pos = currentPath.getPos();
                      mapViewer.centerView(pos.get(0), pos.get(1));
                  }

                  if(currentPath.isScaling){
                      double scale =currentPath.getAnimatedScale();
                      scaleMap(scale);
                  }

                }
                //search things
                if(searchCount>0){
                    if(searchCount==1){
                        endSearching=true;
                    }
                    searchCount--;
                }
            }
        };
        zoomPath.start();

        //getting node position on mouse click
        initSearch();
        mapPane.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) {
                double sX=event.getX();
                double sY=event.getY();
                if(currentFloor!=null && isSearching){
                    List<Node> v = map.getNodesInArea((int)sX,(int)sY,currentFloor);
                    if(v.size()>0){
                        //set up search up searching things
                        Node selected = v.get(0);
                        if(startSearching){
                            btnClear.setVisible(true);//set clear button to visible when searching
                            startTabPane.getSelectionModel().select(0);
                            startNode = v.get(0);
                            startTextField.setText(startNode.toString());
                            startSearching=false;
                            //endSearching=true;
                            searchCount=5;
                            //add shape representing
                            setStartNode(selected);

                        }
                        if(endSearching){
                            startTabPane.getSelectionModel().select(0);
                            endNode = v.get(0);
                            endTextField.setText(endNode.toString());
                            endSearching=false;
                            isSearching=false;
                            //do the same thing
                            setEndNode(selected);
                        }

                    }
                }
            }
        });
        //add listeners to start and end choice boxes
        startNodeChoice.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Node> observable,
                              Node oldValue, Node newValue) ->
                        setStartNode(newValue,true));
        endNodeChoice.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Node> observable,
                              Node oldValue, Node newValue) ->
                        setEndNode(newValue,true));


        qrPane = new Pane();
        textDirectionDropDown = new JFXNodesList();
        qrPane.getChildren().add(qrImageView);
        textDirectionDropDown.addAnimatedNode(textDirectionButton);
        textDirectionDropDown.addAnimatedNode(directionsList);
        directionsList.setPrefWidth(textDirectionButton.getPrefWidth());
        textDirectionDropDown.addAnimatedNode(qrPane);
        textDirectionDropDown.setSpacing(5);
        textDirectionDropDown.setVisible(false);
        mainAnchorPane.getChildren().add(textDirectionDropDown);
        AnchorPane.setTopAnchor(textDirectionDropDown,100.0);




    }

    public void onShow(){
        //remove start and end points if available
        if(mapPane.getChildren().contains(startPoint)){
            mapPane.getChildren().remove(startPoint);
        }
        if(mapPane.getChildren().contains(endPoint)){
            mapPane.getChildren().remove(endPoint);
        }
        NEXT.setVisible(false);
        PREV.setVisible(false);
        mapViewer.setScale(1);
        startNodeChoice.setItems(FXCollections.observableArrayList(map.getKioskLocation()));
        //set the default start location to be the kiosk
        startNodeChoice.setValue(map.getKioskLocation());
        //remove any previous paths from the display
        clearPaths();

        startType = "Information";
        startTypeMenu.setText(startType);
        startFloor = map.getKioskLocation().getFloor().getDbMapping();
        startFloorMenu.setText(startFloor);
        startFloorMenu.setDisable(false); //make this false

        endNodeChoice.setDisable(true);
        endNodeChoice.setValue(null);
        endFloorMenu.setDisable(true);
        endFloorMenu.setText("Floor");
        endTypeMenu.setText("Type");

        directionsList.setItems(FXCollections.observableArrayList()); //function implementation

        mapViewer.resetView();
        btnReverse.setVisible(false);//hide button because there is no path
        btnClear.setVisible(false); //Hide button because there is no path
        startNodeOptionList.setVisible(false);
        endNodeOptionList.setVisible(false);
        //reset search boxes

        startNodeChoice.setValue(map.getKioskLocation()); //redundant
        startNodeChoice.setDisable(false);

        startNode = map.getKioskLocation();
        startTextField.setText(startNode.toString());


        initSearch();
        //handle emergencies
        if(map.searchNodes.size()>1){
            startNode=map.searchNodes.get(0);
            endNode=map.searchNodes.get(1);
            thePath=getPath();
            displayPaths(thePath);
            map.searchNodes=new ArrayList<>();//clear search nodes
        }

        mapViewer.resizeSpacers((int)parent.getWidth());

        textDirectionDropDown.setVisible(false);

    }

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    private void initSearch(){
        //setup search variables
        isSearching=true;
        startSearching=true;
        endSearching=false;
        //move tab to search by text
        startTabPane.getSelectionModel().select(0);

    }

    private void searchText(KeyEvent keyEvent, JFXTextField textField, JFXListView<Node> listView){
            KeyCode code = keyEvent.getCode();
            if(code.equals(KeyCode.ENTER)) {
                Node node;
                if(listView.getSelectionModel().selectedItemProperty().isNull().get()){
                    node = listView.getItems().get(0);
                }
                else{
                    node = listView.getSelectionModel().getSelectedItem();
                }
                if(textField.equals(startTextField)){
                    startNode = node;
                    setStartNode(startNode,true);
                }
                else{
                    endNode = node;
                    setEndNode(endNode,true);
                }
                    textField.setText(node.toString());
                    listView.setVisible(false);
                }
            else if(code.equals(KeyCode.DOWN)){
                if(listView.getSelectionModel().getSelectedIndex() == -1) {
                    listView.getSelectionModel().select(0);
                }
                else if(listView.getSelectionModel().getSelectedIndex() <= listView.getItems().size() - 1){
                    listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);
                }
                textField.setText(listView.getSelectionModel().getSelectedItem().toString());
            }
            else if(code.equals(KeyCode.UP)){
                if(listView.getSelectionModel().getSelectedIndex() == -1) {
                    listView.getSelectionModel().select(0);
                }
                else if(listView.getSelectionModel().getSelectedIndex() >= 0){
                    listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() - 1);
                }
                textField.setText(listView.getSelectionModel().getSelectedItem().toString());
            }
            else if(code.equals(KeyCode.ESCAPE)){
                startNodeOptionList.setVisible(false);
                endNodeOptionList.setVisible(false);
            }
            else if(code.isLetterKey() || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                String text = ((JFXTextField) keyEvent.getSource()).getText();
                text = (code.isLetterKey()) ? text + keyEvent.getText() : text.substring(0, text.length()-1);
                if(text.equals("")){
                    listView.setVisible(false);
                }
                else {
                    List<Node> ans = map.getNodesByText(text);
                    if (ans.size() > 10) {
                        listView.getItems().setAll(ans.subList(0, 5));
                        listView.setVisible(true);
                    }
                    if (textField.equals(startTextField)) {
                        startNode = null;
                    } else {
                        endNode = null;
                    }
                }
            }
    }

    private void suggestionPressed(MouseEvent e, JFXTextField textField, JFXListView<Node> listView) {
        Node selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Node node = listView.getSelectionModel().getSelectedItem();
            if (textField.equals(startTextField)) {
                startNode = node;
                setStartNode(startNode,true);
            } else {
                endNode = node;
                setEndNode(endNode,true);
            }
            textField.setText(selected.toString());
            listView.setVisible(false);
        }
    }

    private void setStartNode(Node selected){
        if(selected!=null) {
            if(mapPane.getChildren().contains(startPoint)){
                mapPane.getChildren().remove(startPoint);
            }
            Circle newp = getStartPoint(selected.getX(), selected.getY());
            startPoint = newp;
            startPoint.setVisible(true);
            startPointFloor = selected.getFloor();
            currentFloor = startPointFloor;
            switchToFloor(currentFloor);
            mapViewer.setFloor(startPointFloor);
            currentFloor=startPointFloor;
            mapPane.getChildren().add(startPoint);
            //adjustNodes();
        }
    }
    private void setStartNode(Node selected, boolean center){
        if(selected!=null) {
            setStartNode(selected);
            mapViewer.centerView(selected.getX(), selected.getY());
        }
    }
    private void setEndNode(Node selected, boolean center){
        if(selected!=null) {
            setEndNode(selected);
            mapViewer.centerView(selected.getX(), selected.getY());
        }
    }

    private void setEndNode(Node selected){
        if(selected!=null) {
            if(mapPane.getChildren().contains(endPoint)){
                mapPane.getChildren().remove(endPoint);
            }
            Circle newp = getPoint(selected.getX(), selected.getY());
            endPoint = newp;
            endPoint.setVisible(true);
            endPointFloor = selected.getFloor();
            currentFloor = endPointFloor;
            switchToFloor(currentFloor);
            mapViewer.setFloor(endPointFloor);
            currentFloor=endPointFloor;
            mapPane.getChildren().add(endPoint);
            //adjustNodes();
        }
    }
    private void switchToFloor(FloorNumber f){
        for(PathViewer p: paths){
            if(p.getFloor()==f){
                switchPath(p);
                break;
            }
        }
    }
    private JFXButton getUP(){
        Image upImage = new Image("images/Icons/dropUp.png");
        ImageView upView = new ImageView(upImage);
        upView.setFitHeight(BUTTON_SIZE);
        upView.setFitWidth(BUTTON_SIZE);
        JFXButton up = new JFXButton("",upView);


        return up;
    }
    private JFXButton getDown(){
        //initialize down direction button
        Image downImage = new Image("images/Icons/dropDown.png");
        ImageView downView = new ImageView(downImage);
        downView.setFitHeight(BUTTON_SIZE);
        downView.setFitWidth(BUTTON_SIZE);
        JFXButton down = new JFXButton("",downView);

        return down;
    }

    private Path getPath(){
        Node s;
        Node e;
        if(startTabPane.getSelectionModel().getSelectedIndex()==0){
            s= startNode;
        }
        else{
            s=startNodeChoice.getValue();
        }
        if(endTabPane.getSelectionModel().getSelectedIndex()==0){
            e= endNode;
        }
        else{
            e=endNodeChoice.getValue();
        }
        wholePath = new PathViewer(map.findPath(s, e));
        return map.findPath(s,e);
    }

    private void controlScroller(PathViewer p){
        double x = p.getCenter().get(0);
        double y = p.getCenter().get(1);
        double cx =mapViewer.getCenter().get(0);
        double cy = mapViewer.getCenter().get(1);
        /**
        if(currentPath.hasAnimated){
            cx=x;
            cy=y;
        }
        else{
            cx=Center.get(0);
            cy=Center.get(1);
        }
         **/
        cx=Center.get(0);
        cy=Center.get(1);

        //set new Center
        Center.set(0,x);
        Center.set(1,y);
        p.initAnimation(cx,cy,x,y);
    }

    private void SetPaths(Path path){
        clearPaths();
        Node node = null;
        FloorNumber current=null; // pointer to the current node of the floor
        Path pathToAdd = new Path();
        for(int i=0;i<path.getPath().size();i++){
            //get first floor
            node = path.getPath().get(i);
            if(node.getFloor()!=current){
                //create a new path for floors
                current = node.getFloor();//get new floor
                floors.add(current);
                if(i==0){
                    currentFloor=current;
                }
                else{
                    paths.add(new PathViewer(pathToAdd));
                    pathToAdd = new Path();
                }
                //add to path
               pathToAdd.addToPath(node);
                //add point for current node
            }
            else{
                //add to path
                pathToAdd.addToPath(node);
            }

        }
        paths.add(new PathViewer(pathToAdd));
        //set current Path
        if(paths.size()>0){
            currentPath=paths.get(0);
            currentFloor=floors.get(0);
        }
        //set up next and previous paths
        for(int i=0;i<paths.size();i++){
            if(i>0){
                //set up elevators
                paths.get(i).setPrevious(paths.get(i-1));
                paths.get(i-1).setnext(paths.get(i));
            }
        }
    }

    //-----------------------ANIMATIONS START--------------------------//
    private Circle getPoint(int x, int y){
        Circle c = new AnimatedCircle();
        c.setCenterX(x);
        c.setCenterY(y);
        c.setVisible(true);
        c.setRadius(7);
        return c;
    }
    private Circle getStartPoint(int x, int y){
        Circle c = new AnimatedCircle();
        c.setRadius(7);
        c.setCenterX(x);
        c.setCenterY(y);
        c.setVisible(true);
        c.setFill(Color.rgb(0,84,153));
        //c.setStroke(Color.rgb(40,40,60));
        //c.setStrokeWidth(3);
        return c;
    }

    private void clearShapes(){
        for(Shape s : shapes){
            s.setVisible(false);
            mapPane.getChildren().remove(s);
        }
        pathTransition.stop();
        arrow.setVisible(false);
        mapPane.getChildren().remove(arrow);
        shapes = new ArrayList<>();
    }

    private void displayPath(PathViewer path){
        //clear path
        //display an entire path if available
        if(path.getNodes().size()>0){
            animatePath(path);
        }
        //set directions buttons to false
        NEXT.setVisible(false);
        PREV.setVisible(false);
        //reposition buttons to nodes
        double calSize = BUTTON_SIZE/mapViewer.getScale();
        //calSize = Math.pow(calSize,0.5);
        if(path.getnext()!=null){
            PathViewer next = path.getnext();
            if(next.getFloor().getNodeMapping()>path.getFloor().getNodeMapping()){
                mapPane.getChildren().remove(NEXT);
                NEXT =getUP();
                NEXT.setVisible(true);
                NEXT.setLayoutX(path.getNodes().get(path.getNodes().size()-1).getX()+calSize/2*mapViewer.getScale());
                NEXT.setLayoutY(path.getNodes().get(path.getNodes().size()-1).getY()+calSize/2*mapViewer.getScale());
                NEXT.setOnAction(e -> updatePath(next) );
                upPath=next;
                mapPane.getChildren().add(NEXT);
            }
            else{
                mapPane.getChildren().remove(NEXT);
                NEXT=getDown();
                NEXT.setVisible(true);
                NEXT.setLayoutX(path.getNodes().get(path.getNodes().size()-1).getX()+calSize/2*mapViewer.getScale());
                NEXT.setLayoutY(path.getNodes().get(path.getNodes().size()-1).getY()+calSize/2*mapViewer.getScale());
                NEXT.setOnAction(e -> updatePath(next) );

                downPath=next;
                mapPane.getChildren().add(NEXT);
            }
        }
        if(path.getPrevious()!=null){
            PathViewer prev = path.getPrevious();
            if(prev.getFloor().getNodeMapping()>path.getFloor().getNodeMapping()){
                int i=0;
                mapPane.getChildren().remove(PREV);
                PREV=getUP();
                PREV.setVisible(true);
                PREV.setLayoutX(path.getNodes().get(0).getX()+calSize/2*mapViewer.getScale());
                PREV.setLayoutY(path.getNodes().get(0).getY()+calSize/2*mapViewer.getScale());
                PREV.setOnAction(e -> updatePath(prev) );
                upPath=prev;
                mapPane.getChildren().add(PREV);
            }
            else{
                mapPane.getChildren().remove(PREV);
                PREV=getDown();
                PREV.setVisible(true);
                PREV.setLayoutX(path.getNodes().get(0).getX()+calSize/2*mapViewer.getScale());
                PREV.setLayoutY(path.getNodes().get(0).getY()+calSize/2*mapViewer.getScale());
                PREV.setOnAction(e -> updatePath(prev) );
                downPath=prev;
                mapPane.getChildren().add(PREV);
            }
        }
    }

    private void animatePath(PathViewer path){
        //represent first and last nodes with animated circles
        Circle newp = getStartPoint(path.getNodes().get(0).getX(),path.getNodes().get(0).getY());
        mapPane.getChildren().add(newp);
        path.addShape(newp);
        shapes.add(newp);

        //add to last node
        Circle lastp = getPoint(path.getNodes().get(path.getNodes().size()-1).getX(),path.getNodes().get(path.getNodes().size()-1).getY());
        mapPane.getChildren().add(lastp);
        path.addShape(lastp);
        shapes.add(lastp);

        if(path.getNodes().size() > 1) {
            //animation that moves the indicator
            pathTransition = new PathTransition();

            //path to follow
            javafx.scene.shape.Path p = new javafx.scene.shape.Path();
            p.setStroke(Color.NAVY);
            p.setStrokeWidth(LINE_STROKE);

            //add shapes currentPath and shapes
            path.addShape(p);
            mapPane.getChildren().addAll(p, arrow);
            shapes.add(p);
            arrow.setVisible(true);

            //starting point defined by MoveTo
            p.getElements().add(new MoveTo(path.getNodes().get(0).getX(), path.getNodes().get(0).getY()));

            //line movements along drawn lines
            for (int i = 1; i < path.getNodes().size(); i++) {
                p.getElements().add(new LineTo(path.getNodes().get(i).getX(), path.getNodes().get(i).getY()));
            }

            //define the animation actions
            pathTransition.setDuration(Duration.millis(p.getElements().size() * 800));//make speed constant
            pathTransition.setNode(arrow);
            pathTransition.setPath(p);
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            pathTransition.setCycleCount(Transition.INDEFINITE);
            pathTransition.play();
        }
    }

    private javafx.scene.shape.Path pathToHighlight;
    private Circle circleToHighlight;


    private void highlightPath(PathViewer pathViewer, int directionIndex){
        Path path = pathViewer.getPath();
        path.findDirections();
        ArrayList<Node> nodesByDirections = path.getPathByDirections().get(directionIndex);
        if(pathToHighlight != null){
            mapPane.getChildren().remove(pathToHighlight);
        }
        pathToHighlight = new javafx.scene.shape.Path();

        if(currentFloor.equals(nodesByDirections.get(nodesByDirections.size()-1).getFloor()) && currentFloor.equals(nodesByDirections.get(0).getFloor())){
            mapPane.getChildren().add(1, pathToHighlight);
            shapes.add(pathToHighlight);
            arrow.toFront();
        }
        pathToHighlight.setStroke(Color.RED);
        pathToHighlight.setStrokeWidth((LINE_STROKE+7) * (1/mapViewer.getScale()));
        int startNode = 0;
        int endNode;
        for(int i=0;i<pathViewer.getPath().getPath().size();i++){
            if(pathViewer.getPath().getPath().get(i).equals(nodesByDirections.get(0))) startNode = i;
        }
        endNode = startNode + nodesByDirections.size();
        pathToHighlight.getElements().add(new MoveTo(pathViewer.getPath().getPath().get(startNode).getX(), pathViewer.getPath().getPath().get(startNode).getY()));
        for(int i=startNode+1;i<endNode;i++){
            pathToHighlight.getElements().add(new LineTo(pathViewer.getPath().getPath().get(i).getX(), pathViewer.getPath().getPath().get(i).getY()));
        }
        if(circleToHighlight != null){
            mapPane.getChildren().remove(circleToHighlight);
        }
        if(pathToHighlight.getElements().size() == 2 && !nodesByDirections.get(0).getFloor().equals(nodesByDirections.get(1).getFloor()) ||
                pathToHighlight.getElements().size() == 1){
            circleToHighlight = new Circle();
            circleToHighlight.setCenterX(nodesByDirections.get(0).getX());
            circleToHighlight.setCenterY(nodesByDirections.get(0).getY());
            circleToHighlight.fillProperty().setValue(Color.RED);
            circleToHighlight.setRadius(6 * (1/mapViewer.getScale()));
            circleToHighlight.setStroke(Color.RED);
            mapPane.getChildren().add(circleToHighlight);
            shapes.add(circleToHighlight);
            circleToHighlight.toFront();
            arrow.toFront();
        }
    }

    private void switchFloorsOnTextDirection(int index, FloorNumber floorNumber){
        if(index == directionsList.getItems().size() - 1 && currentFloorIndex < floors.size() - 1){
            currentFloorIndex++;
            mapViewer.floorButtonPressed(floors.get(currentFloorIndex), currentFloorIndex);
        }
    }

    private void doStuffForTextDirections(PathViewer pathViewer, int index, FloorNumber floorNumber){
        highlightPath(pathViewer, index);
        switchFloorsOnTextDirection(index, floorNumber);
    }

    //method to switch between paths when toggling between floors
    private void switchPath(PathViewer path){
        clearShapes();
        currentFloor=path.getFloor();
        setScale(path);
        displayPath(path);
        controlScroller(path);//reposition map
    }

    public void clearPaths(){
        for(Shape s: shapes){
            s.setVisible(false);
            mapPane.getChildren().remove(s);
        }

        arrow.setVisible(false);
        mapPane.getChildren().remove(arrow);

        //clear all lines and paths
        floors= new ArrayList<>();
        shapes=new ArrayList<>();
        paths=new ArrayList<>();
        pathTransition.stop();
        arrow.setVisible(false);
        mapPane.getChildren().removeAll(arrow);
    }
    //-----------------------ANIMATIONS END--------------------------//

    //-------------------------MAP SCALE START--------------------------//
    public void setScale(PathViewer path){
        double scale = path.getScale();
        scale = mapViewer.checkScale(scale);
        path.initScaling(mapViewer.getScale(),scale); //animate the scaling process
    }

    public void scaleMap(double scale){
        mapViewer.setScale(scale);
        mapViewer.setZoom(scale);
        for(Shape s : shapes){
            if(s instanceof AnimatedCircle){
                s.setScaleX(1/scale);
                s.setScaleY(1/scale);
            }
            else{
                s.setStrokeWidth(LINE_STROKE/scale);
            }
        }
        //scale mapPane children
        arrow.setScaleX(1/scale);
        arrow.setScaleY(1/scale);
        double btnscale = Math.pow((1/scale),0.5);
        NEXT.setScaleX(btnscale * .75);
        NEXT.setScaleY(btnscale * .75);
        PREV.setScaleX(btnscale * .75);
        PREV.setScaleY(btnscale * .75);
    }

    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        scaleMap(slideBarZoom.getValue()+0.2);
        currentPath.hasAnimated=false;
    }

    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){
        scaleMap(slideBarZoom.getValue()-0.2);
        currentPath.hasAnimated=false;
    }
    //-------------------------MAP SCALE END--------------------------//

    private void displayPaths(Path thePath){
        SetPaths(thePath);
        mapViewer.setButtonsByFloor(floors);
        currentFloorIndex = 0;
        ArrayList<String> directions = thePath.findDirections();
        ArrayList<String> directionsByFloor = thePath.getDirectionsByFloor().get(0);
        directionsList.setItems(FXCollections.observableList(directionsByFloor));
        textDirectionDropDown.setVisible(true);
        //textDirectionDropDown.animateList(false);
        try {
            qr.writeQRList(directions, "src/images/qr");
            while(!qr.isComplete()){
            }
            File f = new File("/image/qr/jpg");
            qrImageView.setImage(new Image("file:src/images/qr.jpg"));
        }
        catch (NonValidQRCodeMessageException e){
            System.out.println("QR too long");
        }
        currentFloor=paths.get(0).getFloor();//set the current floor
        switchPath(paths.get(0));
    }

    public void enterPressed(ActionEvent e) throws InvalidNodeException {
        if(((startNodeChoice.getValue()!= null && startTypeTab.isSelected()) || (startNode != null && startTextTab.isSelected()) ) &&
                ((endNodeChoice.getValue() != null && endTypeTab.isSelected()) || (endNode != null && endTextTab.isSelected()) )) {
            btnReverse.setVisible(true);//make reverse button visible
            btnClear.setVisible(true);//make clear button visible
            thePath = getPath();
            displayPaths(thePath);
            isSearching=false;
            //remove start and end nodes if contained in there
            if(mapPane.getChildren().contains(startPoint)){
                mapPane.getChildren().remove(startPoint);
            }
            if(mapPane.getChildren().contains(endPoint)){
                mapPane.getChildren().remove(endPoint);
            }
        }
        else{
            ShakeTransition shake = new ShakeTransition();
            if(startNodeChoice.getValue() == null){
                shake.shake(startNodeChoice);
            }
            if(startNode == null){
                shake.shake(startTextField);
            }
            if(endFloorMenu.getText().equals("Floor")){
                shake.shake(endFloorMenu);
            }
            if(endTypeMenu.getText().equals("Type")){
                shake.shake(endTypeMenu);
            }
            if(endNodeChoice.getValue() == null){
                shake.shake(endNodeChoice);
            }
            if(endNode == null){
                shake.shake(endTextField);
            }
        }
    }

    public void reversePressed(ActionEvent e){
        if(thePath!=null){
            currentFloorIndex = 0;
            thePath = thePath.getReverse();
            displayPaths(thePath);
        }
    }

    public void clearPressed(ActionEvent e) {
        onShow();
        //center map at kiosk location
        mapViewer.centerView(map.getKioskLocation().getX(),map.getKioskLocation().getY());

    }

    public void cancelPressed(ActionEvent e) {
        clearPaths();
        parent.setScreen(ScreenController.MainID,"RIGHT");
    }

    public void update(Observable o, Object arg){
        if(arg instanceof PathID){
            PathID ID = (PathID) arg;
            if(ID.getID() != -1) {
                if(paths.get(ID.getID())!=currentPath){//if the path is changes
                    currentPath.hasAnimated=false; //it is probably not well positioned
                }
                currentPath = paths.get(ID.getID());
                currentFloorIndex = ID.getID();
                directionsList.setItems(FXCollections.observableList(thePath.getDirectionsByFloor().get(currentFloorIndex)));
                
                currentFloor = currentPath.getFloor();
                switchPath(currentPath);
            }
            else{
                currentFloor=ID.getFloor();
            }
            adjustNodes();

        }
    }
    public void updatePath(PathViewer p){
        currentFloor=p.getFloor();
        mapViewer.setFloor(currentFloor);
        currentPath=p;
        switchPath(currentPath);

    }

    private void adjustNodes(){
        if(startPointFloor==currentFloor){
            startPoint.setVisible(true);
        }
        else{
            startPoint.setVisible(false);
        }
        //adjust end nodes
        if(endPointFloor==currentFloor){
            endPoint.setVisible(true);
        }
        else{
            endPoint.setVisible(false);
        }
    }

    public void questionPressed(ActionEvent e) {
        parent.setScreen(ScreenController.DirectionHelpID, "HELP_IN");
    }
    //-----------------------NODE SELECT END--------------------------//

    public void startTypeSelected(ActionEvent e){
        startType = ((MenuItem)e.getSource()).getText();
        startTypeMenu.setText(startType);
        startFloorMenu.setDisable(false);
        if(!startFloor.equals("")){
            startChosen();
        }
    }

    public void startFloorSelected(ActionEvent e){
        startFloor = ((MenuItem)e.getSource()).getText();
        startFloorMenu.setText(startFloor);
        startChosen();
    }

    private void startChosen(){
        final String f = Node.getFilterText(startType);
        if(startFloor.equals("ALL")){
            startNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f))));
        }
        else{
            FloorNumber floor = FloorNumber.fromDbMapping(startFloor);
            startNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f) && n.getFloor().equals(floor))));
        }
        startNodeChoice.setDisable(false);
        if(startNodeChoice.getValue()!=null){
            setStartNode(startNodeChoice.getValue());
        }
    }

    public void endTypeSelected(ActionEvent e){
        endType = ((MenuItem)e.getSource()).getText();
        endTypeMenu.setText(endType);
        endFloorMenu.setDisable(false);
        if(!endFloor.equals("")){
            endChosen();
        }
    }

    public void endFloorSelected(ActionEvent e){
        endFloor = ((MenuItem)e.getSource()).getText();
        endFloorMenu.setText(endFloor);
        endChosen();
    }

    private void endChosen(){

        final String f = Node.getFilterText(endType);
        if(endFloor.equals("ALL")){
            endNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f))));
        }
        else{
            FloorNumber floor = FloorNumber.fromDbMapping(endFloor);
            endNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f) && n.getFloor().equals(floor))));
        }
        endNodeChoice.setDisable(false);
        if(endNodeChoice.getValue()!=null){
            setEndNode(endNodeChoice.getValue());
        }
    }
    //-----------------------NODE SELECT END--------------------------//
}
