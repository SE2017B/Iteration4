/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import DepartmentSubsystem.*;
import DepartmentSubsystem.Services.Controllers.CurrentServiceController;
import com.jfoenix.controls.*;
import database.staffDatabase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    private CurrentServiceController currentServiceController;


    private static int requestIDCount = 0;

    private ArrayList<Staff> staff;
    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXTextField jobTitletxt;

    @FXML
    private JFXTextField passwordTxt;

    @FXML
    private JFXTextField fullNametxt;

    @FXML
    private JFXButton createStaffButton;

    @FXML
    private JFXButton cancelStaffButton;

    @FXML
    private JFXTextField usernameDeleteTxt;

    @FXML
    private JFXButton removeStaffBtn;

    @FXML
    private JFXTextField idDeleteTxt;

    @FXML
    private ListView<Staff> staffListView;

    @FXML
    private JFXTextField modifyUsername;

    @FXML
    private JFXTextField modifyID;

    @FXML
    private ChoiceBox<String> choiceboxModifyStaff;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton makeEditsModify;

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
    private JFXListView<ServiceRequest> resolveServiceListView;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnEditMap;

    @FXML
    private ChoiceBox<Service> staffJobTypeChoiceBox;

    @FXML
    private ChoiceBox<Service> addStaffServiceChoiceBox;


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
                new ChangeListener<ServiceRequest>() {
                    @Override
                    public void changed(ObservableValue<? extends ServiceRequest> observable,
                                        ServiceRequest oldValue, ServiceRequest newValue) {

                        lblSelectedService.setText(newValue.getService().toString());
                        lblSelectedAdditionalInfo.setText(newValue.getInputData());
                        lblSelectedDT.setText(newValue.getTime());
                        lblSelectedLocation.setText(newValue.getLocation().toString());

                    }
                }
        );
    }

    public void onShow(){

        //Staff requests display
        staffNameLabel.setText(depSub.getCurrentLoggedIn().toString());
        System.out.println(depSub.getCurrentLoggedIn().getAllRequest());
        if(depSub.getCurrentLoggedIn().getAllRequest().isEmpty())
        {
            resolveServiceListView.getItems().clear();
        }else{
            resolveServiceListView.getItems().addAll(depSub.getCurrentLoggedIn().getAllRequest());
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

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));

        staffJobTypeChoiceBox.setItems(FXCollections.observableList(depSub.getAllServices()));
        addStaffServiceChoiceBox.setItems(FXCollections.observableList(depSub.getAllServices()));
    }
    @FXML

    public void resolveServicePressed(ActionEvent e)
    {

        //todo test?
        resolveServiceListView.getItems().removeAll(resolveServiceListView.getSelectionModel().getSelectedItems());
        System.out.println("Requests " + (resolveServiceListView.getSelectionModel().getSelectedItems()) + "resolved");

        lblSelectedService.setText("Service");
        lblSelectedAdditionalInfo.setText("Location");
        lblSelectedDT.setText("Date & Time");
        lblSelectedLocation.setText("Additional Info");

    }

    public void requestCreatePressed(ActionEvent e)
    {
        //todo create the request
        requestIDCount++;

        //fillInServiceSpecificRecs();

        //Submit request
        //depSub.submitRequest(choiceBoxService.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString() , locationChoiceBox.getValue(), choiceBoxStaff.getValue(),requestIDCount, false, "EMAIL");

        ServiceRequest nReq = new ServiceRequest(choiceBoxService.getValue(), requestIDCount, locationChoiceBox.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString(), choiceBoxStaff.getValue());

        //Add new service to List
        System.out.println("request submitted");
        nReq.setInputData(currentServiceController.getInputData());
        resolveServiceListView.getItems().add(nReq);
        //fillInServiceSpecificRecs();

    }

//    private String fillInServiceSpecificRecs() {
//        Service service = choiceBoxService.getValue();
//
//        return currentServiceController.getInputData();
//
//        if(service.toString().equalsIgnoreCase("Translation Service")){
//            //Sets the language to the service, form the controller
//            ((Translation)service).setRequestedLanguage(((TranslationController)this.currentServiceController).getLanguageSel());
//            //Sets the duration of the session to the service, form the controller
//            ((Translation)service).setDuration(Integer.parseInt(((TranslationController)this.currentServiceController).getDuration()));
//        }
//        else if(service.toString().equalsIgnoreCase("Transport Service")){
//            //Sets the end location to the service
//            ((Transport)service).setEndLocation(((TransportController)this.currentServiceController).returnNode());
//        }
//        else if(service.toString().equalsIgnoreCase("Sanitation")){
//            ((Sanitation)service).setRequestedService(((SanitationController)this.currentServiceController).getSanSel());
//        }
//        else if(service.toString().equalsIgnoreCase("Food Delivery Service")){
//            ((FoodDelivery)service).setSelectedFood(((FoodDeliveryController)this.currentServiceController).getFoodSelected());
//            ((FoodDelivery)service).setAllergies(((FoodDeliveryController)this.currentServiceController).getAllergy());
//        }
//    }

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
        choiceBoxDept.setDisable(false);
        choiceBoxService.setItems(FXCollections.observableList(newValue.getServices()));

    }
    public void servSelected(Service newValue)
    {

            choiceBoxService.setDisable(false);
            choiceBoxStaff.setItems(FXCollections.observableList(newValue.getStaff()));

            //todo URL ??????????????????????????????????????????????????????????????\
            String URLPLS = newValue.getURL();
            //String testURL = "/fxml/FoodDelivery.fxml";
            System.out.println(URLPLS);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLPLS));
                AnchorPane servicePane = loader.load();
                //AnchorPane servicePane = FXMLLoader.load(getClass().getResource(testURL));
                servicePane1.getChildren().setAll(servicePane);
                this.currentServiceController = loader.getController();

                // Feeds data into respective service controller
                this.currentServiceController.onShow();

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


    @FXML
    void cancelStaffPressed(ActionEvent event) {

    }

    @FXML
    void createStaffPressed(ActionEvent event) {

        String tempUsername = usernameTxt.getText();
        String tempPassword = passwordTxt.getText();
        String tempJobTitle = jobTitletxt.getText();
        String tempFullName = fullNametxt.getText();
        Service tempService = addStaffServiceChoiceBox.getValue();


        staffDatabase.incStaffCounter();
        depSub.addStaff(tempService, tempUsername, tempPassword, tempJobTitle, tempFullName, staffDatabase.getStaffCounter());

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }


    @FXML
    void makeModify(ActionEvent event) {

    }

    @FXML
    void removeStaffPressed(ActionEvent event) {
        String tempUsername = usernameDeleteTxt.getText();
        Service tempService = staffJobTypeChoiceBox.getValue();

        depSub.deleteStaff(tempService, tempUsername);

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }

    @FXML
    void searchbuttonPressed(ActionEvent event) {

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
