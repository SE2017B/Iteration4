package search;

import map.Edge;
import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AStarDijkstrasSearchTemplate {
    public abstract void initialize(HashMap<Node, Integer> fSCore, Node start, Node end);
    public abstract void sortFrontier(ArrayList<Node> frontier, HashMap<Node, Integer> fScore, HashMap<Node, Integer> greedy);
    public abstract void putMapValues(HashMap<Node, Integer> fScore, HashMap<Node, Integer> greedy, Node neighbor, Node end);

    public final Path findPath(Node start, Node end){
        ArrayList<Node> frontier = new ArrayList<>();
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
        HashMap<Node, Integer> greedy = new HashMap<>();
        HashMap<Node, Integer> fScore = new HashMap<>();
        frontier.add(start);
        greedy.put(start, 0);
        initialize(fScore, start, end);
        while(!frontier.isEmpty()){
            sortFrontier(frontier, fScore, greedy);
            Node currentNode = frontier.get(0);
            if (currentNode.equals(end)) { return returnPath(cameFrom, currentNode); }
            frontier.remove(currentNode);
            explored.add(currentNode);
            for (Edge e : currentNode.getConnections()) {
                Node neighbor = e.getOtherNode(currentNode);
                if (explored.contains(neighbor)) continue;
                if (!frontier.contains(neighbor)) frontier.add(neighbor);
                int newGreedy = greedy.get(currentNode) + (int) e.getCost();
                if (greedy.containsKey(neighbor) && newGreedy >= greedy.get(neighbor)) continue;
                cameFrom.put(neighbor, currentNode);
                greedy.put(neighbor, newGreedy);
                fScore.put(neighbor, greedy.get(neighbor) + (int)neighbor.getEuclidianDistance(end));
                putMapValues(fScore, greedy, neighbor, end);
            }
        }
        //Sort the frontier list and form the path accordingly
        //Return the proper path
        return new Path();

    }
    private Path returnPath(HashMap<Node, Node> cameFrom, Node currentNode){
        Path path = new Path();
        path.addToPath(currentNode);
        while(cameFrom.containsKey(currentNode)) {
            currentNode = cameFrom.get(currentNode);
            path.addToPath(currentNode, 0);
        }
        return path;
    }
}
