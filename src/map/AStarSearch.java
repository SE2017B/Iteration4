package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class AStarSearch implements SearchStrategy {
    public AStarSearch(){}

    @Override
    public ArrayList<Node> findPath(Node start, Node end){
        LinkedList<Node> frontier = new LinkedList<>();
        ArrayList<Node> explored = new ArrayList<>();
        ArrayList<Node> path = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();     //Need to know where each Node's shortest path comes from
        //Key: currentNode, Value: Node that currentNode came from
        path.add(start);
        //Handles no start case
        if (start == null){
            System.out.println("No start point set, use the other version of this method");
            return path;
        }
        start.greedy = 0;      //Greedy score from start to start is 0
        start.fScore = (int)getEuclideanDistance(start, end);     //Total score is only heuristic(getEuclidianDistance)
        //Starting the frontier with node 1
        frontier.add(start);
        frontier.addAll(start.getConnections().keySet());
        while(frontier.size() > 0){
            //Sets what node we are examining
            Node currentNode = frontier.getFirst();
            int lowestFScore = 10000;
            for (Node aFrontier : frontier) {     //We examine the Node in frontier with the lowest fScore for efficiency
                if (aFrontier.fScore < lowestFScore) {
                    currentNode = aFrontier;
                    lowestFScore = aFrontier.fScore;
                }
            }
            explored.add(currentNode);
            frontier.remove(currentNode);      //Have to remove currentNode from frontier and add to explored
            //Checks if we reached the end node yet
            if (currentNode == end) {
                for(Node n:explored){      //Reset fScores and gScores for the next time we run findPath
                    n.fScore = 10000;
                    n.greedy = 10000;
                }
                return returnPath(currentNode, cameFrom);       //Path is generated in returnPath
            }
            for(Node n: currentNode.getConnections().keySet()){
                //checks if we already explored it
                if(explored.contains(n)){
                    continue;
                }
                //checks if we have not seen it yet
                if(!frontier.contains(n)){
                    frontier.add(n);
                }
                int newGScore = currentNode.greedy + n.getCostFromNode(currentNode);
                if(newGScore >= n.greedy) continue;     //If neighbors greedy is lower then there is a better path through that Node so current path is irrelevant
                cameFrom.put(n, currentNode);       //Otherwise record currentNode(Node where neighbor came from) so we can retrace path later
                n.greedy = newGScore;       //Update neighbors greedy as it is now the best path
                n.fScore = n.greedy + (int)getEuclideanDistance(n, end);        //New fScore for neighbor Node is its greedy plus its heuristic
            }
        }
        //Could not find the end node
        for(Node n:explored){
            n.fScore = 10000;       //Reset each Nodes greedy and fScore for next run of findPath
            n.greedy = 10000;
        }
        return path;
    }

    @Override
    public ArrayList<Node> returnPath(Node current, HashMap<Node, Node> cameFrom){

        ArrayList<Node> path = new ArrayList<>();
        path.add(current);
        while(cameFrom.containsKey(current)){       //Each key Node contains the Node from where it came and this path
            current = cameFrom.get(current);        //Is the best path
            path.add(0, current);
        }
        return path;
    }

    @Override
    public double getEuclideanDistance(Node start, Node end){
        double xDeltaSquared = Math.pow((end.getX()-start.getX()), 2);
        double yDeltaSquared = Math.pow((end.getY()-start.getY()), 2);
        return Math.sqrt(xDeltaSquared + yDeltaSquared);
    }
}
