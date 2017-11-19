package search;

import map.Node;
import map.Path;

public class DepthFirstSearch implements SearchStrategy {
    public DepthFirstSearch(){}

    @Override
    public Path findPath(Node start, Node end) {
        return new Path();
    }

    public double getEuclideanDistance(Node start, Node end) {
        return 0;
    }
}
