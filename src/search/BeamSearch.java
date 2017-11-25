package search;

import map.Edge;
import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class BeamSearch implements SearchStrategy{
    private int beam;
    public BeamSearch(int beam){
        this.beam = beam;
    }

    @Override
    public Path findPath(Node start, Node end) {
//        ArrayList<Path> frontier = new ArrayList<>();
//        Path first = new Path();
//        first.addToPath(start);
//        frontier.add(first);
//
//        while(frontier.size() != 0){
//            Path currentPath = frontier.get(0);
//            Node currentNode = currentPath.getPath().get(currentPath.getPath().size()-1);
//            frontier.remove(currentPath);
//            if(currentNode == end) return currentPath;
//            ArrayList<Path> newPaths = new ArrayList<>();
//            for(int i=0;i<currentNode.getConnections().size();i++){
//                Node neighbor = currentNode.getConnections().get(i).getOtherNode(currentNode);
//                if(currentPath.getPath().contains(neighbor)) continue;
//                Path add = new Path(currentPath);
//                add.addToPath(neighbor);
//                newPaths.add(add);
//            }
//            newPaths.sort((o1, o2) -> (int) (o1.getPath().get(o1.getPath().size() - 1).getEuclidianDistance(end) - o2.getPath().get(o2.getPath().size() - 1).getEuclidianDistance(end)));
//            if(newPaths.size() < beam) frontier.addAll(newPaths);
//            else frontier.addAll(newPaths.subList(0, beam));
//        }
//        return new Path();
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
