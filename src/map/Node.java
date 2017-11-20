/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Nicholas Fajardo, Tyrone Patterson, Leo Grande, Meghana Bhatia
* The following code
*/

package map;

import exceptions.InvalidNodeException;

import java.util.ArrayList;
import java.util.List;

public class Node{
    private String longName;    //shortName of node
    private String shortName;   //longName of node
    private String ID;      //id of node
    private String type;    //type of node
    private String building; //building where node is located
    private ArrayList<Edge> connections; //connection for node
    private FloorNumber floor;  //floor on which node is on
    private int x;  //x-coordinate of node
    private int y;  //y-coordinate of node
    private String team;

    public Node(String ID, String x, String y, String floor, String building, String type, String longName, String shortName, String team){
        this.longName = longName;
        this.shortName = shortName;
        this.ID = ID;
        this.type = type;
        this.building = building;
        this.connections = new ArrayList<>();
        this.floor = FloorNumber.fromDbMapping(floor);
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.team = team;
    }

    //Adds edge between
    public void addConnection(Edge egde){
        connections.add(egde);
    }

    //Gets the Euclidian Distance from a start node to an end node
    public double getEuclidianDistance(Node otherNode){
        double xDeltaSquared = Math.pow((this.x - otherNode.getX()), 2);
        double yDeltaSquared = Math.pow((this.y - otherNode.getY()), 2);
        //scale factor weights the z component so that the path wants to be on the right floor, and it doesn't want to leave it
        //(may need to increase this value)
        double zDeltaSquared =  Math.pow((this.floor.getNodeMapping() - otherNode.getFloor().getNodeMapping()), 2) * 10000;
        double distance = Math.sqrt(xDeltaSquared + yDeltaSquared + zDeltaSquared);
        return distance;
    }

    //Gets the cost from a node to a different node
    /*public double getCostFromNode(Node node) throws InvalidNodeException{
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getID().contains(node.getID())) {
                return connections.get(i).getCost();
            }
        }
        throw new InvalidNodeException("");
    }*/

//    //added for Chima
//    public void addEdge(Edge edge){
//        connections.add(edge);
//    }
//
//    //takes a node and gets all nodes that it shares an edge with
//    public ArrayList<Node> getSiblingNodes(){
//        ArrayList<Node> ans = new ArrayList<Node>();
//        for(Edge e: this.connections ){
//            ans.add(e.getOtherNode(this));
//        }
//        return ans;
//    }

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
    public ArrayList<Edge> getConnections() {
        return this.connections;
    }

    public Edge getEdgeOf(Node node){
            for (Edge e : this.connections) {
                if (e.getOtherNode(this) == node) return e;
            }
        return null;
    }

    public void addConnection(Node node){
        Edge edge = new Edge(this, node);
    }

    public FloorNumber getFloor() {
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

    public String getTeam() {
        return team;
    }

    //Setters
    public void setLongName(String longName){
        this.longName = longName;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setBuilding(String building){
        this.building = building;
    }
    public void setFloor(String floor) {
        this.floor = FloorNumber.fromDbMapping(floor);
    }
    public void setX(String x){
        this.x = Integer.parseInt(x);
    }
    public void setY(String y){
        this.y = Integer.parseInt(y);
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

    public void removeConnection(Edge edge) {
        connections.remove(edge);
    }

    public void deleteNode() {
        for(int i = 0;i < connections.size(); i++){
            connections.get(i).deleteConnection();
        }
    }
}