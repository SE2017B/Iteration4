package service;

import java.util.ArrayList;

public class FoodService extends Service{

    public FoodService (){
        this.type = "Food Service";
        this.personnel = new ArrayList<>();
        this.backlog = new ArrayList<>();
        this.description = "";
    }

    public FoodService (String description){
        this.type = "Food Service";
        this.personnel = new ArrayList<>();
        this.backlog = new ArrayList<>();
        this.description = description;
    }



    public String getType(){
        return this.type;
    }
    public ArrayList<Staff> getPersonnel(){
        return this.personnel;
    }
    public ArrayList<ServiceRequest> getRequests() {
        return this.backlog;
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