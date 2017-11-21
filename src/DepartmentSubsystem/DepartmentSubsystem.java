/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

import DepartmentSubsystem.Services.*;

import java.util.*;

public class DepartmentSubsystem {
    ArrayList<Department> departments = new ArrayList<Department>();
    HashMap<String, Staff> staff = new HashMap<String, Staff>();

    //Singleton for Department Sub System
    private static final DepartmentSubsystem singleton = new DepartmentSubsystem();

    //private constructor
    private DepartmentSubsystem(){}

    //get the instance of singleton
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

    //obtains the list of services with noted department from requestServices
    public ArrayList<Service> getService(String department){
        return null;
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    //allows staff to input a DepartmentSubsystem with specific info (ex. department, DepartmentSubsystem, staff)
    public void requestService (Service input){

    }

    //UI uses this to check which department was selected, and then they populate the services depending on what happens
    public Department getDepartment(String departmentName){
        for(Department d: this.departments){
            if(d.toString().equals(departmentName)){
                return d;
            }
        }
        return null;
    }

    //Sets up the DSS to have the four departments
    public void setup(){
        Department translationDepartment = new Department("Translation Department");
        translationDepartment.addService(new FoodDelivery());
        departments.add(translationDepartment);

        Department transportationDepartment = new Department("Transportation Department");
        transportationDepartment.addService(new Translation());
        departments.add(transportationDepartment);

        Department facilities = new Department("Facilities");
        facilities.addService(new Sanitation());
        departments.add(facilities);

        Department food = new Department("Food");
        food.addService(new FoodDelivery());
        departments.add(food);
    }
}
