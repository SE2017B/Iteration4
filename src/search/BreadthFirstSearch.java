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
        ArrayList<Node> frontier = new ArrayList<>();
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
        Path path = new Path();

        path.addToPath(start);
        cameFrom.put(start, null);

        if(start == null){
            System.out.println("No start point set");
            return path;
        }

        frontier.add(start);
        while(frontier.size() != 0){
            Node currentNode = frontier.get(0);
            if(currentNode == end) return returnPath(currentNode, cameFrom);
            for(int i=0;i<currentNode.getConnections().size();i++){
                Node neighbor = currentNode.getConnections().get(i).getOtherNode(currentNode);
                if(explored.contains(neighbor)) continue;
                else{
                    frontier.add(neighbor);
                    cameFrom.put(neighbor, currentNode);
                }
            }
            frontier.remove(0);
            explored.add(currentNode);
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
