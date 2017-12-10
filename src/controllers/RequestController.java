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
//import api.SanitationService;
import com.jfoenix.controls.*;
import database.staffDatabase;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import map.HospitalMap;
import map.Node;
import search.SearchStrategy;
import ui.ShakeTransition;

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
    private ArrayList<String> apiServ;

    private static int requestIDCount = 0;

    private ArrayList<Staff> staff;

    ShakeTransition s = new ShakeTransition();

    @FXML
    private JFXTextField usernameTxt;
    @FXML
    private JFXTextField jobTitletxt;
    @FXML
    private JFXTextField passwordTxt;
    @FXML
    private JFXTextField fullNametxt;
    @FXML
    private JFXButton createPressedApi;
    @FXML
    private JFXButton cancelPressedAPI;
    @FXML
    private ChoiceBox<Node> apiLocationChoiceBox;
    @FXML
    private ChoiceBox<String> apiServiceChoiceBox;
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
        depSub = DepartmentSubsystem.getSubsystem();
        map = HospitalMap.getMap();
        apiServ = new ArrayList<>();
        apiServ.add("Sanitation");
        choiceBoxService.setItems(FXCollections.observableList(depSub.getServices()));

        apiLocationChoiceBox.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));

        apiServiceChoiceBox.setItems(FXCollections.observableList(apiServ));
        choiceBoxService.valueProperty().addListener( (v, oldValue, newValue) -> servSelected(newValue));
        choiceBoxStaff.valueProperty().addListener( (v, oldValue, newValue) -> staffSelected(newValue));


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
        if(depSub.getCurrentLoggedIn().getAllRequest().isEmpty()){
            resolveServiceListView.getItems().clear();
        }else{
            resolveServiceListView.getItems().addAll(depSub.getCurrentLoggedIn().getAllRequest());
        }

        //Update the nodes in the map
        ArrayList<Node> nodes = map.getNodeMap();

        searchStrategyChoice.setItems(FXCollections.observableList(map.getSearches()));
        searchStrategyChoice.setValue(map.getSearchStrategy());
        kioskLocationChoice.setItems(FXCollections.observableList(map.getNodeMap()));
        kioskLocationChoice.setValue(map.getKioskLocation());

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));

        staffJobTypeChoiceBox.setItems(FXCollections.observableList(depSub.getServices()));
        addStaffServiceChoiceBox.setItems(FXCollections.observableList(depSub.getServices()));
    }

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    public void resolveServicePressed(ActionEvent e){
        System.out.println(resolveServiceListView.getSelectionModel().getSelectedItems());
        depSub.getCurrentLoggedIn().removeRequests(resolveServiceListView.getSelectionModel().getSelectedItems());
        resolveServiceListView.getItems().removeAll(resolveServiceListView.getSelectionModel().getSelectedItems());
        //System.out.println("Requests " + (resolveServiceListView.getSelectionModel().getSelectedItems()) + "resolved");

        lblSelectedService.setText("Service");
        lblSelectedAdditionalInfo.setText("Location");
        lblSelectedDT.setText("Date & Time");
        lblSelectedLocation.setText("Additional Info");
    }

    public void createPressedApi(ActionEvent e){
        Node desNode = apiLocationChoiceBox.getSelectionModel().getSelectedItem();
        if(desNode != null) {
            runAPI(desNode);
        }
    }

    public void runAPI(Node desNode){
//        Stage primaryStage = new Stage();
//        SanitationService api = SanitationService.newInstance(primaryStage);
//        api.run(100, 100, 500, 500, "/fxml/SceneStyle.css", desNode.getID(), null);
    }

    public void cancelPressedAPI(ActionEvent e){
        apiLocationChoiceBox.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));
        apiServiceChoiceBox.setItems(FXCollections.observableList(apiServ));
    }

    //creates a service request, and then sends it to the staff member
    public void requestCreatePressed(ActionEvent e){
        requestIDCount++;
        ServiceRequest nReq = new ServiceRequest(choiceBoxService.getValue(), requestIDCount, locationChoiceBox.getValue(), "", dateMenu.getValue().toString(), choiceBoxStaff.getValue());
        System.out.println("request submitted");
        nReq.setInputData(currentServiceController.getInputData());
        //choiceBoxStaff.getValue().addRequest(nReq);

        resolveServiceListView.getItems().clear();
        resolveServiceListView.getItems().addAll(FXCollections.observableList(depSub.getCurrentLoggedIn().getAllRequest()));
        //resolveServiceListView.getItems().add(nReq);
        //fillInServiceSpecificRecs();
    }

    public void cancelPressed(ActionEvent e){
        //clear choiceboxes
        choiceBoxStaff.setItems(FXCollections.observableList(new ArrayList<Staff>()));
        choiceBoxStaff.setValue(null);
        choiceBoxService.setItems(FXCollections.observableList(new ArrayList<Service>()));
        choiceBoxService.setValue(null);
        choiceBoxService.setDisable(true);
        choiceBoxStaff.setDisable(true);


        //clear time and date
        //timeMenu.getEditor().clear();
        dateMenu.getEditor().clear();

        //repopulate location choice box
        locationChoiceBox.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));
    }

    public void logoutPressed(ActionEvent e){
        parent.setScreen(ScreenController.LogoutID);
    }

    public void editPressed(ActionEvent e){
        parent.setScreen(ScreenController.AddNodeID, "LEFT");
    }

    public void selectAlgorithmPath(ActionEvent e){
        selectedAlg = ((MenuItem)e.getSource()).getText();
        menuButtonAl.setText(selectedAlg);
    }

    public void servSelected(Service newValue){
        if(newValue != null) {
            System.out.println("Services was selected and listener triggered");
            choiceBoxStaff.setDisable(false);
            choiceBoxStaff.setItems(FXCollections.observableList(newValue.getStaff()));
            System.out.println("This is newValue.getStaff() " + newValue.getStaff());
            String URLPLS = newValue.getURL();
            System.out.println(URLPLS);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLPLS));
                AnchorPane servicePane = loader.load();
                //AnchorPane servicePane = FXMLLoader.load(getClass().getResource(testURL));
                servicePane1.getChildren().setAll(servicePane);
                this.currentServiceController = loader.getController();

                // Feeds data into respective service controller
                this.currentServiceController.onShow();

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(URLPLS);
            }
        }
        else{
            servicePane1.getChildren().clear();
        }
    }

    public void staffSelected(Staff newValue) {
        if(newValue != null) {
            nameStaff = newValue.toString();
        }
    }

    public void timeSelected(ActionEvent e) {
        time = ((JFXTimePicker)e.getSource()).getValue().toString();
    }

    public void dateSelected(ActionEvent e){
        date = ((JFXDatePicker)e.getSource()).getValue().toString();
    }

    @FXML
    void cancelStaffPressed(ActionEvent event) {}

    @FXML
    void createStaffPressed(ActionEvent event) {
        try {
            String tempUsername = usernameTxt.getText();
            String tempPassword = passwordTxt.getText();
            String tempJobTitle = jobTitletxt.getText();
            String tempFullName = fullNametxt.getText();
            Service tempService = addStaffServiceChoiceBox.getValue();

            staffDatabase.incStaffCounter();
            //TODO change into actual language selection
            depSub.addStaff(tempService, tempUsername, tempPassword, tempJobTitle, tempFullName, staffDatabase.getStaffCounter(), 0, new ArrayList<>());

            staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
        }
        catch (Exception ex){
            System.out.println("Add Staff Failed");
            if(usernameTxt.getText().equals("")){
                s.shake(usernameTxt);
            }
            if(passwordTxt.getText().equals("")){
                s.shake(passwordTxt);
            }
            if(jobTitletxt.getText().equals("")){
                s.shake(jobTitletxt);
            }
            if(fullNametxt.getText().equals("")){
                s.shake(fullNametxt);
            }
            if(addStaffServiceChoiceBox.getValue() == null){
                s.shake(addStaffServiceChoiceBox);
            }
        }
    }

    @FXML
    void makeModify(ActionEvent event) {}

    @FXML
    void removeStaffPressed(ActionEvent event) {
        String tempUsername = usernameDeleteTxt.getText();
        Service tempService = staffJobTypeChoiceBox.getValue();

        depSub.deleteStaff(tempService, tempUsername);

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }

    @FXML
    void searchbuttonPressed(ActionEvent event) {}

    //////////////////////////////////////////////////////////
    /////////           Settings Tab                 /////////
    //////////////////////////////////////////////////////////

    @FXML
    private ChoiceBox<SearchStrategy> searchStrategyChoice;
    @FXML
    private JFXButton saveSettingsButton;
    @FXML
    private ChoiceBox<Node> kioskLocationChoice;

    public void saveSettingsPressed(ActionEvent e) {
        if (searchStrategyChoice.getValue() != null) {
            System.out.println(searchStrategyChoice.getValue());
            map.setSearchStrategy(searchStrategyChoice.getValue());
            map.setKioskLocation(kioskLocationChoice.getValue());
        } else {
            s.shake(searchStrategyChoice);
        }
    }
}
