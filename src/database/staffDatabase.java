package database;

import DepartmentSubsystem.Admin;
import DepartmentSubsystem.Staff;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class staffDatabase {

    // Table Schema
    //////////////////////////////////////////////////////////////////
    // hospitalStaff (username U1, password, jobType, fullName, ID PK)
    //////////////////////////////////////////////////////////////////

    private static final String JDBC_URL_STAFF = "jdbc:derby:hospitalStaffDB;create=true";
    private static Connection conn;

    // Staff Primary Key Counter
    private static int staffCounter;

    public static void incStaffCounter() {
        staffCounter++;
    }

    // All staff members from the staff table in hospitalStaffDB
    static ArrayList<Staff> allStaff = new ArrayList<>();

    // Getter for all staff array list
    public static ArrayList<Staff> getStaff() {
        return allStaff;
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteStaffTable() {

        try {

            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "HOSPITALSTAFF", null);

            Statement stmtDelete3 = conn.createStatement();
            String deleteStaffTable = ("DROP TABLE HOSPITALSTAFF");

            if (res.next()) {
                int rsetDelete1 = stmtDelete3.executeUpdate(deleteStaffTable);
                System.out.println("Drop Staff Table Successful!");
                conn.commit();
                stmtDelete3.close();
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the Staff Members
    ///////////////////////////////////////////////////////////////////////////////
    public static void createStaffTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "HOSPITALSTAFF", null);

            //Add a new node table
            Statement stmtCreateStaffTable = conn.createStatement();
            String createStaffTable = ("CREATE TABLE hospitalStaff" +
                    "(username VARCHAR(64)," +
                    "password VARCHAR(64)," +
                    "jobTitle VARCHAR(50)," +
                    "fullname VARCHAR(64)," +
                    "ID INTEGER," +
                    "isAdmin INTEGER," +
                    "CONSTRAINT hospitalStaff_PK PRIMARY KEY (ID)," +
                    "CONSTRAINT hospitalStaff_U1 UNIQUE (username)," +
                    "CONSTRAINT ID_chk CHECK (ID > 0))");

            int rsetCreate3 = stmtCreateStaffTable.executeUpdate(createStaffTable);
            System.out.println("Create Staff table Successful!");

            conn.commit();
            stmtCreateStaffTable.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Insert into staff table using a prepared statement from csv
    ///////////////////////////////////////////////////////////////////////////////
    public static void insertStaffFromCSV() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement insertStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?, ?)");

            for (int j = 0; j < allStaff.size(); j++) {

                insertStaff.setString(1, allStaff.get(j).getUsername());
                insertStaff.setString(2, allStaff.get(j).getPassword());
                insertStaff.setString(3, allStaff.get(j).getJobTitle());
                insertStaff.setString(4, allStaff.get(j).getFullName());
                insertStaff.setInt(5, allStaff.get(j).getID());
                insertStaff.setInt(6, allStaff.get(j).getaAdmin());

                insertStaff.executeUpdate();

                setStaffCounter(j);
            }

            conn.commit();
            insertStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Add a Staff member to Staff table Function
    ///////////////////////////////////////////////////////////////////////////////
    public static void addStaff(Staff anyStaff) {

        staffCounter++;

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?, ?)");

            addAnyStaff.setString(1, anyStaff.getUsername());
            addAnyStaff.setString(2, anyStaff.getPassword());
            addAnyStaff.setString(3, anyStaff.getJobTitle());
            addAnyStaff.setString(4, anyStaff.getFullName());
            addAnyStaff.setInt(5, anyStaff.getID());
            addAnyStaff.setInt(6, anyStaff.getaAdmin());

            addAnyStaff.executeUpdate();

            conn.commit();

            allStaff.add(new Staff(anyStaff.getUsername(), anyStaff.getPassword(), anyStaff.getJobTitle(), anyStaff.getFullName(), anyStaff.getID(), anyStaff.getaAdmin()));

            addAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Modify a Staff Member in the Staff Database
    ///////////////////////////////////////////////////////////////////////////////
    public static void modifyStaff(Staff anyStaff1, Staff anyStaff2) {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String strModDrop = "DELETE FROM hospitalStaff WHERE ID = ?";
            String strModAdd = "INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement modDelAnyStaff = conn.prepareStatement(strModDrop);
            modDelAnyStaff.setInt(1, anyStaff1.getID());
            modDelAnyStaff.executeUpdate();
            allStaff.remove(anyStaff1);

            PreparedStatement modAddAnyStaff = conn.prepareStatement(strModAdd);

            modAddAnyStaff.setString(1, anyStaff2.getUsername());
            modAddAnyStaff.setString(2, anyStaff2.getPassword());
            modAddAnyStaff.setString(3, anyStaff2.getJobTitle());
            modAddAnyStaff.setString(4, anyStaff2.getFullName());
            modAddAnyStaff.setInt(5, anyStaff2.getID());
            modAddAnyStaff.setInt(6, anyStaff2.getaAdmin());

            conn.commit();

            allStaff.add(new Staff(anyStaff2.getUsername(), anyStaff2.getPassword(), anyStaff2.getJobTitle(), anyStaff2.getFullName(), anyStaff2.getID(), anyStaff2.getaAdmin()));

            modAddAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete a staff member from the staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteStaff(Staff anyStaff) {

        int anyStaffID = anyStaff.getID();

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement deleteAnyStaff = conn.prepareStatement("DELETE FROM hospitalStaff WHERE ID = ?");

            // set the corresponding param
            deleteAnyStaff.setInt(1, anyStaffID);
            // execute the delete statement
            deleteAnyStaff.executeUpdate();

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
            int intStaffID;
            int intIsAdmin;

            System.out.printf("%-65s %-65s %-30s %-65s %-20s %-20s\n", "staffID", "password", "jobTitle", "fullName", "ID", "isAdmin");

            //Process the results
            while (rsetAllStaff.next()) {
                strUsername = rsetAllStaff.getString("username");
                strPW = rsetAllStaff.getString("password");
                strTitle = rsetAllStaff.getString("jobTitle");
                strFullname = rsetAllStaff.getString("fullName");
                intStaffID = rsetAllStaff.getInt("ID");
                intIsAdmin = rsetAllStaff.getInt("isAdmin");

                System.out.printf("%-65s %-65s %-30s %-65s %-20d %-20d\n", strUsername, strPW, strTitle, strFullname, intStaffID, intIsAdmin);
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

    ///////////////////////////////////////////////////////////////////////////////
    // Read from Staff CSV File and store columns in staff array lists
    ///////////////////////////////////////////////////////////////////////////////
    public static void readStaffCSV(String fname) {
        int count = 0;

        InputStream in = Class.class.getResourceAsStream(fname);

        if (in == null) {
            System.out.println("Error: Could not find the file: " + fname);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] staffValues = line.split(",");
                String tempStr1 = staffValues[4];
                String tempStr2 = staffValues[5];

                if (count != 0) {
                    staffDatabase.allStaff.add(new Staff(staffValues[0], staffValues[1], staffValues[2], staffValues[3], Integer.valueOf(tempStr1), Integer.valueOf(tempStr2)));
                }
                count++;
            }
            reader.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    ///////////////////////////////////////////////////////////////////////////////
    // Write to a output Staff csv file (No Password Encryption)
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputStaffCSV() {
        String outStaffFileName = "outputStaff.csv";

        try {
            FileWriter fw2 = new FileWriter(outStaffFileName, false);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            PrintWriter pw2 = new PrintWriter(bw2);

            pw2.println("username,password,jobTitle,fullName,ID");
            for (int j = 0; j < staffDatabase.allStaff.size(); j++) {

                pw2.println(staffDatabase.allStaff.get(j).getUsername() + "," +
                        staffDatabase.allStaff.get(j).getPassword() + "," +
                        staffDatabase.allStaff.get(j).getJobTitle() + "," +
                        staffDatabase.allStaff.get(j).getFullName() + "," +
                        staffDatabase.allStaff.get(j).getID() + "," +
                        staffDatabase.allStaff.get(j).getaAdmin()
                );
            }

            pw2.flush();
            pw2.close();
            bw2.close();
            fw2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getStaffCounter() {
        return staffCounter;
    }

    public static void setStaffCounter(int aCount) {
        staffCounter = aCount;
    }

    public static String encStaffData(String anyString) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] protect = messageDigest.digest(anyString.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(protect);
        return encoded;
    }
}
