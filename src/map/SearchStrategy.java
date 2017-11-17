package map;

import java.util.HashMap;
import java.util.Stack;

public interface SearchStrategy {
    Stack<Node> findPath(Node start, Node end);
    Stack<Node> returnPath(Node current, HashMap<Node, Node> cameFrom);
    double getEuclideanDistance(Node start, Node end);
}
