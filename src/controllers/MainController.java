/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import map.Path;
import ui.AnimatedCircle;
import ui.MapViewer;
import ui.PathID;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MainController implements ControllableScreen, Observer{
    private ScreenController parent;
    private MapViewer mapViewer;
    private AnimatedCircle kioskIndicator;
    private FloorNumber currentFloor;
    private HospitalMap map;
    private ArrayList<Circle> indicators;
    private Pane mapPane;

    @FXML
    private HBox dropDownBox;

    @FXML
    private JFXButton filterHeaderButton;

    @FXML
    private JFXButton nearestHeaderButton;

    @FXML
    private GridPane menuGridPane;

    private JFXButton bathFilterButton;

    private JFXButton exitFilterButton;

    private JFXButton elevatorFilterButton;

    private JFXButton retailFilterButton;

    private JFXButton stairsFilterButton;

    private JFXButton bathNearestButton;

    private JFXButton exitNearestButton;

    private JFXButton elevatorNearestButton;

    private JFXButton retailNearestButton;

    private JFXButton stairsNearestButton;

    private JFXNodesList filterList;

    private JFXNodesList nearestList;

    //Tutorial List
    private JFXNodesList questionList;

    //Tutorial Buttons
    public JFXButton questionButton;

    private JFXButton tutorialHelpButton;

    private JFXButton feedbackAboutHelpButton;
    
    @FXML
    private AnchorPane mainAnchorPane;

    public void init() {
        currentFloor = FloorNumber.FLOOR_ONE;
        map = HospitalMap.getMap();
        mapViewer = new MapViewer(this, parent);
        mapViewer.setFloor(currentFloor);
        mapPane = mapViewer.getMapPane();
        indicators = new ArrayList<>();
        kioskIndicator = new AnimatedCircle();
        kioskIndicator.setCenterX(map.getKioskLocation().getX()*mapViewer.getScale());
        kioskIndicator.setCenterY(map.getKioskLocation().getY()*mapViewer.getScale());
        kioskIndicator.setVisible(false);
        kioskIndicator.setFill(Color.rgb(0,84,153));
        kioskIndicator.setStroke(Color.rgb(40,40,60));
        kioskIndicator.setStrokeWidth(3);
        initButtons();

        mapPane.getChildren().add(kioskIndicator);
        mainAnchorPane.getChildren().add(0,mapViewer.getMapViewerPane());
        mapViewer.getMapScrollPane().setPannable(true);

        mapViewer.centerView((int)kioskIndicator.getCenterX(), (int)kioskIndicator.getCenterY());

        mainAnchorPane.prefWidthProperty().bind(parent.prefWidthProperty());
        mainAnchorPane.prefHeightProperty().bind(parent.prefHeightProperty());
    }

    private int BUTTON_WIDTH = 100;
    private int BUTTON_HEIGHT = 60;
    private int BUTTON_SPACING = 65;


    private JFXButton filterSpacer = new JFXButton();
    private JFXButton nearestSpacer = new JFXButton();
    private JFXButton questionSpacer = new JFXButton();
    private void initButtons(){
        filterList = new JFXNodesList();
        nearestList = new JFXNodesList();
        questionList = new JFXNodesList();
        ArrayList<JFXButton> buttons = new ArrayList<>();
        bathFilterButton = new JFXButton();
        exitFilterButton = new JFXButton();
        elevatorFilterButton = new JFXButton();
        retailFilterButton = new JFXButton();
        stairsFilterButton = new JFXButton();
        bathNearestButton = new JFXButton();
        exitNearestButton = new JFXButton();
        elevatorNearestButton = new JFXButton();
        retailNearestButton = new JFXButton();
        stairsNearestButton = new JFXButton();

        //Tutorial init
        tutorialHelpButton = new JFXButton();
        feedbackAboutHelpButton = new JFXButton();
        
        buttons.add(bathFilterButton);
        buttons.add(exitFilterButton);
        buttons.add(elevatorFilterButton);
        buttons.add(retailFilterButton);
        buttons.add(stairsFilterButton);
        buttons.add(bathNearestButton);
        buttons.add(exitNearestButton);
        buttons.add(elevatorNearestButton);
        buttons.add(retailNearestButton);
        buttons.add(stairsNearestButton);
        //Tutorial add
        buttons.add(tutorialHelpButton);
        buttons.add(feedbackAboutHelpButton);

        filterList.addAnimatedNode(filterSpacer);
        nearestList.addAnimatedNode(nearestSpacer);

        //Tutorial question spacer
        questionList.addAnimatedNode(questionSpacer);


        filterHeaderButton.setOnAction(e ->  {
            if(filterList.isExpanded()){
                hideFilterButtons(e);
            }
            else{
                showFilterButtons(e);
            }
                });
        nearestHeaderButton.setOnAction(e -> {
            if(nearestList.isExpanded()){
                hideNearestButtons(e);
            }
            else{
                showNearestButtons(e);
            }
        });

        questionButton.setOnAction(e -> {
            if(questionList.isExpanded()) {
                hideQuestionButtons(e);
            }
            else {
                showQuestionButtons(e);
            }
        });


        for(int i = 0; i< 12; i++){
            JFXButton b  = buttons.get(i);
            b.setPrefSize(BUTTON_WIDTH,BUTTON_HEIGHT);
            if(i < 5){
                b.setOnAction(e -> filterButtonPressed(e));
                filterList.addAnimatedNode(b);
            }
            else if((5 <= i) && (i <10)) {
                b.setOnAction(e -> nearestPressed(e));
                nearestList.addAnimatedNode(b);
            }
            else {
                b.setOnAction(e -> questionPressed(e));
                questionList.addAnimatedNode(b);
            }
        }
        filterList.setSpacing(BUTTON_SPACING);
        nearestList.setSpacing(BUTTON_SPACING);
        //Tutorial
        questionList.setSpacing(BUTTON_SPACING);
        filterList.setVisible(false);
        nearestList.setVisible(false);
        //Tutorial
        questionList.setVisible(false);

        //hide when they disappear
        filterList.getListAnimation(true).setOnFinished( e -> filterList.setVisible(false));
        nearestList.getListAnimation(true).setOnFinished( e -> nearestList.setVisible(false));
        //Tutorial
        questionList.getListAnimation(true).setOnFinished(e -> questionList.setVisible(false));

        filterList.setRotate(270); //open to the right
        nearestList.setRotate(270);
        //Tutorial
        questionList.setRotate(270);

        mainAnchorPane.getChildren().addAll(filterList, nearestList, questionList);
        filterList.setLayoutX(30);
        filterList.translateYProperty().bind(parent.prefHeightProperty().subtract(100).divide(3.0).subtract(50));


        nearestList.setLayoutX(30);
        nearestList.setLayoutY(70);

        //Tutorial
        questionList.setLayoutX(30);
        questionList.translateYProperty().bind(parent.prefHeightProperty().subtract(-420).divide(3.0).subtract(-30));

        bathFilterButton.setText("Restroom");
        exitFilterButton.setText("Exit");
        elevatorFilterButton.setText("Elevator");
        retailFilterButton.setText("Retail");
        stairsFilterButton.setText("Stairs");
        bathNearestButton.setText("Restroom");
        exitNearestButton.setText("Exit");
        elevatorNearestButton.setText("Elevator");
        retailNearestButton.setText("Retail");
        stairsNearestButton.setText("Stairs");

        //Tutorial
        tutorialHelpButton.setText("Tutorial");
        feedbackAboutHelpButton.setText("Feedback\n/ About");

    }

    public void onShow(){

        kioskIndicator.setCenterX(map.getKioskLocation().getX());
        kioskIndicator.setCenterY(map.getKioskLocation().getY());
        setFloor(currentFloor);
        mapViewer.centerView((int)kioskIndicator.getCenterX(), (int)kioskIndicator.getCenterY());
        mapViewer.setZoom(0.8);
    }

    //Setters
    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    private void setFloor(FloorNumber floor){
        currentFloor = floor;
        clearPressed(new ActionEvent());
        if (currentFloor.equals(map.getKioskLocation().getFloor())){
            kioskIndicator.setVisible(true);
        }
        else{
            kioskIndicator.setVisible(false);
        }
    }

    //circle helper function for nodeTypePressed
    public Circle makeCircle(Node node){
        AnimatedCircle newIndicator = new AnimatedCircle();
        newIndicator.setCenterX(node.getX());
        newIndicator.setCenterY(node.getY());
        newIndicator.setVisible(true);
        newIndicator.setFill(Color.rgb(153, 63, 62)); //not sure what color this should be
        newIndicator.setStroke(Color.rgb(60, 26, 26));
        newIndicator.setStrokeWidth(3);
        indicators.add(newIndicator);
        newIndicator.getTimeline().stop();
        newIndicator.getTimeline().setCycleCount(5);
        newIndicator.getTimeline().setOnFinished(e -> {
            newIndicator.setVisible(false);
            mapPane.getChildren().remove(newIndicator);
            indicators.remove(newIndicator);
        });

        newIndicator.getTimeline().play();

        return newIndicator;
    }

    public Circle makeClearCircle(Node node){
        Circle newIndicator = makeCircle(node);
        newIndicator.setFill(Color.TRANSPARENT);
        return newIndicator;
    }

    public void update(Observable o, Object arg) {
        if(arg instanceof PathID){
            setFloor(((PathID) arg).getFloor());
        }
    }

    //-----------------------HANDLE ACTIONS START--------------------------//
    public void clearMousePressed(MouseEvent e){
        clearPressed(new ActionEvent());
    }

    ////////////////////////////////////////////////////////////
    /////////////           Find Nearest
    ////////////////////////////////////////////////////////////
    public void clearPressed(ActionEvent e){
        int size = mapPane.getChildren().size();
        mapPane.getChildren().remove(0, size);
        mapPane.getChildren().addAll(mapViewer.getMapImage(), kioskIndicator);
        indicators.clear();
    }

    public void exitTypePressed(ActionEvent e){
        //switch to kiosk floor
        mapViewer.setFloor(map.getKioskLocation().getFloor());
        kioskIndicator.setVisible(true);
        //find nearest node of given type
        Path path = map.findNearest(map.getKioskLocation(), "EXIT");
        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //if nearest node is on same floor as kiosk, make a circle
        if(node.getFloor() == map.getKioskLocation().getFloor()){
            Circle c = makeCircle(node);
            mapPane.getChildren().add(c);
        }
        //draw path to exit in directions screen
        map.searchNodes=new ArrayList<>();
        map.searchNodes.add(map.getKioskLocation());
        map.searchNodes.add(node);
        parent.setScreen(ScreenController.PathID,"LEFT");

    }



    public void nearestPressed(ActionEvent e){
        //switch to kiosk floor
        clearPressed(e);
        hideNearestButtons(e);
        hideFilterButtons(e);
        hideQuestionButtons(e);
        mapViewer.setFloor(map.getKioskLocation().getFloor());
        kioskIndicator.setVisible(true);
        //find nearest node of given type
        Path path;
        if(e.getSource().equals(bathNearestButton)){
            path = map.findNearest(map.getKioskLocation(), "REST");
        }
        else if(e.getSource().equals(elevatorNearestButton)){
            path = map.findNearest(map.getKioskLocation(), "ELEV");
        }
        else if(e.getSource().equals(exitNearestButton)){
            path = map.findNearest(map.getKioskLocation(), "EXIT");
        }
        else if(e.getSource().equals(retailNearestButton)){
            path = map.findNearest(map.getKioskLocation(), "RETL");
        }
        else {
            path = map.findNearest(map.getKioskLocation(), "STAI");
        }

        System.out.println("Nearest Pressed");

        int size = path.getPath().size();
        Node node = path.getPath().get(size - 1);
        //if nearest node is on same floor as kiosk, make a circle
        if(node.getFloor() == map.getKioskLocation().getFloor()){
            Circle c = makeCircle(node);
            mapPane.getChildren().add(c);
        }

    }



    ////////////////////////////////////////////////////////////
    /////////////           Filter
    ////////////////////////////////////////////////////////////
    public void filterButtonPressed(ActionEvent e) {
        clearPressed(new ActionEvent());
        hideNearestButtons(e);
        hideFilterButtons(e);
        hideQuestionButtons(e);
        System.out.println("Filter Pressed");
        JFXButton pressed = (JFXButton) e.getSource();
        ArrayList<Node> filteredNodes = new ArrayList<Node>();
        if (pressed.equals(bathFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(currentFloor) && n.getType().equals("REST")));
        } else if (pressed.equals(exitFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(currentFloor) && n.getType().equals("EXIT")));
        } else if (pressed.equals(elevatorFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(currentFloor) && n.getType().equals("ELEV")));
        } else if (pressed.equals(retailFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(currentFloor) && n.getType().equals("RETL")));
        } else if (pressed.equals(stairsFilterButton)) {
            filteredNodes.addAll( map.getNodesBy(n -> n.getFloor().equals(currentFloor) && n.getType().equals("STAI")));
        }
        for (Node n: filteredNodes)
        {
            Circle c = makeClearCircle(n);
            mapPane.getChildren().add(c);
        }
    }

    public void showFilterButtons(ActionEvent e){
            filterList.setVisible(true);
            filterList.animateList(true);
            filterSpacer.setVisible(false);

    }
    public void hideFilterButtons(ActionEvent e){
        filterList.animateList(false);
        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished( event -> filterList.setVisible(false));
        pause.play();
    }

    public void showNearestButtons(ActionEvent e) {
        nearestList.setVisible(true);
        nearestList.animateList(true);
        nearestSpacer.setVisible(false);
    }

    public void hideNearestButtons(ActionEvent e){
            nearestList.animateList(false);
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished( event -> nearestList.setVisible(false));
            pause.play();
    }

    //Tutorial hide and show
    public void showQuestionButtons(ActionEvent e) {
        questionList.setVisible(true);
        questionList.animateList(true);
        questionSpacer.setVisible(false);
    }

    public void hideQuestionButtons(ActionEvent e) {
        questionList.animateList(false);
        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished(event -> questionList.setVisible(false));
        pause.play();
    }

    //////////////////////////////////////////
    /////////       Tutorial
    //////////////////////////////////////////
    public void questionPressed(ActionEvent e) {
        hideNearestButtons(e);
        hideFilterButtons(e);
        hideQuestionButtons(e);
        System.out.println("Question Pressed");
        JFXButton pressed = (JFXButton) e.getSource();
        if(pressed.equals(tutorialHelpButton)){
            parent.setScreen(ScreenController.HelpID, "HELP_IN");
        }
        else if(pressed.equals(feedbackAboutHelpButton)) {
            parent.setScreen(ScreenController.FeedbackID, "LEFT");
        }
    }


    //when login button is pressed go to login screen
    public void loginPressed(ActionEvent e){
        parent.setScreen(ScreenController.LoginID,"RIGHT");
    }

    //when direction button is pressed go to directions screen
    public void directionPressed(ActionEvent e){
        parent.setScreen(ScreenController.PathID,"LEFT");
    }
}