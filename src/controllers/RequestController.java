/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import DepartmentSubsystem.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import map.HospitalMap;
import map.Node;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import DepartmentSubsystem.ServiceRequest;
import DepartmentSubsystem.Staff;
import DepartmentSubsystem.DepartmentSubsystem;


import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class RequestController implements ControllableScreen{
    private ScreenController parent;
    private String serviceType;
    private LocalTime time;
    private Staff staffMember;
    private LocalDate date;
    private String nameServiceFile;
    private String nameDept;
    private String nameService;
    private String nameStaff;
    private ArrayList<String> deps;
    private ArrayList<Service> serv;
    private ArrayList<String>
    private DepartmentSubsystem DSS = DepartmentSubsystem.getSubsystem();

    private static int requestIDCount = 0;

    private ArrayList<Staff> staff;
    public void setParentController(ScreenController parent){
        this.parent = parent;
    }
    @FXML
    private Label staffNameLabel;

    @FXML
    private ChoiceBox<String> choiceBoxDept;

    @FXML
    private MenuButton menuButtonAl;

    @FXML
    private JFXButton btncreate;

    @FXML
    private JFXButton btncancel;

    @FXML
    private JFXDatePicker dateMenu;

    @FXML
    private JFXTimePicker timeMenu;

    @FXML
    private ChoiceBox<Node> locationChoiceBox;

    @FXML
    private Pane servicePane1;

    @FXML
    private ChoiceBox<String> choiceBoxService;

    @FXML
    private ChoiceBox<String> choiceBoxStaff;

    @FXML
    private Label lblSelectedService;

    @FXML
    private JFXButton btncreate1;

    @FXML
    private JFXButton btnserviceResCancel;

    @FXML
    private JFXListView<String> resolveServiceListView;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnEditMap;



    public void init(){
    }

    public void onShow(){
        //Update the nodes in the map
        ArrayList<Node> nodes = HospitalMap.getNodesForSearch();

        //update the items in the checklist
        locationChoiceBox.setItems(FXCollections.observableList(nodes));

    }
    @FXML

    public void resolveServicePressed(ActionEvent e)
    {
        //todo
    }

    public void requestCreatePressed(ActionEvent e)
    {
        //todo create the request
        //submitRequest(Service service, String time, String date, Node location, Staff person, int RID){


    }
    public void cancelPressed(ActionEvent e)
    {
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.LoginID);
    }

    public void logoutPressed(ActionEvent e){
        System.out.println("Logout Pressed");
        parent.setScreen(ScreenController.LogoutID);
    }

    public void editPressed(ActionEvent e){
        System.out.println("Edit Pressed");
        parent.setScreen(ScreenController.AddNodeID);
    }

    public void selectAlgorithmPath(ActionEvent e)
    {
        //todo select algorithms
    }

    public void deptSelected(ActionEvent e)
    {
        //todo fix deptSelected. Populate checkboxes based on the selection from previous checkbox
       // ObservableList<String> obsDeps = FXCollections.observableArrayList(deps);
        choiceBoxDept.setItems(FXCollections.observableList(DepartmentSubsystem.getSubsystem().getDepartments()));
        nameDept = choiceBoxDept.getSelectionModel().getSelectedItem().toString();
        choiceBoxDept.setDisable(false);

        choiceBoxService.setItems(FXCollections.observableList(DepartmentSubsystem.getSubsystem().getServices(nameDept)));
        nameService = choiceBoxService.getSelectionModel().getSelectedItem().toString();
        choiceBoxService.setDisable(false);

        choiceBoxStaff.setItems(FXCollections.observableList(DepartmentSubsystem.getSubsystem().getStaff(nameService)));
        nameStaff = choiceBoxStaff.getSelectionModel().getSelectedItem().toString();
        choiceBoxStaff.setDisable(false);

    }
//    public void serviceSelected(ActionEvent e){
//        System.out.println("Service Selected");
//        serviceType = ((MenuItem) e.getSource()).getText();
//        serviceDropDown.setText(serviceType);
//        staff = parent.getEngine().getService(serviceType).getPersonnel();
//        if(staff.size() != 0) {
//            staffDropDown.setItems(FXCollections.observableList(staff));
//            staffDropDown.setDisable(false);
//        }
//
//        if(serviceType.equals("Food")){
//            infoLabel.setText("Food Type");
//            infoLabel.setVisible(true);
//            infoText.setVisible(true);
//        }
//        else{
//            infoLabel.setVisible(false);
//            infoText.setVisible(false);
//        }
//
//
//    }

    public void timeSelected(ActionEvent e) {
        //todo Time Selected menu
        System.out.println("Time selescted");
        time = ((JFXTimePicker)e.getSource()).getValue();
    }

    public void dateSelected(ActionEvent e){
        //todo check if it works like this
        System.out.println("Date Selected" );
        date = ((JFXDatePicker)e.getSource()).getValue();
    }

}
