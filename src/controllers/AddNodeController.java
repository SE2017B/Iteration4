package controllers;

import a_star.Node;
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
import kioskEngine.KioskEngine.*;


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

        txtfldX.textProperty().addListener((observable, oldValue, newValue) -> xCoordEntered());
        txtfldY.textProperty().addListener((observable, oldValue, newValue) -> yCoordEntered());

    }

    public void onShow() {
        x = "";
        y = "";
        failText.setVisible(false);
        nodeLocation.setVisible(false);
        ArrayList<Node> nodes = parent.getEngine().getMap().getNodesAsArrayList();


        for (Node node : nodes) {
            NodeCheckBox box = new NodeCheckBox();
            box.setNode(node); //sets checkbox to node location

            box.setVisible(true);
            mapPane.getChildren().add(box);
            nodeCheckBoxes.add(box);

        }
    }

    //Action upon pressing enter
    //variables for all text fields is set up and populated
    //add node command is executed
    //user is returned to menu screen
    public void enterPressed(ActionEvent e) {
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

            parent.getEngine().addNode(nodeID, x, y, floor, building, nodeType, name, connections);
            System.out.println("Enter Pressed");
            parent.setScreen(ScreenController.AdminMenuID);
        } else
            failText.setVisible(true);

    }

    //comands for button cancel press
    public void cancelPressed(ActionEvent e) {
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.AdminMenuID);
    }

    //set up variables when building drop down selected
    @FXML
    void buildingSelected(ActionEvent e) {
        System.out.println("Building Selected");
        building = ((MenuItem) e.getSource()).getText();
        //Setting the variables equal to values read from UI
        buildingDropDown.setText(building);
    }

    //set up variable when floor drop down selected
    @FXML
    void floorSelected(ActionEvent e) {
        System.out.println("Floor Selected");
        floor = ((MenuItem) e.getSource()).getText();
        //Setting the variables equal to values read from UI
        floorDropDown.setText(floor);
    }

    //set up variable when floor drop down selected
    @FXML
    void nodeTypeSelected(ActionEvent e) {
        System.out.println("Node Type Selected");
        nodeType = ((MenuItem) e.getSource()).getText();
        //Setting the variables equal to values read from UI
        nodeTypeDropDown.setText(nodeType);
    }

    //set variable to text from text field name
    @FXML
    void filledNodeID(ActionEvent e) {
        //Setting the variables equal to values read from UI
        nodeID = txtfldID.getText();
        System.out.println(nodeID);
    }

    void xCoordEntered() {
        x = txtfldX.getText();
        if (!y.equals(""))
            drawNodeLocation();
    }

    void yCoordEntered() {
        y = txtfldY.getText();
        if (!x.equals(""))
            drawNodeLocation();
    }

    void drawNodeLocation() {
        System.out.println("Drawing Node: " + x + " " + y);
        nodeLocation.setLayoutX(Integer.parseInt(x));
        nodeLocation.setLayoutY(Integer.parseInt(y));
        nodeLocation.setVisible(true);


    }
}
