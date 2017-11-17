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

    //Constructors
    public HospitalMap() {
        map = new HashMap<>();
    }

    //Getters and Setters

    //Helper Methods

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