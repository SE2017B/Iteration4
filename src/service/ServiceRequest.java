package service;

import a_star.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceRequest{
    private Service service;
    private int requestID;
    private Node where;
    private String description;


    public ServiceRequest(Service service, int requestID, Node where, String description){
        this.service = service;
        this.requestID = requestID;
        this.where = where;
        this.description = description;
    }
    public void giveRequest(){
        service.addRequest(this);
    }

    //getters
    public Service getRequestType(){
        return this.service;
    }
    public int getRequestID(){
        return this.requestID;
    }
    public Node getWhere(){
        return this.where;
    }
    public String getDescription() { return this.description; }

    //setters
    public void setRequestType(Service requestType){
        this.service = requestType;
    }
    public void setRequestID(int requestID){
        this.requestID = requestID;
    }
    public void setWhere(Node where){
        this.where = where;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public static ArrayList<Staff> getStaffForServiceType(String ServiceType){
        //STUB
        ArrayList<Staff> temp = new ArrayList<Staff>();
        Staff john = new Staff("username","password","Translator","Translator Smith",1234, new TranslatorService());
        john.setFullName("John Smith");
        temp.add(john);
        return temp;
    }

}
