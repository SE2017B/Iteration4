/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 4
* Original author: Jack Palmstrom
* The following code
*/
package DepartmentSubsystem;
    //differentiate between admin and staff
public class Admin {

    private int adminID;    //admind ID number
    private String username;    //username
    //contructor for admins
    public Admin(int adminID, Staff staff) {
        this.adminID = adminID;
        this.username = staff.getUsername();
    }

    public int getAdminID() {
        return adminID;
    }

    public int incAdminID() {
        ++adminID;
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
