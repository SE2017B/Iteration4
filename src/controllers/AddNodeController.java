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
        HospitalMap.addNodeandEdges("putNodeIDHere",
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

    public void nodeRemoveEnterPressed(ActionEvent e){
        System.out.println("Node Remove Enter Pressed");
        //todo add node
        for(NodeCheckBox n : nodeCheckBoxes){
            if(n.isSelected()) HospitalMap.deleteNode(n.getNode());
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
        HospitalMap.editNode(node,
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
}
