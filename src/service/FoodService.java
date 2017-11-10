package service;

import java.util.List;

public class FoodService extends Service{
    private String foodType;
    private String type;
    List<Staff> personel;

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

    public List<Staff> getPersonel(){
        return this.personel;
    }
}