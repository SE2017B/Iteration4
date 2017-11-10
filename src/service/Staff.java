
package service;

public class Staff{
    private String username;
    private String password;
    private String jobTitle;
    private String fullName;
    private int ID;

    public Staff(String username, String password, String jobTitle, String fullName, int ID){
        this.username = username;
        this.password = password;
        this.jobTitle = jobTitle;
        this.fullName = fullName;
        this.ID = ID;
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

    public void changePassword(String newPass, String oldPass){

    }
}