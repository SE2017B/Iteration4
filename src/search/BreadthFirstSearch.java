package search;

import map.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class BreadthFirstSearch implements SearchStrategy {
    public BreadthFirstSearch(){}

    @Override
    public ArrayList<Node> findPath(Node start, Node end) {
        return null;
    }

    @Override
    public ArrayList<Node> returnPath(Node current, HashMap<Node, Node> cameFrom) {
        return null;
    }

    @Override
    public double getEuclideanDistance(Node start, Node end) {
        return 0;
    }
}
