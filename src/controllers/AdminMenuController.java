package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import service.ServiceRequest;
import service.Staff;

public class AdminMenuController implements ControllableScreen{
    private ScreenController parent;
    private Staff staffMember;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }
    @FXML
    private Button btnService;

    @FXML
    private Label nameLabel;

    @FXML
    private ListView requestList;

    @FXML
    private Button btnComplete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnLogout;

    public void init(){}

    public void onShow(){}

    public void servicePressed(ActionEvent e){
        System.out.println("Service Pressed");
        parent.setScreen(ScreenController.RequestID);
    }

    public void completePressed(ActionEvent e){
        //remove the selected request from the request list and the staff member
        System.out.println("Complete Pressed");
    }

    public void editPressed(ActionEvent e){
        System.out.println("Edit Pressed");
        parent.setScreen(ScreenController.AddNodeID);
    }

    public void logoutPressed(ActionEvent e){
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.LogoutID);
    }


    //Take in a Staff object, set the name and list of assigned tasks
    public void setForStaff(Staff member){
        staffMember = member;
        nameLabel.setText(staffMember.getFullName());

        //Enable map editing for Admins
        if(!staffMember.getJobTitle().contains("Admin")){
            btnEdit.setDisable(true);
        }

        //Populate the list of service requests
        //todo


    }


}
