package map;

import java.util.HashMap;
import java.util.Stack;

public class DijkstrasSearch implements SearchStrategy {
    public DijkstrasSearch(){}

    @Override
    public Stack<Node> findPath(Node start, Node end) {
        return null;
    }

    @Override
    public Stack<Node> returnPath(Node current, HashMap<Node, Node> cameFrom) {
        return null;
    }

    @Override
    public double getEuclideanDistance(Node start, Node end) {
        return 0;
    }
}
