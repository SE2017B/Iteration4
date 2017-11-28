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
    //Only essential for logging in
    private String username;
    private String password;
    private boolean admin;

    //These are just details for display
    private String jobTitle;
    private String fullName;
    private int ID;
    private Department department;

    //Use this to determine if they can perform a service or not.
    private ArrayList<Service> capableServices;

    //Keeps track of a certain staff members requests
    private LinkedList<ServiceRequest> currentRequests;
    private ArrayList<ServiceRequest> completedRequests;

    //This only applies if someone can speak multiple languages. We set english as the only language by default
    private ArrayList<String> languages = new ArrayList<>();

    //Constructor DB uses
    public Staff(String username, String password, String jobTitle, String fullName, int ID){
        this.username = username;
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = ID;

        //Setting stuff to blanks for now
        this.department = new Department("FILLER DEPT");
        this.capableServices = new ArrayList<Service>();
        this.currentRequests = new LinkedList<ServiceRequest>();
        this.completedRequests = new ArrayList<ServiceRequest>();

        this.languages = new ArrayList<String>();
        this.languages.add("English");
    }

    //Gives the staff member a new request from the backlog, and if the backlog is empty, frees the staff member.
    public void completeCurrentRequest() {
        try {
            completedRequests.add(currentRequests.pop());
        }
        catch (NoSuchElementException exception){
         //   available = true;
        }
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

    //Updates Database with the current staff object
    public void uptadeDB(){
        //mainDatabase.modifyStaff(this);
    }

    //Important Getters and Setters
    public boolean isAdmin() {
        return admin;
    }
    public ArrayList<Service> getCapableServices() {
        return capableServices;
    }
    public void addCapableService(Service capableService) {
        this.capableServices.add(capableService);
    }
    public ArrayList<String> getLanguages() {
        return languages;
    }
    public void addLanguages(ArrayList<String> languages) {
        //Verifies that all languages added are not already inside the list of known languages
        for(String language: languages){
            if(!this.languages.contains(language)){
                this.languages.add(language);
            }
        }
    }
    public void addLanguage(String language){
        //If its not known, then LET IT BE KNOWN!
        if(!this.languages.contains(language)){this.languages.add(language);}
    }
    public void addRequest(ServiceRequest request){
        this.currentRequests.add(request);
    }

    //Other Getters and Setters
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
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
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public LinkedList<ServiceRequest> getCurrentRequests() {
        if(currentRequests.isEmpty()){
           return null;
        }
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

    public String toString(){
        return fullName;
    }
}