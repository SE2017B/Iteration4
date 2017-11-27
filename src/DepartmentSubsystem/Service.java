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
    private Department department;
    private String description;
    private ArrayList<Staff> eligibleStaff;
    private String URL;
    private boolean Used = false;

    public Service(Department department, String description) {
        this.department = department;
        this.description = description;
        this.eligibleStaff = new ArrayList<Staff>();
    }

    //Getters and Setters
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public ArrayList<Staff> getEligibleStaff() {
        System.out.println("get Staff");
        return eligibleStaff;
    }
    public void setEligibleStaff(ArrayList<Staff> eligibleStaff) {
        this.eligibleStaff = eligibleStaff;
    }
    public void addEligibleStaff(Staff person){
        if(person != null) {
            this.eligibleStaff.add(person);
        }
    }
    public String getURL() {
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }
    public void use(){Used = true;}
    public boolean isUsed(){return this.Used;}

    @Override
    public String toString(){
        return this.description;
    }
}