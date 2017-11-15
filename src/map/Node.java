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

    //get floor number
    private String longName;    //shortName of node
    private String shortName;   //longName of node
    private String ID;      //id of node
    private String type;    //type of node
    private String building; //building where node is located
    private String team;    //
    private HashMap<Node, Integer> connections; //connection for node
    private String floor;  //floor on which node is on
    private int x;  //x-coordinate of node
    private int y;  //y-coordinate of node
    public int fScore;  //greedy + heuristic scores for node
    public int greedy;  //greedy scores

    public Node(String name, String ID, String type, HashMap<Node, Integer> connections, String floor, int x, int y) {
        this.shortName = name;   //name of node
        this.longName = name;
        this.ID = ID;   //id of node
        this.type = type;   //type of node
        this.connections = connections; //connection for node
        this.floor = floor; //floor on which node is on
        this.x = x; //x-coordinate of node
        this.y = y; //y-coordinate of node
        this.fScore = 10000;    //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000;    //greedy scores
    }


    //Added for KioskEngine::AddNode()
    public Node(String name, String ID, String type, int x, int y, String floor, ArrayList<Node> connections) {
        this.shortName = name;
        this.longName = name;
        this.ID = ID;
        this.type = type;
        this.x = x;
        this.y = y;
        this.floor = floor;
        this.fScore = 10000; //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000; //greedy scores

        for(Node node : connections){
            this.addConnection(node);
        }
    }


    public Node(String name, String ID, String type, String floor, int x, int y) {
        this.shortName = name; //name of node
        this.longName = name;
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
        this.x = x;
        this.y = y;

        this.shortName = "BLANK"; //name of node
        this.longName = "BLANK";
        this.ID = "BLANK"; //id of node
        this.type = "BLANK";  //type of node
        this.connections = new HashMap<>(); //connection for node
        this.floor = "1"; //floor on which node is on


        this.fScore = 10000; //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000; //greedy scores


    }

    public Node(){
        this.x = 100;
        this.y = 100;

        this.shortName = "BLANK"; //name of node
        this.ID = "BLANK"; //id of node
        this.type = "BLANK";  //type of node
        this.connections = new HashMap<>(); //connection for node
        this.floor = "1"; //floor on which node is on


        this.fScore = 10000; //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000; //greedy scores


    }
    public Node(String ID, String x, String y, String floor, String building, String type, String longName, String shortName, String team){
        this.ID = ID;
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.floor = floor;
        this.building = building;
        this.type = type;
        this.longName = longName;
        this.shortName = shortName;
        this.team = team;
        this.connections = new HashMap<>();
        this.greedy = 10000;
        this.fScore = 10000;
    }


    //Getters and Setters

    //Retrieves the shortName of the node
    public String getShortName() {
        return this.shortName;
    }

    //Sets the name of the node
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    //Gets the longName of the node
    public String getLongName(){
        return this.longName;
    }

    public void setLongName(String longName){
        this.longName = longName;
    }

    public String getBuilding(){
        return this.building;
    }

    public void setBuilding(String building){
        this.building = building;
    }

    public String getTeam(){
        return this.team;
    }

    public void setTeam(String team){
        this.team = team;
    }

    //Gets the ID number from the node
    public String getID() {
        return ID;
    }

    //Sets the ID number of node
    public void setID(String ID) {
        this.ID = ID;
    }

    //Gets the type of the node
    public String getType() {
        return type;
    }

    //Sets the type of the node
    public void setType(String type) {
        this.type = type;
    }

    //Gets the connections list for a node
    public HashMap<Node, Integer> getConnections() { return connections; }

    //Sets the connections HashMap for the node
    public void setConnections(HashMap<Node, Integer> connections) {
        this.connections = connections;
    }

    //Gets the floor number
    public String getFloor() {
        return floor;
    }

    //Sets the floor number
    public void setFloor(String floor) {
        this.floor = floor;
    }

    //Gets the x-coordinate of the node

    public int getX() {
        return x;
    }
    public String getXString(){
        return Integer.toString(this.x);
    }

    //Sets the x-coordinate of the node
    public void setX(int x) { this.x = x; }

    //Gets the y-coordinate of the node
    public int getY() {
        return y;
    }
    public String getYString(){
        return Integer.toString(this.y);
    }

    //Sets the y-coordinate of the node
    public void setY(int y) { this.y = y; }

    //Gets the fScore of a node
    public int getfScore() { return fScore; }

    //Sets the fScore for a node
    public void setfScore(int fScore) { this.fScore = fScore; }

    //Gets the greedy cost for a node
    public int getGreedy() { return greedy; }

    //Sets the greedy cost for a node
    public void setGreedy(int greedy) { this.greedy = greedy; }

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