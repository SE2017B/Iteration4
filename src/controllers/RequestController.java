/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import javafx.scene.layout.Pane;
import map.HospitalMap;
import map.Node;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import DepartmentSubsystem.ServiceRequest;
import DepartmentSubsystem.Staff;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class RequestController implements ControllableScreen{
    private ScreenController parent;
    private String serviceType;
    private String hour;
    private String min;
    private String merid;
    private Staff staffMember;
    private LocalDate date;

    private static int requestIDCount = 0;



    private ArrayList<Staff> staff;
    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    @FXML
    private Button btncreate;

    @FXML
    private Button btncancel;

    @FXML
    private MenuButton serviceDropDown;

    @FXML
    private ChoiceBox staffDropDown;


    @FXML
    private MenuButton hourDropDown;

    @FXML
    private MenuButton minDropDown;

    @FXML
    private MenuButton meridDropDown;

    @FXML
    private DatePicker dateMenu;

    @FXML
    private Label infoLabel;

    @FXML
    private TextArea infoText;

    @FXML
    private ChoiceBox locationChoiceBox;


    @FXML
    private Label staffNameLabel;


    public void init(){
    }

    public void onShow(){
        //Update the nodes in the map
        ArrayList<Node> nodes = HospitalMap.getNodesForSearch();

        //update the items in the checklist
        locationChoiceBox.setItems(FXCollections.observableList(nodes));

    }

    public void resolveServicePressed(ActionEvent e){//todo
        }

    public void requestCreatePressed(ActionEvent e){
        //todo create the request

    }
    public void cancelPressed(ActionEvent e){
        System.out.println("Cancel Pressed");
    }

    public void logoutPressed(ActionEvent e){
        System.out.println("Logout Pressed");
        parent.setScreen(ScreenController.LogoutID);
    }

    public void editPressed(ActionEvent e){
        System.out.println("Edit Pressed");
        parent.setScreen(ScreenController.AddNodeID);
    }


    public void serviceSelected(ActionEvent e){
        System.out.println("Service Selected");
        serviceType = ((MenuItem) e.getSource()).getText();
        serviceDropDown.setText(serviceType);
        staff = parent.getEngine().getService(serviceType).getPersonnel();
        if(staff.size() != 0) {
            staffDropDown.setItems(FXCollections.observableList(staff));
            staffDropDown.setDisable(false);
        }

        if(serviceType.equals("Food")){
            infoLabel.setText("Food Type");
            infoLabel.setVisible(true);
            infoText.setVisible(true);
        }
        else{
            infoLabel.setVisible(false);
            infoText.setVisible(false);
        }


    }

    public void hourSelected(ActionEvent e){
        System.out.println("Hour Selected");
        String hour = ((MenuItem) e.getSource()).getText();
        hourDropDown.setText(hour);
    }

    public void minSelected(ActionEvent e){
        System.out.println("Min Selected");
        String min = ((MenuItem) e.getSource()).getText();
        minDropDown.setText(min);
    }

    public void meridSelected(ActionEvent e){
        System.out.println("Merid Selected");
        String merid = ((MenuItem) e.getSource()).getText();
        meridDropDown.setText(merid);
    }

    public void dateSelected(ActionEvent e){
        System.out.println("Date Selected" );
        date = ((DatePicker)e.getSource()).getValue();
    }

    public void setForStaff(Staff staff){
        staffMember = staff;
        staffNameLabel.setText(staff.getFullName());
    }
}
