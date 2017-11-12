package kioskEngine;

import a_star.HospitalMap;
import a_star.Node;
import service.Staff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KioskEngine{
    private HospitalMap map;
    private HashMap<String, Staff> loginInfo;


    public KioskEngine(){
        loginInfo = new HashMap<>();
    }

    public void addNode(){

    }

    public List<Node> findPath(){
        ArrayList<Node> stub = new ArrayList<Node>();
        stub.add(new Node());
        return stub;
    }

    public void requestService(){

    }

    public void compleatRequest(Staff staffMem){
        staffMem.completeCurRec();
    }
    public boolean login(String username, String password){
        if(this.loginInfo.containsKey(username)){
            return this.loginInfo.get(username).getPassword().equals(password);
        } else {
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
}
