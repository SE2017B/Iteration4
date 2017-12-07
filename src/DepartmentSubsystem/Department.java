/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

import java.util.HashMap;
import java.util.ArrayList;

public class Department{
    private String type;
    //private ArrayList<Staff> personel;
    private ArrayList<Service> services;

    public Department(String type) {
        this.type = type;
        //this.personel = new ArrayList<Staff>();
        this.services = new ArrayList<Service>();
    }

//    public HashMap<Integer, ServiceRequest> getBacklog(){
//        return backlog;
//    }
    //Adds a request to the backlog
//    public void addRequest(int requestID, ServiceRequest serviceRequest){
//        backlog.put(requestID, serviceRequest);
//    }

    //returns all the available services
    public ArrayList<Service> getServices(){
        return this.services;
    }

    //Removes a request (this is done when a request is cancelled or completed
//    public void removeRequest(int requestID){
//        backlog.remove(requestID);
//    }

    public void addService(Service service){
        this.services.add(service);
    }

    @Override
    public String toString(){
        return this.type;
    }

//    public void addPersonel(Staff person){
//        this.personel.add(person);
//    }
//
//    public void removePersonel(Staff person){
//        this.personel.remove(person);
//    }
}
