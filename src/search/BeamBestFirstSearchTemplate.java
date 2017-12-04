package search;

import map.Node;
import map.Path;

import java.util.ArrayList;

public abstract class BeamBestFirstSearchTemplate {
    public abstract void addToFrontier(ArrayList<Node> frontier);

    public Path findPath(Node start, Node end){
        //Declare all data structures
        //Go through loops
        //Add the appropriate number of Nodes to frontier in overriden addToFrontier
        addToFrontier(frontier);
        return new Path();
    }
}
