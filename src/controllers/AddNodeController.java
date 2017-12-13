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

import java.util.*;
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
    @FXML
    private JFXButton undoButton;

    public void init() {
        map = HospitalMap.getMap();
        mainAnchorPane.prefWidthProperty().bind(parent.prefWidthProperty().subtract(400));
        mainAnchorPane.prefHeightProperty().bind(parent.prefHeightProperty());

        mapViewer = new MapViewer(this, mainAnchorPane);
        mainAnchorPane.getChildren().add(mapViewer.getMapViewerPane());
        mapViewer.setFloor(FloorNumber.FLOOR_GROUND);
        mapPane = mapViewer.getMapPane();

        mapPane.setOnMouseClicked(e -> mapPaneClicked(e));
        mapPane.scaleXProperty().addListener( e -> setZoom(mapPane.getScaleX()));

        nodeAddLocation = new AnimatedCircle();
        nodeAlignedLines = new ArrayList<Line>();
        nodeAddLocation.setFill(Color.DODGERBLUE);

        mapViewer.getMapScrollPane().setPannable(true);

        nodeCheckBoxes = new ArrayList<NodeCheckBox>();
        edgeCheckBoxes = new ArrayList<EdgeCheckBox>();
        nodeEditSelectedNodes = new ArrayList<NodeCheckBox>();

        nodeTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        edgeTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        nodeAddTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        nodeEditTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        nodeRemoveTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        edgeAddTab.setOnSelectionChanged(e -> refreshNodesandEdges());
        edgeRemoveTab.setOnSelectionChanged(e -> refreshNodesandEdges());

        refreshNodesandEdges();

        nodeContextMenu = new ContextMenu();
        MenuItem alignH = new MenuItem("Align Horizontal");
        MenuItem alignV = new MenuItem("Align Vertical");
        alignH.setOnAction(e -> alignHPressed(e));
        alignV.setOnAction(e -> alignVPressed(e));
        nodeContextMenu.getItems().addAll(alignH,alignV);
    }

    public void onShow() {
        checkUndo();
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
        resetNodeAdd();
        if(nodeRemoveTab != null) {
            nodeRemoveSelectedList.getItems().clear();
        }
        resetNodeEdit();
        resetEdgeAdd();
        if(edgeRemoveTab != null) {
            edgeRemoveList.getItems().clear();
        }
        refreshNodesandEdges();
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
                cb.setOnContextMenuRequested(e -> {
                    if(nodeEditSelectedNodes.size() > 1){
                        nodeContextMenu.show(cb,e.getScreenX(),e.getScreenY());
                    }
                });
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
        setZoom(mapPane.getScaleX());
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
            if (nodeEditSelectedNodes.size() == 0){
                nodeEditSelectedNodes.add(0,source);
                setEditForNode(source);
            }
            else if (nodeEditSelectedNodes.get(0).equals(source)) {
                nodeEditSelectedNodes.remove(source);
                if(nodeEditSelectedNodes.size() == 0){
                    resetNodeEdit();
                }
                else{
                    setEditForNode(nodeEditSelectedNodes.get(0));
                }
            }
            else if(nodeEditSelectedNodes.contains(source)){
                nodeEditSelectedNodes.remove(source);
            }
            else{
                nodeEditSelectedNodes.add(0,source);
                setEditForNode(source);
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

    public void undoPressed(ActionEvent e ){
        System.out.println("Undo Pressed");
        undo();
        checkUndo();
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
            if(!nodeAddXField.getText().matches("[0-9]+")){
                s.shake(nodeAddXField);
            }
            if(!nodeAddYField.getText().matches("[0-9]+")){
                s.shake(nodeAddYField);
            }
            if(nodeAddBuildingDropDown.getText().equals("Building")){
                s.shake(nodeAddBuildingDropDown);
            }
            if(nodeAddFloorDropDown.getText().equals("Floor")){
                s.shake(nodeAddFloorDropDown);
            }
            if(nodeAddTypeDropDown.getText().equals("Type")){
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
        resetNodeAdd();
        refreshNodesandEdges();
    }

    public void resetNodeAdd(){
        if(nodeAddTab != null) {
            nodeAddXField.setText("");
            nodeAddYField.setText("");
            nodeAddBuildingDropDown.setText("Building");
            nodeAddFloorDropDown.setText("Floor");
            nodeAddTypeDropDown.setText("Type");
            nodeAddNameField.setText("");
            nodeAddShortField.setText("");
        }
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
            Line line = newAlignLine();
            line.setLayoutX(x_aligned);
            line.setLayoutY(y_aligned);
            line.setEndX(0);
            line.setEndY((v_neighbors.get(0).getY()) - y_aligned);
            nodeAlignedLines.add(line);
            mapPane.getChildren().add(line);

        }
        if(y != y_aligned){
            Line line = newAlignLine();
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

    private Line newAlignLine(){
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
    @FXML
    private Label nodeRemoveLabel;

    public void nodeRemoveAddToList(NodeCheckBox node){
        nodeRemoveSelectedList.getItems().add(node.getNode());
    }

    public void nodeRemoveEnterPressed(ActionEvent e){

        if(nodeRemoveSelectedList.getItems().isEmpty()){
            s.shake(nodeRemoveLabel);
        }
        else {
            saveStateToMemento();
            for (NodeCheckBox n : nodeCheckBoxes) {
                if (n.isSelected()) map.removeNode(n.getNode());
            }
            refreshNodesandEdges();
            nodeRemoveSelectedList.getItems().clear();
        }
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


    private ArrayList<NodeCheckBox> nodeEditSelectedNodes;

    private ContextMenu nodeContextMenu;


    double orgSceneX, orgSceneY;

    EventHandler<MouseEvent> boxOnMousePressedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            NodeCheckBox source = (NodeCheckBox) event.getSource();
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            source.setOrgX(source.getLayoutX());
            source.setOrgY(source.getLayoutY());

            for(NodeCheckBox cb : nodeEditSelectedNodes){
                cb.setOrgX(cb.getLayoutX());
                cb.setOrgY(cb.getLayoutY());
            }
        }
    };

    EventHandler<MouseEvent> boxOnMouseDraggedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            NodeCheckBox source = (NodeCheckBox) event.getSource();
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;

            source.setLayoutX(source.getOrgX() + offsetX);
            source.setLayoutY(source.getOrgY() + offsetY);


            for(NodeCheckBox cb : nodeEditSelectedNodes){
                    cb.setLayoutX(cb.getOrgX() + offsetX);
                    cb.setLayoutY(cb.getOrgY() + offsetY);

            }

            nodeEditXField.setText(Integer.toString((int)((source.getLayoutX()+9))));
            nodeEditYField.setText(Integer.toString((int)((source.getLayoutY()+9))));

            if(source.isSelected()){
                source.setSelected(false);
                nodeEditSelectedNodes.remove(source);
            }
        }
    };

    private void setEditForNode(NodeCheckBox source){
        Node n = source.getNode();
        nodeEditNameField.setText(n.getLongName());
        nodeEditShortField.setText(n.getShortName());
        nodeEditBuildingDropDown.setText(n.getBuilding());
        nodeEditFloorDropDown.setText(n.getFloor().getDbMapping());
        nodeEditTypeDropDown.setText(n.getType());
        nodeEditXField.setText(Integer.toString((int)(source.getLayoutX())));
        nodeEditYField.setText(Integer.toString((int)(source.getLayoutY())));
        nodeEditIDLabel.setText(n.getID());
    }

    private void nodeEditAlignVertical(){
        double x = nodeEditSelectedNodes.get(0).getLayoutX();
        for(NodeCheckBox cb : nodeEditSelectedNodes){
            cb.setLayoutX(x);
            cb.setOrgX(x);
        }
    }

    private void nodeEditAlignHorizontal(){
        double y = nodeEditSelectedNodes.get(0).getLayoutY();
        for(NodeCheckBox cb : nodeEditSelectedNodes){
            cb.setLayoutY(y);
            cb.setOrgY(y);
        }
    }

    public void nodeEditXEntered(ActionEvent e){
        String text = nodeEditXField.getText();
        nodeEditXField.setText(text);
    }

    public void alignHPressed(ActionEvent e){
        nodeEditAlignHorizontal();
    }
    public void alignVPressed(ActionEvent e){
        nodeEditAlignVertical();
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

    public void nodeEditEnterPressed(ActionEvent e) {
        if (nodeEditSelectedNodes.size() > 0) {

            saveStateToMemento();
            Node node = nodeEditSelectedNodes.get(0).getNode();

            if (nodeEditXField.getText().equals("") || nodeEditYField.getText().equals("") || nodeEditFloorDropDown.getText().equals("")
                    || nodeEditFloorDropDown.getText().equals("Floor") || nodeEditBuildingDropDown.getText().equals("Building")
                    || nodeEditTypeDropDown.getText().equals("Type") || nodeEditNameField.getText().equals("")
                    || nodeEditShortField.getText().equals("")) {
                if (!nodeEditXField.getText().matches("[0-9]+")) {
                    s.shake(nodeEditXField);
                }
                if (!nodeEditYField.getText().matches("[0-9]+")) {
                    s.shake(nodeEditYField);
                }
                if (nodeEditFloorDropDown.getText().equals("Floor")) {
                    s.shake(nodeEditFloorDropDown);
                }
                if (nodeEditBuildingDropDown.getText().equals("Building")) {
                    s.shake(nodeEditBuildingDropDown);
                }
                if (nodeEditTypeDropDown.getText().equals("Type")) {
                    s.shake(nodeEditTypeDropDown);
                }
                if (nodeEditShortField.getText().equals("")) {
                    s.shake(nodeEditShortField);
                }
                if (nodeEditNameField.getText().equals("")) {
                    s.shake(nodeEditNameField);
                }
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

            for (NodeCheckBox cb : nodeEditSelectedNodes) {
                Node n = cb.getNode();

                map.editNode(n,
                        Integer.toString((int) cb.getLayoutX()),
                        Integer.toString((int) cb.getLayoutY()),
                        n.getFloor().getDbMapping(),
                        n.getBuilding(),
                        n.getType(),
                        n.getLongName(),
                        n.getShortName());
            }
            refreshNodesandEdges();
        }
    }

    public void nodeEditCancelPressed(ActionEvent e){
        resetNodeEdit();
        refreshNodesandEdges();
    }

    public void resetNodeEdit(){
        if(nodeEditTab != null) {
            nodeEditSelectedNodes.clear();
            nodeEditNameField.clear();
            nodeEditShortField.setText("");
            nodeEditBuildingDropDown.setText("Building");
            nodeEditFloorDropDown.setText("Floor");
            nodeEditTypeDropDown.setText("Type");
            nodeEditXField.setText("");
            nodeEditYField.setText("");
            nodeEditIDLabel.setText("Node ID");
        }

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
    private Label edgeAddNodeOne;
    @FXML
    private Label edgeAddID1Label;
    @FXML
    private Label edgeAddNodeTwo;
    @FXML
    private Label edgeAddID2Label;

    private Node edgeAddNode1;
    private Node edgeAddNode2;

    public void edgeAddEnterPressed(ActionEvent e){
        Node nodeOne = edgeAddNode1;
        Node nodeTwo = edgeAddNode2;

        if(nodeOne == null || nodeTwo == null){

            if(nodeOne == null){
                s.shake(edgeAddNodeOne);
            }
            if(nodeTwo == null){
                s.shake(edgeAddNodeTwo);
            }
        }
        else {

            System.out.println("Warning: Less than two nodes selected.");
            saveStateToMemento();
            map.addEdge(new Edge(nodeOne, nodeTwo));
        }
        refreshNodesandEdges();
    }

    public void edgeAddCancelPressed(ActionEvent e){
        resetEdgeAdd();
        refreshNodesandEdges();
    }

    public void resetEdgeAdd(){
        if(edgeAddTab != null){
            edgeAddID1Label.setText("");
            edgeAddID2Label.setText("");
        }
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
    @FXML
    private Label edgeRemoveLabel;

    public void edgeRemoveEnterPressed(ActionEvent e){

        if(edgeRemoveList.getItems().isEmpty()){
            s.shake(edgeRemoveLabel);
        }

        saveStateToMemento();
        for(Edge edge : edgeRemoveList.getItems()){
            map.removeEdge(edge);
        }
        edgeRemoveList.getItems().clear();
        refreshNodesandEdges();
    }

    public void edgeRemoveCancelPressed(ActionEvent e){
        edgeRemoveList.getItems().clear();
        refreshNodesandEdges();
    }
    //---------------------EDGE TAB END-------------------//



    public void setZoom(double zoom){
        for(NodeCheckBox cb : nodeCheckBoxes){
            cb.setScaleX(1/zoom);
            cb.setScaleY(1/zoom);
        }
    }
    private void checkUndo(){
        if(mapEditorMementos.size() > 0){
            undoButton.setDisable(false);
            undoButton.setOpacity(0.9);
        }
        else{
            undoButton.setDisable(true);
            undoButton.setOpacity(0.5);
        }
    }

    // Memento Stuff
    public void saveStateToMemento(){
        undoButton.setDisable(false);
        undoButton.setOpacity(0.9);
        HashMap<Edge, ArrayList<Node>> newMap = map.getCopy();
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        edges.addAll(newMap.keySet());
        for(Edge edge : newMap.keySet()){
            for(Node node : newMap.get(edge)){
                if(!nodes.contains(node)) nodes.add(node);
            }
        }
        mapEditorMementos.push(new MapEditorMemento(nodes, edges));
    }
    public void setMemento(MapEditorMemento memento){
        map.setNodeMap(memento.getSavedNodeState());
        map.setEdgeMap(memento.getSavedEdgeState());
        refreshNodesandEdges();
    }
    public void undo(){
        if(mapEditorMementos.size() > 0){
            setMemento(mapEditorMementos.pop());
        }
    }



}
