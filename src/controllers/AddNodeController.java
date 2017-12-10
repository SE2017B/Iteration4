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
import com.jfoenix.controls.JFXSlider;
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
import javafx.scene.shape.Line;
import map.Edge;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import ui.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
import java.util.stream.Collectors;

public class AddNodeController implements ControllableScreen, Observer {
    private ScreenController parent;
    private HospitalMap map;
    private FloorNumber currentFloor;
    private MapViewer mapViewer;
    private Stack<MapEditorMemento> mapEditorMementos = new Stack<>();

    //Nodes in the map
    private ArrayList<NodeCheckBox> nodeCheckBoxes = new ArrayList<NodeCheckBox>();
    private ArrayList<EdgeCheckBox> edgeCheckBoxes = new ArrayList<EdgeCheckBox>();

    ShakeTransition s = new ShakeTransition();

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
        nodeAlignedLines = new ArrayList<Line>();
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

    public void clearInputs(){
        //todo clear all the text and options from UI inputs
    }

    public void returnPressed(ActionEvent e){
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
                showNodesbyFloor(currentFloor);
                showEdgesbyFloor(currentFloor);
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
            NodeCheckBox cb = new NodeCheckBox(node);
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
            EdgeCheckBox cb = new EdgeCheckBox(edge);
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
            if(nodeEditSelectedNode == null){
                nodeEditSelectedNode = source.getNode();
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
            if (!edgeRemoveList.getItems().contains(source.getEdge()))
                edgeRemoveList.getItems().add(source.getEdge());
        }
        else{
            edgeRemoveList.getItems().remove(source.getEdge());
        }
    }

    @Override
    public void update(Observable o, Object arg){
        if(arg instanceof PathID){
            setFloor(((PathID) arg).getFloor());
        }
    }

    //----------------------NODE TAB START--------------------//

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
    private ArrayList<Line> nodeAlignedLines;

    public void nodeAddXEntered(ActionEvent e){
        String text = nodeAddXField.getText();
        nodeAddXField.setText(text);
    }
    public void nodeAddYEntered(ActionEvent e){
        String text = nodeAddYField.getText();
        nodeAddYField.setText(text);
    }

    public void nodeAddShortEntered(ActionEvent e){
        String text = nodeAddShortField.getText();
        nodeAddYField.setText(text);
    }

    public void nodeAddNameEntered(ActionEvent e){
        String text = nodeAddNameField.getText();
        nodeAddNameField.setText(text);
    }

    public void nodeAddFloorSelected(ActionEvent e){
        String text = ((MenuItem)e.getSource()).getText();
        nodeAddFloorDropDown.setText(text);
    }

    public void nodeAddBuildingSelected(ActionEvent e){
        String text = ((MenuItem)e.getSource()).getText();
        nodeAddBuildingDropDown.setText(text);
    }

    public void nodeAddTypeSelected(ActionEvent e){
        String text = ((MenuItem)e.getSource()).getText();
        nodeAddTypeDropDown.setText(text);
    }

