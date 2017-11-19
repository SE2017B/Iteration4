package search;

import exceptions.InvalidNodeException;
import map.Node;
import map.Path;

import java.util.HashMap;

public interface SearchStrategy {
    Path findPath(Node start, Node end) throws InvalidNodeException;
    Path returnPath(Node current, HashMap<Node, Node> cameFrom);
}
