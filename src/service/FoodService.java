package service;

import java.util.ArrayList;

public class FoodService extends Service{
    private String foodType;

    public FoodService (){
        this.type = "FoodService";
        personnel = new ArrayList<>();
    }

    public FoodService (String foodType){
        this.foodType = foodType;
        this.type = "FoodService";
        personnel = new ArrayList<>();
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
    public String getFoodType(){
        return this.foodType;
    }

    public void setType(String type){
        this.type = type;
    }
    public void setFoodType(String foodType){
        this.foodType = foodType;
    }
}