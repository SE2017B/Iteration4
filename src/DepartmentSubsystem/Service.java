/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 4
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/
package DepartmentSubsystem;

import java.util.ArrayList;

public abstract class Service{
    private String name;    //name of type of service
    private ArrayList<Staff> staff; //list of staff
    private String URL; //url for each type of service (fmxl)
    public Staff assignedPerson;    //assigned person for a task
    private boolean isUsed; //boolean for used
    //contructor for services
    public Service(String name) {
        this.name = name;
        this.staff = new ArrayList<Staff>();
        this.isUsed = false;
    }
    //getters and setters for staff services
    //get staff name
    public ArrayList<Staff> getStaff() {
        //System.out.println("get Staff");
        return staff;
    }

    public String getName() {
        return name;
    }

    public void setStaff(ArrayList<Staff> staff) {
        this.staff = staff;
    }
    public String getURL() {
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }
    //see if a person is able to perform a specific service
    public void addEligibleStaff(Staff person){
        if(person != null) {
            this.staff.add(person);
        }
    }
    //remove staff who are busy from the list
    public void removeEligibleStaff(Staff person){
        staff.remove(person);
    }

    public void use(){
        this.isUsed = true;
    }
    public boolean isUsed(){
        return this.isUsed;
    }

    @Override
    public String toString(){
        return this.name;
    }
}