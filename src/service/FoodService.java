package service;

import java.util.ArrayList;

public class FoodService extends Service{
    private String description;
    private ArrayList<ServiceRequest> requests;

    public FoodService (){
        this.type = "FoodService";
        this.personnel = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.description = "";
    }

    public FoodService (String description){
        this.type = "FoodService";
        this.personnel = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.description = description;
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
    public ArrayList<ServiceRequest> getRequests() {
        return this.requests;
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