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

    //CONSTRUCTOR IS NOT NECESSARY

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
        return eligibleStaff;
    }
    public void setEligibleStaff(ArrayList<Staff> eligibleStaff) {
        this.eligibleStaff = eligibleStaff;
    }
}