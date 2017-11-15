package a_star;
import java.util.*;
import java.util.HashMap;
public class HospitalMap{
    private HashMap<String, Node> map;
    private LinkedList<Node> frontier;
    private ArrayList<Node> explored;
    private Node start;
    private Node end;

    //Constructors
    public HospitalMap() {
        map = new HashMap<String, Node>();
        frontier = new LinkedList<>();
        explored = new ArrayList<>();
    }

    //Getters and Setters

    //Gets the start node
    public Node getStart() {
        return start;
    }

    //Sets the start node
    public void setStart(Node start) {
        this.start = start;
    }

    //Gets the end node
    public Node getEnd() { return end; }

    //Sets the end node
    public void setEnd(Node end) {
        this.end = end;
    }

    //Helper Methods

    //Method to retrieve the Manhattan Distance
    private int manhattanDistance(Node p1, Node p2){
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    //Method to add a new node to the map
    public void addNode(String id, Node node){
        map.put(id, node);
    }
    //Callable navigation methods

    //Method to find the path from a starting node to an end note
    public Stack<Node> findPath(Node start, Node end){
        Node storedStart = this.start;
        this.start = start;
        Stack<Node> answer = findPath(end);
        this.start = storedStart;
        return answer;
    }

    //Method to find the path from a pre-determined starting node to an end node
    //specified in the parameters of  the function
    public Stack<Node> findPath(Node end){
        Stack<Node> path = new Stack<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();     //Need to know where each Node's shortest path comes from
        //Key: currentNode, Value: Node that currentNode came from
        path.push(this.start);
        //Handles no start case
        if (this.start == null){
            System.out.println("No start point set, use the other version of this method");
            return path;
        }
        this.start.greedy = 0;      //Greedy score from start to start is 0
        this.start.fScore = (int)getEuclidianDistance(this.start, end);     //Total score is only heuristic(getEuclidianDistance)
        //Starting the frontier with node 1
        frontier.add(this.start);
        for(Node n: this.start.getConnections().keySet()){
            frontier.add(n);
        }
        while(frontier.size() > 0){
            //Sets what node we are examining
            Node currentNode = frontier.getFirst();
            int lowestFScore = 10000;
            for(int i=0;i<frontier.size();i++){     //We examine the Node in frontier with the lowest fScore for efficiency
                if(frontier.get(i).fScore < lowestFScore) {
                    currentNode = frontier.get(i);
                    lowestFScore = frontier.get(i).fScore;
                }
            }
            this.explored.add(currentNode);
            this.frontier.remove(currentNode);      //Have to remove currentNode from frontier and add to explored
            //Checks if we reached the end node yet
            if (currentNode == end) {
                for(Node n:this.explored){      //Reset fScores and gScores for the next time we run findPath
                    n.fScore = 10000;
                    n.greedy = 10000;
                }
                return returnPath(currentNode, cameFrom);       //Path is generated in returnPath
            }
            for(Node n: currentNode.getConnections().keySet()){
                //checks if we already explored it
                if(this.explored.contains(n)){
                    continue;
                }
                //checks if we have not seen it yet
                if(!this.frontier.contains(n)){
                    this.frontier.add(n);
                }
                int newGScore = currentNode.greedy + n.getCostFromNode(currentNode);
                if(newGScore >= n.greedy) continue;     //If neighbors greedy is lower then there is a better path through that Node so current path is irrelevant
                cameFrom.put(n, currentNode);       //Otherwise record currentNode(Node where neighbor came from) so we can retrace path later
                n.greedy = newGScore;       //Update neighbors greedy as it is now the best path
                n.fScore = n.greedy + (int)getEuclidianDistance(n, end);        //New fScore for neighbor Node is its greedy plus its heuristic
            }
        }
        //Could not find the end node
        for(Node n:this.explored){
            n.fScore = 10000;       //Reset each Nodes greedy and fScore for next run of findPath
            n.greedy = 10000;
        }
        return path;
    }

    //Method that takes the HashMap containing where each Node came from and generates
    // A stack containing a path from the start Node to the end Node
    private Stack<Node> returnPath(Node current, HashMap<Node, Node> cameFrom){
        Stack<Node> path = new Stack<>();
        path.push(current);
        while(cameFrom.containsKey(current)){       //Each key Node contains the Node from where it came and this path
            current = cameFrom.get(current);        //Is the best path
            path.add(0, current);
        }
        return path;
    }

    //Sets the default start location, which should be the kiosk location
    public void setDefault(Node defaultNode){
        //Default should be Kiosk locaiton
        this.start = defaultNode;
    }

    //Retrieves the Euclidian Distance from one node to another
    public double getEuclidianDistance(Node start, Node end){
        double xDeltaSquared = Math.pow((end.getX()-start.getX()), 2);
        double yDeltaSquared = Math.pow((end.getY()-start.getY()), 2);
        double distance = Math.sqrt(xDeltaSquared + yDeltaSquared);
        return distance;
    }


    public ArrayList<Node> getNodesAsArrayList(){
        ArrayList<Node> output = new ArrayList<Node>();
        output.addAll(map.values());
        return output;
    }

    //Cache for stuff
}