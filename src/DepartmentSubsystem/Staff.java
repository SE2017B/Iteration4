/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/


package DepartmentSubsystem;

import database.staffDatabase;
import exceptions.InvalidPasswordException;
import javafx.collections.ObservableList;

import java.util.*;

public class Staff{
    //Only essential for logging in
    private String username;
    private String password;
    private boolean admin;
    //These are just details for display
    private ArrayList<ServiceRequest> workload;
    private ArrayList<String> languages;
    private String jobTitle;
    private String fullName;
    private int ID;

    //Constructor DB uses
    public Staff(String username, String password, String jobTitle, String fullName, int ID, int admin, ArrayList<String> languages){
        this.username = username;
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = ID;
        if(admin == 1){
            this.admin = false;
        }
        else{
            this.admin = true;
        }

        this.languages = languages;

        //Setting stuff to blanks for now
        workload = new ArrayList<ServiceRequest>();
    }

    //Constructor DB uses
    //////////////////////
    ////    LEGACY    ////
    //////////////////////
    public Staff(String username, String password, String jobTitle, String fullName, int ID){
        this.username = username;
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = ID;
        this.admin = false;

        this.languages = new ArrayList<>();

        //Setting stuff to blanks for now
        workload = new ArrayList<ServiceRequest>();
    }

    //Takes in an old password and a new password, and changes the staff's current password to the new one.
    public void changePassword(String newPassword, String oldPassword) throws InvalidPasswordException {
        try {
            if (this.password.equals(oldPassword))
                this.password = newPassword;
            else
                throw new InvalidPasswordException();
        }
        catch(InvalidPasswordException e) {
            System.out.println("invalid password"); //mainly for testing purposes
        }
    }

    //Update
    public void uptadeDB(){
        staffDatabase.modifyStaff(this);
    }

    //Important Getters and Setters
    public boolean isAdmin() {
        return admin;
    }

    //Other Getters and Setters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public String getFullName() {
        return fullName;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public ArrayList<String> getlanguages() {
        return languages;
    }

    //Modifies the staff member
    public void updateCredentials(String username, String password, String jobTitle, String fullName, int ID, int admin, ArrayList<String> languages) throws InvalidPasswordException {
        this.username = username;
        changePassword(password, this.password);
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = ID;
        if(admin == 1){
            this.admin = false;
        }
        else{
            this.admin = true;
        }
        this.languages = languages;
    }

    //Service request related methods
    public void addRequest(ServiceRequest newRequest){
        workload.add(newRequest);
    }
    public ArrayList<ServiceRequest> getAllRequest(){
        return workload;
    }
    public void removeRequests(ObservableList<ServiceRequest> requestCompleted) {
        ArrayList<ServiceRequest> removedRequests = ObservableListToArrayList(requestCompleted);
        this.workload.removeAll(removedRequests);
    }

    //Helper methods
    private ArrayList<ServiceRequest> ObservableListToArrayList(ObservableList<ServiceRequest> requestCompleted) {
        ArrayList<ServiceRequest> returnVal = new ArrayList<ServiceRequest>();
        for(ServiceRequest sr: requestCompleted){
            returnVal.add(sr);
        }
        return returnVal;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof Staff)) return false;
        Staff other = (Staff)obj;
        return this.getUsername().equals(other.getUsername());
    }

    @Override
    public String toString(){
        return fullName;
    }
}
