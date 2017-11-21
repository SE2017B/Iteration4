package database;

import map.Node;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class nodeDatabase {

    private static final String JDBC_URL_MAP="jdbc:derby:hospitalMapDB;create=true";
    private static Connection conn;

    public static int hallCounter;
    public static int elevCounter;
    public static int restCounter;
    public static int staiCounter;
    public static int deptCounter;
    public static int labsCounter;
    public static int infoCounter;
    public static int confCounter;
    public static int exitCounter;
    public static int retlCounter;
    public static int servCounter;

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the nodes
    ///////////////////////////////////////////////////////////////////////////////

    public static void createNodeTable() {

        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, "NODES", null);

            if (!res.next()) {
                Statement stmtCreate1 = conn.createStatement();
                String createNodesTable = ("CREATE TABLE nodes" +
                        "(nodeID VARCHAR(50) PRIMARY KEY," +
                        "xCoord VARCHAR(50)," +
                        "yCoord VARCHAR(50)," +
                        "floor VARCHAR(50)," +
                        "building VARCHAR(50)," +
                        "nodeType VARCHAR(50)," +
                        "longName VARCHAR(75)," +
                        "shortName VARCHAR(50)," +
                        "teamAssigned VARCHAR(50))");

                int rsetCreate1 = stmtCreate1.executeUpdate(createNodesTable);
                System.out.println("Create Nodes table Successful!");

                conn.commit();
                stmtCreate1.close();
                conn.close();
            } else {
                Statement stmtDelete1 = conn.createStatement();
                String deleteNodesTable = ("DROP TABLE nodes");
                int rsetDelete1 = stmtDelete1.executeUpdate(deleteNodesTable);
                System.out.println("Drop Node Table Successful!");
                stmtDelete1.close();

                Statement stmtCreate1 = conn.createStatement();
                String createNodesTable = ("CREATE TABLE nodes" +
                        "(nodeID VARCHAR(50) PRIMARY KEY," +
                        "xCoord VARCHAR(50)," +
                        "yCoord VARCHAR(50)," +
                        "floor VARCHAR(50)," +
                        "building VARCHAR(50)," +
                        "nodeType VARCHAR(50)," +
                        "longName VARCHAR(75)," +
                        "shortName VARCHAR(50)," +
                        "teamAssigned VARCHAR(50))");

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

            PreparedStatement insertNode = conn.prepareStatement("INSERT INTO nodes VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");


            for (int j = 1; j < mainDatabase.allNodes.size(); j++) {

                insertNode.setString(1, mainDatabase.allNodes.get(j).getID());
                insertNode.setString(2, Integer.toString(mainDatabase.allNodes.get(j).getX()));
                insertNode.setString(3, Integer.toString(mainDatabase.allNodes.get(j).getY()));
                insertNode.setString(4, mainDatabase.allNodes.get(j).getFloor().getDbMapping());
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
    public static void addNode(Node anyNode) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyNode = conn.prepareStatement("INSERT INTO nodes VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            addAnyNode.setString(1, anyNode.getID());
            addAnyNode.setString(2, anyNode.getXString());
            addAnyNode.setString(3, anyNode.getYString());
            addAnyNode.setString(4, anyNode.getFloor().getDbMapping());
            addAnyNode.setString(5, anyNode.getBuilding());
            addAnyNode.setString(6, anyNode.getType());
            addAnyNode.setString(7, anyNode.getLongName());
            addAnyNode.setString(8, anyNode.getShortName());
            addAnyNode.setString(9, anyNode.getTeam());

            addAnyNode.executeUpdate();
            System.out.println("Insert Node Successful for nodeID: " + anyNode.getID());

            conn.commit();
            addAnyNode.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Modify a node from the node table
    ///////////////////////////////////////////////////////////////////////////////

    // Modify item(s) from node table
    public static void modifyNode(Node anyNode) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String strModDrop = "DELETE FROM nodes WHERE nodeID = ?";
            String strModAdd = "INSERT INTO nodes VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement modDropNode = conn.prepareStatement(strModDrop);
            modDropNode.setString(1, anyNode.getID());
            modDropNode.executeUpdate();
            conn.commit();
            modDropNode.close();

            PreparedStatement modAddNode = conn.prepareStatement(strModAdd);
            modAddNode.setString(1, anyNode.getID());
            modAddNode.setString(2, anyNode.getXString());
            modAddNode.setString(3, anyNode.getYString());
            modAddNode.setString(4, anyNode.getFloor().getDbMapping());
            modAddNode.setString(5, anyNode.getBuilding());
            modAddNode.setString(6, anyNode.getType());
            modAddNode.setString(7, anyNode.getLongName());
            modAddNode.setString(8, anyNode.getShortName());
            modAddNode.setString(9, anyNode.getTeam());
            modAddNode.executeUpdate();
            conn.commit();
            System.out.println("Update Node Successful!");
            modAddNode.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
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

            PreparedStatement deleteAnyNode = conn.prepareStatement("DELETE FROM nodes WHERE nodeID = ?");

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
            String allNodes = "SELECT * FROM NODES";
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
    // Find how many Hall Nodes and set hall counter
    ///////////////////////////////////////////////////////////////////////////////
    public static void cntNodes() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement cntAllType = conn.createStatement();
            String strCntNodes = "SELECT nodeType, COUNT(nodeType) AS type FROM nodes GROUP BY nodeType";
            ResultSet rsetCntNodesHall = cntAllType.executeQuery(strCntNodes);

            String aType = "";
            int numHall = 0;
            System.out.printf("%-20s %-20s\n", "nodeType", "COUNT(nodeType)");

            //Process the results
            while (rsetCntNodesHall.next()) {
                aType = rsetCntNodesHall.getString("nodeType");
                numHall = rsetCntNodesHall.getInt("type");

                switch (aType) {
                    case "REST":
                        restCounter = numHall;
                        break;
                    case "CONF":
                        confCounter = numHall;
                        break;
                    case "DEPT":
                        deptCounter = numHall;
                        break;
                    case "ELEV":
                        elevCounter = numHall;
                        break;
                    case "EXIT":
                        exitCounter = numHall;
                        break;
                    case "HALL":
                        hallCounter = numHall;
                        break;
                    case "INFO":
                        infoCounter = numHall;
                        break;
                    case "LABS":
                        labsCounter = numHall;
                        break;
                    case "RETL":
                        retlCounter = numHall;
                        break;
                    case "SERV":
                        servCounter = numHall;
                        break;
                    case "STAI":
                        staiCounter = numHall;
                }

                System.out.printf("%-20s %-20s\n", aType, numHall);
            } // End While

            conn.commit();
            rsetCntNodesHall.close();
            cntAllType.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getHallCounter() {
        int temp = hallCounter;
        hallCounter++;
        return temp;
    }

    public int getRestCounter() {
        int temp = restCounter;
        restCounter++;
        return temp;
    }

    public int getRetlCounter() {
        int temp = retlCounter;
        retlCounter++;
        return temp;
    }

    public int getElevCounter() {
        int temp = elevCounter;
        elevCounter++;
        return temp;
    }

    public int getStaiCounter() {
        int temp = staiCounter;
        staiCounter++;
        return temp;
    }

    public int getDeptCounter() {
        int temp = deptCounter;
        deptCounter++;
        return temp;
    }

    public int getLabsCounter() {
        int temp = labsCounter;
        labsCounter++;
        return temp;
    }

    public int getInfoCounter() {
        int temp = infoCounter;
        infoCounter++;
        return temp;
    }

    public int getConfCounter() {
        int temp = confCounter;
        confCounter++;
        return temp;
    }

    public int getExitCounter() {
        int temp = exitCounter;
        exitCounter++;
        return temp;
    }

    public int getServCounter() {
        int temp = servCounter;
        servCounter++;
        return temp;
    }

    public int getNodeID(String anyType) {
        switch (anyType) {
            case "REST":
                return getRestCounter();
            case "CONF":
                return getConfCounter();
            case "DEPT":
                return getDeptCounter();
            case "ELEV":
                return getElevCounter();
            case "EXIT":
                return getExitCounter();
            case "HALL":
                return getHallCounter();
            case "INFO":
                return getInfoCounter();
            case "LABS":
                return getLabsCounter();
            case "RETL":
                return getRetlCounter();
            case "SERV":
                return getServCounter();
            case "STAI":
                return getStaiCounter();
        }
        return 0;
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
        String outNodesFileName = "outputNodes.csv";

        try {
            FileWriter fw1 = new FileWriter(outNodesFileName, false);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter pw1 = new PrintWriter(bw1);

            pw1.println("nodeID,xcoord,ycoord,floor,building,nodeType,longName,shortName,teamAssigned");
            for (int j = 0; j < mainDatabase.allNodes.size(); j++) {


                pw1.println(mainDatabase.allNodes.get(j).getID() + "," +
                        mainDatabase.allNodes.get(j).getX()+ "," +
                        mainDatabase.allNodes.get(j).getY() + "," +
                        mainDatabase.allNodes.get(j).getFloor().getDbMapping() + "," +
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
