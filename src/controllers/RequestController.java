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
import database.serviceDatabase;
import database.staffDatabase;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import map.HospitalMap;
import map.Node;
import search.SearchStrategy;
import ui.ShakeTransition;

import java.util.ArrayList;

import static java.awt.Color.black;

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
    private ArrayList<Staff> staffForCB;

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
    private JFXTextField fullNameRemove;
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
    private JFXTimePicker timeMenu;
    @FXML
    private JFXTextField staffDeleteFullNametxt;
    @FXML
    private JFXTextField usernameEdit;
    @FXML
    private JFXTextField fullnameEdit;
    @FXML
    private JFXTextField passwordEdit;
    @FXML
    private JFXTextField jobTitleEdit;
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
    private ListView<Staff> staffListView1;
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
    private JFXButton editStaff;
    @FXML
    private ChoiceBox<Service> staffJobTypeChoiceBox;
    @FXML
    private ChoiceBox<Service> addStaffServiceChoiceBox;
    @FXML
    private Tab staffManagementTab;
    @FXML
    private Tab settingsTab;

    @FXML
    private JFXCheckBox modifyAdminCheckBox;
    @FXML
    private JFXCheckBox addStaffCheckBox;

    @FXML
    private Label lblFeedbackRating;

    @FXML
    private Label lblFeedbackTitle;

    @FXML
    private JFXListView<Feedback> feedbackListView;

    @FXML
    private PieChart feedbackPieChart;

    @FXML
    private BarChart<String, Number> feedbackBarChart;

    @FXML
    private CategoryAxis xBarChart;

    @FXML
    private NumberAxis yBarChart;

    @FXML
    private LineChart<String, Number> feedbackLineChart;

    @FXML
    private CategoryAxis xLineChart;

    @FXML
    private NumberAxis yLineChart;

    @FXML
    private Tab pieChartTab;

    @FXML
    private Tab barChartTab;

    @FXML
    private Tab lineChartTab;

    @FXML
    private JFXTabPane chartTabPane;

    public void init(){
        depSub = DepartmentSubsystem.getSubsystem();
        staffForCB = new ArrayList<Staff>();
        depSub = DepartmentSubsystem.getSubsystem();
        map = HospitalMap.getMap();
        apiServ = new ArrayList<>();
        apiServ.add("Sanitation");
        choiceBoxService.setItems(FXCollections.observableList(depSub.getServices()));
//        choiceBoxService.setItems(FXCollections.observableList(depSub.getServices()));

//        apiLocationChoiceBox.setItems(FXCollections.observableList(
//                map.getNodesBy(n -> !n.getType().equals("HALL"))));

        apiServiceChoiceBox.setItems(FXCollections.observableList(apiServ));
       // choiceBoxService.valueProperty().addListener( (v, oldValue, newValue) -> servSelected(newValue));
        //choiceBoxStaff.valueProperty().addListener( (v, oldValue, newValue) -> staffSelected(newValue));


        //location set up
//        locationChoiceBox.setItems(FXCollections.observableList(
//                map.getNodesBy(n -> !n.getType().equals("HALL"))));

        //Display selected request on label
//        resolveServiceListView.getSelectionModel().selectedItemProperty().addListener(
//                new ChangeListener<ServiceRequest>() {
//                    @Override
//                    public void changed(ObservableValue<? extends ServiceRequest> observable,
//                                        ServiceRequest oldValue, ServiceRequest newValue) {
//
//                        if(newValue != null) {
//                            lblSelectedService.setText(newValue.getService().toString());
//                            lblSelectedLocation.setText(newValue.getLocation().toString());
//                            lblSelectedDT.setText(newValue.getTime() + newValue.getDate());
//                            lblSelectedAdditionalInfo.setText(newValue.getInputData());
//                        }
//
//                    }
//                }
//        );
        staffListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
            @Override
            public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue) {
                if (newValue != null) {
                    usernameDeleteTxt.setText(newValue.getUsername());
                    fullNameRemove.setText(newValue.getFullName());
                }
            }
                                                                             }
        );

        staffListView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
                                                                                  @Override
                                                                                  public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue) {
                                                                                      if (newValue != null) {
                                                                                          fullnameEdit.setText(newValue.getFullName());
                                                                                          usernameEdit.setText(newValue.getUsername());
                                                                                          passwordEdit.setText(newValue.getPassword());
                                                                                          jobTitleEdit.setText(newValue.getJobTitle());
                                                                                      }
                                                                                  }
                                                                              }
        );
    }

    public void onShow(){
        //Staff requests display
        staffNameLabel.setText(depSub.getCurrentLoggedIn().toString());

        //todo uncomment!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        if(!depSub.getCurrentLoggedIn().isAdmin())
//        {
//            btnEditMap.setDisable(true);
//            btnEditMap.setOpacity(0);
//            staffManagementTab.setDisable(true);
//            settingsTab.setDisable(true);
//        }
        System.out.println(depSub.getCurrentLoggedIn().getAllRequest());
//        if(depSub.getCurrentLoggedIn().getAllRequest().isEmpty()){
//            resolveServiceListView.getItems().clear();
//        }else{
//            resolveServiceListView.getItems().addAll(depSub.getCurrentLoggedIn().getAllRequest());
//        }

        //Update the nodes in the map
        ArrayList<Node> nodes = map.getNodeMap();

        searchStrategyChoice.setItems(FXCollections.observableList(map.getSearches()));
        searchStrategyChoice.setValue(map.getSearchStrategy());
        kioskLocationChoice.setItems(FXCollections.observableList(map.getNodeMap()));
        kioskLocationChoice.setValue(map.getKioskLocation());

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
        staffListView1.setItems(FXCollections.observableList(staffDatabase.getStaff()));

        staffJobTypeChoiceBox.setItems(FXCollections.observableList(depSub.getServices()));
        addStaffServiceChoiceBox.setItems(FXCollections.observableList(depSub.getServices()));

        lblFeedbackRating.setStyle("-fx-background-color: rgb(40,40,60)");
        lblFeedbackRating.setText(serviceDatabase.avgFeedback());

        lblFeedbackTitle.setStyle("-fx-background-color: rgb(40,40,60)");
        lblFeedbackTitle.setText("Feedback Charts");

        feedbackListView.setItems(FXCollections.observableList(serviceDatabase.getAllFeedbacks()));

        // Populate Feedback Charts
        pieChartCreate();
        lineChartCreate();
        barChartCreate();
        //staffJobTypeChoiceBox.setItems(FXCollections.observableList(depSub.getServices()));
        //addStaffServiceChoiceBox.setItems(FXCollections.observableList(depSub.getServices()));
    }

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

