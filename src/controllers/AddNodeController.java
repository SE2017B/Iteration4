/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import map.Edge;
import map.HospitalMap;
import map.Node;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class AddNodeController implements ControllableScreen {
    private ScreenController parent;


    //node variables
    private String name;
    private String nodeID;
    private String x;
    private String y;
    private String floor;
    private String building;
    private String nodeType;

    //Nodes in the map
    private ArrayList<NodeCheckBox> nodeCheckBoxes = new ArrayList<NodeCheckBox>();
    private ArrayList<EdgeCheckBox> edgeCheckBoxes = new ArrayList<EdgeCheckBox>();




    //setter for parent
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }


    @FXML
    private TextField txtfldX;

    @FXML
    private Label failText;

    @FXML
    private TextField txtfldY;

    @FXML
    private Label menubtnFloor;

    @FXML
    private Button btnEnter;

    @FXML
    private Button btnCancel;

    @FXML
    private MenuButton floorDropDown;

    @FXML
    private MenuItem floor3;

    @FXML
    private MenuItem floor2;

    @FXML
    private MenuItem floor1;

    @FXML
    private MenuItem floorG;

    @FXML
    private MenuItem floorL1;

    @FXML
    private MenuItem FloorL2;

    @FXML
    private TextField txtfldID;

    @FXML
    private Label menubtnBuilding;

    @FXML
    private MenuButton buildingDropDown;

    @FXML
    private MenuItem buildingBTM;

    @FXML
    private MenuItem buildingShapiro;

    @FXML
    private MenuItem buldingTower;

    @FXML
    private MenuItem building45F;

    @FXML
    private MenuItem building15F;

    @FXML
    private Label menubtnNodeType;

    @FXML
    private MenuButton nodeTypeDropDown;

    @FXML
    private MenuItem nTypeHALL;

    @FXML
    private MenuItem nTypeELEV;

    @FXML
    private MenuItem nTypeREST;

    @FXML
    private MenuItem nTypeSTAI;

    @FXML
    private MenuItem nTypeDEPT;

    @FXML
    private MenuItem nTypeLABS;

    @FXML
    private MenuItem nTypeINFO;

    @FXML
    private MenuItem nTypeCONF;

    @FXML
    private MenuItem nTypeEXIT;

    @FXML
    private MenuItem nTypeRETL;

    @FXML
    private MenuItem nTypeSERV;

    @FXML
    private TextField txtfldName;

    @FXML
    private Pane mapPane;

    @FXML
    private Circle nodeLocation;

    public void init() {
        nodeCheckBoxes = new ArrayList<NodeCheckBox>();

    }

    public void onShow() {
//        x = "";
//        y = "";
//        name = "";
//        nodeID = "";
//        floor = "";
//        txtfldX.setText("");
//        txtfldY.setText("");
//        txtfldID.setText("");
//        txtfldName.setText("");
//
//        floorDropDown.setText("");
//        buildingDropDown.setText("");
//        nodeTypeDropDown.setText("");
//
//        for(NodeCheckBox box : nodeCheckBoxes){
//            box.setVisible(false);
//            mapPane.getChildren().remove(box);
//        }
//        nodeCheckBoxes = new ArrayList<NodeCheckBox>();
//        failText.setVisible(false);
//        nodeLocation.setVisible(false);
//
//        ArrayList<Node> nodes = new ArrayList<Node>();//Todo find nodes to display
//        for (Node node : nodes) {
//            NodeCheckBox box = new NodeCheckBox();
//            box.setNode(node); //sets checkbox to node location
//
//            box.setVisible(true);
//            mapPane.getChildren().add(box);
//            nodeCheckBoxes.add(box);

//        }
    }

    public void removeEnterPressed(ActionEvent e) {
        System.out.println("Remove entered");
    }

    public void removeEdgeEnterPressed(ActionEvent e) {
        System.out.println("Remove entered");
    }

    //Action upon pressing enter
    //variables for all text fields is set up and populated
    //add node command is executed
    //user is returned to menu screen
    public void addEnterPressed(ActionEvent e) {
        nodeID = txtfldID.getText();
        if (nodeID.length() == 10) {
            //x and y and ints
//        x = Integer.valueOf(txtfldX.getText());
//        y = Integer.valueOf(txtfldY.getText());
            x = txtfldX.getText();
            y = txtfldY.getText();

            System.out.println(nodeID);
            System.out.println(x);
            System.out.println(y);

            name = txtfldName.getText();
            System.out.println(floor);
            System.out.println(nodeType);
            System.out.println(building);
            System.out.println(name);

            ArrayList<Node> connections = new ArrayList<Node>();
            for(NodeCheckBox box : nodeCheckBoxes){
                if(box.isSelected())
                    connections.add(box.getNode());
            }

            HospitalMap.addNode(new Node(nodeID, x, y, floor, building, nodeType, name, name));//Todo add connections
            System.out.println("Enter Pressed");
        } else
            failText.setVisible(true);

    }

    //comands for button cancel press
    public void cancelPressed(ActionEvent e) {
        System.out.println("Cancel Pressed");
        clearInputs();
    }

    public void returnPressed(ActionEvent e) {
        System.out.println("Return Pressed");
        parent.setScreen(ScreenController.RequestID);
    }

    //set up variables when building drop down selected
    @FXML
    public void buildingSelected(ActionEvent e) {
        System.out.println("Building Selected");
        building = ((MenuItem) e.getSource()).getText();
        //Setting the variables equal to values read from UI
        buildingDropDown.setText(building);
    }

    //set up variable when floor drop down selected
    @FXML
    public void floorSelected(ActionEvent e) {
        System.out.println("Floor Selected");
        //floor = ((MenuItem) e.getSource()).getText();
        //Setting the variables equal to values read from UI
        //floorDropDown.setText(floor);
    }

    //set up variable when floor drop down selected
    @FXML
    public void nodeTypeSelected(ActionEvent e) {
        System.out.println("Node Type Selected");
        nodeType = ((MenuItem) e.getSource()).getText();
        //Setting the variables equal to values read from UI
        nodeTypeDropDown.setText(nodeType);
    }

    //set variable to text from text field name
    @FXML
    public void filledNodeID(ActionEvent e) {
        //Setting the variables equal to values read from UI
        nodeID = txtfldID.getText();
        System.out.println(nodeID);
    }

    public void xCoordEntered() {
        x = txtfldX.getText();
        drawNodeLocation();
    }

    public void yCoordEntered() {
        y = txtfldY.getText();
        drawNodeLocation();
    }

    public void drawNodeLocation() {
        if (x.length()>0 & y.length()>0) {
            System.out.println("Drawing Node: " + x + " " + y);
            nodeLocation.setLayoutX(Integer.parseInt(x));
            nodeLocation.setLayoutY(Integer.parseInt(y));
            nodeLocation.setVisible(true);
        }

    }

    public void clearInputs(){
        //todo clear all the text and options from UI inputs
    }

    ////////////////////////////////////////////////////////////
    /////////////           Node ADD
    ////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField nodeAddXField;
    @FXML
    private JFXButton nodeAddEnterButton;
    @FXML
    private JFXButton nodeAddCancelButton;

    public void setNodeAddXEntered(ActionEvent e){
        System.out.println("Node Add X Entered");
    }

    
    public void nodeAddEnterPressed(ActionEvent e){
        System.out.println("Node Add Enter Pressed");
        //todo add node

        //also get selected nodes and pass them to map to add neighbors
    }

    public void nodeAddCancelPressed(ActionEvent e){
        System.out.println("Node Add Cancel Pressed");
        //todo cancel node add
    }

    ////////////////////////////////////////////////////////////
    /////////////           Node Remove
    ////////////////////////////////////////////////////////////
    @FXML
    private JFXButton nodeRemoveEnterButton;
    @FXML
    private JFXButton nodeRemoveCancelButton;


    public void nodeRemoveEnterPressed(ActionEvent e){
        System.out.println("Node Remove Enter Pressed");
        //todo add node

        //also get selected nodes and pass them to map to add neighbors
    }

    public void nodeRemoveCancelPressed(ActionEvent e){
        System.out.println("Node Remove Cancel Pressed");
        //todo cancel node add
    }

    ////////////////////////////////////////////////////////////
    /////////////           Node Edit
    ////////////////////////////////////////////////////////////
    @FXML
    private JFXButton nodeEditEnterButton;
    @FXML
    private JFXButton nodeEditCancelButton;

    public void nodeEditEnterPressed(ActionEvent e){
        System.out.println("Node Edit Enter Pressed");
        //todo add node

        //also get selected nodes and pass them to map to add neighbors
    }

    public void nodeEditCancelPressed(ActionEvent e){
        System.out.println("Node Edit Cancel Pressed");
        //todo cancel node add
    }


    ////////////////////////////////////////////////////////////
    /////////////           EDGE ADD
    ////////////////////////////////////////////////////////////
    @FXML
    private JFXButton edgeAddEnterButton;
    @FXML
    private JFXButton edgeAddCancelButton;
    @FXML
    private Label edgeAddID1Label;
    @FXML
    private Label edgeAddID2Label;

    public void edgeAddEnterPressed(ActionEvent e){
        System.out.println("Edge Add Enter Pressed");
        //todo add edge
        Node nodeOne = null;
        Node nodeTwo = null;
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected())  {
                if(nodeOne == null) nodeOne = n.getNode();
                else if(nodeTwo == null) nodeTwo = n.getNode();
                else {
                    System.out.println("Warning: More than two nodes selected.");
                    return;
                }
            }
        }
        if(nodeOne == null || nodeTwo == null){
            System.out.println("Warning: Less than two nodes selected.");
        } else {
            HospitalMap.addEdge(new Edge(nodeOne, nodeTwo));
        }
    }

    public void edgeAddCancelPressed(ActionEvent e){
        System.out.println("Edge Add Cancel Pressed");
        //todo cancel edge add
    }

    ////////////////////////////////////////////////////////////
    /////////////           EDGE REMOVE
    ////////////////////////////////////////////////////////////
    @FXML
    private JFXButton edgeRemoveEnterButton;
    @FXML
    private JFXButton edgeRemoveCancelButton;
    @FXML
    private Label edgeRemoveIDLabel;

    public void edgeRemoveEnterPressed(ActionEvent e){
        System.out.println("Edge Remove Enter Pressed");
        Node nodeOne = null;
        Node nodeTwo = null;
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected()){
                if(nodeOne == null) nodeOne = n.getNode();
                else if(nodeTwo == null) nodeTwo = n.getNode();
                else {
                    System.out.println("Warning: More than two nodes selected.");
                    break;
                }
            }
        }
        if(nodeOne == null || nodeTwo == null){
            System.out.println("Warning: Less than two nodes selected. ");
        } else {
            HospitalMap.DeleteEdge(nodeOne.getEdgeOf(nodeTwo));
        }
        //todo remove edge
    }

    public void edgeRemoveCancelPressed(ActionEvent e){
        System.out.println("Edge Remove Cancel Pressed");
        //todo cancel edge add
    }


    ////////////////////////////////////////////////////////////
    /////////////           EDGE EDIT
    ////////////////////////////////////////////////////////////
    @FXML
    private Label edgeEditIDLabel;
    @FXML
    private JFXTextField edgeEditNodeOneField;
    @FXML
    private JFXTextField edgeEditeNodeTwoField;
    @FXML
    private JFXButton edgeEditEnterButton;
    @FXML
    private JFXButton edgeEditCancelButton;

    public void edgeEditNodeOneEntered(ActionEvent e){
        System.out.println("Edge Edit Node One Entered");
    }

    public void edgeEditNodeTwoEntered(ActionEvent e){
        System.out.println("Edge Edit Node Two Entered");
    }

    public void edgeEditEnterPressed(ActionEvent e){
        System.out.println("Edge Edit Enter Pressed");
    }

    public void edgeEditCancelPressed(ActionEvent e){
        System.out.println("Edge Edit Cancel Pressed");
    }



}
