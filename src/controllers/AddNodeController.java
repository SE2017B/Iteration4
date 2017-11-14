package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.lang.*;
import kioskEngine.KioskEngine.*;



import java.util.List;

public class AddNodeController implements ControllableScreen{
    private ScreenController parent;


    //node variables
    private String name;
    private String nodeID;
    private String x;
    private String y;
    private String floor;
    private String building;
    private String nodeType;


    //setter for parent
    public void setParentController(ScreenController parent){
        this.parent = parent;
    }


    @FXML
    private TextField txtfldX;

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

    public void init(){}

    public void onShow(){}

    //Action upon pressing enter
    //variables for all text fields is set up and populated
    //add node command is executed
    //user is returned to menu screen
    public void enterPressed(ActionEvent e){
        nodeID = txtfldID.getText();
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

        kioskEngine.KioskEngine.addNode(nodeID, x, y, floor, building, nodeType, name);
        System.out.println("Enter Pressed");
        parent.setScreen(ScreenController.AdminMenuID);
    }
    //comands for button cancel press
    public void cancelPressed(ActionEvent e){
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.AdminMenuID);
    }

    //set up variables when building drop down selected
    @FXML
    void buildingSelected(ActionEvent e)
    {
        System.out.println("Building Selected");
        building = ((MenuItem) e.getSource()).getText();
        //Setting the variables equal to values read from UI
        buildingDropDown.setText(building);
    }

    //set up variable when floor drop down selected
    @FXML
    void floorSelected(ActionEvent e)
    {
        System.out.println("Floor Selected");
        floor = ((MenuItem) e.getSource()).getText();
        //Setting the variables equal to values read from UI
        floorDropDown.setText(floor);
    }

    //set up variable when floor drop down selected
    @FXML
    void nodeTypeSelected(ActionEvent e)
    {
        System.out.println("Node Type Selected");
        nodeType = ((MenuItem) e.getSource()).getText();
        //Setting the variables equal to values read from UI
        nodeTypeDropDown.setText(nodeType);
    }

    //set variable to text from text field name
    @FXML
    void filledNodeID(ActionEvent e)
    {
        //Setting the variables equal to values read from UI
        nodeID = txtfldID.getText();
        System.out.println(nodeID);
    }



    }