//    public void resolveServicePressed(ActionEvent e){
//        //todo test?
//
//            resolveServiceListView.getItems().removeAll(resolveServiceListView.getSelectionModel().getSelectedItems());
//            System.out.println("Requests " + (resolveServiceListView.getSelectionModel().getSelectedItems()) + "resolved");
//
//            lblSelectedService.setText("Service");
//            lblSelectedAdditionalInfo.setText("Aditional Info");
//            lblSelectedDT.setText("Date & Time");
//            lblSelectedLocation.setText("Location");
//
//    }

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

//    public void requestCreatePressed(ActionEvent e){
//        //TEST
//        requestIDCount++;
//
//        //Submit request
//        //depSub.submitServiceRequest(choiceBoxService.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString() , locationChoiceBox.getValue(), choiceBoxStaff.getValue(),requestIDCount, false, "EMAIL");
//
//        ServiceRequest nReq = new ServiceRequest(choiceBoxService.getValue(), requestIDCount, locationChoiceBox.getValue(), timeMenu.getValue().toString(), dateMenu.getValue().toString(), choiceBoxStaff.getValue());
//        depSub.submitServiceRequest(nReq);
//        //Add new service to List
//        System.out.println("request submitted");
//        nReq.setInputData(currentServiceController.getInputData());
//        resolveServiceListView.getItems().add(nReq);
//    }


