/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import DepartmentSubsystem.*;
import controllers.LoginController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import map.HospitalMap;
import map.Node;
import search.SearchStrategy;

import java.util.ArrayList;

public class RequestController implements ControllableScreen{
    private ScreenController parent;
    private HospitalMap map;
    private String serviceType;
    private String time;
    private Staff staffMember;
    private String date;
    private String nameServiceFile;
    private String nameDept;
    private String nameService;
    private String nameStaff;
    private String selectedAlg;
    private ArrayList<String> deps;
    private ArrayList<Service> serv;
    private DepartmentSubsystem depSub;
    private Service servSelect;
    private ServiceRequest reqServPls;



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
    private Label lblSelectedAdditionalInfo;

    @FXML
    private Label lblSelectedDT;

    @FXML
    private Label lblSelectedLocation;

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
    private AnchorPane servicePane1;

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
    private JFXListView<String> resolveServiceListView;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnEditMap;



    public void init(){
        map = HospitalMap.getMap();

        choiceBoxDept.valueProperty().addListener( (v, oldValue, newValue) -> deptSelected(newValue));
        choiceBoxService.valueProperty().addListener( (v, oldValue, newValue) -> servSelected(newValue));
        choiceBoxStaff.valueProperty().addListener( (v, oldValue, newValue) -> staffSelected(newValue));
        depSub = DepartmentSubsystem.getSubsystem();

        //location set up
        locationChoiceBox.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));


        //Display selected request on label
        resolveServiceListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {

                        lblSelectedService.setText(choiceBoxService.getValue().toString());
                        lblSelectedAdditionalInfo.setText("Test");
                        lblSelectedDT.setText(timeMenu.getValue().toString() + " " + dateMenu.getValue().toString());
                        lblSelectedLocation.setText(locationChoiceBox.getValue().toString());

                    }
                }
        );
    }

    public void onShow(){
        //Dummy requests for testing
//                ServiceRequest test = new ServiceRequest(null,2,null,"123456","22/22/22", null);
//                ServiceRequest test2 = new ServiceRequest(null,3,null,"asdf","zxcv", null);
//                ServiceRequest test3 = new ServiceRequest(null,4,null,"qwer","zxcv", null);
//                resolveServiceListView.getItems().add(test.toString());
//                resolveServiceListView.getItems().add(test2.toString());
//                resolveServiceListView.getItems().add(test3.toString());


        //Staff requests display
        staffNameLabel.setText(depSub.getCurrentLoggedIn().toString());
        if(depSub.getCurrentLoggedIn().getCurrentRequests() == null)
        {
            resolveServiceListView.getItems().clear();
        }else{
            resolveServiceListView.getItems().add(depSub.getCurrentLoggedIn().getCurrentRequests().toString());
        }

        //Update the nodes in the map
        ArrayList<Node> nodes = map.getNodeMap();


        //resolveServiceListView.getItems().add(Department.getBacklog().values());
        choiceBoxDept.setItems(FXCollections.observableList(depSub.getDepartments()));


        searchStrategyChoice.setItems(FXCollections.observableList(map.getSearches()));
        System.out.println(map.getSearchStrategy() + " ");
        searchStrategyChoice.setValue(map.getSearchStrategy());
        kioskLocationChoice.setItems(FXCollections.observableList(map.getNodeMap()));
        kioskLocationChoice.setValue(map.getKioskLocation());


    }
    @FXML

    public void resolveServicePressed(ActionEvent e)
    {

        //todo test?
        resolveServiceListView.getItems().removeAll(resolveServiceListView.getSelectionModel().getSelectedItems());
        System.out.println("Requests " + (resolveServiceListView.getSelectionModel().getSelectedItems()) + "resolved");
    }

    public void requestCreatePressed(ActionEvent e)
    {
        //todo create the request
        requestIDCount++;

        //Submit request
        depSub.submitRequest(choiceBoxService.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString() , locationChoiceBox.getValue(), choiceBoxStaff.getValue(),requestIDCount, false, "EMAIL");


        ServiceRequest nReq = new ServiceRequest(choiceBoxService.getValue(), requestIDCount, locationChoiceBox.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString(), choiceBoxStaff.getValue());


        //Add new service to List
        System.out.println("request submitted");
        resolveServiceListView.getItems().add(nReq.toString());

    }
    public void cancelPressed(ActionEvent e)
    {
        //todo add clear for time
        System.out.println("Cancel Pressed");

        //clear choiceboxes
        choiceBoxStaff.getItems().clear();
        choiceBoxService.getItems().clear();
        //choiceBoxDept.getItems().clear();

        //clear time and date
        timeMenu.getEditor().clear();
        dateMenu.getEditor().clear();

        //repopulate choiceboxes
        choiceBoxDept.setItems(FXCollections.observableList(depSub.getDepartments()));

        //repopulate location choice box
        locationChoiceBox.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));

    }

    public void logoutPressed(ActionEvent e){
        System.out.println("Logout Pressed");
        parent.setScreen(ScreenController.LogoutID);
    }

    public void editPressed(ActionEvent e){
        System.out.println("Edit Pressed");
        parent.setScreen(ScreenController.AddNodeID, "LEFT");
    }

    public void selectAlgorithmPath(ActionEvent e)
    {
        System.out.println("Algorithm Selected");
        selectedAlg = ((MenuItem)e.getSource()).getText();
        menuButtonAl.setText(selectedAlg);
    }

    public void deptSelected(Department newValue)
    {
        nameDept = newValue.toString();
        choiceBoxDept.setDisable(false);
        choiceBoxService.setItems(FXCollections.observableList(depSub.getServices(nameDept)));

    }
    public void servSelected(Service newValue)
    {
            nameService = newValue.toString();
            choiceBoxService.setDisable(false);
            choiceBoxStaff.setItems(FXCollections.observableList(depSub.getStaff(nameService)));
            ;

            //todo URL ??????????????????????????????????????????????????????????????\
            String URLPLS = newValue.getURL();
            //String testURL = "/fxml/FoodDelivery.fxml";
            System.out.println(URLPLS);
            try {
                AnchorPane servicePane = FXMLLoader.load(getClass().getResource(URLPLS));
                //AnchorPane servicePane = FXMLLoader.load(getClass().getResource(testURL));
                servicePane1.getChildren().setAll(servicePane);
            }catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println(URLPLS);
            }


    }
    public void staffSelected(Staff newValue)
    {
        nameStaff = newValue.toString();
    }

    public void timeSelected(ActionEvent e) {
        //todo undo comments
        System.out.println("Time selescted");
        //time = ((JFXTimePicker)e.getSource()).getValue().toString();
    }

    public void dateSelected(ActionEvent e){
        System.out.println("Date Selected" );
        date = ((JFXDatePicker)e.getSource()).getValue().toString();
    }

//////////////////////////////////////////////////////////
    /////////           Settings Tab
    //////////////////////////////////////////////////////////

    @FXML
    private ChoiceBox<SearchStrategy> searchStrategyChoice;
    @FXML
    private JFXButton saveSettingsButton;
    @FXML
    private ChoiceBox<Node> kioskLocationChoice;

    public void saveSettingsPressed(ActionEvent e){
        if(searchStrategyChoice.getValue() != null){
            map.setSearchStrategy(searchStrategyChoice.getValue());
        }

    }

}
