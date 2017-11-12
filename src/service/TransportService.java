package service;


import java.util.ArrayList;

public class TransportService extends Service{
    private String transportationType;

    public TransportService (){
        this.type = "TransportationService";
        this.personnel = new ArrayList<>();
    }

    public TransportService(String type){
        this.transportationType = type;
        this.type = "TransportationService";
        this.personnel = new ArrayList<>();
    }

    public void assignPerson(Staff person){

        this.personnel.add(person);
    }

    public void assignPeople(ArrayList<Staff> people){
        this.personnel.addAll(people);
    }

    public String getType(){
        return this.type;
    }
    public ArrayList<Staff> getPersonnel(){
        return this.personnel;
    }
    public String getTransportationType(){
        return this.transportationType;
    }

    public void setType(String type){
        this.type = type;
    }
    public void setTransportationType(String transportationType){
        this.transportationType = transportationType;
    }
}