//    public void cancelPressed(ActionEvent e){
//        //clear choiceboxes
//        choiceBoxStaff.setItems(FXCollections.observableList(new ArrayList<Staff>()));
//        choiceBoxStaff.setValue(null);
//        choiceBoxService.setItems(FXCollections.observableList(new ArrayList<Service>()));
//        choiceBoxService.setValue(null);
//        choiceBoxService.setDisable(true);
//        choiceBoxStaff.setDisable(true);
//
//
//        //clear time and date
//        timeMenu.getEditor().clear();
//        dateMenu.getEditor().clear();
//
//        //repopulate choiceboxes
//
//        //repopulate location choice box
//        locationChoiceBox.setItems(FXCollections.observableList(
//                map.getNodesBy(n -> !n.getType().equals("HALL"))));
//    }

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

//    public void deptSelected(Department newValue){
//        if(newValue != null) {
//            choiceBoxService.setDisable(false);
//            choiceBoxService.setItems(FXCollections.observableList(newValue.getServices()));
//        }
//    }

//    public void servSelected(Service newValue){
//        if(newValue != null) {
//            System.out.println("Services was selected and listener triggered");
//            choiceBoxStaff.setDisable(false);
//            choiceBoxStaff.setItems(FXCollections.observableList(newValue.getStaff()));
//            System.out.println("This is newValue.getStaff() " + newValue.getStaff());
//            String URLPLS = newValue.getURL();
//            System.out.println(URLPLS);
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource(URLPLS));
//                AnchorPane servicePane = loader.load();
//                //AnchorPane servicePane = FXMLLoader.load(getClass().getResource(testURL));
//                servicePane1.getChildren().setAll(servicePane);
//                this.currentServiceController = loader.getController();
//
//                // Feeds data into respective service controller
//                this.currentServiceController.onShow();
//
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                System.out.println(URLPLS);
//            }
//        }
//        else{
//            servicePane1.getChildren().clear();
//        }
//    }

//    public void staffSelected(Staff newValue) {
//        if(newValue != null) {
//            nameStaff = newValue.toString();
//        }
//    }
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

