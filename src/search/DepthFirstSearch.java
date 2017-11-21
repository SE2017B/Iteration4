package search;

import map.Node;
import map.Path;

import javax.annotation.PostConstruct;
import java.util.HashMap;

public class DepthFirstSearch implements SearchStrategy {
    public DepthFirstSearch(){}

    @Override
    public Path findPath(Node start, Node end) {
        return new Path();
    }

    @Override
    public Path returnPath(Node current, HashMap<Node, Node> cameFrom) {
        return new Path();
    }

    @Override
    public String toString(){
        return "DepthFirst";
    }
}
