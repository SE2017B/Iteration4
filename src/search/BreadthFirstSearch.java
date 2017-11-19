package search;

import com.sun.applet2.AppletParameters;
import map.Edge;
import map.Node;
import map.Path;

import java.util.*;

public class BreadthFirstSearch implements SearchStrategy {
    public BreadthFirstSearch(){}

    @Override
    public Path findPath(Node start, Node end) {
        ArrayList<Path> frontier = new ArrayList<>();
        Path first = new Path();
        first.addToPath(start);
        frontier.add(first);

        while(frontier.size() != 0){
            Path currentPath = frontier.get(0);
            Node currentNode = currentPath.getPath().get(currentPath.getPath().size()-1);
            frontier.remove(currentPath);
            if(currentNode == end) return currentPath;
            ArrayList<Path> newPaths = new ArrayList<>();
            for(int i=0;i<currentNode.getConnections().size();i++){
                Node neighbor = currentNode.getConnections().get(i).getOtherNode(currentNode);
                if(currentPath.getPath().contains(neighbor)) continue;
                Path add = new Path(currentPath);
                add.addToPath(neighbor);
                newPaths.add(add);
            }
            Collections.sort(newPaths);
            frontier.addAll(newPaths);
        }
        return new Path();
    }

    @Override
    public Path returnPath(Node current, HashMap<Node, Node> cameFrom) {
        Path path = new Path();
        while(true){
            path.addToPath(current, 0);
            if(cameFrom.get(current) != null){
                current = cameFrom.get(current);
            } else break;
        }

        return path;
    }

    public double getEuclideanDistance(Node start, Node end) {
        return 0;
    }
}
