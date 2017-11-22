package search;

import exceptions.InvalidNodeException;
import map.Node;
import map.Path;
import map.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DepthFirstSearch implements SearchStrategy {
    public DepthFirstSearch(){}

    @Override
    public Path findPath(Node start, Node end) {
        ArrayList<Path> frontier = new ArrayList<>();
        Path first = new Path();
        first.addToPath(start);
        frontier.add(first);

        while (frontier.size() != 0) {
            Path currentPath = frontier.get(0);
            Node currentNode = currentPath.getPath().get(currentPath.getPath().size()-1);
            frontier.remove(currentPath);
            ArrayList<Path> newPaths = new ArrayList<>();

            if(currentNode == end) return currentPath;

            for(int i=0; i<currentNode.getConnections().size(); i++) {
                Node neighbor = currentNode.getConnections().get(i).getOtherNode(currentNode);

                //If there is a connecting node and the connected node has not been visited
                if(neighbor != null && !(currentPath.getPath().contains(neighbor))) {
                    Path add = new Path(currentPath);
                    add.addToPath(neighbor);
                    newPaths.add(add);
                }
            }
            frontier.addAll(0, newPaths);
        }
        return new Path();
    }

    @Override
    public String toString(){
        return "Depth First";
    }
}
