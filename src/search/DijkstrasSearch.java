package search;

import map.Edge;
import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DijkstrasSearch implements SearchStrategy {
    public DijkstrasSearch(){}

    @Override
    public Path findPath(Node start, Node end) {
//        ArrayList<Path> frontier = new ArrayList<>();
//        Path first = new Path();
//        first.addToPath(start);
//        frontier.add(first);
//
//        while(frontier.size() != 0){
//        Path currentPath = frontier.get(0);
//        frontier.remove(currentPath);
//
//        if(currentPath.getPath().get(currentPath.getPath().size()-1) == end){
//            return currentPath;
//        }
//
//        ArrayList<Path> newPaths = new ArrayList<>();
//        for(Edge e : currentPath.getPath().get(currentPath.getPath().size()-1).getConnections()){
//            Node node = e.getOtherNode(currentPath.getPath().get(currentPath.getPath().size()-1));
//            if(currentPath.getPath().contains(node)) continue;
//
//            Path add = new Path(currentPath);
//            add.addToPath(node);
//            newPaths.add(add);
//        }
//
//        Collections.sort(newPaths);
//        frontier.addAll(newPaths);
//    }
        ArrayList<Node> frontier = new ArrayList<>();
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
        HashMap<Node, Integer> greedy = new HashMap<>();
        frontier.add(start);
        greedy.put(start, 0);
        while(!frontier.isEmpty()){
            frontier.sort((n1, n2) -> (int)(n1.getEuclidianDistance(end) - n2.getEuclidianDistance(end)));
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
