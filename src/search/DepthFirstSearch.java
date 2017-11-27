package search;

import exceptions.InvalidNodeException;
import map.Node;
import map.Path;
import map.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
/*

This class contains functions to find a path based on the Depth First search method,
returning paths based on the Depth First search method.

 */
public class DepthFirstSearch implements SearchStrategy {
    public DepthFirstSearch(){}

    @Override
    public Path findPath(Node start, Node end) {
        ArrayList<Node> frontier = new ArrayList<>();
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
        frontier.add(start);
        while(!frontier.isEmpty()){
            Node currentNode = frontier.get(0);
            frontier.remove(currentNode);
            explored.add(currentNode);
            if(currentNode.equals(end)) return returnPath(cameFrom, currentNode);
            ArrayList<Node> neighbors = new ArrayList<>();
            for(Edge e : currentNode.getConnections()){
                Node neighbor = e.getOtherNode(currentNode);
                if(explored.contains(neighbor)) continue;
                neighbors.add(neighbor);
            }
            neighbors.sort((n1, n2) -> (int)(n1.getEuclidianDistance(end) - n2.getEuclidianDistance(end)));
            frontier.addAll(0, neighbors);
            for(Node n : neighbors) cameFrom.put(n, currentNode);
        }
        return new Path();
    }

    private Path returnPath(HashMap<Node, Node> cameFrom, Node currentNode){
        Path path = new Path();
        path.addToPath(currentNode);
        while(cameFrom.containsKey(currentNode)){
            currentNode = cameFrom.get(currentNode);
            path.addToPath(currentNode, 0);
        }
        return path;
    }


    @Override
    public String toString(){
        return "Depth First";
    }
}
