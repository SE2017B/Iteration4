package search;

import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AStarDijkstrasSearchTemplate {
    public abstract void initialize(ArrayList<Node> frontier, ArrayList<Node> explored, HashMap<Node, Node> cameFrom, HashMap<Node, Integer> greedy, HashMap<Node, Integer> fSCore);
    public abstract void sortFrontier(ArrayList<Node> frontier);
    public abstract void assignMapValues(HashMap<Node, Integer> fScore);

    public final Path findPath(Node start, Node end){
        //Declare all data structures here and initalize appropriate ones in initialize overriden functions
        initialize(frontier, explored, cameFrom, greedy, fScore);
        // Loop stuff here and appropriate sorting function in sortFrontier overriden functions
        frontier = sortFrontier(frontier);
        // Only AStar needs stuff to be put into fScore HashMap so Dijkstras assignMapValues should be empty
        assignMapValues(fScore);
        return new Path();
    }
}
