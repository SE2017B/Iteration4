/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 4
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/


package DepartmentSubsystem.Services;

import DepartmentSubsystem.Service;
import map.Node;

public class Transport extends Service {
    private Node endLocation;

    public Transport(String description) {
        super(description);
    }
    //destination point
    public Node getEndLocation() {
        return endLocation;
    }
    //pick-up location
    public void setEndLocation(Node endLocation) {
        this.endLocation = endLocation;
    }
}
