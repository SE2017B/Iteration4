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
//    private Department department;
    private ArrayList<Staff> staff;
    private String URL;
//    private boolean Used = false;

    public Service(String name) {
        this.name = name;
        this.staff = new ArrayList<Staff>();
    }

    //Getters and Setters
//    public Department getDepartment() {
//        return department;
//    }
//    public void setDepartment(Department department) {
//        this.department = department;
//    }
//    public String getDescription() {
//        return description;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }
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
    public void addEligibleStaff(Staff person){
        if(person != null) {
            this.staff.add(person);
        }
    }
    public void removeEligibleStaff(Staff person){
        staff.remove(person);
    }
    public String getURL() {
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }
//    public void use(){Used = true;}
//    public boolean isUsed(){return this.Used;}

    @Override
    public String toString(){
        return this.name;
    }
}