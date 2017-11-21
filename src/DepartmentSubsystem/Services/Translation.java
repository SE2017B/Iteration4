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
import DepartmentSubsystem.Staff;

import java.util.*;

public class Translation extends Service {
    private ArrayList<String> languages = populateLanguageTypes(); // available langiages for translation
    private int duration; //Set in minutes

    public Translation(Department department, String description) {
        super(department, description);
    }

    private ArrayList<String> populateLanguageTypes(){
        ArrayList<String> returnVal = new ArrayList<>();
        returnVal.add("Czech");
        returnVal.add("Spanish");
        returnVal.add("Hindi");
        returnVal.add("Russian");
        return returnVal;
    }

    private ArrayList<Staff> returnEligibleStaff(String language){
        ArrayList<Staff> returnList = new ArrayList<>();
        for(Staff person: super.getEligibleStaff()){
            if(person.getLanguages().contains(language)){
                returnList.add(person);
            }
        }
        return returnList;
    }
}
