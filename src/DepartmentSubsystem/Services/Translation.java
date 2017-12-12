/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 4
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem.Services;

import DepartmentSubsystem.DepartmentSubsystem;
import DepartmentSubsystem.Service;
import DepartmentSubsystem.Staff;

import java.util.*;

public class Translation extends Service {
    private String requestedLanguage = "";
    private int duration;
    private HashMap<String, ArrayList<Staff>> languageMap = new HashMap<>();

    public Translation(String description) {
        super(description);
    }
    //see wwho can speak the language requested
    public ArrayList<Staff> returnEligibleStaff(String language){
        //We already know the language they are going to feed us is going to be good.
        return languageMap.get(language);
    }
    //add staff to each language that can be spoken
    public void addStaff(ArrayList<String> spokenLanguages, Staff person){
        //for each language that the person can speak, we add it to the list of languages we can offer
        for(String language: spokenLanguages){
            if(languageMap.containsKey(language)){
                languageMap.get(language).add(person);
            }
            else{
                ArrayList<Staff> temporaryArrayList = new ArrayList<>();
                temporaryArrayList.add(person);
                languageMap.put(language, temporaryArrayList);
            }
        }
    }
    //get a list of languages
    public ArrayList<String> getLanguages() {
        return new ArrayList<>(languageMap.keySet());
    }

    public void setRequestedLanguage(String requestedLanguage) {
        this.requestedLanguage = requestedLanguage;
    }
    public String getRequestedLanguage() {
        return requestedLanguage;
    }
    //how long you need the translator for
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
