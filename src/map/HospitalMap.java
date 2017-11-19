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
    private ArrayList<Edge> edgeMap;

    //Constructors
    public HospitalMap() {
        nodeMap = new ArrayList<>();
        edgeMap = new ArrayList<>();
    }

    //Helper Methods

    //Method to add a new node to the map
    public void addNode(Node node){
        nodeMap.add(node);
    }

    public void addNode(String ID, String x, String y, String floor, String building, String type, String longName, String shortName){
        nodeMap.add(new Node(ID,x,y,floor,building,type,longName,shortName));
    }

    public void editNode(Node node, String x, String y, String floor, String building, String type, String longName, String shortName){
        node.setBuilding(building);
        node.setFloor(floor);
        node.setLongName(longName);
        node.setShortName(shortName);
        node.setX(x);
        node.setY(y);
        node.setType(type);
    }

    public void removeNode(Node node){
        node.deleteNode();
        nodeMap.remove(node);
    }

    public void addEdge(Edge edge){
        edgeMap.add(edge);
    }

    public void addEdge(Node nodeOne,Node nodeTwo){
        edgeMap.add(new Edge(nodeOne,nodeTwo));
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
//
//    public List<Node> getNodesBy(Function<Node, Boolean> function){
//        return this.map.values().stream().filter(function::apply).collect(Collectors.toList());
//    }

    private Node findNode(String nodeID){
        int tempLoc = nodeMap.indexOf(new Node(nodeID,null,null,null,null,null,null,null));

        return nodeMap.get(tempLoc);
    }

    public Path findPath(Node start, Node end){
        return null;
    }
    //Cache for stuff
}