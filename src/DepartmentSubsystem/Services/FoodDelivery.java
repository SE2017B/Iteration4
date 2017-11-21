/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem.Services;

import DepartmentSubsystem.Service;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class FoodDelivery extends Service {
    //Set list of menu items available for the user to choose
    private HashMap<String, String> menuItems = populateMenuItems(); //Cost and item associated with it
    private HashMap<String, String> populateMenuItems(){
        HashMap<String, String> menuItem = new HashMap<>();
        menuItems.put("Cheese Pizza","12 Cheese pizza");
        menuItems.put("Cheeseburger","Two buttery buns, lettuce, cheese, tomato, beef, and onions");
        menuItems.put("Sushi","Six California rolls served with Soy sauce and ginger");
        menuItems.put("Lamb Vindaloo","Famous Indian dish");
        menuItems.put("Duck","Three quack units");
        menuItems.put("Turkey","Gobble Gobble");
        menuItems.put("Sausage","One unit of sausage");
        menuItems.put("Half a Pig","one half units of pig");
        return menuItem;
    }

    //These two fields contain the selected items and any allergies
    private ArrayList<String> selectedFood;
    private String allergies = ""; //allergy field


    public void showFields(ActionEvent e){
        TextField allergies = new TextField();


    }
}
