package service;

import exceptions.InvalidPasswordException;

public class Staff{
    private String username;
    private String password;
    private String jobTitle;
    private String fullName;
    private int ID;
    private Service jobType;
    private ServiceRequest currentRequest;
    private boolean isBusy;


    public Staff(String username, String password, String jobTitle, String fullName, int ID, Service jobType){

        this.username = username;
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = ID;
        this.jobType = jobType;
        currentRequest = null;
        isBusy = false;

    }

    /*
    takes in an old password and a new password, and changes the staff's current password to the new one
     */

    public void changePassword(String newPass, String oldPass) throws InvalidPasswordException {
        try
        {
            if(this.password.equals(oldPass)) this.password = newPass;
            else throw new InvalidPasswordException();
        } catch(InvalidPasswordException e) {
            System.out.println("invalid password"); //mainly for testing purposes
        }
    }

    /*
    gives the staff member a new request from the backlog, and if the backlog is empty, frees the
    staff member
     */
    public void completeCurRec() {
        currentRequest = jobType.getNextRequest();
        if (currentRequest == null){
            jobType.addAvailable(this);
            this.isBusy = false;
        }

    }

    //getters
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public String getFullName() {
        return fullName;
    }
    public int getID(){
        return ID;
    }
    public Service getJobType() {
        return jobType;
    }
    public ServiceRequest getCurrentRequest() {
        return currentRequest;
    }
    public boolean isBusy() {
        return isBusy;
    }

    //setters
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setJobType(Service jobType) {
        this.jobType = jobType;
    }
    public void setCurrentRequest(ServiceRequest request) {
        this.currentRequest = request;
        isBusy = true;
    }

    public String toString(){
        return fullName;
    }
}