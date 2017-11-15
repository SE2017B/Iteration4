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
    private ChoiceBox<Node> startChoice;

    @FXML
    private ChoiceBox<Node> endChoice;

    @FXML
    private Pane mapPane;


    //Methods start here
    public void init()
    {
        path = new ArrayList<Node>();
        lines = new ArrayList<Line>();
        onShow();

    }

    public void onShow(){
        //Update the nodes in the map
        ArrayList<Node> nodes = parent.getEngine().getMap().getNodesAsArrayList();

        //update the items in the checklist
        startChoice.setItems(FXCollections.observableList(nodes));
        endChoice.setItems(FXCollections.observableList(nodes));

        //remove any previous paths from the display
        for (Line line: lines) {
            line.setVisible(false);
            mapPane.getChildren().remove(line);
        }
        lines = new ArrayList<>();
    }


    public void enterPressed(ActionEvent e)
    {
        System.out.println("Enter Pressed");
        //Remove last path from screen
        for(Line line : lines){
            line.setVisible(false);
        }
        path = parent.getEngine().findPath(startChoice.getValue(),endChoice.getValue());
        System.out.println(path);
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

}
