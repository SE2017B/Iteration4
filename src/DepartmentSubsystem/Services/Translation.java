/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem.Services;

import DepartmentSubsystem.Service;
import DepartmentSubsystem.Staff;

import java.util.*;

public class Translation extends Service {
    private ArrayList<String> languages = populateLanguageTypes(); // available langiages for translation
    private String requestedLanguage = "";
    private int duration; //Set in minutes

    public Translation(String description) {
        super(description);
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
        for(Staff person: super.getStaff()){
            if(person.getLanguages().contains(language)){
                returnList.add(person);
            }
        }
        return returnList;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public void setRequestedLanguage(String requestedLanguage) {
        this.requestedLanguage = requestedLanguage;
    }

    public String getRequestedLanguage() {
        return requestedLanguage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
