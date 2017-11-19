package database;

import map.Node;

import java.sql.*;

public class nodeDatabase {

    private static final String JDBC_URL="jdbc:derby:teamHDB;create=true";
    private static Connection conn;



    ///////////////////////////////////////////////////////////////////////////////
    // Add a node function
    ///////////////////////////////////////////////////////////////////////////////

    // Add item(s) from corresponding ArrayList

    // Add new node to the node table

    public static void addNode(String anyNodeID, String anyXcoord, String anyYcoord, String anyFloor, String anyBuilding, String anyNodeType, String anyName) {
        try {
            conn = DriverManager.getConnection(JDBC_URL);
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
        //mainDatabase.allNodes.add(new Node(anyNodeID, anyXcoord, anyYcoord, anyFloor, anyBuilding, anyNodeType, anyName, anyName, "Team H"));//Todo talk to jack

    }

    ///////////////////////////////////////////////////////////////////////////////
    // Modify a node from the node table
    ///////////////////////////////////////////////////////////////////////////////

    // Modify item(s) from corresponding ArrayList

    // Modify item(s) from node table

    public static void modifyNode(String colAttr, String setCond, String anyNodeID) {
        try {
            conn = DriverManager.getConnection(JDBC_URL);
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
        int indexOf = mainDatabase.allNodes.indexOf(new Node(anyNodeID,"0","0","","","","",""));

        String tempID, tempX, tempY, tempFloor, tempBuilding, tempType, tempLong, tempShort, tempTeam = null;

        switch (colAttr) {

            case "nodeID":
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor().getDbMapping();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getBuilding();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                //tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(setCond, tempX, tempY, tempFloor, tempBuilding, tempType, tempLong, tempShort));
                break;

            case "xCoord":
                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor().getDbMapping();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getBuilding();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                //tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, setCond, tempY, tempFloor, tempBuilding, tempType, tempLong, tempShort));
                break;

            case "yCoord":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor().getDbMapping();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getBuilding();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                //tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, setCond, tempFloor, tempBuilding, tempType, tempLong, tempShort));
                break;


            case "floor":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempBuilding = mainDatabase.allNodes.get(indexOf).getBuilding();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                //tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, setCond, tempBuilding, tempType, tempLong, tempShort));
                break;


            case "building":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor().getDbMapping();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                //tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, setCond, tempType, tempLong, tempShort));
                break;


            case "nodeType":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor().getDbMapping();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getBuilding();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                //tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, tempBuilding, setCond, tempLong, tempShort));
                break;

            case "longName":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor().getDbMapping();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getBuilding();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempShort = mainDatabase.allNodes.get(indexOf).getShortName();
                //tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, tempBuilding, tempType, setCond, tempShort));
                break;


            case "shortName":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor().getDbMapping();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getBuilding();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                //tempTeam = mainDatabase.allNodes.get(indexOf).getTeam();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, tempBuilding, tempType, tempLong, setCond));
                break;


            case "teamAssigned":

                tempID = mainDatabase.allNodes.get(indexOf).getID();
                tempX = Integer.toString(mainDatabase.allNodes.get(indexOf).getX());
                tempY = Integer.toString(mainDatabase.allNodes.get(indexOf).getY());
                tempFloor = mainDatabase.allNodes.get(indexOf).getFloor().getDbMapping();
                tempBuilding = mainDatabase.allNodes.get(indexOf).getBuilding();
                tempType = mainDatabase.allNodes.get(indexOf).getType();
                tempLong = mainDatabase.allNodes.get(indexOf).getLongName();
                //tempShort = mainDatabase.allNodes.get(indexOf).getShortName();

                mainDatabase.allNodes.remove(indexOf);
                mainDatabase.allNodes.add(new Node(tempID, tempX, tempY, tempFloor, tempBuilding, tempType, tempLong, setCond));
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
            conn = DriverManager.getConnection(JDBC_URL);
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
            conn = DriverManager.getConnection(JDBC_URL);
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

}
