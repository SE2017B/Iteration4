package database;

import map.Node;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class edgeDatabase {

    private static final String JDBC_URL_MAP = "jdbc:derby:hospitalMapDB;create=true";
    private static Connection conn;

    ///////////////////////////////////////////////////////////////////////////////
    // Create a table for the edges
    ///////////////////////////////////////////////////////////////////////////////

    public static void createEdgeTable() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
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
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Insert into edges table using a prepared statement
    ///////////////////////////////////////////////////////////////////////////////
    public static void insertEdgesFromCSV() {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement insertEdge = conn.prepareStatement("INSERT INTO mapHEdges VALUES (?, ?, ?)");

            for (int j = 1; j < mainDatabase.edgeID.toArray().length; j++) {

                insertEdge.setString(1, mainDatabase.edgeID.toArray()[j].toString());
                insertEdge.setString(2, mainDatabase.startNode.toArray()[j].toString());
                insertEdge.setString(3, mainDatabase.endNode.toArray()[j].toString());

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

    // Add item(s) from corresponding ArrayList

    // Add new edge to the edge table

    public static void addEdge(String anyStartNode, String anyEndNode) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String anyEdgeID = (anyStartNode + "_" + anyEndNode);

            PreparedStatement addAnyEdge = conn.prepareStatement("INSERT INTO mapHEdges VALUES (?, ?, ?)");

            addAnyEdge.setString(1, anyEdgeID);
            addAnyEdge.setString(2, anyStartNode);
            addAnyEdge.setString(3, anyEndNode);

            addAnyEdge.executeUpdate();
            System.out.println("Insert Edge Successful for edgeID: " + anyEdgeID);

            conn.commit();
            addAnyEdge.close();
            conn.close();

            // Add nodes to their ArrayLists
            mainDatabase.edgeID.add(anyEdgeID);
            mainDatabase.startNode.add(anyStartNode);
            mainDatabase.endNode.add(anyEndNode);

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Modify an edge from the edge table
    ///////////////////////////////////////////////////////////////////////////////

    // Modify item(s) from corresponding ArrayList

    // Modify item(s) from edge table

    public static void modifyEdge(String colAttr, String setCond, String anyEdgeID) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String code= "UPDATE MapHEdges SET "+ colAttr+ " = ? WHERE edgeID = ?";

            PreparedStatement modifyAnyEdge = conn.prepareStatement(code);

            modifyAnyEdge.setString(1, setCond);
            modifyAnyEdge.setString(2, anyEdgeID);

            modifyAnyEdge.executeUpdate();
            System.out.println("Update Edge Successful!");

            conn.commit();
            modifyAnyEdge.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }

        // Add nodes to their ArrayLists
        int indexOf = mainDatabase.edgeID.indexOf(anyEdgeID);

        switch (colAttr) {

            case "edgeID":
                mainDatabase.edgeID.remove(indexOf);
                mainDatabase.edgeID.add(indexOf, setCond);
                break;

            case "startNode":
                mainDatabase.startNode.remove(indexOf);
                mainDatabase.startNode.add(indexOf, setCond);
                break;

            case "endNode":
                mainDatabase.endNode.remove(indexOf);
                mainDatabase.endNode.add(indexOf, setCond);
                break;

            default:
                break;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    // Delete an edge from the edge table
    ///////////////////////////////////////////////////////////////////////////////

    // Delete item(s) from corresponding ArrayList

    // Delete item(s) from edge table
    public static void deleteAnyEdge(String delCond) {
        try {
            conn = DriverManager.getConnection(JDBC_URL_MAP);
            conn.setAutoCommit(false);
            conn.getMetaData();

            PreparedStatement deleteAnyEdge = conn.prepareStatement("DELETE FROM mapHEdges WHERE edgeID = ?");

            deleteAnyEdge.setString(1, delCond);

            deleteAnyEdge.executeUpdate();
            System.out.println("Delete Edge Successful");

            conn.commit();
            deleteAnyEdge.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }

        int indexOf2 = mainDatabase.edgeID.indexOf(delCond);

        mainDatabase.edgeID.remove(indexOf2);
        mainDatabase.startNode.remove(indexOf2);
        mainDatabase.endNode.remove(indexOf2);

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
            String allEdges = "SELECT * FROM MAPHEDGES";
            ResultSet rsetAllEdges = selectAllEdges.executeQuery(allEdges);

            String strEdgeID = "";
            String strStartNode = "";
            String strEndNode = "";

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

                mainDatabase.edgeID.add(edgeValues[0]);
                mainDatabase.startNode.add(edgeValues[1]);
                mainDatabase.endNode.add(edgeValues[2]);

                nodeOne = mainDatabase.allNodes.indexOf(new Node(edgeValues[1], "-1", "-1", null, null, null, null, null, null));

                if (nodeOne < 0) {
                    System.out.println("Error: invalid edge");
                } else {
                    tempOne = mainDatabase.allNodes.get(nodeOne);
                }
                nodeTwo = mainDatabase.allNodes.indexOf(new Node(edgeValues[2], "-1", "-1", null, null, null, null, null, null));
                if (nodeTwo < 0) {
                    System.out.println("Error: invalid edge");
                } else {
                    tempTwo = mainDatabase.allNodes.get(nodeTwo);
                }
                if (tempOne != null && tempTwo != null) {
                    tempOne.addConnection(tempTwo);
                    tempTwo.addConnection(tempOne);
                } else {
                    System.out.println(" ");
                }
                mainDatabase.allNodes.get(nodeOne);
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

        String outEdgesFileName = "outputEdgesTeamH.csv";

        try {
            FileWriter fw2 = new FileWriter(outEdgesFileName, false);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            PrintWriter pw2 = new PrintWriter(bw2);

            pw2.println("edgeID,startNode,endNode");

            for (int j = 0; j < mainDatabase.edgeID.toArray().length; j++) {

                pw2.println(mainDatabase.edgeID.toArray()[j].toString() + "," +
                        mainDatabase.startNode.toArray()[j].toString() + "," +
                        mainDatabase.endNode.toArray()[j].toString()
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
