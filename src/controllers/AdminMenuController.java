package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import service.ServiceRequest;
import service.Staff;
import service.TransportService;

public class AdminMenuController implements ControllableScreen{
    private ScreenController parent;
    private Staff staffMember;
    private ServiceRequest serviceRequest;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }
    @FXML
    private Button btnService;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnLogout;

    @FXML
    private Label nameLabel;

    @FXML
    private Button btnComplete;

    @FXML
    private Label jobDescrLabel;

    @FXML
    private Label locationLabel;

    public void init(){
    }

    public void onShow(){}

    //when services button pressed move to the services window
    public void servicePressed(ActionEvent e){
        System.out.println("Service Pressed");
        parent.setScreen(ScreenController.RequestID);
    }


    public void completePressed(ActionEvent e){
        //remove the selected request from the request list and the staff member
        System.out.println("Complete Pressed");
        staffMember.completeCurRec();
        setForStaff(staffMember);
    }

    //moves to edit node menu when button is pressed
    public void editPressed(ActionEvent e){
        System.out.println("Edit Pressed");
        parent.setScreen(ScreenController.AddNodeID);
    }

    //logs out when button is pressed
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
            btnEdit.setVisible(false);
        }
        else{
            btnEdit.setDisable(false);
            btnEdit.setVisible(true);
        }

        //requestList.setItems(FXCollections.observableArrayList(staffMember.getFullName(), staffMember.getJobTitle()));
        //Populate the list of service requests
        if(staffMember.getCurrentRequest() == null)
        {
            jobDescrLabel.setText("No Current Requests");
            locationLabel.setText(" ");
        }else{


        jobDescrLabel.setText(staffMember.getCurrentRequest().getDescription());
        locationLabel.setText(staffMember.getCurrentRequest().getWhere().getX() + " " + staffMember.getCurrentRequest().getWhere().getX());
        }

    }


}
