package service;

import a_star.Node;

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

    public Service getRequestType(){
        return this.service;
    }
    public int getRequestID(){
        return this.requestID;
    }
    public Node getWhere(){
        return this.where;
    }

    public void setRequestType(Service requestType){
        this.service = requestType;
    }
    public void setRequestID(int requestID){
        this.requestID = requestID;
    }
    public void setWhere(Node where){
        this.where = where;
    }
}
