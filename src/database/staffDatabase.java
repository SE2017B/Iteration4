package database;

import DepartmentSubsystem.Staff;

import java.sql.*;
import java.util.ArrayList;

public class staffDatabase {

    private static final String JDBC_URL_STAFF="jdbc:derby:hospitalStaffDB;create=true";
    private static Connection conn;

    // All staff members from the staff table in hospitalStaffDB
    static ArrayList<Staff>allStaff=new ArrayList<Staff>();

    // Getter for all staff array list
    public static ArrayList<Staff> getStaff(){ return allStaff; }

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the Staff Members
    ///////////////////////////////////////////////////////////////////////////////
    public static void createStaffTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "HOSPITALSTAFF", null);

            // Staff table DNE just add
            if (!res.next()) {
                Statement stmtCreateStaffTable = conn.createStatement();
                String createStaffTable = ("CREATE TABLE hospitalStaff" +
                        "(username VARCHAR(20) PRIMARY KEY," +
                        "password VARCHAR(20)," +
                        "jobTitle VARCHAR(50)," +
                        "fullname VARCHAR(20)," +
                        "ID INTEGER)");

                int rsetCreate3 = stmtCreateStaffTable.executeUpdate(createStaffTable);
                System.out.println("Create Staff table Successful!");

                conn.commit();
                stmtCreateStaffTable.close();
                conn.close();
            }
            // Staff table already exists delete and re-add
            else {
                Statement stmtDeleteStaffTable = conn.createStatement();
                String deleteStaffTable = ("DROP TABLE hospitalStaff");
                System.out.println("Drop Staff Table Successful!");
                int rsetDeleteStaffTable = stmtDeleteStaffTable.executeUpdate(deleteStaffTable);

                stmtDeleteStaffTable.close();

                Statement stmtCreateStaffTable = conn.createStatement();
                String createStaffTable = ("CREATE TABLE hospitalStaff" +
                        "(username VARCHAR(20) PRIMARY KEY," +
                        "password VARCHAR(20)," +
                        "jobTitle VARCHAR(50)," +
                        "fullName VARCHAR(20)," +
                        "ID INTEGER)");

                int rsetCreate1 = stmtCreateStaffTable.executeUpdate(createStaffTable);
                System.out.println("Create Staff table Successful!");

                conn.commit();
                stmtCreateStaffTable.close();
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Add a Staff member to Staff table Function
    ///////////////////////////////////////////////////////////////////////////////
    public static void addStaff(Staff anyStaff) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?)");

            addAnyStaff.setString(1, anyStaff.getUsername());
            addAnyStaff.setString(2, anyStaff.getPassword());
            addAnyStaff.setString(3, anyStaff.getJobTitle());
            addAnyStaff.setString(4, anyStaff.getFullName());
            addAnyStaff.setInt(5, anyStaff.getID());

            addAnyStaff.executeUpdate();
            System.out.println("Insert Staff Successful for staffID: " + anyStaff.getID());

            conn.commit();
            addAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
        allStaff.add(new Staff(anyStaff.getUsername(), anyStaff.getPassword(), anyStaff.getJobTitle(), anyStaff.getFullName(), anyStaff.getID()));
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Modify a Staff Member in the Staff Database
    ///////////////////////////////////////////////////////////////////////////////
    public static void modifyStaff(Staff anyStaff) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String strModDrop = "DELETE FROM hospitalStaff WHERE ID = ?";
            String strModAdd = "INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?)";

            PreparedStatement modDelAnyStaff = conn.prepareStatement(strModDrop);
            modDelAnyStaff.setInt(1, anyStaff.getID());
            modDelAnyStaff.executeUpdate();

            PreparedStatement modAddAnyStaff = conn.prepareStatement(strModAdd);
            modAddAnyStaff.setString(1, anyStaff.getUsername());
            modAddAnyStaff.setString(2, anyStaff.getPassword());
            modAddAnyStaff.setString(3, anyStaff.getJobTitle());
            modAddAnyStaff.setString(4, anyStaff.getFullName());
            modAddAnyStaff.setInt(5, anyStaff.getID());

            System.out.println("Update Staff Member Successful!");

            conn.commit();
            modAddAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete a staff member from the staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteStaff(Staff anyStaff){

        int anyStaffID = anyStaff.getID();

        try  {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement deleteAnyStaff = conn.prepareStatement("DELETE FROM hospitalStaff WHERE ID = ?");

            // set the corresponding param
            deleteAnyStaff.setInt(1, anyStaffID);
            // execute the delete statement
            deleteAnyStaff.executeUpdate();

            System.out.println("Delete Staff Member Successful!");

            conn.commit();
            deleteAnyStaff.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int indexOf = allStaff.indexOf(anyStaff);
        allStaff.remove(indexOf);
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Query all staff members from the staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static void queryAllStaff() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectAllStaff = conn.createStatement();
            String allStaff = "SELECT * FROM hospitalStaff";
            ResultSet rsetAllStaff = selectAllStaff.executeQuery(allStaff);


            String strUsername;
            String strPW;
            String strTitle;
            String strFullname;
            Integer intStaffID;


            System.out.println("");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", "staffID", "password", "jobTitle", "fullName", "ID");

            //Process the results
            while (rsetAllStaff.next()) {
                strUsername = rsetAllStaff.getString("username");
                strPW = rsetAllStaff.getString("password");
                strTitle = rsetAllStaff.getString("jobTitle");
                strFullname = rsetAllStaff.getString("fullName");
                intStaffID = rsetAllStaff.getInt("ID");

                System.out.printf("%-20s %-20s %-20s %-20s %-20d\n", strUsername, strPW, strTitle, strFullname, intStaffID);
            } // End While

            conn.commit();

            rsetAllStaff.close();
            selectAllStaff.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
