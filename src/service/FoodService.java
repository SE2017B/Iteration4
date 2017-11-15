/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/

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
}