package a_star;

import java.util.*;

public class HospitalMap{
    private HashMap<String, Node> map;
    private LinkedList<Node> frontier;
    private ArrayList<Node> explored;
    private Node start;
    private Node end;

    //Constructors
    public HospitalMap(){
        frontier = new LinkedList<Node>();
        explored = new ArrayList<>();
    }

    //Getters and Setters
    public Node getStart() {
        return start;
    }
    public void setStart(Node start) {
        this.start = start;
    }
    public Node getEnd() {
        return end;
    }
    public void setEnd(Node end) {
        this.end = end;
    }

    //helper methods
    private int manhattanDistance(Node p1, Node p2){
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    public void addNode(String id, Node node){
        map.put(id, node);
    }

    //Callable navigation methods
    public Stack<Node> findPath(Node end){
        Stack<Node> path = new Stack<>();
        path.push(this.start);

        //Handles no start case
        if (this.start == null){
            System.out.println("No start point set, use the other version of this method");
            return path;
        }

        //Since we start at this node, we already explored it, and we need to explore around it
        this.explored.add(this.start);
        frontier.add(this.start);
        for(Node n: this.start.getConnections().keySet()){
            frontier.add(n);
        }

        //A*
        int currentStep = 1;
        int totalCost = 0;
        while(frontier.size() > 0){
            //Sets what node we are examining
            Node currentNode = frontier.poll();

            //Checks if we reached the end node yet
            if (currentNode == end){
                path.push(currentNode);
                break;
            }

            this.explored.add(currentNode);

            int bestScore = 100;
            Node bestStepperNode = new Node();

            int bestH = 10000;
            for(Node n: currentNode.getConnections().keySet()){
                //checks if we already explored it
                if(this.explored.contains(n)){
                    continue;
                }
                //checks if we have not seen it yet
                if(!this.frontier.contains(n)){
                    this.frontier.add(n);
                }

                /*
                if(bestScore < 0){
                    bestStepperNode = n;
                    bestScore = currentNode.getCostFromNode(currentNode) + manhattanDistance(currentNode, n);
                }
                */

                int tempCost = totalCost + n.getCostFromNode(currentNode);
                int heuristic = manhattanDistance(n, this.end);
                if (tempCost >=  bestScore && bestH < heuristic){
                    continue;
                }

                bestH = heuristic;
                bestStepperNode = n;
                bestScore = tempCost;
            }

            totalCost += bestStepperNode.getCostFromNode(currentNode);
            path.push(bestStepperNode);
        }

        //Could not find the end node
        return path;
    }

    public List<Node> findPath(Node start, Node end){
        return (new ArrayList<Node>());
    }

    public void setDefault(Node defaultNode){
        //Default should be Kiosk locaiton
        this.start = defaultNode;
    }

    public double getEuclidianDistance(Node start, Node end){
        double xDeltaSquared = Math.pow((end.getX()-start.getX()), 2);
        double yDeltaSquared = Math.pow((end.getY()-start.getY()), 2);
        double distance = Math.sqrt(xDeltaSquared + yDeltaSquared);
        return distance;
    }
}