//    public void timeSelected(ActionEvent e) {
//        //todo undo comments
//        //time = ((JFXTimePicker)e.getSource()).getValue().toString();
//    }

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
//    public void dateSelected(ActionEvent e){
//        date = ((JFXDatePicker)e.getSource()).getValue().toString();
//    }

    @FXML
    void cancelStaffPressed(ActionEvent event) {
        usernameTxt.clear();
        passwordTxt.clear();
        jobTitletxt.clear();
        fullNametxt.clear();
        addStaffCheckBox.setSelected(false);
    }

    @FXML
    void createStaffPressed(ActionEvent event) {
        try {
            String tempUsername = usernameTxt.getText();
            String tempPassword = passwordTxt.getText();
            String tempJobTitle = jobTitletxt.getText();
            String tempFullName = fullNametxt.getText();

            //Service tempService = addStaffServiceChoiceBox.getValue();

            //todo Test new add staff UNCOMMENT
            staffDatabase.incStaffCounter();
            //TODO change into actual language selection
            //depSub.addStaff(tempService, tempUsername, tempPassword, tempJobTitle, tempFullName, staffDatabase.getStaffCounter(), 0, new ArrayList<>());
            //depSub.addStaff(addStaffCheckBox.isSelected(), tempUsername, tempPassword, tempJobTitle, tempFullName, staffDatabase.getStaffCounter(), 0);

            staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
            staffListView1.setItems(FXCollections.observableList(staffDatabase.getStaff()));
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
        }
        usernameTxt.clear();
        passwordTxt.clear();
        jobTitletxt.clear();
        fullNametxt.clear();
        addStaffCheckBox.setSelected(false);
    }

    @FXML
    void makeModify(ActionEvent event) {}

    @FXML
    public void editStaffPressed(ActionEvent e)
    {
        //ArrayList<Staff> tempAL = new ArrayList<>(staffForCB);

        String tempUsername = usernameEdit.getText();
        String tempPassword = passwordEdit.getText();
        String tempFullName = fullnameEdit.getText();
        String tempJobTitle = jobTitleEdit.getText();





        Staff tempStaff = staffListView1.getSelectionModel().getSelectedItem();
       // tempStaff.updateCredidentials(tempUsername, tempPassword, modifyAdminCheckBox.isSelected(), tempFullName, tempStaff.getID());

        usernameEdit.clear();
        passwordEdit.clear();
        fullnameEdit.clear();
        jobTitleEdit.clear();
        modifyAdminCheckBox.setSelected(false);

        //staffResolveServiceChoiceBox.getItems().clear();
        staffListView1.getItems().clear();
        staffListView.getItems().clear();
        //staffChoiceBox.getItems().clear();

//        staffForCB.clear();
//        if (staffForCB.isEmpty() || staffForCB == null) {
//            System.out.println("Error: No staff members found.");
//        }
//        staffListView.setItems(FXCollections.observableList(tempAL));
//        staffListView1.setItems(FXCollections.observableList(tempAL));
//        staffChoiceBox.setItems(FXCollections.observableList(tempAL));
//        staffResolveServiceChoiceBox.setItems(FXCollections.observableList(tempAL));
//        staffForCB.addAll(tempAL);
    }

    @FXML
    void removeStaffPressed(ActionEvent event) {
        String tempUsername = usernameDeleteTxt.getText();
        String tempFullName = fullNameRemove.getText();
        //todo uncomment
        //depSub.deleteStaff(tempFullName, tempUsername);

        usernameDeleteTxt.clear();
        fullNameRemove.clear();

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }

    @FXML
    void searchbuttonPressed(ActionEvent event) {}

    @FXML
    void pieChartCreate() {

        // Setting up the Pie Chart on Feedback tab
        feedbackPieChart.setLabelLineLength(10);
        feedbackPieChart.setLegendSide(Side.RIGHT);
        feedbackPieChart.setData(serviceDatabase.cntFeedback());
    }

    @FXML
    void lineChartCreate() {

        ArrayList<Integer> tempLineArray = serviceDatabase.cntChartFeedback();

        XYChart.Series<String, Number> aLineChart = new XYChart.Series<>();
        aLineChart.getData().add(new XYChart.Data<String,Number>("0", tempLineArray.get(0)));
        aLineChart.getData().add(new XYChart.Data<String,Number>("1", tempLineArray.get(1)));
        aLineChart.getData().add(new XYChart.Data<String,Number>("2", tempLineArray.get(2)));
        aLineChart.getData().add(new XYChart.Data<String,Number>("3", tempLineArray.get(3)));
        aLineChart.getData().add(new XYChart.Data<String,Number>("4", tempLineArray.get(4)));
        aLineChart.getData().add(new XYChart.Data<String,Number>("5", tempLineArray.get(5)));
        aLineChart.setName("Number of Ratings");

        xLineChart.setLabel("Feedback Rating");
        yLineChart.setLabel("Number of Ratings");
        feedbackLineChart.getData().add(aLineChart);
    }

    @FXML
    void barChartCreate() {

        ArrayList<Integer> tempLineArray2 = serviceDatabase.cntChartFeedback();

        XYChart.Series<String, Number> aBarChart = new XYChart.Series<>();
        aBarChart.getData().add(new XYChart.Data<String,Number>("0", tempLineArray2.get(0)));
        aBarChart.getData().add(new XYChart.Data<String,Number>("1", tempLineArray2.get(1)));
        aBarChart.getData().add(new XYChart.Data<String,Number>("2", tempLineArray2.get(2)));
        aBarChart.getData().add(new XYChart.Data<String,Number>("3", tempLineArray2.get(3)));
        aBarChart.getData().add(new XYChart.Data<String,Number>("4", tempLineArray2.get(4)));
        aBarChart.getData().add(new XYChart.Data<String,Number>("5", tempLineArray2.get(5)));
        aBarChart.setName("Number of Ratings");

        xBarChart.setLabel("Feedback Rating");
        yBarChart.setLabel("Number of Ratings");
        feedbackBarChart.getData().addAll(aBarChart);

    }
/*
    @FXML
    void changeChartTitlePie(ActionEvent event) {
        lblFeedbackTitle.setText("Feedback Pie Chart");
    }
    @FXML
    void changeChartTitleLine(ActionEvent event) {
        lblFeedbackTitle.setText("Feedback Line Chart");
    }
    @FXML
    void changeChartTitleBar(ActionEvent event) {
        lblFeedbackTitle.setText("Feedback Bar Chart");
    }
*/

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
