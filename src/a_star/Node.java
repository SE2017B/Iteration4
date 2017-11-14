package a_star;

import java.util.ArrayList;
import java.util.HashMap;

public class Node{
    private String name;
    private String ID;
    private String type;
    private HashMap<Node, Integer> connections;
    private int floor;
    private int x;
    private int y;
    public int fScore;
    public int greedy;
    public ArrayList<Node> cameFrom;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Node(String name, String ID, String type, HashMap<Node, Integer> connections, int floor, int x, int y) {
        this.name = name;
        this.ID = ID;
        this.type = type;
        this.connections = connections;
        this.floor = floor;
        this.x = x;
        this.y = y;
        this.fScore = 10000;    //Need to keep track of greedy and heuristic scores for each Node at all times
        this.greedy = 10000;
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

    public Node(){ }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<Node, Integer> getConnections() {
        return connections;
    }

    public void setConnections(HashMap<Node, Integer> connections) {
        this.connections = connections;
    }

    public void addConnection(int edgeCost, Node node){
        this.connections.put(node, edgeCost);
    }

    public int getCostFromNode(Node node){
        for(Node n: connections.keySet()){
            if(node.toString().equals(n.toString())){
                return connections.get(n);
            }
        }
        return 0;
    }

    @Override
    public String toString(){
        return this.name;
    }
}