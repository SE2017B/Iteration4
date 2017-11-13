package kioskEngine;

import a_star.HospitalMap;
import a_star.Node;
import exceptions.InvalidLoginException;
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
        ArrayList<Node> stub = new ArrayList<>();
        stub.add(new Node());
        return stub;
    }

    public void requestService(){

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
}
