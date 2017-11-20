/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

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
        return mainDatabase.;
    }

    //obtains the list of services with noted department from requestServices
    public ArrayList<Service> getService(String department){
        return null;
    }
    //allows staff to input a DepartmentSubsystem with specific info (ex. department, DepartmentSubsystem, staff)
    public void requestService (Service input){

    }
}
