package database;

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

    static ArrayList<String>languages=new ArrayList<>();

    // Getter for all staff array list
    public static ArrayList<Staff> getStaff(){ return allStaff; }

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
    // Insert into staff table using a prepared statement from csv
    ///////////////////////////////////////////////////////////////////////////////
    public static void insertStaffFromCSV() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_STAFF);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement insertStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?, ?)");

            for (int j = 0; j < allStaffEnc.size(); j++) {

                insertStaff.setString(1, allStaffEnc.get(j).getUsername());
                insertStaff.setString(2, allStaffEnc.get(j).getPassword());
                insertStaff.setString(3, allStaffEnc.get(j).getJobTitle());
                insertStaff.setString(4, allStaffEnc.get(j).getFullName());
                insertStaff.setInt(5, allStaffEnc.get(j).getID());
                insertStaff.setInt(6, allStaffEnc.get(j).getIsAdmin());

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

            PreparedStatement addAnyStaff = conn.prepareStatement("INSERT INTO hospitalStaff VALUES (?, ?, ?, ?, ?, ?)");

            addAnyStaff.setString(1, encUser);
            addAnyStaff.setString(2, encPass);
            addAnyStaff.setString(3, anyStaff.getJobTitle());
            addAnyStaff.setString(4, encName);
            addAnyStaff.setInt(5, anyStaff.getID());
            addAnyStaff.setInt(6, anyStaff.getIsAdmin());

            addAnyStaff.executeUpdate();

            System.out.printf("Insert Staff Successful for staffID: %-5d\n", anyStaff.getID());
            System.out.println();

            conn.commit();

            allStaffEnc.add(new Staff(encUser, encPass, anyStaff.getJobTitle(), encName, anyStaff.getID(), anyStaff.getIsAdmin()));
            allStaff.add(new Staff(anyStaff.getUsername(), anyStaff.getPassword(), anyStaff.getJobTitle(), anyStaff.getFullName(), anyStaff.getID(), anyStaff.getIsAdmin()));

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
            modAddAnyStaff.setInt(6, anyStaff.getIsAdmin());

            System.out.println("Update Staff Member Successful!");

            conn.commit();

            allStaffEnc.add(new Staff(encUser, encPass, anyStaff.getJobTitle(), encName, anyStaff.getID(), anyStaff.getIsAdmin()));

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
            Integer intStaffAdmin

            System.out.println("");
            System.out.printf("%-65s %-65s %-30s %-65s %-20s %-10s\n", "staffID", "password", "jobTitle", "fullName", "ID", "isAdmin");

            //Process the results
            while (rsetAllStaff.next()) {
                strUsername = rsetAllStaff.getString("username");
                strPW = rsetAllStaff.getString("password");
                strTitle = rsetAllStaff.getString("jobTitle");
                strFullname = rsetAllStaff.getString("fullName");
                intStaffID = rsetAllStaff.getInt("ID");
                intStaffAdmin = rsetAllStaff.getInt("isAdmin");

                System.out.printf("%-65s %-65s %-30s %-65s %-20d %-10d\n", strUsername, strPW, strTitle, strFullname, intStaffID, intStaffAdmin);
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
    public static void readStaffCSV (String fname) {

        File staffFile = new File(fname);

        try {
            Scanner inputStreamStaff = new Scanner(staffFile);
            inputStreamStaff.nextLine();
            while (inputStreamStaff.hasNext()) {

                String staffData = inputStreamStaff.nextLine();
                String[] staffValues = staffData.split(",");

                String encUser = encStaffData(staffValues[0]);
                String encPass = encStaffData(staffValues[1]);
                String encName = encStaffData(staffValues[3]);

                staffDatabase.allStaffEnc.add(new Staff(encUser, encPass, staffValues[2], encName, Integer.valueOf(staffValues[4]), staffValues[5]));
                staffDatabase.allStaff.add(new Staff(staffValues[0], staffValues[1], staffValues[2], staffValues[3], Integer.valueOf(staffValues[4]), staffValues[5]));

            }
            inputStreamStaff.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Write to a output Staff csv file (With Password Encryption)
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputStaffEncCSV() {
        String outStaffFileName = "outputStaffEncrypted.csv";

        try {
            FileWriter fwEnc = new FileWriter(outStaffFileName, false);
            BufferedWriter bwEnc = new BufferedWriter(fwEnc);
            PrintWriter pwEnc = new PrintWriter(bwEnc);

            pwEnc.println("username,password,jobTitle,fullName,ID,isAdmin");
            for (int k = 0; k < staffDatabase.allStaffEnc.size(); k++) {

                pwEnc.println(staffDatabase.allStaffEnc.get(k).getUsername() + "," +
                        staffDatabase.allStaffEnc.get(k).getPassword() + "," +
                        staffDatabase.allStaffEnc.get(k).getJobTitle() + "," +
                        staffDatabase.allStaffEnc.get(k).getFullName() + "," +
                        staffDatabase.allStaffEnc.get(k).getID() + "," +
                        staffDatabase.allStaffEnc.get(k).getIsAdmin()
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

    ///////////////////////////////////////////////////////////////////////////////
    // Write to a output Staff csv file (No Password Encryption)
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputStaffCSV() {
        String outStaffFileName = "outputStaff.csv";

        try {
            FileWriter fw3 = new FileWriter(outStaffFileName, false);
            BufferedWriter bw3 = new BufferedWriter(fw3);
            PrintWriter pw3 = new PrintWriter(bw3);

            pw3.println("username,password,jobTitle,fullName,ID,isAdmin");
            for (int j = 0; j < staffDatabase.allStaff.size(); j++) {

                pw3.println(staffDatabase.allStaff.get(j).getUsername() + "," +
                        staffDatabase.allStaff.get(j).getPassword() + "," +
                        staffDatabase.allStaff.get(j).getJobTitle() + "," +
                        staffDatabase.allStaff.get(j).getFullName() + "," +
                        staffDatabase.allStaff.get(j).getID() + "," +
                        staffDatabase.allStaff.get(j).getIsAdmin()
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
