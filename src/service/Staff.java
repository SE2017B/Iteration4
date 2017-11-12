
package service;

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

    public String getFullName() {
        return fullName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setJobType(Service jobType) {
        this.jobType = jobType;
    }

    public Service getJobType() {
        return jobType;
    }

    public ServiceRequest getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(ServiceRequest request) {
        this.currentRequest = request;
        isBusy = true;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public int getID(){
        return ID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // make a try catch eventually so that if password fails, a message pops up saying "invalid password"
    public void changePassword(String newPass, String oldPass){
        if(oldPass.equals(this.password)) this.password = newPass;
    }

    public void completeCurRec() {
        currentRequest = jobType.getNextRequest();
        if (currentRequest == null){
            jobType.addAvailable(this);
        }
    }
}