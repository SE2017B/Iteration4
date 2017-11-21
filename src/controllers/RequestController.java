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
import com.jfoenix.skins.JFXTimePickerContent;
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

import search.SearchStrategy;
import DepartmentSubsystem.DepartmentSubsystem;
import org.omg.CORBA.Request;


import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RequestController implements ControllableScreen{
    private ScreenController parent;
    private HospitalMap map;
    private String serviceType;
    private LocalTime time;
    private Staff staffMember;
    private LocalDate date;
    private String nameServiceFile;
    private String nameDept;
    private String nameService;
    private String nameStaff;
    private String selectedAlg;
    private ArrayList<String> deps;
    private ArrayList<Service> serv;
    private DepartmentSubsystem DSS = DepartmentSubsystem.getSubsystem();

    private static int requestIDCount = 0;

    private ArrayList<Staff> staff;
    public void setParentController(ScreenController parent){
        this.parent = parent;
    }
    @FXML
    private Label staffNameLabel;

    @FXML
    private ChoiceBox<Department> choiceBoxDept;

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
    private ChoiceBox<Service> choiceBoxService;

    @FXML
    private ChoiceBox<Staff> choiceBoxStaff;

    @FXML
    private Label lblSelectedService;

    @FXML
    private JFXButton btncreate1;

    @FXML
    private JFXButton btnserviceResCancel;

    @FXML
    private JFXListView<ServiceRequest> resolveServiceListView;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnEditMap;



    public void init(){
        map = new HospitalMap();
    }

    public void onShow(){
        //Update the nodes in the map

        ArrayList<Node> nodes = map.getNodeMap();

        //todo populate list of requests upon login
        //resolveServiceListView.getItems().add(Department.getBacklog().values());

        //update the items in the checklist
        locationChoiceBox.setItems(FXCollections.observableList(nodes));

        searchStrategyChoice.setItems(FXCollections.observableList(map.getSearches()));

    }
    @FXML

    public void resolveServicePressed(ActionEvent e)
    {
        //todo test?
        //resolveServiceListView.getItems().remove(selectedService);
        //List<Integer> selectedRequests = new ArrayList<Integer>(resolveServiceListView.getSelectionModel().getSelectedItems());
        resolveServiceListView.getItems().removeAll(resolveServiceListView.getSelectionModel().getSelectedItems());
        System.out.println("Requests " + (resolveServiceListView.getSelectionModel().getSelectedItems()) + "resolved");
    }

    public void requestCreatePressed(ActionEvent e)
    {
        //todo create the request
        //todo get Request ID
        int reqID = 444;
        //todo does this need to be added t listView or is it handled?
        //submitRequest(Service service, String time, String date, Node location, Staff person, int RID){
        ServiceRequest nReq = new ServiceRequest(choiceBoxService.getValue(), reqID, locationChoiceBox.getValue(), time.toString(), date.toString(), choiceBoxStaff.getValue());

    }
    public void cancelPressed(ActionEvent e)
    {
        //todo clear the selected items
        System.out.println("Cancel Pressed");
        choiceBoxStaff.getItems().clear();
        choiceBoxService.getItems().clear();
        choiceBoxDept.getItems().clear();
        //time = JFXDatePicker.setValue(null);
        //date = JFXTimePicker
        //parent.setScreen(ScreenController.LoginID);
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
        //todo test?
        System.out.println("Algorithm Selected");
        selectedAlg = ((MenuItem)e.getSource()).getText();
        menuButtonAl.setText(selectedAlg);
    }

    public void deptSelected(ActionEvent e)
    {
        //todo fix deptSelected. Add listener. Test?
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
        //todo test?
        System.out.println("Time selescted");
        time = ((JFXTimePicker)e.getSource()).getValue();
    }

    public void dateSelected(ActionEvent e){
        //todo test?
        System.out.println("Date Selected" );
        date = ((JFXDatePicker)e.getSource()).getValue();
    }

    public void setForStaff(Staff staff){
        staffMember = staff;
        staffNameLabel.setText(staff.getFullName());
    }

    //////////////////////////////////////////////////////////
    /////////           Settings Tab
    //////////////////////////////////////////////////////////

    @FXML
    private ChoiceBox<SearchStrategy> searchStrategyChoice;
    @FXML
    private JFXButton saveSettingsButton;

    public void saveSettingsPressed(ActionEvent e){
        if(searchStrategyChoice.getValue() != null){
            map.setSearchStrategy(searchStrategyChoice.getValue());
        }

    }

}
