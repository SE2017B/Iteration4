package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class BeamSearch implements SearchStrategy{
    public BeamSearch(){}

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
