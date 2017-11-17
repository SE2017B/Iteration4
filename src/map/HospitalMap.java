/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Nicholas Fajardo, Tyrone Patterson, Leo Grande, Meghana Bhatia
* The following code
*/

package map;
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
        map = new HashMap<>();
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
    public ArrayList<Node> getNodesForSearch(){
        ArrayList<Node> output = new ArrayList<>();
        for(Node node : map.values()){
            if(!node.getType().equals("HALL") && node.getFloor().equals("2") && node.getTeam().equals("Team H")){
                output.add(node);
            }
        }
        return output;
    }

    public ArrayList<Node> getNodesForEdit(){
        ArrayList<Node> output = new ArrayList<>();
        for(Node node : map.values()){
            if(node.getFloor().equals("2") && node.getTeam().equals("Team H")){
                output.add(node);
            }
        }
        return output;
    }

    //Cache for stuff
}