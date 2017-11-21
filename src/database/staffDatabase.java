package database;

import service.Staff;
import java.sql.*;

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
                        "ID INTEGER," +
                        "jobType VARCHAR(20))");

                int rsetCreate3 = stmtCreateStaffTable.executeUpdate(createStaffTable);
                System.out.println("Create Staff table Successful!");

                conn.commit();
                stmtCreateStaffTable.close();
                conn.close();
            } else {
                Statement stmtDeleteStaffTable = conn.createStatement();
                String deleteStaffTable = ("DROP TABLE hospitalStaff");
                int rsetDeleteStaffTable = stmtDeleteStaffTable.executeUpdate(deleteStaffTable);

                stmtDeleteStaffTable.close();

                Statement stmtCreateStaffTable = conn.createStatement();
                String createStaffTable = ("CREATE TABLE hospitalStaff" +
                        "(staffID VARCHAR(20) PRIMARY KEY," +
                        "password VARCHAR(20)," +
                        "jobTitle VARCHAR(20)," +
                        "fullName VARCHAR(20)," +
                        "ID INTEGER," +
                        "jobType VARCHAR(20))");

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
    // Add a node function
    ///////////////////////////////////////////////////////////////////////////////

    // Add new Staff Member to the Staff Members Table
    public static void addStaff(String anyStaffID, String anyPW, String anyTitle, String anyFullname, int anyID, String anyType) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            addAnyStaff.setString(1, anyStaffID);
            addAnyStaff.setString(2, anyPW);
            addAnyStaff.setString(3, anyTitle);
            addAnyStaff.setString(4, anyFullname);
            addAnyStaff.setInt(5, anyID);
            addAnyStaff.setString(6, anyType);

            addAnyStaff.executeUpdate();
            System.out.println("Insert Staff Successful for staffID: " + anyStaffID);

            conn.commit();
            addAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Modify a Staff Member in the Staff Database
    ///////////////////////////////////////////////////////////////////////////////
    public static void modifyStaff(String colAttr, String setCond, String anyStaffID) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String strModifyStaff = "UPDATE hospitalStaff SET " + colAttr + " = ? WHERE anyStaffID = ?";

            PreparedStatement modifyAnyStaff = conn.prepareStatement(strModifyStaff);

            modifyAnyStaff.setString(1, setCond);
            modifyAnyStaff.setString(2, anyStaffID);

            modifyAnyStaff.executeUpdate();
            System.out.println("Update Staff Member Successful!");

            conn.commit();
            modifyAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete a staff member from the staff table
    ///////////////////////////////////////////////////////////////////////////////

    // Delete item(s) from node table
    public static void deleteStaff(Staff anyStaff){

        int anyStaffID = anyStaff.getID();

        try  {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement deleteAnyStaff = conn.prepareStatement("DELETE FROM hospitalStaff WHERE staffID = ?");

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
            String strType = "";


            System.out.println("");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n", "staffID", "password", "jobTitle", "fullName", "ID", "jobType");

            //Process the results
            while (rsetAllStaff.next()) {
                strStaffID = rsetAllStaff.getString("staffID");
                strPW = rsetAllStaff.getString("password");
                strTitle = rsetAllStaff.getString("jobTitle");
                strFullname = rsetAllStaff.getString("fullName");
                intStaffID = rsetAllStaff.getInt("ID");
                strType = rsetAllStaff.getString("jobType");

                System.out.printf("%-20s %-20s %-20s %-20s %-20d %-20s\n", strStaffID, strPW, strTitle, strFullname, intStaffID, strType);
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
