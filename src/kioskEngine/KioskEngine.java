/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/

package kioskEngine;

import map.HospitalMap;
import map.Node;
import exceptions.InvalidLoginException;
import service.FoodService;
import service.Service;
import service.Staff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class KioskEngine{
    private HospitalMap map;
    private HashMap<String, Staff> loginInfo;
    private HashMap<String, Service> availableServices;

    private static final String JDBC_URL="jdbc:derby:teamHDB;create=true";
    private static Connection conn;


    public KioskEngine(){
        loginInfo = new HashMap<>();
        map = new HospitalMap();
        availableServices = new HashMap<String, Service>();
        FoodService food = new FoodService();
        availableServices.put("Food",food);
    }

    public void addNode(Node node, ArrayList<Node> connections){
        for (Node connNode: connections) {
            node.addConnection(connNode);
        }
        map.addNode(node.getID(),node);

    }

    public void addNode(String anyNodeID, String anyXcoord, String anyYcoord, String anyFloor, String anyBuilding, String anyNodeType, String anyName, ArrayList<Node> connections) {
        try {
            //Open DB connection
            conn = DriverManager.getConnection(JDBC_URL);
            conn.setAutoCommit(false);
            conn.getMetaData();


            //Add Node to DB
            Node newNode = new Node(anyNodeID,anyName, anyNodeType, Integer.parseInt(anyXcoord),Integer.parseInt(anyYcoord),anyFloor,connections);
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


            for (Node connectTo: connections) {
                //Add Edge to DB
                String anyEdgeID = (anyNodeID + "_" + connectTo.getID());

                PreparedStatement addAnyEdge = conn.prepareStatement("INSERT INTO mapHEdges VALUES (?, ?, ?)");

                addAnyEdge.setString(1, anyEdgeID);
                addAnyEdge.setString(2, anyNodeID);
                addAnyEdge.setString(3, connectTo.getID());

                addAnyEdge.executeUpdate();
                System.out.println("Insert Edge Successful for edgeID: " + anyEdgeID);

                //Add revers edge to DB
                String anyReverseEdgeID = (connectTo.getID() + "_" + anyNodeID);

                PreparedStatement addAnyReverseEdge = conn.prepareStatement("INSERT INTO mapHEdges VALUES (?, ?, ?)");

                addAnyEdge.setString(1, anyEdgeID);
                addAnyEdge.setString(2, connectTo.getID());
                addAnyEdge.setString(3, anyNodeID);

                addAnyEdge.executeUpdate();
                System.out.println("Insert Reverse Successful for edgeID: " + anyEdgeID);

            }

            conn.commit();
            addAnyNode.close();
            conn.close();

            //add node to map in memory
            map.addNode(anyNodeID,newNode);


        } catch (Exception e) {
            System.out.println("DB Add Failed");
            e.printStackTrace();// end try
        }
    }

    public ArrayList<Node> findPath(Node start, Node end){
        Stack<Node> stack = map.findPath(start,end);
        ArrayList<Node> path = new ArrayList<Node>();
        for (Node node : stack) {
            path.add(node);
        }
        return path;
    }


    public void compleatRequest(Staff staffMem){
        staffMem.completeCurRec();
    }


    public boolean login(String username, String password) throws InvalidLoginException{
//        if(this.loginInfo.containsKey(username)){
//            if (this.loginInfo.get(username).getPassword().equals(password)) return true;
//            else throw new InvalidLoginException();
//        } else throw new InvalidLoginException();
        try
        {
            if(this.loginInfo.containsKey(username)){
                if(this.loginInfo.get(username).getPassword().equals(password)) return true;
                else throw new InvalidLoginException();
            } else throw new InvalidLoginException();
        } catch (InvalidLoginException e) {
            return false;
        }
    }

    public HospitalMap getMap() {
        return map;
    }

    public HashMap<String, Staff> getLoginInfo() {
        return loginInfo;
    }

    public void addStaffLogin(Staff staff, String serviceType){
        loginInfo.put(staff.getUsername(), staff);
        availableServices.get(serviceType).addPersonnel(staff);
    }

    public Staff getStaff(String username){
        return loginInfo.get(username);
    }


    public Service getService(String type) {
        if (type.equals("Food"))
            return availableServices.get(type);
        else
            return null;
    }
}
