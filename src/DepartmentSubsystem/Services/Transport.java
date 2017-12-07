/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
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

    public Node getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Node endLocation) {
        this.endLocation = endLocation;
    }
}
