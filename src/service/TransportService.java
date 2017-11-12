package service;


import java.util.ArrayList;

public class TransportService extends Service{
    private String description;
    private ArrayList<ServiceRequest> requests;

    public TransportService (){
        this.type = "TransportationService";
        this.personnel = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    public TransportService(String description){
        this.description = description;
        this.type = "TransportationService";
        this.personnel = new ArrayList<>();
        this.requests = new ArrayList<>();
    }

    public void assignPerson(Staff person){
        this.personnel.add(person);
    }
    public void assignPeople(ArrayList<Staff> people){
        this.personnel.addAll(people);
    }
    public void addRequest(ServiceRequest request){
        this.requests.add(request);
    }

    public String getType(){
        return this.type;
    }
    public ArrayList<Staff> getPersonnel(){
        return this.personnel;
    }
    public String getDescription(){
        return this.description;
    }

    public void setType(String type){
        this.type = type;
    }
    public void setDescription(String description){
        this.description = description;
    }
}