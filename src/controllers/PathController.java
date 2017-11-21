/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXTextField;
import exceptions.InvalidNodeException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import map.HospitalMap;
import map.Node;
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
    private JFXTextField startChoice;

    @FXML
    private JFXTextField endChoice;

    @FXML
    private Pane mapPane;

    @FXML
    private Label failLabel;


    //Methods start here
    public void init()
    {
        path = new ArrayList<Node>();
        lines = new ArrayList<Line>();
        onShow();

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

    public void diplayPath(ArrayList<Node> path){
        if( path.size() <= 1){
            System.out.println("NO PATH FOUND");
            failLabel.setVisible(true);
        } else if(path.size() > 1) {
            failLabel.setVisible(false);
            for (int i = 0; i < path.size() - 1; i++) {
                Line line = new Line();
                Node start = path.get(i);
                Node end = path.get(i + 1);
                line.setLayoutX((start.getX())/2);
                line.setLayoutY((start.getY())/2);


                line.setEndX((end.getX() - start.getX())/2);
                line.setEndY((end.getY() - start.getY())/2);

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
    public void clearPaths(){
        for(Line line : lines){
            line.setVisible(false);
            mapPane.getChildren().remove(line);
        }
    }


    public void enterPressed(ActionEvent e) throws InvalidNodeException
    {
        System.out.println("Enter Pressed");
        //Remove last path from screen
        clearPaths();
        //draw some random lines for now
        Line line = new Line();
        line.setLayoutX(100);
        line.setLayoutY(100);
        line.setEndX(300);
        line.setEndY((300));
        line.setVisible(true);
        line.setStrokeWidth(5);
        mapPane.getChildren().add(line);
        lines.add(line);

        Line line1 = new Line();
        line1.setLayoutX(0);
        line1.setLayoutY(0);
        line1.setEndX(1000);
        line1.setEndY((1000));
        line1.setVisible(true);
        line1.setStrokeWidth(5);
        mapPane.getChildren().add(line1);
        lines.add(line1);

        Image img = new Image("@../images/02_thesecondfloor.png"); //create new image
        ImageView imgView = new ImageView(img); //create new image view pane
        imgView.setFitWidth(2000);
        imgView.setFitHeight(3000);
        imgView.setVisible(true);
        mapPane.getChildren().add(imgView);

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
