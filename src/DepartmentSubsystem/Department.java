/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

import java.util.*;

public abstract class Department {
    private String type;
    private ArrayList<Staff> personel;
    private ArrayList<Service> services;
    private Backlog backlog;

    public Department(){

    }

    //Adds a request to
    public void addRequest(int requestID, ServiceRequest serviceRequest){
        backlog.addRequest(requestID, serviceRequest);
    }

    public ArrayList<Service> getServices(){
        return this.services;
    }

    public void completeRequest(int requestID){
        Backlog.removeRequest(requestID);
    }

    private class Backlog {
        Department department;
        HashMap<Integer, ServiceRequest> requests;

        Backlog() {

        }

        //Adds a request
        void addRequest(int requestID, ServiceRequest serviceRequest) {
            requests.put(requestID, serviceRequest);
        }

        ArrayList<ServiceRequest> getServicesRequests() {
            return new ArrayList<ServiceRequest>(requests.values());
        }

        void removeRequest(int requestID){
            requests.remove(requestID);
        }
    }
}
