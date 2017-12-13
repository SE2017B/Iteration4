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
import database.feedbackDatabase;
import database.staffDatabase;
import exceptions.InvalidPasswordException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

    ////////////////////////////////////////////////////////
    // STAFF ADD EDIT REMOVE//
    ///////////////////////////////////////////////////////
    @FXML
    private JFXButton createAPIbtn;
    @FXML
    private JFXButton editStaff;
    @FXML
    private JFXCheckBox adminAddStaff;
    @FXML
    private JFXCheckBox adminEdit;
    @FXML
    private JFXTextField removeStaffFullName;
    @FXML
    private JFXTextField usernameEdit;
    @FXML
    private JFXTextField fullnameEdit;
    @FXML
    private JFXTextField passwordEdit;
    @FXML
    private JFXTextField jobTitleEdit;
    @FXML
    private ListView<Staff> staffListView1;
    @FXML
    private Tab staffManageTab;
    @FXML
    private Tab settingsTab;


    public void init(){
        depSub = DepartmentSubsystem.getSubsystem();
        map = HospitalMap.getMap();
        apiServ = new ArrayList<>();
        apiServ.add("Sanitation");


        apiLocationChoiceBox.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));

        apiServiceChoiceBox.setItems(FXCollections.observableList(apiServ));


        staffListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Staff>() {
                                                                                 @Override
                                                                                 public void changed(ObservableValue<? extends Staff> observable, Staff oldValue, Staff newValue) {
                                                                                     if (newValue != null) {
                                                                                         usernameDeleteTxt.setText(newValue.getUsername());
                                                                                         removeStaffFullName.setText(newValue.getFullName());
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
        //System.out.println(depSub.getCurrentLoggedIn().getAllRequest());

        //System.out.println(depSub.getCurrentLoggedIn().getAllRequest());

        if(depSub.getCurrentLoggedIn().getaAdmin() == 1){
            btnEditMap.setDisable(false);
            settingsTab.setDisable(false);
            staffManageTab.setDisable(false);

        }else{
            btnEditMap.setDisable(true);
            settingsTab.setDisable(true);
            staffManageTab.setDisable(true);
        }

        //Update the nodes in the map
        ArrayList<Node> nodes = map.getNodeMap();

        searchStrategyChoice.setItems(FXCollections.observableList(map.getSearches()));
        searchStrategyChoice.setValue(map.getSearchStrategy());
        kioskLocationChoice.setItems(FXCollections.observableList(map.getNodeMap()));
        kioskLocationChoice.setValue(map.getKioskLocation());

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
        staffListView1.setItems(FXCollections.observableList(staffDatabase.getStaff()));

        lblFeedbackRating.setStyle("-fx-background-color: rgb(40,40,60)");
        lblFeedbackRating.setText(feedbackDatabase.avgFeedback());

        lblFeedbackTitle.setStyle("-fx-background-color: rgb(40,40,60)");
        lblFeedbackTitle.setText("Feedback Charts");

        feedbackListView.setItems(FXCollections.observableList(feedbackDatabase.getAllFeedbacks()));

        // Populate Feedback Charts
        pieChartCreate();
        lineChartCreate();
        barChartCreate();
    }

    public void setParentController(ScreenController parent){
        this.parent = parent;
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


    @FXML
    void cancelStaffPressed(ActionEvent event) {
        usernameTxt.clear();
        passwordTxt.clear();
        jobTitletxt.clear();
        fullNametxt.clear();
        adminAddStaff.setSelected(false);
    }

    @FXML
    void editStaffPressed(ActionEvent e) {

        String tempUsername = usernameEdit.getText();
        String tempPassword = passwordEdit.getText();
        String tempFullName = fullnameEdit.getText();
        String tempJobTitle = jobTitleEdit.getText();
        int tempIsAdmin;

        if (adminAddStaff.isSelected()) {
            tempIsAdmin = 1;
        } else {
            tempIsAdmin = 0;
        }

        Staff tempStaff = staffListView1.getSelectionModel().getSelectedItem();

        try {
            //tempStaff.updateCredentials(tempUsername, tempPassword, tempJobTitle, tempFullName, tempStaff.getID(), tempIsAdmin);
            depSub.modifyStaff(tempStaff, tempUsername, tempPassword, tempJobTitle, tempFullName, tempStaff.getID(), tempIsAdmin);
        } catch (InvalidPasswordException e1) {
            e1.printStackTrace();
        }


        //usernameEdit.clear();
        //passwordEdit.clear();
        //fullnameEdit.clear();
        //jobTitleEdit.clear();
        //adminEdit.setSelected(false);

        //staffResolveServiceChoiceBox.getItems().clear();
        //staffListView1.getItems().clear();
        //staffListView.getItems().clear();
        //staffChoiceBox.getItems().clear();

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
        staffListView1.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }


    @FXML
    void createStaffPressed(ActionEvent e) {
        try {
            String tempUsername = usernameTxt.getText();
            String tempPassword = passwordTxt.getText();
            String tempJobTitle = jobTitletxt.getText();
            String tempFullName = fullNametxt.getText();
            int tempIsAdmin;

            if (adminAddStaff.isSelected()) {
                tempIsAdmin = 1;
            }
            else {
                tempIsAdmin = 0;
            }
            //Service tempService = addStaffServiceChoiceBox.getValue();

            //todo Test new add staff UNCOMMENT
            staffDatabase.incStaffCounter();
            depSub.addStaff(tempUsername, tempPassword, tempJobTitle, tempFullName, staffDatabase.getStaffCounter(), tempIsAdmin);

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
        adminAddStaff.setSelected(false);
    }

    @FXML
    void makeModify(ActionEvent event) {}

    @FXML
    void removeStaffPressed(ActionEvent event) {
        String tempUsername = usernameDeleteTxt.getText();
        String tempFullName = removeStaffFullName.getText();
        //todo uncomment
        depSub.deleteStaff(tempFullName, tempUsername);

        usernameDeleteTxt.clear();
        removeStaffFullName.clear();

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
        staffListView1.setItems(FXCollections.observableList(staffDatabase.getStaff()));
    }

    @FXML
    void searchbuttonPressed(ActionEvent event) {}

    @FXML
    void pieChartCreate() {
        feedbackPieChart.getData().clear();
        // Setting up the Pie Chart on Feedback tab
        feedbackPieChart.setLabelLineLength(10);
        feedbackPieChart.setLegendSide(Side.RIGHT);
        feedbackPieChart.setData(feedbackDatabase.cntFeedback());
    }

    @FXML
    void lineChartCreate() {
        feedbackLineChart.getData().clear();

        ArrayList<Integer> tempLineArray = feedbackDatabase.cntChartFeedback();

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
        feedbackBarChart.getData().clear();
        ArrayList<Integer> tempLineArray2 = feedbackDatabase.cntChartFeedback();

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
