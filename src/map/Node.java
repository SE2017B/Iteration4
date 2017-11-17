/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Nicholas Fajardo, Tyrone Patterson, Leo Grande, Meghana Bhatia
* The following code
*/

package map;
import java.util.HashMap;
import java.util.ArrayList;

public class Node{
    private String longName;    //shortName of node
    private String shortName;   //longName of node
    private String ID;      //id of node
    private String type;    //type of node
    private String building; //building where node is located
    private ArrayList<Edge> connections; //connection for node
    private a_star.FloorNumber floor;  //floor on which node is on
    private int x;  //x-coordinate of node
    private int y;  //y-coordinate of node
    public int fScore;  //greedy + heuristic scores for node
    public int greedy;  //greedy scores

    //Constructors
    public Node(String name, String ID, String type, HashMap<Node, Integer> connections, String floor, int x, int y) {
        this.longName = name;   //name of node
        this.shortName = name;
        this.ID = ID;   //id of node
        this.type = type;   //type of node
        this.connections = new ArrayList<>(); //connection for node
        this.floor = floor; //floor on which node is on
        this.x = x; //x-coordinate of node
        this.y = y; //y-coordinate of node
        this.fScore = 10000;    //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000;    //greedy scores
    }

    //Added for KioskEngine::AddNode()
    public Node(String name, String ID, String type, int x, int y, String floor, ArrayList<Node> connections) {
        this.longName = name;
        this.shortName = name;
        this.ID = ID;
        this.type = type;
        this.floor = floor;
        this.x = x;
        this.y = y;
        this.fScore = 10000; //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000; //greedy scores

        for(Node node : connections){
            this.addConnection(node);
        }
    }

    public Node(String name, String ID, String type, String floor, int x, int y) {
        this.longName = name;   //name of node
        this.shortName = name;
        this.ID = ID; //id of node
        this.type = type;  //type of node
        this.connections = new HashMap<>(); //connection for node
        this.floor = floor; //floor on which node is on
        this.x = x; //x-coordinate of node
        this.y = y; //y-coordinate of node
        this.fScore = 10000; //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000; //greedy scores
    }

    public Node(int x, int y){
        this.longName = "BLANK";    //name of node
        this.shortName = "BLANK";
        this.ID = "BLANK"; //id of node
        this.type = "BLANK";  //type of node
        this.connections = new HashMap<>(); //connection for node
        this.floor = "1"; //floor on which node is on
        this.x = x;
        this.y = y;
        this.fScore = 10000; //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000; //greedy scores
    }

    public Node(){
        this.shortName = "BLANK"; //name of node
        this.ID = "BLANK"; //id of node
        this.type = "BLANK";  //type of node
        this.connections = new HashMap<>(); //connection for node
        this.floor = "1"; //floor on which node is on
        this.x = 100;
        this.y = 100;
        this.fScore = 10000; //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000; //greedy scores
    }

    public Node(String ID, String x, String y, String floor, String building, String type, String longName, String shortName, String team){
        this.longName = longName;
        this.shortName = shortName;
        this.ID = ID;
        this.type = type;
        this.building = building;
        this.connections = new HashMap<>();
        this.floor = floor;
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.fScore = 10000;
        this.greedy = 10000;
    }

    //Adds edge between nodes
    public void addConnection(Node node){
        int edgeCost = (int)getEuclidianDistance(this, node);
        this.connections.put(node, edgeCost);
    }

    //Gets the Euclidian Distance from a start node to an end node
    public double getEuclidianDistance(Node start, Node end){
        double xDeltaSquared = Math.pow((end.getX()-start.getX()), 2);
        double yDeltaSquared = Math.pow((end.getY()-start.getY()), 2);
        double distance = Math.sqrt(xDeltaSquared + yDeltaSquared);
        return distance;
    }

    //Gets the cost from a node to a different node
    public int getCostFromNode(Node node){
        for(Node n: connections.keySet()){
            if(node.toString().equals(n.toString())){
                return connections.get(n);
            }
        }
        return 0;
    }

    //Getters
    public String getLongName() {
        return longName;
    }
    public String getShortName() {
        return this.shortName;
    }
    public String getID() {
        return ID;
    }
    public String getBuilding(){
        return this.building;
    }
    public HashMap<Node, Integer> getConnections() { return connections; }
    public String getFloor() {
        return floor;
    }

    //Gets the x-coordinate of the node
    public int getX() {
        return x;
    }
    public String getXString(){
        return Integer.toString(this.x);
    }

    //Gets the y-coordinate of the node
    public int getY() {
        return y;
    }
    public String getYString(){
        return Integer.toString(this.y);
    }

    public int getfScore() { return fScore; }
    public int getGreedy() { return greedy; }

    //Setters
    public void setLongName(String longName){
        this.longName = longName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }    public void setBuilding(String building){
        this.building = building;
    }
    public void setTeam(String team){
        this.team = team;
    }
    public void setConnections(HashMap<Node, Integer> connections) {
        this.connections = connections;
    }
    public void setFloor(String floor) {
        this.floor = floor;
    }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setfScore(int fScore) { this.fScore = fScore; }
    public void setGreedy(int greedy) { this.greedy = greedy; }

    //Override to turn int into a string
    @Override
    public String toString(){
        return this.shortName;
    }

    //Keep this method in mind for when we are having HashTable issues
    @Override
    public int hashCode(){
        final int prime = 7;
        //Commented this out because we were getting some overflow errors
        int ascii = this.shortName.hashCode();
        int returnVal = 1;
        returnVal = prime * returnVal + this.x;
        returnVal = prime * returnVal + this.y;
        returnVal = prime * returnVal + ascii;
        return returnVal;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof Node)) return false;
        Node other = (Node)obj;
        if(!this.getID().equals(other.getID())) return false;
        return true;
    }
}