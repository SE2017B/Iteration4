package database;

import DepartmentSubsystem.Admin;
import DepartmentSubsystem.Staff;

import java.io.*;
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

    //////////////////////////////////////////////////////////////////
    // hospitalAdmins (adminID PK, username U1)
    //////////////////////////////////////////////////////////////////

    private static final String JDBC_URL_STAFF="jdbc:derby:hospitalStaffDB;create=true";
    private static Connection conn;

    // Staff Primary Key Counter
    private static int staffCounter;

    public static void incStaffCounter() {
        staffCounter++;
    }

    // All staff members from the staff table in hospitalStaffDB
    static ArrayList<Staff>allStaff=new ArrayList<>();
    static ArrayList<Staff>allStaffEnc = new ArrayList<>();

    // Getter for all staff array list
    public static ArrayList<Staff> getStaff(){ return allStaff; }

    static ArrayList<Admin>allAdmins = new ArrayList<>();

    // Getter for all staff array list
    public static ArrayList<Admin> getAllAdmins(){ return allAdmins; }

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
                    "CONSTRAINT hospitalStaff_PK PRIMARY KEY (ID)," +
                    "CONSTRAINT hospitalStaff_U1 UNIQUE (username)," +
                    "CONSTRAINT jobTitle CHECK (jobTitle IN ('Translator', 'Janitor', 'Chef', 'Food Delivery', 'Transport Staff'))," +
                    "CONSTRAINT ID_chk CHECK (ID > 0))");

            int rsetCreate3 = stmtCreateStaffTable.executeUpdate(createStaffTable);
            System.out.println("Create Staff table Successful!");

            conn.commit();
            System.out.println();

            stmtCreateStaffTable.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////
    // Delete staff table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteAdminTable() {

        try {

            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "HOSPITALADMINS", null);

            Statement stmtDeleteAdmin = conn.createStatement();
            String deleteAdminTable = ("DROP TABLE HOSPITALADMINS");

            if (res.next()) {
                int rsetDelete = stmtDeleteAdmin.executeUpdate(deleteAdminTable);
                System.out.println("Drop Staff Table Successful!");
                conn.commit();

            }
            stmtDeleteAdmin.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the Admin Staff Members
    ///////////////////////////////////////////////////////////////////////////////
    public static void createAdminTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "HOSPITALADMINS", null);

            //Add a new node table
            Statement stmtCreateAdminsTable = conn.createStatement();
            String createAdminTable = ("CREATE TABLE hospitalAdmins" +
                    "(adminID INTEGER," +
                    "username VARCHAR(64)," +
                    "CONSTRAINT hospitalAdmins_PK PRIMARY KEY (adminID))");

            int rsetCreateAdmins = stmtCreateAdminsTable.executeUpdate(createAdminTable);
            System.out.println("Create Admin table Successful!");

            conn.commit();
            System.out.println();

            stmtCreateAdminsTable.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Add an Admin member to Admin table Function
    ///////////////////////////////////////////////////////////////////////////////
    public static void addAdmin(Admin anyAdmin) {

        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyStaff = conn.prepareStatement("INSERT INTO hospitalAdmins VALUES (?, ?)");

            anyAdmin.incAdminID();

            addAnyStaff.setInt(1, anyAdmin.getAdminID());
            addAnyStaff.setString(2, anyAdmin.getUsername());

            allAdmins.add(new Admin(anyAdmin.getAdminID(), anyAdmin.getUsername()));

            addAnyStaff.executeUpdate();

            System.out.printf("Insert Admin Successful\n");

            conn.commit();

            addAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete an admin from the admin table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteAdmin(Admin anyAdmin){

        try  {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement deleteAnyAdmin = conn.prepareStatement("DELETE FROM hospitalAdmins WHERE adminID = ?");

            // set the corresponding param
            deleteAnyAdmin.setInt(1, anyAdmin.getAdminID());
            // execute the delete statement
            deleteAnyAdmin.executeUpdate();

            System.out.println("Delete Admin Successful!");

            conn.commit();
            deleteAnyAdmin.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        int indexOf = allAdmins.indexOf(anyAdmin);
        allAdmins.remove(indexOf);
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Query all admins from the admin table
    ///////////////////////////////////////////////////////////////////////////////
    public static void queryAllAdmins() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectAllAdmins = conn.createStatement();
            String allAdmins = "SELECT * FROM hospitalAdmins";
            ResultSet rsetAllAdmins = selectAllAdmins.executeQuery(allAdmins);

            Integer intAdminID;
            String strUsername;

            System.out.printf("%-20s %-65s\n", "adminID", "username");

            //Process the results
            while (rsetAllAdmins.next()) {
                intAdminID = rsetAllAdmins.getInt("adminID");
                strUsername = rsetAllAdmins.getString("username");

                System.out.printf("%-20d %-65s\n", intAdminID, strUsername);
            } // End While

            conn.commit();
            System.out.println();

            rsetAllAdmins.close();
            selectAllAdmins.close();
            conn.close();

        } // end try
        catch (SQLException e) {
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

            PreparedStatement insertStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?)");

            for (int j = 0; j < allStaffEnc.size(); j++) {

                insertStaff.setString(1, allStaffEnc.get(j).getUsername());
                insertStaff.setString(2, allStaffEnc.get(j).getPassword());
                insertStaff.setString(3, allStaffEnc.get(j).getJobTitle());
                insertStaff.setString(4, allStaffEnc.get(j).getFullName());
                insertStaff.setInt(5, allStaffEnc.get(j).getID());

                insertStaff.executeUpdate();

                staffCounter++;
                System.out.printf("%-5d: Insert Staff Successful!\n",(j+1));
            }

            conn.commit();
            System.out.println();

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

            String encUser = encStaffData(anyStaff.getUsername());
            String encPass = encStaffData(anyStaff.getPassword());
            String encName = encStaffData(anyStaff.getUsername());

            PreparedStatement addAnyStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?)");

            addAnyStaff.setString(1, encUser);
            addAnyStaff.setString(2, encPass);
            addAnyStaff.setString(3, anyStaff.getJobTitle());
            addAnyStaff.setString(4, encName);
            addAnyStaff.setInt(5, anyStaff.getID());

            addAnyStaff.executeUpdate();

            System.out.printf("Insert Staff Successful for staffID: %-5d\n", anyStaff.getID());
            System.out.println();

            conn.commit();

            allStaffEnc.add(new Staff(encUser, encPass, anyStaff.getJobTitle(), encName, anyStaff.getID()));
            allStaff.add(new Staff(anyStaff.getUsername(), anyStaff.getPassword(), anyStaff.getJobTitle(), anyStaff.getFullName(), anyStaff.getID()));

            addAnyStaff.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
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
            String strModAdd = "INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement modDelAnyStaff = conn.prepareStatement(strModDrop);
            modDelAnyStaff.setInt(1, anyStaff.getID());
            modDelAnyStaff.executeUpdate();

            PreparedStatement modAddAnyStaff = conn.prepareStatement(strModAdd);

            String encUser = encStaffData(anyStaff.getUsername());
            String encPass = encStaffData(anyStaff.getPassword());
            String encName = encStaffData(anyStaff.getFullName());

            modAddAnyStaff.setString(1, encUser);
            modAddAnyStaff.setString(2, encPass);
            modAddAnyStaff.setString(3, anyStaff.getJobTitle());
            modAddAnyStaff.setString(4, encName);
            modAddAnyStaff.setInt(5, anyStaff.getID());

            System.out.println("Update Staff Member Successful!");

            conn.commit();

            allStaffEnc.add(new Staff(encUser, encPass, anyStaff.getJobTitle(), encName, anyStaff.getID()));

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
        allStaffEnc.remove(indexOf);
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
            System.out.printf("%-65s %-65s %-30s %-65s %-20s\n", "staffID", "password", "jobTitle", "fullName", "ID");

            //Process the results
            while (rsetAllStaff.next()) {
                strUsername = rsetAllStaff.getString("username");
                strPW = rsetAllStaff.getString("password");
                strTitle = rsetAllStaff.getString("jobTitle");
                strFullname = rsetAllStaff.getString("fullName");
                intStaffID = rsetAllStaff.getInt("ID");

                System.out.printf("%-65s %-65s %-30s %-65s %-20d\n", strUsername, strPW, strTitle, strFullname, intStaffID);
            } // End While

            conn.commit();
            System.out.println();

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
                System.out.println("Error");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            try {

                for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                    String[] staffValues = line.split(",");
                    String tempStr = staffValues[4];

                    if (count != 0) {
                        staffDatabase.allStaff.add(new Staff(staffValues[0], staffValues[1], staffValues[2], staffValues[3], Integer.valueOf(tempStr)));
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
    // Write to a output Staff csv file (With Password Encryption)
    ///////////////////////////////////////////////////////////////////////////////
    /*
    public static void outputStaffEncCSV() {
        String outStaffFileName = "outputStaffEncrypted.csv";

        try {
            FileWriter fwEnc = new FileWriter(outStaffFileName, false);
            BufferedWriter bwEnc = new BufferedWriter(fwEnc);
            PrintWriter pwEnc = new PrintWriter(bwEnc);

            pwEnc.println("username,password,jobTitle,fullName,ID");
            for (int k = 0; k < staffDatabase.allStaffEnc.size(); k++) {

                pwEnc.println(staffDatabase.allStaffEnc.get(k).getUsername() + "," +
                        staffDatabase.allStaffEnc.get(k).getPassword() + "," +
                        staffDatabase.allStaffEnc.get(k).getJobTitle() + "," +
                        staffDatabase.allStaffEnc.get(k).getFullName() + "," +
                        staffDatabase.allStaffEnc.get(k).getID()
                );
                System.out.printf("%-5d: Staff Record Saved!\n", k);
            }
            System.out.println();
            pwEnc.flush();
            pwEnc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    ///////////////////////////////////////////////////////////////////////////////
    // Write to a output Staff csv file (No Password Encryption)
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputStaffCSV() {
        String outStaffFileName = "src/csv/outputStaff.csv";

        try {
            FileWriter fw3 = new FileWriter(outStaffFileName, false);
            BufferedWriter bw3 = new BufferedWriter(fw3);
            PrintWriter pw3 = new PrintWriter(bw3);

            pw3.println("username,password,jobTitle,fullName,ID");
            for (int j = 0; j < staffDatabase.allStaff.size(); j++) {

                pw3.println(staffDatabase.allStaff.get(j).getUsername() + "," +
                        staffDatabase.allStaff.get(j).getPassword() + "," +
                        staffDatabase.allStaff.get(j).getJobTitle() + "," +
                        staffDatabase.allStaff.get(j).getFullName() + "," +
                        staffDatabase.allStaff.get(j).getID()
                );
                System.out.printf("%-5d: Staff Record Saved!\n", j);
            }
            System.out.println();
            pw3.flush();
            pw3.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getStaffCounter() {
        return staffCounter;
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
