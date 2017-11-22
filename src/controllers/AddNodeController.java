/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import map.Edge;
import map.FloorNumber;
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
    private HospitalMap map;
    private  FloorNumber currentFloor;


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
    private Pane mapPane;
    @FXML
    private Tab nodeTab;
    @FXML
    private Tab edgeTab;
    @FXML
    private Tab nodeAddTab;
    @FXML
    private Tab nodeRemoveTab;
    @FXML
    private Tab nodeEditTab;
    @FXML
    private Tab edgeAddTab;
    @FXML
    private Tab edgeRemoveTab;

    @FXML
    private Circle nodeLocation;


    private proxyImagePane mapImage;

    public void init() {
        map = HospitalMap.getMap();
        mapImage = new proxyImagePane();
        mapImage.setImage(FloorNumber.FLOOR_GROUND);
        mapPane.getChildren().add(mapImage);
        nodeCheckBoxes = new ArrayList<NodeCheckBox>();
        edgeCheckBoxes = new ArrayList<EdgeCheckBox>();

        for (Node node:map.getNodeMap()) {
            NodeCheckBox cb = new NodeCheckBox(node, mapImage.getScale());
            nodeCheckBoxes.add(cb);
            cb.setOnAction(e -> nodeSelected(e));
        }
        for (Edge edge:map.getEdgeMap()){
            EdgeCheckBox cb = new EdgeCheckBox(edge, mapImage.getScale());
            edgeCheckBoxes.add(cb);
            cb.setOnMouseClicked(e -> edgeSelected(e));
        }

        //////////    STUB FOR TESTING  ////////////////////////////////////
        Node test1 = new Node(1000,400,FloorNumber.FLOOR_ONE);
        Node test2 = new Node(1200,400,FloorNumber.FLOOR_ONE);
        Node test3 = new Node(1200,400,FloorNumber.FLOOR_TWO);
        Node test4 = new Node(1000,400,FloorNumber.FLOOR_TWO);
        test1.setLongName("Test 1");
        test2.setLongName("Test 2");
        test3.setLongName("Test 3");
        test4.setLongName("Test 4");
        nodeCheckBoxes.add(new NodeCheckBox(test1, mapImage.getScale()));
        nodeCheckBoxes.add(new NodeCheckBox(test2, mapImage.getScale()));
        nodeCheckBoxes.add(new NodeCheckBox(test3, mapImage.getScale()));
        nodeCheckBoxes.add(new NodeCheckBox(test4, mapImage.getScale()));
        edgeCheckBoxes.add(new EdgeCheckBox(new Edge(test1,test2), mapImage.getScale()));
        edgeCheckBoxes.add(new EdgeCheckBox(new Edge(test2,test3), mapImage.getScale()));
        edgeCheckBoxes.add(new EdgeCheckBox(new Edge(test3,test4), mapImage.getScale()));

        /////////////////////////////////////////////////////////////////////////




        nodeTab.setOnSelectionChanged(e -> showNodesandEdges());
        edgeTab.setOnSelectionChanged(e -> showNodesandEdges());
        nodeAddTab.setOnSelectionChanged(e -> showNodesandEdges());
        nodeEditTab.setOnSelectionChanged(e -> showNodesandEdges());
        nodeRemoveTab.setOnSelectionChanged(e -> showNodesandEdges());
        edgeAddTab.setOnSelectionChanged(e -> showNodesandEdges());
        edgeRemoveTab.setOnSelectionChanged(e -> showNodesandEdges());


    }

    public void onShow() {
        currentFloor = FloorNumber.FLOOR_ONE;
        mapImage.setImage(currentFloor);
        showNodesandEdges();
    }

    //Action upon pressing enter
    //variables for all text fields is set up and populated
    //add node command is executed
    //user is returned to menu screen

    public void clearInputs(){
        //todo clear all the text and options from UI inputs
    }

    public void returnPressed(ActionEvent e){
        System.out.println("Return Pressed");
        parent.setScreen(ScreenController.RequestID);
    }

    public void floorButtonPressed(ActionEvent e){
        //todo: display the nodes and edges for the floor that was pressed
        //todo: hide the nodes and edges from the other floors
        FloorNumber floor = FloorNumber.fromDbMapping(((JFXButton)e.getSource()).getText());
        System.out.println("Floor Pressed: " + floor);
        currentFloor = floor;
        mapImage.setImage(floor);

    }

    public void showNodesandEdges(){
        clearNodesandEdges();
        if(nodeTab.isSelected()){
            showNodesbyFloor(currentFloor);
            if(nodeRemoveTab.isSelected()){
                nodeRemoveSelectedList.getItems().clear();
            }
        }
        else{
            if(edgeAddTab.isSelected()){
                showNodesbyFloor(currentFloor);
                edgeAddNode1 = null;
                edgeAddNode2 = null;
                edgeAddID1Label.setText("");
                edgeAddID2Label.setText("");

            }
            else{
                showEdgesbyFloor(currentFloor);
                edgeRemoveList.getItems().clear();
            }
        }
    }

    public void clearNodesandEdges(){
        mapPane.getChildren().clear();
        mapPane.getChildren().add(mapImage);
    }


    public void showNodesandEdgesbyFloor(FloorNumber floor){
        showEdgesbyFloor(floor);
        showNodesbyFloor(floor);
    }

    public void showNodesbyFloor(FloorNumber floor){
        ObservableList children = mapPane.getChildren();
        for(NodeCheckBox cb : nodeCheckBoxes){
            if(cb.getNode().getFloor().equals(floor)){
                cb.setVisible(true);
                cb.setSelected(false);
                cb.setOnAction(e -> nodeSelected(e));
                children.add(cb);
            }
        }
    }

    public void showEdgesbyFloor(FloorNumber floor){
        ObservableList children = mapPane.getChildren();
        for(EdgeCheckBox cb : edgeCheckBoxes){
            if(cb.getEdge().getNodeOne().getFloor().equals(floor)) {
                cb.setVisible(true);
                cb.setSelected(false);
                cb.setOnMousePressed(e -> edgeSelected(e));
                children.add(cb);

            }
        }
    }

    public void nodeSelected(ActionEvent e){
        NodeCheckBox source = (NodeCheckBox)e.getSource();
        if(nodeTab.isSelected() && nodeRemoveTab.isSelected()){
            if(source.isSelected()){
                nodeRemoveSelectedList.getItems().add(source.getNode());
            }
            else {
                nodeRemoveSelectedList.getItems().remove(source.getNode());
            }
        }
        else if (edgeTab.isSelected() && edgeAddTab.isSelected()){
            if(edgeAddNode1 == null){
                edgeAddNode1 = source.getNode();
                edgeAddID1Label.setText(source.getNode().getID());
            }
            else if(edgeAddNode1.equals(source.getNode())){
                edgeAddNode1 = null;
                edgeAddID1Label.setText("");
            }
            else if(edgeAddNode2 == null){
                edgeAddNode2 = source.getNode();
                edgeAddID2Label.setText(source.getNode().getID());
            }
            else if(edgeAddNode2.equals(source.getNode())){
                edgeAddNode2 = null;
                edgeAddID2Label.setText("");
            }


        }

    }



    public void edgeSelected(MouseEvent e){
        EdgeCheckBox source = (EdgeCheckBox)e.getSource();
        if(!source.isSelected()) {
            System.out.println("Edge Added to List");
            edgeRemoveList.getItems().add(source.getEdge());
        }
        else{
            System.out.println("Edge Removed from List");
            edgeRemoveList.getItems().remove(source.getEdge());
        }

    }
    ////////////////////////////////////////////////////////////
    /////////////           Node ADD
    ////////////////////////////////////////////////////////////
    @FXML
    private JFXTextField nodeAddXField;
    @FXML
    private JFXTextField nodeAddYField;
    @FXML
    private JFXTextField nodeAddShortField;
    @FXML
    private JFXTextField nodeAddNameField;
    @FXML
    private JFXButton nodeAddEnterButton;
    @FXML
    private JFXButton nodeAddCancelButton;
    @FXML
    private MenuButton nodeAddFloorDropDown;
    @FXML
    private MenuButton nodeAddBuildingDropDown;
    @FXML
    private MenuButton nodeAddTypeDropDown;
    @FXML
    private Circle nodeAddIndicator;




    public void nodeAddXEntered(ActionEvent e){
        System.out.println("Node Add X Entered");
        String text = nodeAddXField.getText();
        nodeAddXField.setText(text);
    }
    public void nodeAddYEntered(ActionEvent e){
        System.out.println("Node Add Y Entered");
        String text = nodeAddYField.getText();
        nodeAddYField.setText(text);
        nodeAddIndicator = new Circle();
        nodeAddIndicator.setCenterX((double)Integer.parseInt(nodeAddXField.getText()));
        nodeAddIndicator.setCenterY((double)Integer.parseInt(nodeAddYField.getText()));
        nodeAddIndicator.setRadius(50.0f);
    }

    public void nodeAddShortEntered(ActionEvent e){
        System.out.println("Node Add Short Name Entered");
        String text = nodeAddShortField.getText();
        nodeAddYField.setText(text);
    }

    public void nodeAddNameEntered(ActionEvent e){
        System.out.println("Node Add Long Name Entered");
        String text = nodeAddNameField.getText();
        nodeAddNameField.setText(text);
    }

    public void nodeAddFloorSelected(ActionEvent e){
        System.out.println("Node add Floor Selected");
        String text = ((MenuItem)e.getSource()).getText();
        nodeAddFloorDropDown.setText(text);
    }

    public void nodeAddBuildingSelected(ActionEvent e){
        System.out.println("Node add Building Selected");
        String text = ((MenuItem)e.getSource()).getText();
        nodeAddBuildingDropDown.setText(text);
    }

    public void nodeAddTypeSelected(ActionEvent e){
        System.out.println("Node add type Selected");
        String text = ((MenuItem)e.getSource()).getText();
        nodeAddTypeDropDown.setText(text);
    }


    public void nodeAddEnterPressed(ActionEvent e){
        System.out.println("Node Add Enter Pressed");

        //todo add node
        ArrayList<Node> connections = new ArrayList<>();
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected()) connections.add(n.getNode());
        }
        map.addNodeandEdges("putNodeIDHere",
                nodeAddXField.getText(),
                nodeAddYField.getText(),
                nodeAddFloorDropDown.getText(),
                nodeAddBuildingDropDown.getText(),
                nodeAddTypeDropDown.getText(),
                nodeAddNameField.getText(),
                nodeAddShortField.getText(),"H", connections);
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
    @FXML
    private JFXListView<Node> nodeRemoveSelectedList;

    public void nodeRemoveAddToList(NodeCheckBox node){
        nodeRemoveSelectedList.getItems().add(node.getNode());
    }

    public void nodeRemoveEnterPressed(ActionEvent e){
        System.out.println("Node Remove Enter Pressed");
        //todo add node
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected()) map.removeNode(n.getNode());
        }
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
    @FXML
    private JFXTextField nodeEditXField;
    @FXML
    private JFXTextField nodeEditYField;
    @FXML
    private JFXTextField nodeEditShortField;
    @FXML
    private JFXTextField nodeEditNameField;
    @FXML
    private MenuButton nodeEditFloorDropDown;
    @FXML
    private MenuButton nodeEditBuildingDropDown;
    @FXML
    private MenuButton nodeEditTypeDropDown;

    public void nodeEditXEntered(ActionEvent e){
        System.out.println("Node Edit X Entered");
        String text = nodeEditXField.getText();
        nodeEditXField.setText(text);
    }
    public void nodeEditYEntered(ActionEvent e){
        System.out.println("Node Edit Y Entered");
        String text = nodeEditYField.getText();
        nodeEditYField.setText(text);
    }

    public void nodeEditShortEntered(ActionEvent e){
        System.out.println("Node Edit Short Name Entered");
        String text = nodeEditShortField.getText();
        nodeEditShortField.setText(text);
    }

    public void nodeEditNameEntered(ActionEvent e){
        System.out.println("Node Edit Long Name Entered");
        String text = nodeEditNameField.getText();
        nodeEditNameField.setText(text);
    }

    public void nodeEditFloorSelected(ActionEvent e){
        System.out.println("Node Edit Floor Selected");
        String text = ((MenuItem)e.getSource()).getText();
        nodeEditFloorDropDown.setText(text);
    }

    public void nodeEditBuildingSelected(ActionEvent e){
        System.out.println("Node Edit Building Selected");
        String text = ((MenuItem)e.getSource()).getText();
        nodeEditBuildingDropDown.setText(text);
    }

    public void nodeEditTypeSelected(ActionEvent e){
        System.out.println("Node Edit type Selected");
        String text = ((MenuItem)e.getSource()).getText();
        nodeEditTypeDropDown.setText(text);
    }


    public void nodeEditEnterPressed(ActionEvent e){
        System.out.println("Node Edit Enter Pressed");
        //todo add node
        Node node = null;
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected()){
                if(node == null) node = n.getNode();
                else{
                    System.out.println("Warning: More than one node selected.");
                    return;
                }
            }
        }
        if(node == null){
            System.out.println("Warning: No nodes selected.");
            return;
        }
        map.editNode(node,
                nodeEditXField.getText(),
                nodeEditYField.getText(),
                nodeEditFloorDropDown.getText(),
                nodeEditBuildingDropDown.getText(),
                nodeEditTypeDropDown.getText(),
                nodeAddNameField.getText(),
                nodeAddShortField.getText());
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

    private Node edgeAddNode1;

    private Node edgeAddNode2;


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
            map.addEdge(new Edge(nodeOne, nodeTwo));
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
    private JFXListView<Edge> edgeRemoveList;

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
            map.removeEdge(nodeOne.getEdgeOf(nodeTwo));
        }
        //todo remove edge
    }

    public void edgeRemoveCancelPressed(ActionEvent e){
        System.out.println("Edge Remove Cancel Pressed");
        //todo cancel edge add
    }
}
