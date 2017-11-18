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

    private static final String JDBC_URL_MAP="jdbc:derby:hospitalMapDB;create=true";
    private static final String JDBC_URL_STAFF="jdbc:derby:hospitalStaffDB;create=true";

    private static Connection conn1;
    private static Connection conn2;

    static ArrayList<String>edgeID=new ArrayList<String>();
    static ArrayList<String>startNode=new ArrayList<String>();
    static ArrayList<String>endNode=new ArrayList<String>();

    static ArrayList<Node>allNodes=new ArrayList<Node>();
    //static ArrayList<Edges>allEdges=new ArrayList<Edges>();

    public mainDatabase() throws SQLException {

        // Connect to embedded database
        try {
            conn1 = DriverManager.getConnection(JDBC_URL_MAP);
            conn1.setAutoCommit(false);

            if (conn1 != null) {
                System.out.println("Hospital Map Database Connected!");
            }
            conn1.close();
            } catch (SQLException e) {
                Logger.getLogger(mainDatabase.class.getName()).log(Level.SEVERE, null, e);
            }
        // Connect to embedded database
        try {
            conn2 = DriverManager.getConnection(JDBC_URL_STAFF);
            conn2.setAutoCommit(false);

            if (conn2 != null) {
                System.out.println("Hospital Staff Database Connected!");
            }
            conn2.close();
        } catch (SQLException e) {
            Logger.getLogger(mainDatabase.class.getName()).log(Level.SEVERE, null, e);
        }
        }

    public static ArrayList<Node> getNodes(){
        return allNodes;
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

        nodeDatabase.readNodeCSV("MapHnodes.csv");
        nodeDatabase.readNodeCSV("MapWnodes.csv");

        edgeDatabase.readEdgesCSV("MapHedges.csv");
        edgeDatabase.readEdgesCSV("MapWedges.csv");

        mainDatabase testConnection1 = new mainDatabase();


        nodeDatabase.outputNodesCSV();
        edgeDatabase.outputEdgesCSV();
    }
}