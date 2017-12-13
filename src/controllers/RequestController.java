/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import DepartmentSubsystem.Services.Transport;
import api.SanitationService;
import foodRequest.FoodRequest;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import translationApi.TranslationService;
import transportApi.TransportService;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import map.FloorNumber;
import map.HospitalMap;
import map.Node;
import search.SearchStrategy;
import ui.ShakeTransition;

import java.util.ArrayList;
import java.util.List;

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
    private DepartmentSubsystem depSub;

    private ArrayList<String> apiServ;
    private String selectedAPI;

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

    @FXML
    private JFXListView<Node> startNodeOptionList;

    @FXML
    private JFXListView<Node> endNodeOptionList;


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

    ////////////////////////////////////////////////////////
    // Feedback Charts
    ///////////////////////////////////////////////////////
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
        map = HospitalMap.getMap();
        apiServ = new ArrayList<>();
        apiServ.add("Sanitation");
        apiServ.add("Food");
        apiServ.add("Translation");
        apiServ.add("Transportation");


        apiServiceChoiceBox.setItems(FXCollections.observableList(apiServ));
        apiServiceChoiceBox.setOnAction(e -> {
            selectedAPI = ((ChoiceBox<String>) e.getSource()).getValue();
            if(selectedAPI!= null && selectedAPI.equals("Transportation")){
                endTabPane.setVisible(true);
                endLabel.setVisible(true);
            }
            else{
                endTabPane.setVisible(false);
                endLabel.setVisible(false);
            }
        });


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





        //add listeners
        startTextField.setOnKeyPressed( e -> searchText(e, startTextField, startNodeOptionList));
        endTextField.setOnKeyPressed(e -> searchText(e, endTextField, endNodeOptionList));
        startNodeOptionList.translateXProperty().bind(parent.widthProperty().divide(2).add(10));
        endNodeOptionList.translateXProperty().bind(startNodeOptionList.translateXProperty());
        startNodeOptionList.translateYProperty().bind(parent.heightProperty().divide(2).subtract(120));
        endNodeOptionList.translateYProperty().bind(startNodeOptionList.translateYProperty().add(100));
        startNodeOptionList.setPrefWidth(400);
        endNodeOptionList.setPrefWidth(400);
        startNodeOptionList.setOnMouseClicked( e -> suggestionPressed(e, startTextField, startNodeOptionList));
        endNodeOptionList.setOnMouseClicked( e -> suggestionPressed(e, endTextField, endNodeOptionList));

        startTextTab.setOnSelectionChanged(e -> {
            startNodeOptionList.setVisible(false);
            startNode = null;
            startTextField.setText("");
        });
        endTextTab.setOnSelectionChanged(e -> {
            endNodeOptionList.setVisible(false);
            endNode = null;
            endTextField.setText("");
        });

        saveRipple= new JFXRippler(ripplePane);
        settingsPane.getChildren().add(0,saveRipple);
        saveRipple.setRipplerFill(Color.GREEN);

        timeoutTextField.setText(Double.toString(parent.getTimeoutLength()/1000));

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
        searchStrategyChoice.setValue(map.getSearches().get(0));
        kioskLocationChoice.setItems(FXCollections.observableList(map.getNodeMap()));
        kioskLocationChoice.setValue(map.getKioskLocation());

        staffListView.setItems(FXCollections.observableList(staffDatabase.getStaff()));
        staffListView1.setItems(FXCollections.observableList(staffDatabase.getStaff()));

        lblFeedbackRating.setStyle("-fx-background-color: rgb(40,40,60)");
        lblFeedbackRating.setText(feedbackDatabase.avgFeedback());

        lblFeedbackTitle.setStyle("-fx-background-color: rgb(40,40,60)");
        lblFeedbackTitle.setText("Feedback Charts");

        feedbackListView.setItems(FXCollections.observableList(feedbackDatabase.getAllFeedbacks()));
        // Disable clicking on feedback list view
        feedbackListView.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                event.consume();
            }
        });

        endNodeOptionList.setVisible(false);
        startNodeOptionList.setVisible(false);
        endTabPane.setVisible(false);
        endLabel.setVisible(false);

        // Populate Feedback Charts
        pieChartCreate();
        lineChartCreate();
        barChartCreate();


        timeoutTextField.setText(Double.toString(parent.getTimeoutLength()/1000));
    }

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    /////////////////////////////////////////////////////////////////
    ////// API
    ////////////////////////////////////////////////////////////////

    @FXML
    private JFXTabPane startTabPane;

    @FXML
    private Tab startTextTab;

    @FXML
    private JFXTextField startTextField;

    @FXML
    private Tab startTypeTab;

    @FXML
    private ChoiceBox<Node> startNodeChoice;

    @FXML
    private MenuButton startTypeMenu;


    @FXML
    private MenuButton startFloorMenu;

    @FXML
    private JFXTabPane endTabPane;

    @FXML Label endLabel;

    @FXML
    private Tab endTextTab;

    @FXML
    private JFXTextField endTextField;

    @FXML
    private Tab endTypeTab;

    @FXML
    private MenuButton endTypeMenu;


    @FXML
    private MenuButton endFloorMenu;

    @FXML
    private ChoiceBox<Node> endNodeChoice;


    private String startType;
    private String endType;
    private String startFloor;
    private String endFloor;

    private Node startNode;
    private Node endNode;

    public void startTypeSelected(ActionEvent e){
        startType = ((MenuItem)e.getSource()).getText();
        startTypeMenu.setText(startType);
        startFloorMenu.setDisable(false);
        if(startFloor != null && !startFloor.equals("")){
            startChosen();
        }
    }

    public void startFloorSelected(ActionEvent e){
        startFloor = ((MenuItem)e.getSource()).getText();
        startFloorMenu.setText(startFloor);
        startChosen();
    }

    private void startChosen(){
        final String f = Node.getFilterText(startType);
        if(startFloor.equals("ALL")){
            startNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f))));
        }
        else{
            FloorNumber floor = FloorNumber.fromDbMapping(startFloor);
            startNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f) && n.getFloor().equals(floor))));
        }
        startNodeChoice.setDisable(false);
    }

    public void endTypeSelected(ActionEvent e){
        endType = ((MenuItem)e.getSource()).getText();
        endTypeMenu.setText(endType);
        endFloorMenu.setDisable(false);
        if(!endFloor.equals("")){
            endChosen();
        }
    }

    public void endFloorSelected(ActionEvent e){
        endFloor = ((MenuItem)e.getSource()).getText();
        endFloorMenu.setText(endFloor);
        endChosen();
    }

    private void endChosen(){

        final String f = Node.getFilterText(endType);
        if(endFloor.equals("ALL")){
            endNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f))));
        }
        else{
            FloorNumber floor = FloorNumber.fromDbMapping(endFloor);
            endNodeChoice.setItems(FXCollections.observableList(map.getNodesBy( n -> n.getType().equals(f) && n.getFloor().equals(floor))));
        }
        endNodeChoice.setDisable(false);
    }



    public void createPressedApi(ActionEvent e){
        if(startTypeTab.isSelected())
            startNode = startNodeChoice.getValue();
        if(endTypeTab.isSelected())
            endNode = endNodeChoice.getValue();
        if(startNode != null && (endNode != null || !selectedAPI.equals("Transportation"))) {
            parent.pauseTimeout();
            if(apiServiceChoiceBox.getSelectionModel().getSelectedItem().equals("Sanitation"))
            {
                runSanitationAPI(startNode);
            }
            else if(apiServiceChoiceBox.getSelectionModel().getSelectedItem().equals("Translation"))
            {
                runTranslationAPI(startNode);
            }
            else if(apiServiceChoiceBox.getSelectionModel().getSelectedItem().equals("Transportation"))
            {
                runTransportationAPI(startNode, endNode);
            }else if(apiServiceChoiceBox.getSelectionModel().getSelectedItem().equals("Food"))
            {
                runFoodDeliveryAPI(startNode);
            }
        }
    }

    public void runSanitationAPI(Node desNode){
        Stage primaryStage = new Stage();
        SanitationService api1 = SanitationService.newInstance(primaryStage);
        api1.run(100, 100, 900, 600, "/fxml/SceneStyle.css", desNode.getID(), null);
    }
    public void runTranslationAPI(Node desNode){
        Stage primaryStage = new Stage();
        TranslationService api2 = TranslationService.newInstance(primaryStage);
        api2.run(100, 100, 900, 600, "/fxml/SceneStyle.css", desNode.getID(), null);
    }
    public void runTransportationAPI(Node desNode, Node end){
        Stage primaryStage = new Stage();
        TransportService api = TransportService.newInstance(primaryStage);
        api.run(100, 100, 900, 600, "/fxml/SceneStyle.css", end.getID(), desNode.getID());
    }
    public void runFoodDeliveryAPI(Node desNode){
        FoodRequest foodRequest = new FoodRequest();
        try{
            foodRequest.run(0,0,1900,1000,null,null,null);
        }catch (Exception e){
            System.out.println("Failed to run API");
            e.printStackTrace();
        }
    }

    private void suggestionPressed(MouseEvent e, JFXTextField textField, JFXListView<Node> listView) {
        Node selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Node node = listView.getSelectionModel().getSelectedItem();
            if (textField.equals(startTextField)) {
                startNode = node;
            } else {
                endNode = node;
            }
            textField.setText(selected.toString());
            listView.setVisible(false);
        }
    }
    public void cancelPressedAPI(ActionEvent e){
        apiServiceChoiceBox.setItems(FXCollections.observableList(apiServ));
    }

    public void logoutPressed(ActionEvent e){
        parent.setScreen(ScreenController.LogoutID);
    }

    public void editPressed(ActionEvent e){
        parent.setScreen(ScreenController.AddNodeID, "LEFT");
    }

    public void selectAlgorithmPath(ActionEvent e) {
        selectedAlg = ((MenuItem) e.getSource()).getText();
        menuButtonAl.setText(selectedAlg);
        parent.resumeTimeout();

    }


    private void searchText(KeyEvent keyEvent, JFXTextField textField, JFXListView<Node> listView){
        KeyCode code = keyEvent.getCode();
        if(code.equals(KeyCode.ENTER)) {
            Node node;
            if(listView.getSelectionModel().selectedItemProperty().isNull().get()){
                node = listView.getItems().get(0);
            }
            else{
                node = listView.getSelectionModel().getSelectedItem();
            }
            if(textField.equals(startTextField)){
                startNode = node;
            }
            else{
                endNode = node;
            }
            textField.setText(node.toString());
            listView.setVisible(false);
        }
        else if(code.equals(KeyCode.DOWN)){
            if(listView.getSelectionModel().getSelectedIndex() == -1) {
                listView.getSelectionModel().select(0);
            }
            else if(listView.getSelectionModel().getSelectedIndex() <= listView.getItems().size()-1){
                listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);
            }
            textField.setText(listView.getSelectionModel().getSelectedItem().toString());
        }
        else if(code.equals(KeyCode.UP)){
            if(listView.getSelectionModel().getSelectedIndex() == -1) {
                listView.getSelectionModel().select(0);
            }
            else if(listView.getSelectionModel().getSelectedIndex() >= 0){
                listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() - 1);
            }
            textField.setText(listView.getSelectionModel().getSelectedItem().toString());
        }
        else if(code.equals(KeyCode.ESCAPE)){
            startNodeOptionList.setVisible(false);
            endNodeOptionList.setVisible(false);
        }
        else if(code.isLetterKey() || keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
            String text = ((JFXTextField) keyEvent.getSource()).getText();
            text = (code.isLetterKey()) ? text + keyEvent.getText() : text.substring(0, text.length()-1);
            if(text.equals("")){
                System.out.println("empty");
                listView.setVisible(false);
            }
            else {
                List<Node> ans = map.getNodesByText(text);
                if (ans.size() > 10) {
                    listView.getItems().setAll(ans.subList(0, 5));
                    listView.setVisible(true);
                }
                if (textField.equals(startTextField)) {
                    startNode = null;
                } else {
                    endNode = null;
                }
            }
        }
    }


    ////////////////////////////////////////////////////////
    /// STAFF
    /////////////////////////////////////////////////////////


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
    public void searchbuttonPressed(ActionEvent event) {
        //make sure to add shake transition when field is empty
    }

    @FXML
    void pieChartCreate() {
        if (feedbackPieChart != null) {
            feedbackPieChart.getData().clear();
        }

        // Setting up the Pie Chart on Feedback tab
        feedbackPieChart.setLabelLineLength(10);
        feedbackPieChart.setLegendSide(Side.RIGHT);
        feedbackPieChart.setData(feedbackDatabase.cntFeedback());
    }

    @FXML
    void lineChartCreate() {
        if (feedbackLineChart != null) {
            feedbackLineChart.getData().clear();
        }
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

        feedbackLineChart.getData().addAll(aLineChart);
    }

    @FXML
    void barChartCreate() {
        if (feedbackBarChart != null) {
            feedbackBarChart.getData().clear();
        }
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
    @FXML
    private Pane ripplePane;
    @FXML
    private AnchorPane settingsPane;
    @FXML
    private JFXTextField timeoutTextField;

    private JFXRippler saveRipple;



    public void saveSettingsPressed(ActionEvent e) {
        if (searchStrategyChoice.getValue() != null) {
            System.out.println(searchStrategyChoice.getValue());
            map.setSearchStrategy(searchStrategyChoice.getValue());
            map.setKioskLocation(kioskLocationChoice.getValue());
        } else {
            s.shake(searchStrategyChoice);
        }

        try{
            parent.setTimeoutLength(Double.parseDouble(timeoutTextField.getText())*1000);
            saveRipple.createManualRipple().run();
        }
        catch(Exception er ){
            s.shake(timeoutTextField);
        }
    }

    private class FeedbackSystem{
        ArrayList<Feedback> cache;
        public FeedbackSystem(){
            cache = new ArrayList<>();
        }

        public void submitFeedback(String message, int rating){
            //TODO get database commands for submitting the message and rating to the system
            Feedback temp = new Feedback(message, rating);
            this.cache.add(temp);
        }

        public ArrayList<Feedback> getCache() {
            return cache;
        }

        //Encapsulates Feedback helper object
        private class Feedback{
            private final String message;
            private final int rating;

            Feedback(String message, int rating){
                this.message = message;
                this.rating = rating;
            }

            public String getMessage() {
                return message;
            }

            public int getRating() {
                return rating;
            }
        }
    }
}
