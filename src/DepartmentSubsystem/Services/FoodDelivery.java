/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem.Services;

import DepartmentSubsystem.Department;
import DepartmentSubsystem.Service;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class FoodDelivery extends Service {
    //Set list of menu items available for the user to choose
    private HashMap<String, String> menuItems = populateMenuItems(); //Cost and item associated with it

    public FoodDelivery(Department department, String description) {
        super(department, description);
    }

    private HashMap<String, String> populateMenuItems(){
        HashMap<String, String> menuItem = new HashMap<>();
        menuItem.put("Cheese Pizza","12 Cheese pizza");
        menuItem.put("Cheeseburger","Two buttery buns, lettuce, cheese, tomato, beef, and onions");
        menuItem.put("Sushi","Six California rolls served with Soy sauce and ginger");
        menuItem.put("Lamb Vindaloo","Famous Indian dish");
        menuItem.put("Duck","Three quack units");
        menuItem.put("Turkey","Gobble Gobble");
        menuItem.put("Sausage","One unit of sausage");
        menuItem.put("Half a Pig","one half units of pig");
        return menuItem;
    }

    //These two fields contain the selected items and any allergies
    private ArrayList<String> selectedFood;
    private String allergies = ""; //allergy field
}
