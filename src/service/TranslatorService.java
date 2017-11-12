package service;

import java.util.ArrayList;

public class TranslatorService extends Service{
    private String description;
    private int endTime;

    public TranslatorService (){
        this.type = "TranslatorService";
        this.personnel = new ArrayList<>();
    }

    public TranslatorService(String description, int endTime){
        this.description = description;
        this.endTime = endTime;
        this.type = "TranslatorService";
        this.personnel = new ArrayList<>();
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
    public int getEndTime(){
        return this.endTime;
    }

    public void setType(String type){
        this.type = type;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setEndTime(int endTime){
        this.endTime = endTime;
    }
}