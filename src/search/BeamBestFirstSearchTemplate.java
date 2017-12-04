package search;

import map.Edge;
import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BeamBestFirstSearchTemplate {
    public abstract void addNeighbors(ArrayList<Node> neighbors, ArrayList<Node> frontier, HashMap<Node, Node> cameFrom, Node currentNode, Node end);
    public Path findPath(Node start, Node end){
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
            addNeighbors(neighbors, frontier, cameFrom, currentNode, end);
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
}