    public void nodeAddEnterPressed(ActionEvent e){
        ArrayList<Node> connections = new ArrayList<>();
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected()) connections.add(n.getNode());
        }
        try {
            saveStateToMemento();
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
            if(!nodeAddXField.getText().matches("[0-9]+")){
                s.shake(nodeAddXField);
            }
            if(!nodeAddYField.getText().matches("[0-9]+")){
                s.shake(nodeAddYField);
            }
            if(nodeAddBuildingDropDown.getText().equals("Building")){
                s.shake(nodeAddBuildingDropDown);
            }
            if(nodeAddTypeDropDown.getText().equals("NodeType")){
                s.shake(nodeAddTypeDropDown);
            }
            if(nodeAddShortField.getText().equals("")){
                s.shake(nodeAddShortField);
            }
            if(nodeAddNameField.getText().equals("")){
                s.shake(nodeAddNameField);
            }
        }
        refreshNodesandEdges();
    }

    public void nodeAddCancelPressed(ActionEvent e){
        //todo cancel node add
    }

    public void mapPaneClicked(MouseEvent e){
        if (e.getClickCount() == 2 && nodeAddTab.isSelected() && nodeTab.isSelected()){
            setNewLocation((int)e.getX(), (int)e.getY(), nodeAddLocation, nodeAddXField, nodeAddYField);
        }
    }

    private void setNewLocation(int x, int y, javafx.scene.Node moved, JFXTextField x_text, JFXTextField y_text ){
        int x_aligned = x;
        int y_aligned = y;

        mapPane.getChildren().removeAll(nodeAlignedLines);
        nodeAlignedLines.clear();
        ArrayList<Node> h_neighbors = new ArrayList<>();
        h_neighbors.addAll(map.getNodesInHorizontal(x,y,currentFloor));
        ArrayList<Node> v_neighbors = new ArrayList<>();
        v_neighbors.addAll(map.getNodesInVertical(x,y,currentFloor));

        if(h_neighbors.size() != 0){
            y_aligned = (h_neighbors.get(0).getY());
        }
        if(v_neighbors.size() != 0){
            x_aligned = (v_neighbors.get(0).getX());

        }
        if(x != x_aligned){
            Line line = newAllignLine();
            line.setLayoutX(x_aligned);
            line.setLayoutY(y_aligned);
            line.setEndX(0);
            line.setEndY((v_neighbors.get(0).getY()) - y_aligned);
            nodeAlignedLines.add(line);
            mapPane.getChildren().add(line);

        }
        if(y != y_aligned){
            Line line = newAllignLine();
            line.setLayoutX(x_aligned);
            line.setLayoutY(y_aligned);
            line.setEndX((h_neighbors.get(0).getX()) - x_aligned);
            line.setEndY(0);
            nodeAlignedLines.add(line);
            mapPane.getChildren().add(line);
        }
        if(moved instanceof AnimatedCircle){
            AnimatedCircle movedCircle = (AnimatedCircle) moved;
            movedCircle.setCenterX(x_aligned);
            movedCircle.setCenterY(y_aligned);
        }
        else{
            moved.setLayoutX(x_aligned);
            moved.setLayoutY(y_aligned);
        }
        if(!mapPane.getChildren().contains(moved))
            mapPane.getChildren().add(moved);
        x_text.setText(Integer.toString(x_aligned));
        y_text.setText(Integer.toString(y_aligned));
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
        saveStateToMemento();
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected()) map.removeNode(n.getNode());
        }
        refreshNodesandEdges();
        nodeRemoveSelectedList.getItems().clear();
    }

    public void nodeRemoveCancelPressed(ActionEvent e){
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

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    EventHandler<MouseEvent> boxOnMousePressedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            orgTranslateX = source.getLayoutX();
            orgTranslateY = source.getLayoutY();

            NodeCheckBox cb =((NodeCheckBox)event.getSource());
            Node n = cb.getNode();
            nodeEditNameField.setText(n.getLongName());
            nodeEditShortField.setText(n.getShortName());
            nodeEditBuildingDropDown.setText(n.getBuilding());
            nodeEditFloorDropDown.setText(n.getFloor().getDbMapping());
            nodeEditTypeDropDown.setText(n.getType());
            nodeEditXField.setText(Integer.toString((int)(cb.getLayoutX())));
            nodeEditYField.setText(Integer.toString((int)(cb.getLayoutY())));
            nodeEditIDLabel.setText(n.getID());
        }
    };

    EventHandler<MouseEvent> boxOnMouseDraggedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            source.setLayoutX(newTranslateX);
            source.setLayoutY(newTranslateY);

            nodeEditXField.setText(Integer.toString((int)((source.getLayoutX()+9))));
            nodeEditYField.setText(Integer.toString((int)((source.getLayoutY()+9))));

            if(((NodeCheckBox)source).isSelected()){
                ((NodeCheckBox)source).setSelected(false);
                nodeEditSelectedNode = null;
            }
        }
    };

    public void nodeEditXEntered(ActionEvent e){
        String text = nodeEditXField.getText();
        nodeEditXField.setText(text);
    }

    public void nodeEditYEntered(ActionEvent e){
        String text = nodeEditYField.getText();
        nodeEditYField.setText(text);
    }

    public void nodeEditShortEntered(ActionEvent e){
        String text = nodeEditShortField.getText();
        nodeEditShortField.setText(text);
    }

    public void nodeEditNameEntered(ActionEvent e){
        String text = nodeEditNameField.getText();
        nodeEditNameField.setText(text);
    }

    public void nodeEditFloorSelected(ActionEvent e){
        String text = ((MenuItem)e.getSource()).getText();
        nodeEditFloorDropDown.setText(text);
    }

    public void nodeEditBuildingSelected(ActionEvent e){
        String text = ((MenuItem)e.getSource()).getText();
        nodeEditBuildingDropDown.setText(text);
    }

    public void nodeEditTypeSelected(ActionEvent e){
        String text = ((MenuItem)e.getSource()).getText();
        nodeEditTypeDropDown.setText(text);
    }

    public void nodeEditEnterPressed(ActionEvent e){
        Node node = nodeEditSelectedNode;
        
        if(node == null){
            System.out.println("Warning: No nodes selected.");
            return;
        }
        if(nodeEditXField.getText().equals("") || nodeEditYField.getText().equals("") || nodeEditFloorDropDown.getText().equals("")
                || nodeEditBuildingDropDown.getText().equals("") || nodeEditTypeDropDown.getText().equals("")
                || nodeEditNameField.getText().equals("") || nodeEditShortField.getText().equals("")){
            if(!nodeEditXField.getText().matches("[0-9]+")){
                s.shake(nodeEditXField);
            }
            if(!nodeEditYField.getText().matches("[0-9]+")){
                s.shake(nodeEditYField);
            }
            if(nodeEditBuildingDropDown.getText().equals("Building")){
                s.shake(nodeEditBuildingDropDown);
            }
            if(nodeEditTypeDropDown.getText().equals("NodeType")){
                s.shake(nodeEditTypeDropDown);
            }
            if(nodeEditShortField.getText().equals("")){
                s.shake(nodeEditShortField);
            }
            if(nodeEditNameField.getText().equals("")){
                s.shake(nodeEditNameField);
            }
            return;
        }
        saveStateToMemento();
        System.out.println("X: " + node.getX() + "\nY: " + node.getY());
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
    //-----------------------NODE TAB END---------------------//

    //----------------------EDGE TAB START--------------------//

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
        Node nodeOne = edgeAddNode1;
        Node nodeTwo = edgeAddNode2;

        if(nodeOne == null || nodeTwo == null){
            System.out.println("Warning: Less than two nodes selected.");
        } else {
            saveStateToMemento();
            map.addEdge(new Edge(nodeOne, nodeTwo));
        }
        refreshNodesandEdges();
    }

    public void edgeAddCancelPressed(ActionEvent e){
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
    @FXML
    private JFXSlider slideBarZoom;

    public void edgeRemoveEnterPressed(ActionEvent e){
        saveStateToMemento();
        for(Edge edge : edgeRemoveList.getItems()){
            map.removeEdge(edge);
        }
        edgeRemoveList.getItems().clear();
        refreshNodesandEdges();
    }

    public void edgeRemoveCancelPressed(ActionEvent e){
//        edgeRemoveList.getItems().clear();
        undo();
        refreshNodesandEdges();
    }
    //---------------------EDGE TAB END-------------------//

    //-------------------------ZOOM-----------------------//
    //when + button is pressed zoom in map
    public void zinPressed(ActionEvent e){
        setZoom(slideBarZoom.getValue()+0.2);
    }

    //when - button pressed zoom out map
    public void zoutPressed(ActionEvent e){
        setZoom(slideBarZoom.getValue()-0.2);
    }

    public void setZoom(double zoom){
        slideBarZoom.setValue(zoom);
        mapViewer.setScale(zoom);
    }

    // Memento Stuff
    public void saveStateToMemento(){
        ArrayList<Node> oldNodes = new ArrayList<>();
        ArrayList<Edge> oldEdges = new ArrayList<>();
        for(Node node : map.getNodeMap()) oldNodes.add(node.getCopy());
        for(Edge edge : map.getEdgeMap()) oldEdges.add(edge.getCopy());
        ArrayList<Node> mapNodeState = new ArrayList<>(oldNodes);
        ArrayList<Edge> mapEdgeState = new ArrayList<>(oldEdges);
        mapEditorMementos.push(new MapEditorMemento(mapNodeState, mapEdgeState));
    }
    public void setMemento(MapEditorMemento memento){
        map.setEdgeMap(memento.getSavedEdgeState());
        map.setNodeMap(memento.getSavedNodeState());
        refreshNodesandEdges();
    }
    public void undo(){
        System.out.println("Undoing");
        System.out.println("Stack size: " + mapEditorMementos.size());
        if(mapEditorMementos.size() > 0){
            setMemento(mapEditorMementos.pop());
        }
    }
}
