package database;

import DepartmentSubsystem.Staff;

import java.sql.*;
import java.util.ArrayList;

public class staffDatabase {

    private static final String JDBC_URL_STAFF="jdbc:derby:hospitalStaffDB;create=true";
    private static Connection conn;

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the Staff Members
    ///////////////////////////////////////////////////////////////////////////////

    public static void createStaffTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "hospitalStaff", null);

            if (!res.next()) {
                Statement stmtCreateStaffTable = conn.createStatement();
                String createStaffTable = ("CREATE TABLE hospitalStaff" +
                        "(username VARCHAR(20) PRIMARY KEY," +
                        "password VARCHAR(20)," +
                        "jobTitle VARCHAR(20)," +
                        "fullname VARCHAR(20)," +
                        "ID INTEGER)");

                int rsetCreate3 = stmtCreateStaffTable.executeUpdate(createStaffTable);
                System.out.println("Create Staff table Successful!");

                conn.commit();
                stmtCreateStaffTable.close();
                conn.close();
            } else {
                Statement stmtDeleteStaffTable = conn.createStatement();
                String deleteStaffTable = ("DROP TABLE hospitalStaff");
                System.out.println("Drop Staff Table Successful!");
                int rsetDeleteStaffTable = stmtDeleteStaffTable.executeUpdate(deleteStaffTable);

                stmtDeleteStaffTable.close();

                Statement stmtCreateStaffTable = conn.createStatement();
                String createStaffTable = ("CREATE TABLE hospitalStaff" +
                        "(username VARCHAR(20) PRIMARY KEY," +
                        "password VARCHAR(20)," +
                        "jobTitle VARCHAR(20)," +
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
    // Add a Staff member Function
    ///////////////////////////////////////////////////////////////////////////////

    // Add new Staff Member to the Staff Members Table
    public static void addStaff(Staff anyStaff) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

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
        mainDatabase.allStaff.add(anyStaff);
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

    // Delete item(s) from staff table
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
        int indexOf = mainDatabase.allStaff.indexOf(anyStaff);
        mainDatabase.allStaff.remove(indexOf);
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


            String strStaffID = "";
            String strPW = "";
            String strTitle = "";
            String strFullname = "";
            Integer intStaffID = 0;


            System.out.println("");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s\n", "staffID", "password", "jobTitle", "fullName", "ID");

            //Process the results
            while (rsetAllStaff.next()) {
                strStaffID = rsetAllStaff.getString("staffID");
                strPW = rsetAllStaff.getString("password");
                strTitle = rsetAllStaff.getString("jobTitle");
                strFullname = rsetAllStaff.getString("fullName");
                intStaffID = rsetAllStaff.getInt("ID");

                System.out.printf("%-20s %-20s %-20s %-20s %-20d\n", strStaffID, strPW, strTitle, strFullname, intStaffID);
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
