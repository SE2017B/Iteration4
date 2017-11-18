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
                        "(temp1 VARCHAR(20) PRIMARY KEY," +
                        "temp2 VARCHAR(20)," +
                        "temp3 VARCHAR(20)," +
                        "temp4 VARCHAR(20)," +
                        "temp5 VARCHAR(20)," +
                        "temp6 VARCHAR(20)," +
                        "temp7 VARCHAR(50)," +
                        "temp8 VARCHAR(30)," +
                        "temp9 VARCHAR(20))");

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
                        "temp2 VARCHAR(20)," +
                        "temp3 VARCHAR(20)," +
                        "temp4 VARCHAR(20)," +
                        "temp5 VARCHAR(20)," +
                        "temp6 VARCHAR(20)," +
                        "temp7 VARCHAR(50)," +
                        "temp8 VARCHAR(30)," +
                        "temp9 VARCHAR(20))");

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
    public static void addStaff(String anyStaffID, String anyTemp2, String anyTemp3, String anyTemp4, String anyTemp5, String anyTemp6, String anyTemp7) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            addAnyStaff.setString(1, anyStaffID);
            addAnyStaff.setString(2, anyTemp2);
            addAnyStaff.setString(3, anyTemp3);
            addAnyStaff.setString(4, anyTemp4);
            addAnyStaff.setString(5, anyTemp5);
            addAnyStaff.setString(6, anyTemp6);
            addAnyStaff.setString(7, anyTemp7);
            addAnyStaff.setString(8, anyTemp7);
            addAnyStaff.setString(9, "Team H");

            addAnyStaff.executeUpdate();
            System.out.println("Insert Node Successful for nodeID: " + anyStaffID);

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

            String strModifyStaff = "UPDATE hospitalStaff SET " + colAttr + " = ? WHERE temp1 = ?";

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

            Integer intStaffID = 0;
            String strtemp2 = "";
            String strtemp3 = "";
            String strtemp4 = "";
            String strtemp5 = "";
            String strtemp6 = "";
            String strtemp7 = "";
            String strtemp8 = "";
            String strtemp9 = "";

            System.out.println("");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-50s %-30s %-20s\n", "nodeID", "xcoord", "ycoord", "floor", "building", "nodeType", "longName", "shortName", "teamAssigned");

            //Process the results
            while (rsetAllStaff.next()) {
                intStaffID = rsetAllStaff.getInt("staffID");
                strtemp2 = rsetAllStaff.getString("temp2");
                strtemp3 = rsetAllStaff.getString("temp3");
                strtemp4 = rsetAllStaff.getString("temp4");
                strtemp5 = rsetAllStaff.getString("temp5");
                strtemp6 = rsetAllStaff.getString("temp6");
                strtemp7 = rsetAllStaff.getString("temp7");
                strtemp8 = rsetAllStaff.getString("temp8");
                strtemp9 = rsetAllStaff.getString("temp9");

                System.out.printf("%-20d %-20s %-20s %-20s %-20s %-20s %-50s %-30s %-20s\n", intStaffID, strtemp2, strtemp3, strtemp4, strtemp5, strtemp6, strtemp7, strtemp8, strtemp9);
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
