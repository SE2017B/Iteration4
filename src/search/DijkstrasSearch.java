package search;

import map.Edge;
import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
/*

This class contains functions to find a path based on the Dijkstras search method,
returning paths based on the Dijkstras search method,
and also finding the path to a node of the specified type
using Dijkstras search method.

 */
public class DijkstrasSearch implements SearchStrategy {
    public DijkstrasSearch(){}

    @Override
    public Path findPath(Node start, Node end) {
        ArrayList<Node> frontier = new ArrayList<>();
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
        HashMap<Node, Integer> greedy = new HashMap<>();
        frontier.add(start);
        greedy.put(start, 0);
        while(!frontier.isEmpty()){
            frontier.sort((n1, n2) -> (greedy.get(n1) - greedy.get(n2)));
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
            }
        }
        return new Path();
    }

    public Path findPathBy(Node start, String type){
        ArrayList<Node> frontier = new ArrayList<>();
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
        HashMap<Node, Integer> greedy = new HashMap<>();
        frontier.add(start);
        greedy.put(start, 0);
        while(!frontier.isEmpty()){
            frontier.sort((n1, n2) -> (greedy.get(n1) - greedy.get(n2)));
            Node currentNode = frontier.get(0);
            if(currentNode.getType().equals(type)) return returnPath(cameFrom, currentNode);
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

    @Override
    public String toString(){
        return "Dijkstras";
    }
}
