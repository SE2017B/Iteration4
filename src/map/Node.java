/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Nicholas Fajardo, Tyrone Patterson, Leo Grande, Meghana Bhatia
* The following code
*/

package map;

import database.nodeDatabase;

import java.util.ArrayList;

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

    //Constructors
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

    public Node(String x, String y, String floor, String building, String type, String longName, String shortName, String team){
        this.longName = longName;
        this.shortName = shortName;
        this.type = type;
        this.building = building;
        this.connections = new ArrayList<>();
        this.floor = FloorNumber.fromDbMapping(floor);
        this.x = Integer.parseInt(x);
        this.y = Integer.parseInt(y);
        this.team = team;
        this.ID = "H" + this.type + this.floor.getDbMapping() +  nodeDatabase.getNodeID(this.type);
    }

    public Node getCopy(){
        return new Node(this.ID, Integer.toString(this.x), Integer.toString(this.y), this.floor.getDbMapping(), this.building, this.type, this.longName, this.shortName, this.team);
    }

    //Adds existing edge between
    public void addConnection(Edge edge){
        connections.add(edge);
    }

    //Adds new edge between
    public void addConnection(Node node){
        Edge edge = new Edge(this, node);
    }

    public void removeConnection(Edge edge) {
        connections.remove(edge);
    }

    //Gets the Euclidian Distance from a start node to an end node
    public double getEuclidianDistance(Node otherNode){
        double xDeltaSquared = Math.pow((this.x - otherNode.getX()), 2);
        double yDeltaSquared = Math.pow((this.y - otherNode.getY()), 2);
        //scale factor weights the z component so that the path wants to be on the right floor, and it doesn't want to leave it
        //(may need to increase this value)
        double zDeltaSquared =  Math.pow((this.floor.getNodeMapping() - otherNode.getFloor().getNodeMapping()), 2) * 10000;
        return Math.sqrt(xDeltaSquared + yDeltaSquared + zDeltaSquared);
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
    public String getType() {
        return type;
    }
    public String getBuilding(){
        return this.building;
    }
    public ArrayList<Edge> getConnections() {
        return this.connections;
    }
    public FloorNumber getFloor() {
        return floor;
    }
    public String getTeam() {
        return team;
    }

    public Edge getEdgeOf(Node node){
        try {
            for (Edge e : this.connections) {
                if (e.getOtherNode(this) == node) return e;
            }
        }catch (Exception e) {
            return null;
        }
        return null;
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
        return this.longName;
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