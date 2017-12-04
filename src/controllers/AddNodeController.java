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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import map.Edge;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import ui.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class AddNodeController implements ControllableScreen, Observer {
    private ScreenController parent;
    private HospitalMap map;
    private  FloorNumber currentFloor;
    private MapViewer mapViewer;

    //Nodes in the map
    private ArrayList<NodeCheckBox> nodeCheckBoxes = new ArrayList<NodeCheckBox>();
    private ArrayList<EdgeCheckBox> edgeCheckBoxes = new ArrayList<EdgeCheckBox>();

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
    private AnchorPane mainAnchorPane;

    public void init() {
        map = HospitalMap.getMap();
        mainAnchorPane.prefWidthProperty().bind(parent.prefWidthProperty().subtract(400));
        mainAnchorPane.prefHeightProperty().bind(parent.prefHeightProperty());

        mapViewer = new MapViewer(this, mainAnchorPane);
        mainAnchorPane.getChildren().add(mapViewer.getMapViewerPane());
        mapViewer.setFloor(FloorNumber.FLOOR_GROUND);
        mapPane = mapViewer.getMapPane();

        mapPane.setOnMouseClicked(e -> mapPaneClicked(e));

        nodeAddLocation = new AnimatedCircle();
        nodeAllignedLines = new ArrayList<Line>();
        nodeAddLocation.setVisible(false);
        nodeAddLocation.setFill(Color.DODGERBLUE);

        mapViewer.getMapScrollPane().setPannable(true);


        nodeCheckBoxes = new ArrayList<NodeCheckBox>();
        edgeCheckBoxes = new ArrayList<EdgeCheckBox>();

        nodeTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        edgeTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        nodeAddTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        nodeEditTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        nodeRemoveTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        edgeAddTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        edgeRemoveTab.setOnSelectionChanged(e -> refreshNodesandEdges());


        refreshNodesandEdges();
    }

    public void onShow() {
        currentFloor = FloorNumber.FLOOR_ONE;
        mapViewer.setFloor(currentFloor);
        refreshNodesandEdges();
        nodeAddFloorDropDown.setText(currentFloor.getDbMapping());
        nodeEditFloorDropDown.setText(currentFloor.getDbMapping());
    }

    //Setters
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }
    public void setFloor(FloorNumber floor){
        currentFloor = floor;
        nodeAddFloorDropDown.setText(floor.getDbMapping());
        nodeEditFloorDropDown.setText(floor.getDbMapping());
        refreshNodesandEdges();
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
        parent.setScreen(ScreenController.RequestID, "RIGHT");
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
        mapPane.getChildren().add(mapViewer.getMapImage());
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
            NodeCheckBox cb = new NodeCheckBox(node, mapViewer.getScale());
            nodeCheckBoxes.add(cb);
            cb.setOnAction(e -> nodeSelected(e));
            if(nodeTab.isSelected() && nodeEditTab.isSelected()) {
                cb.setOnMousePressed(boxOnMousePressedHandler);
                cb.setOnMouseDragged(boxOnMouseDraggedHandler);
            }
        }
    }

    public void refreshEdges(){
        edgeCheckBoxes.clear();
        for (Edge edge:map.getEdgeMap()){
            EdgeCheckBox cb = new EdgeCheckBox(edge, mapViewer.getScale());
            edgeCheckBoxes.add(cb);
            cb.setOnMousePressed(e -> edgeSelected(e));
        }
    }

    public void refreshNodesandEdges(){
        refreshNodes();
        refreshEdges();
        showNodesandEdges();
    }

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

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
            if(edgeAddNode1 == null && (edgeAddNode2 == null || !edgeAddNode2.equals(source.getNode()))){
                edgeAddNode1 = source.getNode();
                edgeAddID1Label.setText(source.getNode().getID());
            }
            else if(edgeAddNode1 != null && edgeAddNode1.equals(source.getNode())){
                edgeAddNode1 = null;
                edgeAddID1Label.setText("");
            }
            else if(edgeAddNode2 == null ){
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

    EventHandler<MouseEvent> boxOnMousePressedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            NodeCheckBox source = (NodeCheckBox)event.getSource();
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            orgTranslateX = source.getTranslateX();
            orgTranslateY = source.getTranslateY();
        }
    };

    EventHandler<MouseEvent> boxOnMouseDraggedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            NodeCheckBox source = (NodeCheckBox)event.getSource();
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            source.setTranslateX(newTranslateX);
            source.setTranslateY(newTranslateY);

            //nodeEditXField.setText("" + (event.getSceneX()*mapViewer.getScale()));
            //nodeEditYField.setText("" + (event.getSceneY()*mapViewer.getScale()));
        }
    };

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

    @Override
    public void update(Observable o, Object arg){
        if(arg instanceof PathID){
            setFloor(((PathID) arg).getFloor());
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


    private AnimatedCircle nodeAddLocation;

    private ArrayList<Line> nodeAllignedLines;


    public void nodeAddXEntered(ActionEvent e){
        System.out.println("Node Add X Entered");
        String text = nodeAddXField.getText();
        nodeAddXField.setText(text);
    }
    public void nodeAddYEntered(ActionEvent e){
        System.out.println("Node Add Y Entered");
        String text = nodeAddYField.getText();
        nodeAddYField.setText(text);
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
            map.addNodeandEdges(nodeAddXField.getText(),
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

    public void mapPaneClicked(MouseEvent e){
        if (e.getClickCount() == 2 && nodeAddTab.isSelected() && nodeTab.isSelected()){
            setNewNodeAddLocation((int)e.getX(), (int)e.getY());
        }
    }

    private void setNewNodeAddLocation(int x, int y){
        int x_alligned = x;
        int y_alligned = y;

        mapPane.getChildren().removeAll(nodeAllignedLines);
        nodeAllignedLines.clear();
        ArrayList<Node> h_neighbors = new ArrayList<>();
        h_neighbors.addAll(map.getNodesInHorizontal((int)(x*mapViewer.getScale()),(int)(y*mapViewer.getScale()),currentFloor));
        ArrayList<Node> v_neighbors = new ArrayList<>();
        v_neighbors.addAll(map.getNodesInVertical((int)(x*mapViewer.getScale()),(int)(y*mapViewer.getScale()),currentFloor));

        if(h_neighbors.size() != 0){
            y_alligned = (int)(h_neighbors.get(0).getY()/mapViewer.getScale());
        }
        if(v_neighbors.size() != 0){
            x_alligned = (int)(v_neighbors.get(0).getX()/mapViewer.getScale());

        }
        if(x != x_alligned){
            Line line = newAllignLine();
            line.setLayoutX(x_alligned);
            line.setLayoutY(y_alligned);
            line.setEndX((h_neighbors.get(0).getX()/mapViewer.getScale()) - x_alligned);
            line.setEndY(0);
            nodeAllignedLines.add(line);
            mapPane.getChildren().add(line);

        }
        if(y != y_alligned){
            Line line = newAllignLine();
            line.setLayoutX(x_alligned);
            line.setLayoutY(y_alligned);
            line.setEndX(0);
            line.setEndY((v_neighbors.get(0).getY()/mapViewer.getScale()) - y_alligned);
            nodeAllignedLines.add(line);
            mapPane.getChildren().add(line);
        }

        nodeAddLocation.setCenterX(x_alligned);
        nodeAddLocation.setCenterY(y_alligned);
        if(!mapPane.getChildren().contains(nodeAddLocation))
            mapPane.getChildren().add(nodeAddLocation);
        nodeAddLocation.setVisible(true);
        nodeAddXField.setText(Integer.toString((int)(x_alligned*mapViewer.getScale())));
        nodeAddYField.setText(Integer.toString((int)(y_alligned*mapViewer.getScale())));
    }

    private Line newAllignLine(){
        Line line = new Line();
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);
        line.setOpacity(0.5);
        return line;
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
        refreshNodesandEdges();
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
