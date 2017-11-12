package service;

import java.util.ArrayList;

public class FoodService extends Service{
    private String type;
    private ArrayList<Staff> personnel;

    public FoodService (){
        this.type = "FoodService";
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
}