package service;

import a_star.Node;

public class ServiceRequest{
    private Service requestType;
    private int requestID;



    public ServiceRequest(Service requestType, int requestID){
        this.requestType = requestType;
        this.requestID = requestID;
    }

    public void requestService(Service service, Node where){

    }

    public Service getRequestType(){
        return this.requestType;
    }
    public int getRequestID(){
        return this.requestID;
    }

    public void setRequestType(Service requestType){
        this.requestType = requestType;
    }
    public void setRequestID(int requestID){
        this.requestID = requestID;
    }
}
