package a_star;

import java.util.HashMap;

public class Node{
    //get floor number
    private String name;
    private String ID;
    private String type;
    private HashMap<Node, Integer> connections;
    private int floor;
    private int x;
    private int y;
    public int fScore;
    public int greedy;

    public int getFloor() {
        return floor;
    }
    //set floor number
    public void setFloor(int floor) {
        this.floor = floor;
    }
    //get x-coordinate
    public int getX() {
        return x;
    }
    //set x-coordinate
    public void setX(int x) {
        this.x = x;
    }
    //get y-coordinate
    public int getY() {
        return y;
    }
    //set y-coordinate
    public void setY(int y) {
        this.y = y;
    }

    public Node(String name, String ID, String type, int floor, int x, int y) {
        this.name = name;
        this.ID = ID;
        this.type = type;
        this.connections = new HashMap<>();
        this.floor = floor;
        this.x = x;
        this.y = y;
        this.fScore = 10000;
        this.greedy = 10000;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    //get name of node
    public void setName(String name) {
        this.name = name;
    }

    //get Id number of node
    public String getID() {
        return ID;
    }

    //set ID number of node
    public void setID(String ID) {
        this.ID = ID;
    }

    //get type of node
    public String getType() {
        return type;
    }

    //set type of node
    public void setType(String type) {
        this.type = type;
    }

    //hasmap for connections within nodes
    public HashMap<Node, Integer> getConnections() {
        return connections;
    }

    //set the connections between nodes
    public void setConnections(HashMap<Node, Integer> connections) {
        this.connections = connections;
    }

    //add a connection between nodes
    public void addConnection(int edgeCost, Node node){
        this.connections.put(node, edgeCost);
    }

    //get the cost from a node to a different node
    public void addConnection(Node node){
        int edgeCost = (int)getEuclidianDistance(this, node);
        this.connections.put(node, edgeCost);
    }

    public double getEuclidianDistance(Node start, Node end){
        double xDeltaSquared = Math.pow((end.getX()-start.getX()), 2);
        double yDeltaSquared = Math.pow((end.getY()-start.getY()), 2);
        double distance = Math.sqrt(xDeltaSquared + yDeltaSquared);
        return distance;
    }

    public int getCostFromNode(Node node){
        for(Node n: connections.keySet()){
            if(node.toString().equals(n.toString())){
                return connections.get(n);
            }
        }
        return 0;
    }

    //override to turn int into a string
    @Override
    public String toString(){
        return this.name;
    }

    //Keep this in mind for when we are having hashtable issues
    @Override
    public int hashCode(){
        final int prime = 7;
        //Commented this out because we were getting some overflow errors
        //int ascii = this.hashCode();
        int ascii = this.name.hashCode();
        int returnVal = 1;
        returnVal = prime * returnVal + this.x;
        returnVal = prime * returnVal + this.y;
        returnVal = prime * returnVal + this.floor;
        returnVal = prime * returnVal + ascii;

        return returnVal;
    }
}