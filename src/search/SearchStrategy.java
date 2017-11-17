package search;

import map.Node;

import java.util.ArrayList;
import java.util.HashMap;

public interface SearchStrategy {
    ArrayList<Node> findPath(Node start, Node end);
    ArrayList<Node> returnPath(Node current, HashMap<Node, Node> cameFrom);
}
