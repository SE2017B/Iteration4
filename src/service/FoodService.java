package service;

import java.util.ArrayList;
import java.util.List;

public class FoodService extends Service{
    private String foodType;
    private String type;
    private ArrayList<Staff> personel;

    public FoodService (){
        this.type = "FoodService";
    }

    public FoodService (String foodType){
        this.foodType = foodType;
        this.type = "FoodService";
    }

    public void assignPerson(Staff person){
        this.personel.add(person);
    }

    public void assignPeople(List<Staff> people){
        for(Staff person: people)
            this.personel.add(person);
    }

    public String getFoodType(){
        return this.foodType;
    }

    public String getType(){
        return this.type;
    }

    public ArrayList<Staff> getPersonel(){
        return this.personel;
    }
}