package database;

import DepartmentSubsystem.Feedback;
import DepartmentSubsystem.ServiceRequest;
import translation.Staff;

import java.sql.*;
import java.util.ArrayList;

public class serviceDatabase {

    // Table Schema
    //////////////////////////////////////////////////////////////////
    // serviceRequests (requestID PK, locationID, time, date, staffID, severity, comments)
    //////////////////////////////////////////////////////////////////

    private static final String JDBC_URL_STAFF = "jdbc:derby:hospitalStaffDB;create=true";
    private static Connection conn;

    // All service requests from the serviceRequest table in hospitalStaffDB
    static ArrayList<ServiceRequest> allRequests=new ArrayList<>();

    // Getter for Array List of all serviceRequests
    public static ArrayList<ServiceRequest> getAllRequests(){
        return allRequests;
    }

    // All feedbacks from the feedback table in the hospitalStaffDB
    static ArrayList<Feedback> allFeedbacks=new ArrayList<>();

    // Getter for Array List of all feedbacks
    public static ArrayList<Feedback> getAllFeedbacks(){
        return allFeedbacks;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete serviceRequests table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteRequestsTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "SERVICEREQUESTS", null);

            Statement stmtDelete = conn.createStatement();
            String deleteServiceTable = ("DROP TABLE serviceRequests");

            if (res.next()) {
                int rsetDelete = stmtDelete.executeUpdate(deleteServiceTable);
                System.out.println("Drop ServiceRequest Table Successful!");
                conn.commit();
                stmtDelete.close();
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for serviceRequests
    ///////////////////////////////////////////////////////////////////////////////
    public static void createServiceTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "SERVICEREQUESTS", null);

            //Add a new node table
            Statement stmtCreate = conn.createStatement();
            String createServiceTable = ("CREATE TABLE serviceRequests" +
                    "(requestID INTEGER," +
                    "locationID VARCHAR(10)," +
                    "time VARCHAR(50)," +
                    "date VARCHAR(50)," +
                    "staffID INTEGER," +
                    "task VARCHAR(30)," +
                    "severity VARCHAR(10)," +
                    "comments VARCHAR(75)," +
                    "CONSTRAINT serviceRequests_PK PRIMARY KEY (requestID))");

            int rsetCreate1 = stmtCreate.executeUpdate(createServiceTable);
            System.out.println("Create serviceRequests table Successful!");

