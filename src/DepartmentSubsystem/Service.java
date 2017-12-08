/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

import java.util.ArrayList;

public abstract class Service{
    private String name;
    private ArrayList<Staff> staff;
    private String URL;
    public Staff assignedPerson;
    private boolean isUsed;

    public Service(String name) {
        this.name = name;
        this.staff = new ArrayList<Staff>();
        this.isUsed = false;
    }

    public ArrayList<Staff> getStaff() {
        System.out.println("get Staff");
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

    public void addEligibleStaff(Staff person){
        if(person != null) {
            this.staff.add(person);
        }
    }
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