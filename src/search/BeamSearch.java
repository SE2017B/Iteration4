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
public class BeamSearch extends BeamBestFirstSearchTemplate implements SearchStrategy{
    private int beam;
    public BeamSearch(int beam){
        this.beam = beam;
    }

    @Override
    public void addNeighbors(ArrayList<Node> neighbors, ArrayList<Node> frontier, HashMap<Node, Node> cameFrom, Node currentNode, Node end) {
        neighbors.sort(Comparator.comparing(n1 -> n1.getEuclidianDistance(end)));
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

    //    @Override
//    public Path findPath(Node start, Node end) {
//        ArrayList<Node> frontier = new ArrayList<>();
//        ArrayList<Node> explored = new ArrayList<>();
//        HashMap<Node, Node> cameFrom = new HashMap<>();
//        frontier.add(start);
//        while(!frontier.isEmpty()){
//            Node currentNode = frontier.get(0);
//            frontier.remove(currentNode);
//            explored.add(currentNode);
//            if(currentNode.equals(end)) return returnPath(cameFrom, currentNode);
//            ArrayList<Node> neighbors = new ArrayList<>();
//            for(Edge e : currentNode.getConnections()){
//                Node neighbor = e.getOtherNode(currentNode);
//                if(explored.contains(neighbor)) continue;
//                neighbors.add(neighbor);
//            }
//            neighbors.sort(Comparator.comparing(n1 -> n1.getEuclidianDistance(end)));
//            if(neighbors.size() < beam){
//                frontier.addAll(neighbors);
//                for (Node neighbor : neighbors) {
//                    cameFrom.put(neighbor, currentNode);
//                }
//            }
//            else{
//                frontier.addAll(neighbors.subList(0, beam));
//                for(int i=0;i<beam;i++){
//                    cameFrom.put(neighbors.get(i), currentNode);
//                }
//            }
//        }
//        return new Path();
//    }


    @Override
    public String toString(){
        return "Beam Search";
    }
}
