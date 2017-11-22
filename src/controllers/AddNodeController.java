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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import map.Edge;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;

import java.util.ArrayList;

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





        nodeTab.setOnSelectionChanged(e -> showNodesandEdges());
        edgeTab.setOnSelectionChanged(e -> showNodesandEdges());
        nodeAddTab.setOnSelectionChanged(e -> showNodesandEdges());
        nodeEditTab.setOnSelectionChanged(e -> showNodesandEdges());
        nodeRemoveTab.setOnSelectionChanged(e -> showNodesandEdges());
        edgeAddTab.setOnSelectionChanged(e -> showNodesandEdges());
        edgeRemoveTab.setOnSelectionChanged(e -> showNodesandEdges());


        refreshNodesandEdges();
    }

    public void onShow() {
        currentFloor = FloorNumber.FLOOR_ONE;
        mapImage.setImage(currentFloor);
        refreshNodesandEdges();
        nodeAddFloorDropDown.setText(currentFloor.getDbMapping());
        nodeEditFloorDropDown.setText(currentFloor.getDbMapping());
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
        FloorNumber floor = FloorNumber.fromDbMapping(((JFXButton)e.getSource()).getText());
        System.out.println("Floor Pressed: " + floor);
        currentFloor = floor;
        nodeAddFloorDropDown.setText(floor.getDbMapping());
        nodeEditFloorDropDown.setText(floor.getDbMapping());
        mapImage.setImage(floor);
        refreshNodesandEdges();

    }

    public void showNodesandEdges(){
        clearNodesandEdges();
        if(nodeTab.isSelected()){
            showNodesbyFloor(currentFloor);
            if(nodeRemoveTab.isSelected()){
                nodeRemoveSelectedList.getItems().clear();
            } else if(nodeEditTab.isSelected()){
                resetNodeEdit();
            }
        }
        else{
            if(edgeAddTab.isSelected()){
                showEdgesbyFloor(currentFloor);
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

    public void refreshNodes(){
        nodeCheckBoxes.clear();
        for (Node node:map.getNodeMap()) {
            NodeCheckBox cb = new NodeCheckBox(node, mapImage.getScale());
            nodeCheckBoxes.add(cb);
            cb.setOnAction(e -> nodeSelected(e));
        }
    }

    public void refreshEdges(){
        edgeCheckBoxes.clear();
        for (Edge edge:map.getEdgeMap()){
            EdgeCheckBox cb = new EdgeCheckBox(edge, mapImage.getScale());
            edgeCheckBoxes.add(cb);
            cb.setOnMousePressed(e -> edgeSelected(e));
        }
    }

    public void refreshNodesandEdges(){
        refreshNodes();
        refreshEdges();
        showNodesandEdges();
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
            else{
                source.setSelected(false);
            }
        }
        else if (nodeTab.isSelected() && nodeEditTab.isSelected()) {
            if (nodeEditSelectedNode == null) {
                Node n = source.getNode();
                nodeEditSelectedNode = n;
                nodeEditNameField.setText(n.getLongName());
                nodeEditShortField.setText(n.getShortName());
                nodeEditBuildingDropDown.setText(n.getBuilding());
                nodeEditFloorDropDown.setText(n.getFloor().getDbMapping());
                nodeEditTypeDropDown.setText(n.getType());
                nodeEditXField.setText(Integer.toString(n.getX()));
                nodeEditYField.setText(Integer.toString(n.getY()));
                nodeEditIDLabel.setText(n.getID());
            } else if (nodeEditSelectedNode.equals(source.getNode())) {
                resetNodeEdit();
            } else{
                source.setSelected(false);
            }
        }
    }


    public void edgeSelected(MouseEvent e){
        EdgeCheckBox source = (EdgeCheckBox)e.getSource();
        source.select();
        if(source.isSelected()) {
            System.out.println("Edge Added to List");
            if (!edgeRemoveList.getItems().contains(source.getEdge()))
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
        nodeAddIndicator.setCenterX((double)Integer.parseInt(nodeAddXField.getText()));
        nodeAddIndicator.setCenterY((double)Integer.parseInt(nodeAddYField.getText()));
        nodeAddIndicator.setRadius(10.0f);
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
        ArrayList<Node> connections = new ArrayList<>();
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected()) connections.add(n.getNode());
        }
        try {
            map.addNodeandEdges("putNodeIDHere",
                    nodeAddXField.getText(),
                    nodeAddYField.getText(),
                    nodeAddFloorDropDown.getText(),
                    nodeAddBuildingDropDown.getText(),
                    nodeAddTypeDropDown.getText(),
                    nodeAddNameField.getText(),
                    nodeAddShortField.getText(), "H", connections);
        }
        catch (Exception ex){
            System.out.println("Add failed");
        }
        refreshNodesandEdges();
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
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected()) map.removeNode(n.getNode());
        }
        refreshNodesandEdges();
        nodeRemoveSelectedList.getItems().clear();

    }

    public void nodeRemoveCancelPressed(ActionEvent e){
        System.out.println("Node Remove Cancel Pressed");
        nodeRemoveSelectedList.getItems().clear();
        refreshNodesandEdges();
    }

    ////////////////////////////////////////////////////////////
    /////////////           Node Edit
    ////////////////////////////////////////////////////////////
    @FXML
    private JFXButton nodeEditEnterButton;
    @FXML
    private Label nodeEditIDLabel;
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

    private Node nodeEditSelectedNode;

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
        Node node = nodeEditSelectedNode;
        
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
                nodeEditNameField.getText(),
                nodeEditShortField.getText());
        refreshNodesandEdges();
    }

    public void nodeEditCancelPressed(ActionEvent e){
        System.out.println("Node Edit Cancel Pressed");
        resetNodeEdit();
    }


    public void resetNodeEdit(){
        nodeEditSelectedNode = null;
        nodeEditNameField.setText("Name");
        nodeEditShortField.setText("Short Name");
        nodeEditBuildingDropDown.setText("Building");
        nodeEditFloorDropDown.setText("Floor");
        nodeEditTypeDropDown.setText("Type");
        nodeEditXField.setText("X Coordinate");
        nodeEditYField.setText("Y Coordinate");
        nodeEditIDLabel.setText("Node ID");
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

        Node nodeOne = edgeAddNode1;
        Node nodeTwo = edgeAddNode2;

        if(nodeOne == null || nodeTwo == null){
            System.out.println("Warning: Less than two nodes selected.");
        } else {
            map.addEdge(new Edge(nodeOne, nodeTwo));
        }
        refreshNodesandEdges();
    }

    public void edgeAddCancelPressed(ActionEvent e){
        System.out.println("Edge Add Cancel Pressed");
        refreshNodesandEdges();
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

        for(Edge edge : edgeRemoveList.getItems()){
            map.removeEdge(edge);
        }
        edgeRemoveList.getItems().clear();
        refreshNodesandEdges();
    }

    public void edgeRemoveCancelPressed(ActionEvent e){
        System.out.println("Edge Remove Cancel Pressed");
        edgeRemoveList.getItems().clear();
        refreshNodesandEdges();
    }
}
