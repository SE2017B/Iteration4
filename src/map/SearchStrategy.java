package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public interface SearchStrategy {
    ArrayList<Node> findPath(Node start, Node end);
    ArrayList<Node> returnPath(Node current, HashMap<Node, Node> cameFrom);
    double getEuclideanDistance(Node start, Node end);
}