            conn.commit();
            stmtCreate.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete serviceRequests table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteFeedbackTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "FEEDBACK", null);

            Statement stmtDelete = conn.createStatement();
            String deleteFeedbackTable = ("DROP TABLE feedback");

            if (res.next()) {
                int rsetDeleteFeedback = stmtDelete.executeUpdate(deleteFeedbackTable);
                System.out.println("Drop Feedback Table Successful!");
                conn.commit();
                stmtDelete.close();
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for serviceRequests
    ///////////////////////////////////////////////////////////////////////////////
    public static void createFeedbackTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "FEEDBACK", null);

            //Add a new node table
            Statement stmtCreateFeedback = conn.createStatement();
            String createFeedbackTable = ("CREATE TABLE feedback" +
                    "(feedbackID INTEGER," +
                    "rating INTEGER," +
                    "additionalInfo VARCHAR(150)," +
                    "CONSTRAINT feedback_PK PRIMARY KEY (feedbackID))");

            int rsetCreate1 = stmtCreateFeedback.executeUpdate(createFeedbackTable);
            System.out.println("Create Feedback table Successful!");

            conn.commit();
            stmtCreateFeedback.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Add a Feedback to the feedback table
    ///////////////////////////////////////////////////////////////////////////////
    public static void addFeedback(Feedback anyFeedback) {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyFeedback = conn.prepareStatement("INSERT INTO serviceRequests VALUES (?, ?, ?)");

            addAnyFeedback.setInt(1, anyFeedback.incFeedbackID());
            addAnyFeedback.setInt(2, anyFeedback.getFeedbackID());
            addAnyFeedback.setString(3, anyFeedback.getAdditionalInfo());

            allFeedbacks.add(new Feedback(anyFeedback.getFeedbackID(), anyFeedback.getFeedbackID(), anyFeedback.getAdditionalInfo()));

            addAnyFeedback.executeUpdate();

            conn.commit();
            addAnyFeedback.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete a feedback from the feedback table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteFeedback(Feedback anyFeedback) {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement deleteAnyFeedback = conn.prepareStatement("DELETE FROM feedback WHERE feedbackID = ?");

            // set the corresponding param
            deleteAnyFeedback.setInt(1, anyFeedback.getFeedbackID());
            // execute the delete statement
            deleteAnyFeedback.executeUpdate();

            System.out.println("Delete Feedback Successful!");

            conn.commit();
            deleteAnyFeedback.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int indexOf = allFeedbacks.indexOf(anyFeedback);
        allFeedbacks.remove(indexOf);
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Get all feedbacks from feedback table
    ///////////////////////////////////////////////////////////////////////////////
    public static void queryAllFeedbacks() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectAllFeedbacks = conn.createStatement();
            String allFeedbacks = "SELECT * FROM feedback";
            ResultSet rsetAllFeedbacks = selectAllFeedbacks.executeQuery(allFeedbacks);


            String strfeedID;
            Integer intRating;
            String strAddInfo;

            System.out.printf("%-20s %-20s %-25s\n", "feedbackID", "rating", "additionalInfo");
            //Process the results
            while (rsetAllFeedbacks.next()) {

                strfeedID = rsetAllFeedbacks.getString("feedbackID");
                intRating = rsetAllFeedbacks.getInt("rating");
                strAddInfo = rsetAllFeedbacks.getString("additionalInfo");

                System.out.printf("%-20s %-20s %-25s\n", strfeedID, intRating, strAddInfo);
            } // End While

            conn.commit();
            System.out.println();

            rsetAllFeedbacks.close();
            selectAllFeedbacks.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    ///////////////////////////////////////////////////////////////////////////////
    // Add a Service Request
    ///////////////////////////////////////////////////////////////////////////////
    public static void addService(ServiceRequest anyService) {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyService = conn.prepareStatement("INSERT INTO serviceRequests VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            addAnyService.setInt(1, anyService.getRequestID());
            addAnyService.setString(2, anyService.getLocation().getID());
            addAnyService.setString(3, anyService.getTime());
            addAnyService.setString(4, anyService.getDate());
            addAnyService.setString(5, anyService.getAssignedPersonnel().getID());
            addAnyService.setString(6, anyService.getTask());
            addAnyService.setString(7, anyService.getSeverity());
            addAnyService.setString(8, anyService.getInputData());

            addAnyService.executeUpdate();

            conn.commit();
            addAnyService.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete a serviceRequest from the table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteService(ServiceRequest anyRequest) {

        int anyServiceID = anyRequest.getRequestID();

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement deleteAnyService = conn.prepareStatement("DELETE FROM serviceRequests WHERE requestID = ?");

            // set the corresponding param
            deleteAnyService.setInt(1, anyServiceID);
            // execute the delete statement
            deleteAnyService.executeUpdate();

            System.out.println("Delete Service Request Successful!");

            conn.commit();
            deleteAnyService.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Get all service requests from the serviceRequest table
    ///////////////////////////////////////////////////////////////////////////////
    public static ArrayList<ServiceRequest> queryAllServices() {

        ArrayList<ServiceRequest> resultServices = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectAllServices = conn.createStatement();
            String allServices = "SELECT * FROM serviceRequests";
            ResultSet rsetAllServices = selectAllServices.executeQuery(allServices);

            Integer intServiceID;
            String strLocID;
            String strTime;
            String strDate;
            String task;
            String strStaffID;
            String strSeverity;
            String strComments;

            //Process the results
            while (rsetAllServices.next()) {
                intServiceID = rsetAllServices.getInt("requestID");
                strLocID = rsetAllServices.getString("locationID");
                strTime = rsetAllServices.getString("time");
                strDate = rsetAllServices.getString("date");
                strStaffID = rsetAllServices.getString("staffID");
                task = rsetAllServices.getString("task");
                strSeverity = rsetAllServices.getString("severity");
                strComments = rsetAllServices.getString("comments");

                Node someNode = db.nodeDatabase.findANode(strLocID);
                Staff someStaff = db.staffDatabase.findAStaff(strStaffID);

                resultServices.add(new ServiceRequest(intServiceID, someNode, strTime, strDate, someStaff, task, strSeverity, strComments));

            } // End While

            conn.commit();
            System.out.println();

            rsetAllServices.close();
            selectAllServices.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultServices;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Get all service requests for a given staff member
    ///////////////////////////////////////////////////////////////////////////////

    public static ArrayList<ServiceRequest> findStaffMemRequests(Staff anyStaff) {

        ArrayList<ServiceRequest> resultServices = new ArrayList<>();
        String anyID = anyStaff.getID();

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String staffMemServices = "SELECT * FROM serviceRequests WHERE staffID = ?";

            PreparedStatement selectStaffServices = conn.prepareStatement(staffMemServices);
            selectStaffServices.setString(1, anyID);

            ResultSet rsetStaffServices = selectStaffServices.executeQuery();

            Integer intServiceID;
            String strLocID;
            String strTime;
            String strDate;
            String strStaffID;
            String task;
            String strSeverity;
            String strComments;

            //Process the results
            while (rsetStaffServices.next()) {

                intServiceID = rsetStaffServices.getInt("requestID");
                strLocID = rsetStaffServices.getString("locationID");
                strTime = rsetStaffServices.getString("time");
                strDate = rsetStaffServices.getString("date");
                strStaffID = rsetStaffServices.getString("staffID");
                task = rsetStaffServices.getString("task");
                strSeverity = rsetStaffServices.getString("severity");
                strComments = rsetStaffServices.getString("comments");

                Staff someStaff = staffDatabase.findAStaff(strStaffID);
                Node someNode = nodeDatabase.findANode(strLocID);
                resultServices.add(new ServiceRequest(intServiceID, someNode, strTime, strDate, someStaff, task, strSeverity, strComments));

            } // End While

            conn.commit();
            rsetStaffServices.close();
            selectStaffServices.close();
            conn.close();
        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
        return resultServices;
    }
    */
}
