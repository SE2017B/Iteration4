package service;

import java.util.ArrayList;

public class TranslatorService extends Service{
    private String language;
    private int endTime;

    public TranslatorService (){
        this.type = "TranslatorService";
        this.personnel = new ArrayList<>();
    }

    public TranslatorService(String language, int endTime){
        this.language = language;
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
    public String getType(){
        return this.type;
    }
    public ArrayList<Staff> getPersonnel(){
        return this.personnel;
    }
    public String getLanguage(){
        return this.language;
    }
    public int getEndTime(){
        return this.endTime;
    }

    public void setType(String type){
        this.type = type;
    }
    public void setLanguage(String language){
        this.language = language;
    }
    public void setEndTime(int endTime){
        this.endTime = endTime;
    }
}