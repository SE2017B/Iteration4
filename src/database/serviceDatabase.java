package database;

import DepartmentSubsystem.Feedback;
import DepartmentSubsystem.ServiceRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import translation.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.Observable;

@SuppressWarnings("Duplicates")
public class serviceDatabase {

    // Table Schema
    //////////////////////////////////////////////////////////////////
    // serviceRequests (requestID PK, locationID, time, date, staffID, severity, comments)
    //////////////////////////////////////////////////////////////////

    private static final String JDBC_URL_STAFF = "jdbc:derby:hospitalStaffDB;create=true";
    private static Connection conn;

    // All feedbacks from the feedback table in the hospitalStaffDB
    static ArrayList<Feedback> allFeedbacks=new ArrayList<>();

    // Getter for Array List of all feedbacks
    public static ArrayList<Feedback> getAllFeedbacks(){
        return allFeedbacks;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete feedback table
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
    // Create a table for feedback
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

            PreparedStatement addAnyFeedback = conn.prepareStatement("INSERT INTO FEEDBACK VALUES (?, ?, ?)");

            addAnyFeedback.setInt(1, anyFeedback.incFeedbackID());
            addAnyFeedback.setInt(2, anyFeedback.getRating());
            addAnyFeedback.setString(3, anyFeedback.getAdditionalInfo());

            allFeedbacks.add(new Feedback(anyFeedback.getFeedbackID(), anyFeedback.getRating(), anyFeedback.getAdditionalInfo()));

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

    //////////////////////////////////////////////////////////////////////////////////////////
    // Find how many ratings are in each category (0 - 5) and return data for pie chart
    //////////////////////////////////////////////////////////////////////////////////////////
    public static ObservableList<PieChart.Data> cntFeedback() {

        ObservableList<PieChart.Data> allPieData = FXCollections.observableArrayList();
        int zeroCounter = 0;
        int oneCounter = 0;
        int twoCounter = 0;
        int threeCounter = 0;
        int fourCounter = 0;
        int fiveCounter = 0;

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement cntAllFeedback = conn.createStatement();
            String strCntFeedback = "SELECT rating, COUNT(rating) AS aRating FROM feedback GROUP BY rating";
            ResultSet rsetCntFeedback = cntAllFeedback.executeQuery(strCntFeedback);

            int aRating;
            int anyRatingCNT;

            //Process the results
            while (rsetCntFeedback.next()) {
                aRating = rsetCntFeedback.getInt("rating");
                anyRatingCNT = rsetCntFeedback.getInt("aRating");

                switch (aRating) {
                    case 0:
                        zeroCounter = anyRatingCNT;
                        break;
                    case 1:
                        oneCounter = anyRatingCNT;
                        break;
                    case 2:
                        twoCounter = anyRatingCNT;
                        break;
                    case 3:
                        threeCounter = anyRatingCNT;
                        break;
                    case 4:
                        fourCounter = anyRatingCNT;
                        break;
                    case 5:
                        fiveCounter = anyRatingCNT;
                        break;
                }
            }
            conn.commit();
            rsetCntFeedback.close();
            cntAllFeedback.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }

        allPieData.addAll(new PieChart.Data("Rating 0", zeroCounter),
                new PieChart.Data("Rating 1", oneCounter),
                new PieChart.Data("Rating 2", twoCounter),
                new PieChart.Data("Rating 3", threeCounter),
                new PieChart.Data("Rating 4", fourCounter),
                new PieChart.Data("Rating 5", fiveCounter));

        return allPieData;
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    // Find how many ratings are in each category (0 - 5) and return array list of data for charts
    //////////////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<Integer> cntChartFeedback() {

        ArrayList<Integer> arrayCounter = new ArrayList<>();

        Integer zeroCounter = 0;
        Integer oneCounter = 0;
        Integer twoCounter = 0;
        Integer threeCounter = 0;
        Integer fourCounter = 0;
        Integer fiveCounter = 0;

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement cntAllFeedback = conn.createStatement();
            String strCntFeedback = "SELECT rating, COUNT(rating) AS aRating FROM feedback GROUP BY rating";
            ResultSet rsetCntFeedback = cntAllFeedback.executeQuery(strCntFeedback);

            int aRating;
            int anyRatingCNT;

            //Process the results
            while (rsetCntFeedback.next()) {

                aRating = rsetCntFeedback.getInt("rating");
                anyRatingCNT = rsetCntFeedback.getInt("aRating");

                switch (aRating) {

                    case 0:
                        zeroCounter = anyRatingCNT;
                        break;
                    case 1:
                        oneCounter = anyRatingCNT;
                        break;
                    case 2:
                        twoCounter = anyRatingCNT;
                        break;
                    case 3:
                        threeCounter = anyRatingCNT;
                        break;
                    case 4:
                        fourCounter = anyRatingCNT;
                        break;
                    case 5:
                        fiveCounter = anyRatingCNT;
                        break;
                }
            }
            conn.commit();
            rsetCntFeedback.close();
            cntAllFeedback.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }

        arrayCounter.add(zeroCounter);
        arrayCounter.add(oneCounter);
        arrayCounter.add(twoCounter);
        arrayCounter.add(threeCounter);
        arrayCounter.add(fourCounter);
        arrayCounter.add(fiveCounter);

        return arrayCounter;
    }

    public static String avgFeedback() {

        float avgFeedback = 0;

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement cntAllFeedback = conn.createStatement();
            String strCntFeedback = "SELECT AVG(CAST (RATING AS DOUBLE PRECISION)) AS avgRating FROM feedback";
            ResultSet rsetCntFeedback = cntAllFeedback.executeQuery(strCntFeedback);

            float anyRatingAVG;

            //Process the results
            if (rsetCntFeedback.next()) {

                anyRatingAVG = rsetCntFeedback.getFloat("avgRating");

                avgFeedback = anyRatingAVG;

            }
            conn.commit();
            rsetCntFeedback.close();
            cntAllFeedback.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
        String avgFeedStr = "Average Feedback Rating: " + avgFeedback;
        return avgFeedStr;
    }

}

