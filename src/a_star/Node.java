package a_star;

import java.util.ArrayList;
import java.util.HashMap;

public class Node{
    private String name;    //name of string
    private String ID;  //name of ID
    private String type;    //type of node
    private HashMap<Node, Integer> connections; //nodes that connect to each other
    private int hCost;  //delete
    private int floor;  //floor on which the node is on
    private int x;  //x-coordinate
    private int y;  //y-coordinate
    public int fScore;  //greedy + heuristic
    public int greedy;  //greedy cost
    public ArrayList<Node> cameFrom;    //array list of previous nodes

    //get floor number
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

    //constructor for node
    public Node(String name, String ID, String type, int hCost, int floor, int x, int y) {
        this.name = name;   //set to this name
        this.ID = ID;   //set to this name
        this.type = type;   //set to this name
        this.connections = new HashMap<>(); //set to this name
        this.hCost = hCost; //set to this name
        this.floor = floor; //set to this name
        this.x = x; //set to this name
        this.y = y; //set to this name india greedy + heuristic set to this name
        this.greedy = 10000;    //set to this name
    }

    public Node(){ }
    //get h cost
    public int gethCost() {
        return hCost;
    }
    //set h cost
    public void sethCost(int hCost) {
        this.hCost = hCost;
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
}