package database;

import map.Edge;
import map.Node;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class edgeDatabase {

    private static final String JDBC_URL_MAP = "jdbc:derby:hospitalMapDB;create=true";
    private static Connection conn;

    // All edges from the edge table in hospitalMapDB
    static ArrayList<Edge> allEdges = new ArrayList<>();

    // Getter for ArrayList of all Edges
    public static ArrayList<Edge> getEdges(){ return allEdges; }

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the edges
    ///////////////////////////////////////////////////////////////////////////////
    public static void createEdgeTable() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);

            DatabaseMetaData meta2 = conn.getMetaData();
            ResultSet res2 = meta2.getTables(null, null, "EDGES", null);

            // Edge table DNE just add edge table
            if (!res2.next()) {
                Statement stmtCreate2 = conn.createStatement();
                String createEdgesTable = ("CREATE TABLE edges" +
                        "(edgeID VARCHAR(30) PRIMARY KEY," +
                        "startNode VARCHAR(20)," +
                        "endNode VARCHAR(20))");

                int rsetCreate2 = stmtCreate2.executeUpdate(createEdgesTable);
                System.out.println("Create Edges table Successful!");

                conn.commit();
                stmtCreate2.close();
                conn.close();
            }
            // Edge table already exists delete and re-add
            else {
                Statement stmtDelete2 = conn.createStatement();
                String deleteNodesTable = ("DROP TABLE edges");
                int rsetDelete2 = stmtDelete2.executeUpdate(deleteNodesTable);
                System.out.println("Drop Edges table Successful!");
                stmtDelete2.close();

                Statement stmtCreate2 = conn.createStatement();
                String createEdgesTable = ("CREATE TABLE edges" +
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
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Insert into edges table using a prepared statement
    ///////////////////////////////////////////////////////////////////////////////
    public static void insertEdgesFromCSV() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement insertEdge = conn.prepareStatement("INSERT INTO edges VALUES (?, ?, ?)");

            for (int j = 1; j < allEdges.size(); j++) {

                insertEdge.setString(1, allEdges.get(j).getID());
                insertEdge.setString(2, allEdges.get(j).getNodeOne().getID());
                insertEdge.setString(3, allEdges.get(j).getNodeTwo().getID());

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
    // Add an edge function
    ///////////////////////////////////////////////////////////////////////////////
    public static void addEdge(Edge anyEdge) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement addAnyEdge = conn.prepareStatement("INSERT INTO edges VALUES (?, ?, ?)");

            addAnyEdge.setString(1, anyEdge.getID());
            addAnyEdge.setString(2, anyEdge.getNodeOne().getID());
            addAnyEdge.setString(3, anyEdge.getNodeTwo().getID());

            addAnyEdge.executeUpdate();
            System.out.println("Insert Edge Successful for edgeID: " + anyEdge.getID());

            conn.commit();
            addAnyEdge.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Modify an edge from the edge table
    ///////////////////////////////////////////////////////////////////////////////
    public static void modifyEdge(Edge anyEdge) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String strModDrop = "DELETE FROM edges WHERE edgeID = ?";
            String strModAdd = "INSERT INTO edges VALUES (?, ?, ?)";

            PreparedStatement modDropEdge = conn.prepareStatement(strModDrop);
            modDropEdge.setString(1, anyEdge.getID());

            modDropEdge.executeUpdate();
            conn.commit();
            modDropEdge.close();

            PreparedStatement modAddEdge = conn.prepareStatement(strModAdd);
            modAddEdge.setString(1, anyEdge.getID());
            modAddEdge.setString(2, anyEdge.getNodeOne().getID());
            modAddEdge.setString(3, anyEdge.getNodeTwo().getID());

            conn.commit();
            System.out.println("Update Edge Successful!");
            modAddEdge.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete an edge from the edge table
    ///////////////////////////////////////////////////////////////////////////////
    public static void deleteAnyEdge(Edge anyEdge) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String strDelEdge = "DELETE FROM edges WHERE edgeID = ?";

            PreparedStatement deleteAnyEdge = conn.prepareStatement(strDelEdge);

            deleteAnyEdge.setString(1, anyEdge.getID());

            deleteAnyEdge.executeUpdate();
            System.out.println("Delete Edge Successful");

            conn.commit();
            deleteAnyEdge.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Query all edges from the edge table
    ///////////////////////////////////////////////////////////////////////////////
    public static void queryAllEdges() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectAllEdges = conn.createStatement();
            String allEdges = "SELECT * FROM edges";
            ResultSet rsetAllEdges = selectAllEdges.executeQuery(allEdges);

            String strEdgeID;
            String strStartNode;
            String strEndNode;

            System.out.println("");
            System.out.printf("%-30s %-20s %-20s\n", "edgeID", "startNode", "endNode");

            //Process the results
            while (rsetAllEdges.next()) {
                strEdgeID = rsetAllEdges.getString("edgeID");
                strStartNode = rsetAllEdges.getString("startNode");
                strEndNode = rsetAllEdges.getString("endNode");

                System.out.printf("%-30s %-20s %-20s\n", strEdgeID, strStartNode, strEndNode);
            } // End While

            conn.commit();

            rsetAllEdges.close();
            selectAllEdges.close();
            conn.close();

        } // end try
        catch (SQLException e) {
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

                nodeOne = nodeDatabase.allNodes.indexOf(new Node(edgeValues[1], "-1", "-1", null, null, null, null, null, null));

                if (nodeOne < 0) {
                    System.out.println(nodeOne + "Error: invalid edge 1" + edgeValues[1]);
                } else {
                    tempOne = nodeDatabase.allNodes.get(nodeOne);
                }
                nodeTwo = nodeDatabase.allNodes.indexOf(new Node(edgeValues[2], "-1", "-1", null, null, null, null, null, null));
                if (nodeTwo < 0) {
                    System.out.println("Error: invalid edge 2");
                } else {
                    tempTwo = nodeDatabase.allNodes.get(nodeTwo);
                }
                if (tempOne != null && tempTwo != null) {
                    Edge edge = new Edge(edgeValues[0],tempOne,tempTwo);
                    allEdges.add(edge);
                } else {
                    System.out.println(" ");
                }
            }

            inputStreamEdges.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Write to a output Edges csv file
    ///////////////////////////////////////////////////////////////////////////////
    public static void outputEdgesCSV() {

        String outEdgesFileName = "outputEdges.csv";

        try {
            FileWriter fw2 = new FileWriter(outEdgesFileName, false);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            PrintWriter pw2 = new PrintWriter(bw2);

            pw2.println("edgeID,startNode,endNode");

            for (int j = 0; j < allEdges.size(); j++) {

                pw2.println(allEdges.get(j).getID() + "," +
                        allEdges.get(j).getNodeOne().getID() + "," +
                        allEdges.get(j).getNodeTwo().getID()
                );

                System.out.println(j + ": Edge Record Saved!");
            }
            pw2.flush();
            pw2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
