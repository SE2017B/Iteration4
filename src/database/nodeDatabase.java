package database;

import map.Node;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class nodeDatabase {

    private static final String JDBC_URL_MAP="jdbc:derby:hospitalMapDB;create=true";
    private static Connection conn;

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the nodes
    ///////////////////////////////////////////////////////////////////////////////

    public static void createNodeTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "MAPHNODES", null);

            if (!res.next()) {
                Statement stmtCreate1 = conn.createStatement();
                String createNodesTable = ("CREATE TABLE mapHNodes" +
                        "(nodeID VARCHAR(20) PRIMARY KEY," +
                        "xCoord VARCHAR(20)," +
                        "yCoord VARCHAR(20)," +
                        "floor VARCHAR(20)," +
                        "buiding VARCHAR(20)," +
                        "nodeType VARCHAR(20)," +
                        "longName VARCHAR(50)," +
                        "shortName VARCHAR(30)," +
                        "teamAssigned VARCHAR(20))");

                int rsetCreate1 = stmtCreate1.executeUpdate(createNodesTable);
                System.out.println("Create Nodes table Successful!");

                conn.commit();
                stmtCreate1.close();
                conn.close();
            } else {
                Statement stmtDelete1 = conn.createStatement();
                String deleteNodesTable = ("DROP TABLE mapHNodes");
                int rsetDelete1 = stmtDelete1.executeUpdate(deleteNodesTable);
                stmtDelete1.close();

                Statement stmtCreate1 = conn.createStatement();
                String createNodesTable = ("CREATE TABLE mapHNodes" +
                        "(nodeID VARCHAR(20) PRIMARY KEY," +
                        "xCoord VARCHAR(20)," +
                        "yCoord VARCHAR(20)," +
                        "floor VARCHAR(20)," +
                        "building VARCHAR(20)," +
                        "nodeType VARCHAR(20)," +
                        "longName VARCHAR(50)," +
                        "shortName VARCHAR(30)," +
                        "teamAssigned VARCHAR(20))");

                int rsetCreate1 = stmtCreate1.executeUpdate(createNodesTable);
                System.out.println("Create Nodes table Successful!");

                conn.commit();
                stmtCreate1.close();
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Insert into nodes table using a prepared statement from csv
    ///////////////////////////////////////////////////////////////////////////////
    public static void insertNodesFromCSV() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement insertNode = conn.prepareStatement("INSERT INTO mapHNodes VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (int j = 1; j < mainDatabase.allNodes.get(j).getID().length(); j++) {

                insertNode.setString(1, mainDatabase.allNodes.get(j).getID());
                insertNode.setString(2, Integer.toString(mainDatabase.allNodes.get(j).getX()));
                insertNode.setString(3, Integer.toString(mainDatabase.allNodes.get(j).getY()));
                insertNode.setString(4, mainDatabase.allNodes.get(j).getFloor());
                insertNode.setString(5, mainDatabase.allNodes.get(j).getBuilding());
                insertNode.setString(6, mainDatabase.allNodes.get(j).getType());
                insertNode.setString(7, mainDatabase.allNodes.get(j).getLongName());
                insertNode.setString(8, mainDatabase.allNodes.get(j).getShortName());
                insertNode.setString(9, mainDatabase.allNodes.get(j).getTeam());

                insertNode.executeUpdate();
                System.out.println(j + ": Insert Node Successful!");
            }

            conn.commit();
            insertNode.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }


    ///////////////////////////////////////////////////////////////////////////////
    // Add a node function
    ///////////////////////////////////////////////////////////////////////////////

    // Add new node to the node table
    public static void addNode(String anyNodeID, String anyXcoord, String anyYcoord, String anyFloor, String anyBuilding, String anyNodeType, String anyName) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyNode = conn.prepareStatement("INSERT INTO mapHNodes VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            addAnyNode.setString(1, anyNodeID);
            addAnyNode.setString(2, anyXcoord);
            addAnyNode.setString(3, anyYcoord);
            addAnyNode.setString(4, anyFloor);
            addAnyNode.setString(5, anyBuilding);
            addAnyNode.setString(6, anyNodeType);
            addAnyNode.setString(7, anyName);
            addAnyNode.setString(8, anyName);
            addAnyNode.setString(9, "Team H");

            addAnyNode.executeUpdate();
            System.out.println("Insert Node Successful for nodeID: " + anyNodeID);

            conn.commit();
            addAnyNode.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }

        // Add nodes to their ArrayLists
        mainDatabase.allNodes.add(new Node(anyNodeID, anyXcoord, anyYcoord, anyFloor, anyBuilding, anyNodeType, anyName, anyName, "Team H"));

    }

    ///////////////////////////////////////////////////////////////////////////////
    // Modify a node from the node table
    ///////////////////////////////////////////////////////////////////////////////

    // Modify item(s) from corresponding ArrayList

    // Modify item(s) from node table

    public static void modifyNode(String colAttr, String setCond, String anyNodeID) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String code= "UPDATE MapHNodes SET "+ colAttr+ " = ? WHERE nodeID = ?";

            PreparedStatement modifyAnyNode = conn.prepareStatement(code);

            modifyAnyNode.setString(1, setCond);
            modifyAnyNode.setString(2, anyNodeID);

            modifyAnyNode.executeUpdate();
            System.out.println("Update Node Successful!");

            conn.commit();
            modifyAnyNode.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();// end try
        }


        // Add nodes to their ArrayLists
        int indexOf = mainDatabase.allNodes.indexOf(new Node(anyNodeID,"0","0","","","","","",""));

        String tempID, tempX, tempY, tempFloor, tempBuilding, tempType, tempLong, tempShort, tempTeam = null;

        switch (colAttr) {

            case "nodeID":
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getFloor();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(setCond, tempX, tempY, tempFloor, tempBuilding, tempType, tempLong, tempShort, tempTeam));
                break;

            case "xCoord":
                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getFloor();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, setCond, tempY, tempFloor, tempBuilding, tempType, tempLong, tempShort, tempTeam));
                break;

            case "yCoord":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getFloor();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, setCond, tempFloor, tempBuilding, tempType, tempLong, tempShort, tempTeam));
                break;


            case "floor":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempBuilding = mainDatabase.allNodes.get(indexOf).getFloor();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, setCond, tempBuilding, tempType, tempLong, tempShort, tempTeam));
                break;


            case "building":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, setCond, tempType, tempLong, tempShort, tempTeam));
                break;


            case "nodeType":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getFloor();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, tempBuilding, setCond, tempLong, tempShort, tempTeam));
                break;

            case "longName":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getFloor();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, tempBuilding, tempType, setCond, tempShort, tempTeam));
                break;


            case "shortName":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getFloor();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, tempBuilding, tempType, tempLong, setCond, tempTeam));
                break;


            case "teamAssigned":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getFloor();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, tempBuilding, tempType, tempLong, tempShort, setCond));
                break;

            default:
                break;
        }

    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete a node from the node table
    ///////////////////////////////////////////////////////////////////////////////

    // Delete item(s) from corresponding ArrayList

    // Delete item(s) from node table

    public static void deleteNode(Node anyNode){

        String anyNodeID = anyNode.getID();

        try  {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();


            PreparedStatement deleteAnyNode = conn.prepareStatement("DELETE FROM mapHNodes WHERE nodeID = ?");

            // set the corresponding param
            deleteAnyNode.setString(1, anyNodeID);
            // execute the delete statement
            deleteAnyNode.executeUpdate();

            System.out.println("Delete Node Successful!");

            conn.commit();
            deleteAnyNode.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Add nodes to their ArrayLists
        int indexOf = mainDatabase.allNodes.indexOf(anyNode);

        mainDatabase.allNodes.remove(indexOf);

    }

    ///////////////////////////////////////////////////////////////////////////////
    // Query all nodes from the node table
    ///////////////////////////////////////////////////////////////////////////////
    public static void queryAllNodes() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectAllNodes = conn.createStatement();
            String allNodes = "SELECT * FROM MAPHNODES";
            ResultSet rsetAllNodes = selectAllNodes.executeQuery(allNodes);

            String strNodeID = "";
            String strXCoord = "";
            String strYCoord = "";
            String strFloor = "";
            String strBuilding = "";
            String strNodeType = "";
            String strLongName = "";
            String strShortName = "";
            String strTeamAssigned = "";

            System.out.println("");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-50s %-30s %-20s\n", "nodeID", "xcoord", "ycoord", "floor", "building", "nodeType", "longName", "shortName", "teamAssigned");

            //Process the results
            while (rsetAllNodes.next()) {
                strNodeID = rsetAllNodes.getString("nodeID");
                strXCoord = rsetAllNodes.getString("xcoord");
                strYCoord = rsetAllNodes.getString("ycoord");
                strFloor = rsetAllNodes.getString("floor");
                strBuilding = rsetAllNodes.getString("building");
                strNodeType = rsetAllNodes.getString("nodeType");
                strLongName = rsetAllNodes.getString("longName");
                strShortName = rsetAllNodes.getString("shortName");
                strTeamAssigned = rsetAllNodes.getString("teamAssigned");

                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s %-50s %-30s %-20s\n", strNodeID, strXCoord, strYCoord, strFloor, strBuilding, strNodeType, strLongName, strShortName, strTeamAssigned);
            } // End While

            conn.commit();

            rsetAllNodes.close();
            selectAllNodes.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    ///////////////////////////////////////////////////////////////////////////////
    // Read from Nodes CSV File and store columns in array lists
    ///////////////////////////////////////////////////////////////////////////////
    public static void readNodeCSV (String fname) {

        File nodefile = new File(fname);

        try {
            Scanner inputStreamNodes = new Scanner(nodefile);
            inputStreamNodes.nextLine();
            while (inputStreamNodes.hasNext()) {

                String nodeData = inputStreamNodes.nextLine();
                String[] nodeValues = nodeData.split(",");

                mainDatabase.allNodes.add(new Node(nodeValues[0], nodeValues[1], nodeValues[2], nodeValues[3], nodeValues[4], nodeValues[5], nodeValues[6], nodeValues[7], nodeValues[8]));

            }
            inputStreamNodes.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Write to a output Nodes csv file
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputNodesCSV() {
        String outNodesFileName = "outputNodesTeamH.csv";

        try {
            FileWriter fw1 = new FileWriter(outNodesFileName, false);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter pw1 = new PrintWriter(bw1);

            pw1.println("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName,teamAssigned");
            for (int j = 0; j < mainDatabase.allNodes.size(); j++) {


                pw1.println(mainDatabase.allNodes.get(j).getID() + "," +
                        mainDatabase.allNodes.get(j).getX()+ "," +
                        mainDatabase.allNodes.get(j).getY() + "," +
                        mainDatabase.allNodes.get(j).getFloor() + "," +
                        mainDatabase.allNodes.get(j).getBuilding() + "," +
                        mainDatabase.allNodes.get(j).getType() + "," +
                        mainDatabase.allNodes.get(j).getLongName() + "," +
                        mainDatabase.allNodes.get(j).getShortName()+ "," +
                        mainDatabase.allNodes.get(j).getTeam()
                );
                System.out.println(j + ": Node Record Saved!");
            }
            pw1.flush();
            pw1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
