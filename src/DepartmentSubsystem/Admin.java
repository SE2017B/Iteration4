package DepartmentSubsystem;

public class Admin {

    private int adminID;
    private String username;

    public Admin(int adminID, String username) {
        this.adminID = adminID;
        this.username = username;
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
