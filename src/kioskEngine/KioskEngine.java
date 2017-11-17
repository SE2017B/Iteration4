/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/

package kioskEngine;

import database.edgeDatabase;
import database.nodeDatabase;
import javafx.collections.ObservableList;
import map.*;
import exceptions.InvalidLoginException;
import service.FoodService;
import service.Service;
import service.Staff;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
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
        availableServices = new HashMap<>();
        FoodService food = new FoodService();
        availableServices.put("Food",food);
    }

    public void addNode(Node node, ArrayList<Node> connections){
        for (Node connNode: connections) {
            node.addConnection(connNode);
        }
        map.addNode(node.getID(),node);

    }

    public void deleteNodes(ObservableList<Node> nodes){
        for(Node node : nodes){

        }
    }

    public void addNode(String anyNodeID, String anyXcoord, String anyYcoord, String anyFloor, String anyBuilding, String anyNodeType, String anyName, ArrayList<Node> connections) {
        try {
            //Create new Node and add to database
            Node newNode = new Node(anyNodeID,anyXcoord,anyYcoord,anyFloor,anyBuilding,anyNodeType,anyName,anyName,"Team H");
            nodeDatabase.addNode(anyNodeID,anyXcoord,anyYcoord,anyFloor,anyBuilding,anyNodeType,anyName);
            //Add connections in both directions
            for (Node connectTo: connections) {
                edgeDatabase.addEdge(anyNodeID,connectTo.getID());
                newNode.addConnection(connectTo);

                edgeDatabase.addEdge(connectTo.getID(),anyNodeID);
                connectTo.addConnection(newNode);
            }
            //Add node to map Hashmap
            map.addNode(anyNodeID,newNode);

        } catch (Exception e) {
            System.out.println("DB Add Failed");
            e.printStackTrace();// end try
        }
    }

    public ArrayList<Node> findPath(Node start, Node end){
        SearchContext search = new SearchContext(new AStarSearch());
        ArrayList<Node> stack = search.findPath(start,end);
        ArrayList<Node> path = new ArrayList<>();
        path.addAll(stack);
        return path;
    }


    public void completeRequest(Staff staffMem){
        staffMem.completeCurrentRequest();
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
