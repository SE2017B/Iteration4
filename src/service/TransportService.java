package service;

import java.util.ArrayList;

public class TransportService extends Service{

    public TransportService (){
        this.type = "TransportationService";
        this.personnel = new ArrayList<>();
        this.backlog = new ArrayList<>();
        this.description = "";
    }

    public TransportService(String description){
        this.type = "TransportationService";
        this.personnel = new ArrayList<>();
        this.backlog = new ArrayList<>();
        this.description = description;
    }

    public void assignPerson(Staff person){
        this.personnel.add(person);
    }
    public void assignPeople(ArrayList<Staff> people){
        this.personnel.addAll(people);
    }
    public void addRequest(ServiceRequest request){
        this.backlog.add(request);
    }

    //getters
    /*
    public String getType(){
        return this.type;
    }
    public ArrayList<Staff> getPersonnel(){
        return this.personnel;
    }
    public ArrayList<ServiceRequest> getRequests() {
        return backlog;
    }
    public String getDescription(){
        return this.description;
    }
    */

    //setters
    /*
    public void setType(String type){
        this.type = type;
    }
    public void setDescription(String description){
        this.description = description;
    }
    */
}