package database;

import a_star.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class moduleDatabase {
    private static final String DRIVER="org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL="jdbc:derby:teamHDB;create=true";

    Connection conn;

    String[] nodeValues;

    public static void main(String[] args) throws SQLException {

        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        moduleDatabase testConnection = new moduleDatabase();
        testConnection.Test();
    }
    public void deleteNode(String nodeid, String TableName){
        String sql = "DELETE FROM " + TableName + " WHERE nodeID = ?";

        try  {
            this.conn = DriverManager.getConnection(JDBC_URL);
            PreparedStatement newpst = conn.prepareStatement(sql);

            // set the corresponding param
            newpst.setString(1, nodeid);
            // execute the delete statement
            newpst.executeUpdate();

            System.out.println("Delete Node Successful");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void LoadNode(Node n){

    }
    //refactor methods
    //method to connect to the database
    private void Connect(){
        try {

            this.conn = DriverManager.getConnection(JDBC_URL);
            conn.setAutoCommit(false);

            if (this.conn != null) {
                System.out.println("Connected!");
            }
            conn.close();
        }

        catch (SQLException e) {
            Logger.getLogger(mainDatabase.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    //read nodes from csv file
    private ArrayList<ArrayList<String>> readCSV(String readFile){
        ArrayList<ArrayList<String>> ans= new ArrayList<ArrayList<String>>();
        //String nodeFile = "MapHnodes.csv";
        File nodefile = new File(readFile);

        try {
            Scanner inputStreamNodes = new Scanner(nodefile);
            while (inputStreamNodes.hasNext()) {
                ArrayList<String> value = new ArrayList<String>();

                String nodeData = inputStreamNodes.nextLine();
                nodeValues = nodeData.split(",");
                //loop through string and add to array
                for(String s : nodeValues){
                    value.add(s);
                }
                ans.add(value);
            }
            inputStreamNodes.close();
            System.out.println("Successfully read csv");
            //return value
            return ans;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ans;// should probably modify this later
    }
    private void CreateTable(String Name, ArrayList<ArrayList<String>> values){
        try {
            this.conn = DriverManager.getConnection(JDBC_URL);
            conn.setAutoCommit(false);

            DatabaseMetaData meta = conn.getMetaData();
            ResultSet res = meta.getTables(null, null, Name, null);

            if (!res.next()) {
                Statement stmtCreate1 = conn.createStatement();
                String code="CREATE TABLE " + Name + "(";
                for(int i=0; i<values.get(0).size();i++ ){
                    code += values.get(0).get(i)+" VARCHAR(50)";
                    //add comma
                    if(i<values.get(0).size()-1){
                        code+=",";
                    }
                }
                code+=")";

                System.out.println("generated code is");
                System.out.println(code);

                String createNodesTable = (code);

                int rsetCreate1 = stmtCreate1.executeUpdate(createNodesTable);
                System.out.println("Create Nodes table Successful!");

                conn.commit();
                stmtCreate1.close();
                conn.close();
            } else {
                Statement stmtDelete1 = conn.createStatement();
                String deleteNodesTable = ("DROP TABLE "+Name);
                int rsetDelete1 = stmtDelete1.executeUpdate(deleteNodesTable);
                stmtDelete1.close();

                Statement stmtCreate1 = conn.createStatement();
                String code="CREATE TABLE " + Name + "(" ;
                for(int i=0; i<values.get(0).size();i++ ){
                    code += values.get(0).get(i)+" VARCHAR(20)";
                    //add comma
                    if(i<values.get(0).size()-1){
                        code+=",";
                    }
                }
                code+=")";

                String createNodesTable = (code);

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
    public void InserttoTable(ArrayList<ArrayList<String>> values, String Table){
        try {

            this.conn = DriverManager.getConnection(JDBC_URL);
            conn.setAutoCommit(false);
            conn.getMetaData();

            String Statement = "INSERT INTO " + Table + " VALUES (";
            //loop through values[0] and add ? to string to be used for prepared statement
            for(int i=0;i<values.get(0).size();i++){
                Statement+= "?";
                //add a comma if not last value
                if(i<values.get(0).size()-1){
                    Statement+= ",";
                }
            }
            //close bracket
            Statement+= ")";


            System.out.println(Statement);

            PreparedStatement insertThing = conn.prepareStatement(Statement);
            //now add all values
            for(int j=1; j<values.size(); j++){
                for(int i=0; i<values.get(j).size(); i++){
                    insertThing.setString((i+1),values.get(j).get(i).toString());
                }
                insertThing.executeUpdate();
                System.out.println(j + ": Insert Node Successful!");
            }

            conn.commit();
            insertThing.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }
    private void printTable(String TableName){
        try{
            this.conn = DriverManager.getConnection(JDBC_URL);
            conn.setAutoCommit(false);
            conn.getMetaData();

            Statement selectAllNodes = conn.createStatement();
            String allNodes = "SELECT * FROM "+ TableName;
            ResultSet rsetAllNodes = selectAllNodes.executeQuery(allNodes);

            ResultSetMetaData rsmd = rsetAllNodes.getMetaData();
            int count = rsmd.getColumnCount(); //get the number of columns

            //print out title
            ArrayList<String> Columns = new  ArrayList<String>();

            for(int i=0;i<count;i++){
                Columns.add(rsmd.getColumnName(i+1));
                System.out.printf("%-20s" , rsmd.getColumnName(i+1));
            }
            System.out.printf("\n");

            //now print colunms
            ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();

            while(rsetAllNodes.next()){
                for(String s : Columns){
                    String v = rsetAllNodes.getString(s);
                    //ArrayList<String> value = new ArrayList<String>();
                    //value.add(rsetAllNodes.getString(s));
                    System.out.printf("%-50s", v);
                }
                System.out.printf("\n");
            }


            conn.commit();

            rsetAllNodes.close();
            selectAllNodes.close();
            conn.close();

        }catch (Exception e) {
            e.printStackTrace();// end try
        }

    }

    private void Test(){
        //test out refactored code
        System.out.println("Now Testing!!!!!!!");
        Connect();
        ArrayList<ArrayList<String>> values = readCSV("MapHnodes.csv");
        //CreateTable("TestTable1",values);
        InserttoTable(values,"TestTable1");
        printTable("TestTable1");
        deleteNode("HHALL00302","TestTable1");
    }

}
