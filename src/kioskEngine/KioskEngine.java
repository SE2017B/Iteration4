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
import search.AStarSearch;
import search.BreadthFirstSearch;
import search.SearchContext;
import service.FoodService;
import service.Service;
import service.Staff;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

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

    public ArrayList<Node> findPath(Node start, Node end){
//        SearchContext search = new SearchContext(new AStarSearch());
//        ArrayList<Node> stack = search.findPath(start,end);
//        ArrayList<Node> path = new ArrayList<>();
//        path.addAll(stack);
        SearchContext search = new SearchContext(new AStarSearch());
        search = new SearchContext(new BreadthFirstSearch());
        ArrayList<Node> path = search.findPath(start, end).getPath();
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
