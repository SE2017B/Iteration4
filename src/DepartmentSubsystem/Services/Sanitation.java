/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem.Services;

import DepartmentSubsystem.Service;
import java.util.ArrayList;
public class Sanitation extends Service {
    //This contains all the possible requests for a specific locaiton
    private ArrayList<String> serviceTypes = populateServiceTypes();
    private ArrayList<String> populateServiceTypes(){
        ArrayList<String> returnVal = new ArrayList<>();
        returnVal.add("Spill Cleaning");
        returnVal.add("Room Preparation"); //Preparing a conference room
        returnVal.add("Room Restocking"); //Bathrooms and Rooms
        returnVal.add("Room Cleaning"); //Cleaning a patients room or bathroom

        return returnVal;
    }

    //Set this to the service that is requested
    private String requestedService;
}