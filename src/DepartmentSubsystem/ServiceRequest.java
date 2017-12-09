/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

import map.Node;

public class ServiceRequest{
    private Service service;
    private int requestID;
    private Node location;
    private String time;
    private String date;
    private Staff assignedPersonnel;
    private String inputData;

    public ServiceRequest(Service service, int requestID, Node location, String time, String date, Staff assignedPersonnel) {
        this.service = service;
        service.use();
        this.requestID = requestID;
        this.location = location;
        this.time = time;
        this.date = date;
        this.assignedPersonnel = assignedPersonnel;
        this.inputData = "";
        this.assignedPersonnel.addRequest(this);
    }

    //Getters and Setters
    public Service getService() {
        return service;
    }
    public void setService(Service service) {
        this.service = service;
    }
    public int getRequestID() {
        return requestID;
    }
    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }
    public Node getLocation() {
        return location;
    }
    public void setLocation(Node location) {
        this.location = location;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Staff getAssignedPersonnel() {
        return assignedPersonnel;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    @Override
    public String toString(){
        String name = String.valueOf(requestID) + " " + service + " " + location + " " + time + " " + date;
        System.out.println(name);
        return name;
    }
}
