/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

import exceptions.InvalidPasswordException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Staff{
    private String username;
    private String password;
    private String jobTitle;
    private String fullName;
    private int ID;
    private boolean available;
    private Department department;
    private Service jobType;
    private boolean admin;
    private LinkedList<ServiceRequest> currentRequests;
    private ArrayList<ServiceRequest> completedRequests;
    private ArrayList<String> languages = new ArrayList<>();

    public Staff(String username, String password, String jobTitle, String fullName, int ID, boolean available, Department department, Service jobType, LinkedList<ServiceRequest> currentRequests, ArrayList<ServiceRequest> completedRequests) {
        this.username = username;
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = ID;
        this.available = available;
        this.department = department;
        this.jobType = jobType;
        this.currentRequests = currentRequests;
        this.completedRequests = completedRequests;
    }

    public Staff(String username, String password, String jobTitle, String fullName, int ID, Service jobType){

        this.username = username;
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = ID;
        this.jobType = jobType;
        currentRequests = new LinkedList<ServiceRequest>();
        completedRequests = new ArrayList<ServiceRequest>();

    }

    //gives the staff member a new request from the backlog, and if the backlog is empty, frees the staff member.
    public void completeCurrentRequest() {
        try {
            completedRequests.add(currentRequests.pop());
        }
        catch (NoSuchElementException exception){
            available = true;
        }
    }

    public String toString(){
        return fullName;
    }

    //Takes in an old password and a new password, and changes the staff's current password to the new one.
    public void changePassword(String newPass, String oldPass) throws InvalidPasswordException {
        try
        {
            if(this.password.equals(oldPass)) this.password = newPass;
            else throw new InvalidPasswordException();
        } catch(InvalidPasswordException e) {
            System.out.println("invalid password"); //mainly for testing purposes
        }
    }

    //Getters and Setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public Service getJobType() {
        return jobType;
    }
    public void setJobType(Service jobType) {
        this.jobType = jobType;
    }
    public LinkedList<ServiceRequest> getCurrentRequests() {
        return currentRequests;
    }
    public void setCurrentRequests(LinkedList<ServiceRequest> currentRequests) {
        this.currentRequests = currentRequests;
    }
    public ArrayList<ServiceRequest> getCompletedRequests() {
        return completedRequests;
    }
    public void setCompletedRequests(ArrayList<ServiceRequest> completedRequests) {
        this.completedRequests = completedRequests;
    }
    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public void setLanguage(String language){
        this.languages.add(language);
    }
    public ArrayList<String> getLanguages() {
        return languages;
    }
}