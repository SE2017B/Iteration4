package database;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import map.Node;

public class mainDatabase {

    private static final String DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL="jdbc:derby:teamHDB;create=true";

    private static Connection conn;

    // Variables for storing nodes after reading csv file
    //String[] nodeValues;

    /*
    static ArrayList<String>nodeID=new ArrayList<String>();
    static ArrayList<String>xCoord=new ArrayList<String>();
    static ArrayList<String>yCoord=new ArrayList<String>();
    static ArrayList<String>floor=new ArrayList<String>();
    static ArrayList<String>building=new ArrayList<String>();
    static ArrayList<String>nodeType=new ArrayList<String>();
    static ArrayList<String>longName=new ArrayList<String>();
    static ArrayList<String>shortName=new ArrayList<String>();
    static ArrayList<String>teamAssigned=new ArrayList<String>();
    */

    // Variables for storing edges after reading csv file
    //String[] edgeValues;

    static ArrayList<String>edgeID=new ArrayList<String>();
    static ArrayList<String>startNode=new ArrayList<String>();
    static ArrayList<String>endNode=new ArrayList<String>();

    static ArrayList<Node>allNodes=new ArrayList<Node>();

    ///////////////////////////////////////////////////////////////////////////////
    // Write to a output Nodes csv file
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputNodesCSV() {
        String outNodesFileName = "outputNodesTeamH.csv";

        try {
            FileWriter fw1 = new FileWriter(outNodesFileName, false);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            PrintWriter pw1 = new PrintWriter(bw1);

            for (int j = 0; j < allNodes.size(); j++) {


                pw1.println(allNodes.get(j).getID() + "," +
                        allNodes.get(j).getX()+ "," +
                        allNodes.get(j).getY() + "," +
                        allNodes.get(j).getFloor() + "," +
                        allNodes.get(j).getBuilding() + "," +
                        allNodes.get(j).getType() + "," +
                        allNodes.get(j).getLongName() + "," +
                        allNodes.get(j).getShortName()+ "," +
                        allNodes.get(j).getTeam()
                );
                System.out.println(j + ": Node Record Saved!");
            }
            pw1.flush();
            pw1.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Write to a output Edges csv file
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputEdgesCSV() {

        String outEdgesFileName = "outputEdgesTeamH.csv";

        try {
            FileWriter fw2 = new FileWriter(outEdgesFileName, false);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            PrintWriter pw2 = new PrintWriter(bw2);

            for (int j = 0; j < edgeID.toArray().length; j++) {

                pw2.println(edgeID.toArray()[j].toString() + "," +
                        startNode.toArray()[j].toString() + "," +
                        endNode.toArray()[j].toString()
                );

                System.out.println(j + ": Edge Record Saved!");
            }
            pw2.flush();
            pw2.close();
        } catch (IOException e) {
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


                    allNodes.add(new Node(nodeValues[0], nodeValues[1], nodeValues[2], nodeValues[3], nodeValues[4], nodeValues[5], nodeValues[6], nodeValues[7], nodeValues[8]));
                    /*
                    nodeID.add(nodeValues[0]);
                    xCoord.add(nodeValues[1]);
                    yCoord.add(nodeValues[2]);
                    floor.add(nodeValues[3]);
                    building.add(nodeValues[4]);
                    nodeType.add(nodeValues[5]);
                    longName.add(nodeValues[6]);
                    shortName.add(nodeValues[7]);
                    teamAssigned.add(nodeValues[8]);
                    */

                }
                inputStreamNodes.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ///////////////////////////////////////////////////////////////////////////////
        // Read from Edges CSV File and store columns in array lists
        ///////////////////////////////////////////////////////////////////////////////
        public static void readEdgesCSV(String fname) {

            File edgesfile = new File(fname);

            try {
                Scanner inputStreamEdges = new Scanner(edgesfile);
                inputStreamEdges.nextLine();
                while (inputStreamEdges.hasNext()) {

                    String edgeData = inputStreamEdges.nextLine();
                    int nodeOne = 0, nodeTwo = 0;
                    Node tempOne = null, tempTwo = null;
                    String[] edgeValues = edgeData.split(",");

                    edgeID.add(edgeValues[0]);
                    startNode.add(edgeValues[1]);
                    endNode.add(edgeValues[2]);

                    nodeOne = allNodes.indexOf(new Node(edgeValues[1], "-1", "-1", null, null, null, null, null, null));

                    if (nodeOne < 0) {
                        System.out.println("Error: invalid edge");
                    } else {
                        tempOne = allNodes.get(nodeOne);
                    }
                    nodeTwo = allNodes.indexOf(new Node(edgeValues[2], "-1", "-1", null, null, null, null, null, null));
                    if (nodeTwo < 0) {
                        System.out.println("Error: invalid edge");
                    } else {
                        tempTwo = allNodes.get(nodeTwo);
                    }
                    if (tempOne != null && tempTwo != null) {
                        tempOne.addConnection(tempTwo);
                        tempTwo.addConnection(tempOne);
                    } else {
                        System.out.println(" ");
                    }
                    allNodes.get(nodeOne);
                }

                inputStreamEdges.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ///////////////////////////////////////////////////////////////////////////////
        // Create a table for the nodes
        ///////////////////////////////////////////////////////////////////////////////
        public mainDatabase() throws SQLException {

            // Connect to embedded database
            try {
                conn = DriverManager.getConnection(JDBC_URL);
                conn.setAutoCommit(false);

                if (conn != null) {
                    System.out.println("Connected!");
                }
                conn.close();
            } catch (SQLException e) {
                Logger.getLogger(mainDatabase.class.getName()).log(Level.SEVERE, null, e);
            }

            try {
            this.conn = DriverManager.getConnection(JDBC_URL);
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

        ///////////////////////////////////////////////////////////////////////////////
        // Create a table for the edges
        ///////////////////////////////////////////////////////////////////////////////
        try {
            this.conn = DriverManager.getConnection(JDBC_URL);
            conn.setAutoCommit(false);

            DatabaseMetaData meta2 = conn.getMetaData();
            ResultSet res2 = meta2.getTables(null, null, "MAPHEDGES", null);

            if (!res2.next()) {
                Statement stmtCreate2 = conn.createStatement();
                String createEdgesTable = ("CREATE TABLE mapHEdges" +
                        "(edgeID VARCHAR(30) PRIMARY KEY," +
                        "startNode VARCHAR(20)," +
                        "endNode VARCHAR(20))");

                int rsetCreate2 = stmtCreate2.executeUpdate(createEdgesTable);
                System.out.println("Create Edges table Successful!");

                conn.commit();
                stmtCreate2.close();
                conn.close();
            } else {
                Statement stmtDelete2 = conn.createStatement();
                String deleteNodesTable = ("DROP TABLE mapHEdges");
                int rsetDelete2 = stmtDelete2.executeUpdate(deleteNodesTable);
                stmtDelete2.close();

                Statement stmtCreate2 = conn.createStatement();
                String createEdgesTable = ("CREATE TABLE mapHEdges" +
                        "(edgeID VARCHAR(30)," +
                        "startNode VARCHAR(20)," +
                        "endNode VARCHAR(20))");

                int rsetCreate2 = stmtCreate2.executeUpdate(createEdgesTable);
                System.out.println("Create Edges table Successful!");

                conn.commit();
                stmtCreate2.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ///////////////////////////////////////////////////////////////////////////////
        // Insert into nodes table using a prepared statement
        ///////////////////////////////////////////////////////////////////////////////
        try {
            this.conn = DriverManager.getConnection(JDBC_URL);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement insertNode = conn.prepareStatement("INSERT INTO mapHNodes VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            for (int j = 1; j < allNodes.get(j).getID().length(); j++) {

                insertNode.setString(1, allNodes.get(j).getID());
                insertNode.setString(2, Integer.toString(allNodes.get(j).getX()));
                insertNode.setString(3, Integer.toString(allNodes.get(j).getY()));
                insertNode.setString(4, allNodes.get(j).getFloor());
                insertNode.setString(5, allNodes.get(j).getBuilding());
                insertNode.setString(6, allNodes.get(j).getType());
                insertNode.setString(7, allNodes.get(j).getLongName());
                insertNode.setString(8, allNodes.get(j).getShortName());
                insertNode.setString(9, allNodes.get(j).getTeam());

                insertNode.executeUpdate();
                System.out.println(j + ": Insert Node Successful!");
            }

            conn.commit();
            insertNode.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }

        ///////////////////////////////////////////////////////////////////////////////
        // Insert into edges table using a prepared statement
        ///////////////////////////////////////////////////////////////////////////////
        try {
            this.conn = DriverManager.getConnection(JDBC_URL);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement insertEdge = conn.prepareStatement("INSERT INTO mapHEdges VALUES (?, ?, ?)");

            for (int j = 1; j < edgeID.toArray().length; j++) {

                insertEdge.setString(1, edgeID.toArray()[j].toString());
                insertEdge.setString(2, startNode.toArray()[j].toString());
                insertEdge.setString(3, endNode.toArray()[j].toString());

                insertEdge.executeUpdate();
                System.out.println(j + ": Insert Edge Successful!");
            }

            conn.commit();
            insertEdge.close();
            conn.close();

        } // end try
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Main Database Function
    ///////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws SQLException {

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        readNodeCSV("MapHnodes.csv");
        readNodeCSV("MapWnodes.csv");

        readEdgesCSV("MapHedges.csv");
        readEdgesCSV("MapWedges.csv");

        mainDatabase testConnection1 = new mainDatabase();



        nodeDatabase.queryAllNodes();
        nodeDatabase.addNode("HHALL05002", "2000", "2000", "2", "Tower", "HALL", "Hallway Connector 50 Floor 2");
        nodeDatabase.queryAllNodes();
        nodeDatabase.modifyNode("floor", "4", "HBATH00203");
        nodeDatabase.queryAllNodes();
        nodeDatabase.deleteNode(new Node("HHALL05002", "2000", "2000", "2", "Tower", "HALL", "Hallway Connector 50 Floor 2","Hallway Connector 50 Floor 2", "Team H"));
        nodeDatabase.queryAllNodes();

        edgeDatabase.queryAllEdges();
        edgeDatabase.addEdge("HHALL05002", "HHALL05102");
        edgeDatabase.queryAllEdges();
        edgeDatabase.modifyEdge("startNode","HHALL07002", "HHALL00102_HHALL00202");
        edgeDatabase.queryAllEdges();
        edgeDatabase.deleteAnyEdge("HHALL05002_HHALL05102");
        edgeDatabase.queryAllEdges();

        outputNodesCSV();
        outputEdgesCSV();
    }
}