/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem.Services;

import DepartmentSubsystem.Service;

import java.util.ArrayList;

public class FoodDelivery extends Service {
    //Set list of menu items available for the user to choose
    private ArrayList<String> menuItems = populateMenuItems(); //Cost and item associated with it

    public FoodDelivery(String description) {
        super(description);
        menuItems = populateMenuItems();
        System.out.println("Hi" + menuItems);
    }

    private ArrayList<String> populateMenuItems(){
        ArrayList<String> menuItem = new ArrayList<>();
        menuItem.add("Cheese Pizza");
        menuItem.add("Cheeseburger");
        menuItem.add("Sushi");
        menuItem.add("Lamb Vindaloo");
        menuItem.add("Duck");
        menuItem.add("Turkey");
        menuItem.add("Sausage");
        menuItem.add("Half a Pig");
        return menuItem;
    }

    //These two fields contain the selected items and any allergies
    private String selectedFood;
    private String allergies = ""; //allergy field

    public void setSelectedFood(String selectedFood) {
        this.selectedFood = selectedFood;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public ArrayList<String> getMenuItems() {
        return menuItems;
    }
}
