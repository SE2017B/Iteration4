package search;

import map.Node;
import map.Path;

import java.util.HashMap;

public class DijkstrasSearch implements SearchStrategy {
    public DijkstrasSearch(){}

    @Override
    public Path findPath(Node start, Node end) {
        return new Path();
    }

    @Override
    public Path returnPath(Node current, HashMap<Node, Node> cameFrom) {
        return new Path();
    }

    public double getEuclideanDistance(Node start, Node end) {
        return 0;
    }
}
