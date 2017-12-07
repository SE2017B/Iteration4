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
//    private String extra1;
//    private String extra2;

    public ServiceRequest(Service service, int requestID, Node location, String time, String date, Staff assignedPersonnel) {
        this.service = service;
        this.requestID = requestID;
        this.location = location;
        this.time = time;
        this.date = date;
        this.assignedPersonnel = assignedPersonnel;
//        this.extra1 = extra1;
//        this.extra2 = extra2;

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

//    public void setAssignedPersonnel(Staff assignedPersonnel) {
//        this.assignedPersonnel = assignedPersonnel;
//    }
//    public void setExtra1(String extra1)
//    {
//        this.extra1 = extra1;
//    }
//    public void setExtra2(String extra2)
//    {
//        this.extra2 = extra2;
//    }
//    public String getExtra1(){
//        return extra1;
//    }
//    public String getExtra2(){
//        return extra2;
//    }

    @Override
    public String toString(){
        String name = String.valueOf(requestID) + " " + service + " " + location + " " + time + " " + date;
        return name;
    }


}
