/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

import DepartmentSubsystem.Services.*;
import map.Node;

import java.util.*;

public class DepartmentSubsystem {
    private boolean initRan = false;
    ArrayList<Department> departments = new ArrayList<Department>();

    //Singleton Stuff
    private static final DepartmentSubsystem singleton = new DepartmentSubsystem();
    private DepartmentSubsystem(){}
    public static DepartmentSubsystem getSubsystem(){
        return singleton;
    }

    //login function for staff members
    public boolean login(String username, String password){
        ArrayList<Staff> allStaff = mainDatabase.getStaff();
        for(Staff member: allStaff){
            if(member.getUsername().equals(username)){
                if(member.getPassword().equals(password)){
                    return true;
                }
                else
                    break;
            }
        }
        return false;
    }

    //Getting services and departments
    public ArrayList<Service> getServices(String department){
        return getDepartment(department).getServices();
    }
    public ArrayList<Department> getDepartments() {
        return departments;
    }
    public ArrayList<Staff> getStaff(Service service){
        return service.getEligibleStaff();
    }
    public Department getDepartment(String departmentName){
        for(Department d: this.departments){
            if(d.toString().equals(departmentName)){
                return d;
            }
        }
        return null;
    }

    //Assign a service request
    public void submitRequest(Service service, String time, String date, Node location, Staff person){
        ServiceRequest temp = new ServiceRequest(service, 0, location,)
    }

    //Sets up the DSS to have the four departments
    public void init(){

        //If the init method was ran, then we dont do it again
        if(initRan = true){ return; }

        //TODO assign staff to departments and services
        Department translationDepartment = new Department("Translation Department");
        Service translation = new Translation();
        translation.setURL("/DepartmentSubsystem/Services/Displays/Translation.fxml");
        translationDepartment.addService(translation);
        departments.add(translationDepartment);

        Department transportationDepartment = new Department("Transportation Department");
        Service transport = new Transport();
        transport.setURL("/DepartmentSubsystem/Services/Displays/Transport.fxml");
        transportationDepartment.addService(new Transport());
        departments.add(transportationDepartment);

        Department facilities = new Department("Facilities");
        Service sanitation = new Sanitation();
        sanitation.setURL("/DepartmentSubsystem/Services/Displays/Sanitation.fxml");
        facilities.addService(sanitation);
        departments.add(facilities);

        Department food = new Department("Food");
        Service foodDelivery = new FoodDelivery();
        foodDelivery.setURL("/DepartmentSubsystem/Services/Displays/FoodDelivery.fxml");
        food.addService(foodDelivery);
        departments.add(food);

        initRan = true;
    }
}