package controllers;

import a_star.HospitalMap;
import a_star.Node;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import service.ServiceRequest;
import service.Staff;

import java.util.ArrayList;

public class PathController implements ControllableScreen{
    private ScreenController parent;
    private HospitalMap map;
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

//    @FXML
//    private MenuButton startMenuB;
//
//    @FXML
//    private MenuButton endMenuB;

    @FXML
    private ChoiceBox<Node> startChoice;

    @FXML
    private ChoiceBox<Node> endChoice;

    @FXML
    private Pane mapPane;


    //Methods start here
    public void init()
    {
        lines = new ArrayList<Line>();
        map = new HospitalMap();
        ArrayList<Node> nodes = map.getNodesAsArrayList();
        startChoice.setItems(FXCollections.observableList(nodes));
        endChoice.setItems(FXCollections.observableList(nodes));
    }

    public void onShow(){
        for (Line line: lines) {
            line.setVisible(false);
            mapPane.getChildren().remove(line);
        }
        lines = new ArrayList<>();
    }

    public void startSelected(ActionEvent e){
//        //Start = ServiceRequest.getStaffForServiceType(serviceType);
//        ArrayList<String> destinations = new ArrayList<String>();
//        for( : destinations){
//            destinations.add(member.getFullName());
//        }
//        if(destinations.size() != 0) {
//            System.out.println(FXCollections.observableList(destinations));
//            startChoice.setItems(FXCollections.observableList(destinations));
//            startChoice.setDisable(false);
//        }

    }

    public void enterPressed(ActionEvent e)
    {
        System.out.println("Enter Pressed");
        //Remove last path from screen
        for(Line line : lines){
            line.setVisible(false);
        }
        path = map.findPath(startChoice.getValue(),endChoice.getValue());
        if(path.size() != 0) {
            for (int i = 0; i < path.size() - 1; i++) {
                Line line = new Line();
                Node start = path.get(i);
                Node end = path.get(i + 1);
                line.setLayoutX(start.getX());
                line.setLayoutY(start.getY());

                line.setEndX(end.getX() - start.getX());
                line.setEndY(end.getY() - start.getY());

                line.setVisible(true);
                line.setStrokeWidth(5);
                mapPane.getChildren().add(line);
                lines.add(line);

            }
        }
        else{
            System.out.println("ERROR: No Path Found");
        }
    }
    public void cancelPressed(ActionEvent e)
    {
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.MainID);
    }

    public void stairsPressed(ActionEvent e)
    {
        System.out.println("Checked off stairs");
    }

//    public void startSelected(ActionEvent e)
//    {
//        System.out.println("Start Selected");
//        String Start = ((MenuItem) e.getSource()).getText();
//        startMenuB.setText(Start);
//
//    }
//
//    public void endSelected(ActionEvent e)
//    {
//        System.out.println("End Selected");
//        String End = ((MenuItem) e.getSource()).getText();
//        endMenuB.setText(End);
//    }

}
