/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Nicholas Fajardo, Tyrone Patterson, Leo Grande, Meghana Bhatia
* The following code
*/

package map;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HospitalMap{
    private ArrayList<Node> nodeMap;

    //Constructors
    public HospitalMap() {
        nodeMap= new ArrayList<>();
    }

    //Helper Methods

    //Method to add a new node to the map
    public void addNode(Node node){
        nodeMap.add(node);
    }

    public ArrayList<Node> getNodeMap() {
        return nodeMap;
    }

    public ArrayList<Node> getNodesByFloor(int floor){
        ArrayList<Node> output = new ArrayList<>();
        for(int i = 0; i < nodeMap.size();i++){
            if(nodeMap.get(1).getFloor().getNodeMapping() == floor){
                output.add(nodeMap.get(1));
            }
        }
        return output;
    }

    public List<Node> getNodesBy(Function<Node, Boolean> function){
        return this.map.values().stream().filter(function::apply).collect(Collectors.toList());
    }
    //Cache for stuff
}