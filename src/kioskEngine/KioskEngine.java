package kioskEngine;

import a_star.HospitalMap;
import a_star.Node;
import exceptions.InvalidLoginException;
import service.FoodService;
import service.Staff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KioskEngine{
    private HospitalMap map;
    private HashMap<String, Staff> loginInfo;
    private static final String JDBC_URL="jdbc:derby:teamHDB;create=true";
    private static Connection conn;


    public KioskEngine(){
        loginInfo = new HashMap<>();
        map = new HospitalMap();
    }

    public static void addNode(String anyNodeID, String anyXcoord, String anyYcoord, String anyFloor, String anyBuilding, String anyNodeType, String anyName, ArrayList<Node> connections) {
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

            //todo add connections to new node

        } catch (Exception e) {
            e.printStackTrace();// end try
        }
    }

    public ArrayList<Node> findPath(Node start, Node end){
        return map.findPath(start,end);
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

    public void addStaffLogin(Staff staff){
        loginInfo.put(staff.getUsername(), staff);
    }

    public Staff getStaff(String username){
        return loginInfo.get(username);
    }
}
