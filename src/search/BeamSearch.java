package search;

import map.Edge;
import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/*

This class contains functions to find a path based on the Beam search method,
returning paths based on the Beam search method.

 */
public class BeamSearch extends SearchTemplate{
    private int beam;
    public BeamSearch(int beam){
        this.beam = beam;
    }

    @Override
    public Path findAndReturn(Node start, Node end, ArrayList<Node> frontier) {
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
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
            if(neighbors.size() < beam){
                frontier.addAll(neighbors);
                for (Node neighbor : neighbors) {
                    cameFrom.put(neighbor, currentNode);
                }
            }
            else{
                frontier.addAll(neighbors.subList(0, beam));
                for(int i=0;i<beam;i++){
                    cameFrom.put(neighbors.get(i), currentNode);
                }
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
        return "Beam Search";
    }
}
