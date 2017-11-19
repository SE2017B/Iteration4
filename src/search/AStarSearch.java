package search;

import map.Edge;
import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class AStarSearch implements SearchStrategy {
    public AStarSearch(){}

    @Override
    public Path findPath(Node start, Node end){
        LinkedList<Node> frontier = new LinkedList<>();
        ArrayList<Node> explored = new ArrayList<>();
        Path path = new Path();
        HashMap<Node, Node> cameFrom = new HashMap<>();     //Need to know where each Node's shortest path comes from
        //Key: currentNode, Value: Node that currentNode came from
        path.addToPath(start);
        //Handles no start case
        if (start == null){
            System.out.println("No start point set, use the other version of this method");
            return path;
        }
        start.setGreedy(0);      //Greedy score from start to start is 0
        start.setFScore((int)getEuclideanDistance(start, end));     //Total score is only heuristic(getEuclidianDistance)
        //Starting the frontier with node 1
        frontier.add(start);
        for(Edge e : start.getConnections()) frontier.add(e.getOtherNode(start));
        while(frontier.size() > 0){
            //Sets what node we are examining
            Node currentNode = frontier.getFirst();
            int lowestFScore = 1000000;
            for (Node aFrontier : frontier) {     //We examine the Node in frontier with the lowest fScore for efficiency
                if (aFrontier.getFScore() < lowestFScore) {
                    currentNode = aFrontier;
                    lowestFScore = aFrontier.getFScore();
                }
            }
            explored.add(currentNode);
            frontier.remove(currentNode);      //Have to remove currentNode from frontier and add to explored
            //Checks if we reached the end node yet
            if (currentNode == end) {
                for(Node n:explored){      //Reset fScores and gScores for the next time we run findPath
                    n.setFScore(1000000);
                    n.setGreedy(1000000);
                }
                return returnPath(currentNode, cameFrom);       //Path is generated in returnPath
            }
            for(Edge edge: currentNode.getConnections()){
                Node n = edge.getOtherNode(currentNode);
                //checks if we already explored it
                if(explored.contains(n)){
                    continue;
                }
                //checks if we have not seen it yet
                if(!frontier.contains(n)){
                    frontier.add(n);
                }
                int newGScore = currentNode.getGreedy() + (int)n.getEuclidianDistance(currentNode);
                if(newGScore >= n.getGreedy()) continue;     //If neighbors greedy is lower then there is a better path through that Node so current path is irrelevant
                cameFrom.put(n, currentNode);       //Otherwise record currentNode(Node where neighbor came from) so we can retrace path later
                n.setGreedy(newGScore);       //Update neighbors greedy as it is now the best path
                n.setFScore(n.getGreedy() + (int)getEuclideanDistance(n, end));        //New fScore for neighbor Node is its greedy plus its heuristic
            }
        }
        //Could not find the end node
        for(Node n:explored){
            n.setFScore(10000000);       //Reset each Nodes greedy and fScore for next run of findPath
            n.setGreedy(1000000);
        }
        return path;
    }

    public Path findPath(ArrayList<Node> nodes){
        Node start = nodes.get(0);
        Node end = nodes.get(nodes.size()-1);

        return new Path();
    }

    @Override
    public Path returnPath(Node current, HashMap<Node, Node> cameFrom){
        Path path = new Path();
        path.addToPath(current);
        while(cameFrom.containsKey(current)){       //Each key Node contains the Node from where it came and this path
            current = cameFrom.get(current);        //Is the best path
            path.addToPath(current, 0);
        }
        return path;
    }

    public double getEuclideanDistance(Node start, Node end){
        double xDeltaSquared = Math.pow((end.getX()-start.getX()), 2);
        double yDeltaSquared = Math.pow((end.getY()-start.getY()), 2);
        return Math.sqrt(xDeltaSquared + yDeltaSquared);
    }
}
