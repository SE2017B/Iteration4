package a_star;

import java.util.Comparator;
import java.util.List;

public class Node implements Comparable<Node>{
    private String name;
    private String ID;
    private String type;
    private List<Node> connections;
    private int hCost;
    private int gCost;
    private int floor;
    private int x;
    private int y;

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

    public Node(List<Node> connections){
        this.connections = connections;
    }

    public Node(String name, String ID, String type, List<Node> connections, int hCost, int gCost, int floor, int x, int y) {
        this.name = name;
        this.ID = ID;
        this.type = type;
        this.connections = connections;
        this.hCost = hCost;
        this.gCost = gCost;
        this.floor = floor;
        this.x = x;
        this.y = y;
    }

    public Node(){ }

    public int gethCost() {
        return hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

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

    public List<Node> getConnections() {
        return connections;
    }

    public void setConnections(List<Node> connections) {
        this.connections = connections;
    }

    public void setX(int){}

    public void setY(){}

    @Override
    public int compareTo(Node other){
        //TODO in order to optimize the priority queue stuff in A*
        return 0;
    }
}