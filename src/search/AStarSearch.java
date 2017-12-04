package search;

import exceptions.InvalidNodeException;
import map.Edge;
import map.Node;
import map.Path;

import java.util.*;
import java.util.function.DoubleToLongFunction;


/*

This class contains functions to find a path based on the AStar search method,
returning paths based on the Astar search method,
finding paths with pit stops,
and a method that obtains the Euclidean distance between two nodes.

 */
public class AStarSearch extends SearchTemplate {
    public AStarSearch(){}

    @Override
    public Path findAndReturn(Node start, Node end, ArrayList<Node> frontier){
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
        HashMap<Node, Integer> greedy = new HashMap<>();
        HashMap<Node, Double> fScore = new HashMap<>();

        greedy.put(start, 0);
        fScore.put(start, getEuclideanDistance(start, end));
        while(!frontier.isEmpty()){
            frontier.sort((n1, n2) -> (int)(fScore.get(n1) - fScore.get(n2)));
            Node currentNode = frontier.get(0);
            if(currentNode.equals(end)) return returnPath(cameFrom, currentNode);
            frontier.remove(currentNode);
            explored.add(currentNode);
            for(Edge e : currentNode.getConnections()){
                Node neighbor = e.getOtherNode(currentNode);
                if(explored.contains(neighbor)) continue;
                if(!frontier.contains(neighbor)) frontier.add(neighbor);
                int newGreedy = greedy.get(currentNode) + (int)e.getCost();
                if(greedy.containsKey(neighbor) && newGreedy >= greedy.get(neighbor)) continue;
                cameFrom.put(neighbor, currentNode);
                greedy.put(neighbor, newGreedy);
                fScore.put(neighbor, greedy.get(neighbor) + getEuclideanDistance(neighbor, end));
            }
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

    public Path findPathPitStop(ArrayList<Node> stops) throws InvalidNodeException {
        Path path = new Path();
        for(int i=0;i<stops.size()-1;i++){
            path.addToPath(this.findPath(stops.get(i), stops.get(i+1)));
        }
        return path;
    }

    public double getEuclideanDistance(Node start, Node end){
        double xDeltaSquared = Math.pow((end.getX()-start.getX()), 2);
        double yDeltaSquared = Math.pow((end.getY()-start.getY()), 2);
        return Math.sqrt(xDeltaSquared + yDeltaSquared);
    }

    @Override
    public String toString(){
        return "AStar";
    }
}
