/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Nicholas Fajardo, Tyrone Patterson, Leo Grande, Meghana Bhatia
* The following code
*/

package map;
import database.edgeDatabase;
import database.nodeDatabase;
import exceptions.InvalidNodeException;
import search.AStarSearch;
import search.BreadthFirstSearch;
import search.SearchContext;
import search.SearchStrategy;

import java.util.*;

public class HospitalMap{
    private ArrayList<Node> nodeMap;
    private ArrayList<Edge> edgeMap;
    private SearchContext search;

    //Constructors
    public HospitalMap() {
        nodeMap = new ArrayList<>();
        edgeMap = new ArrayList<>();
        search = new SearchContext(new AStarSearch());
    }

    //Helper Methods

    //Method to add a new node to the map
    public void addNode(Node node){
        nodeMap.add(node);
    }

    public void addNode(String ID, String x, String y, String floor, String building, String type, String longName, String shortName, String team){
        nodeMap.add(new Node(ID,x,y,floor,building,type,longName,shortName, team));
        //nodeDatabase.addNode(new Node(ID,x,y,floor,building,type,longName,shortName, team));
    }

    public void editNode(Node node, String x, String y, String floor, String building, String type, String longName, String shortName){
        node.setBuilding(building);
        node.setFloor(floor);
        node.setLongName(longName);
        node.setShortName(shortName);
        node.setX(x);
        node.setY(y);
        node.setType(type);
        //nodeDatabase.modifyNode(node);
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

    public void removeEdge(Edge edge){
        edge.deleteConnection();
        edgeMap.remove(edge);
    }

    public void editEdge(Edge edge,Node oldNode, Node newNode) throws InvalidNodeException {
        try {
            edge.replaceNode(oldNode, newNode);
        } catch (Exception e){
            throw new InvalidNodeException("the edge does not contain the old node");
        }
        //edgeDatabase.modifyEdge(edge);
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

    public Node findNode(String nodeID){
        int tempLoc = nodeMap.indexOf(new Node(nodeID,null,null,null,null,null,null,null, null));

        return nodeMap.get(tempLoc);
    }

    public void setSearchStrategy(SearchStrategy searchStrategy){
        search.setStrategy(searchStrategy);
    }

    public Path findPath(Node start, Node end){
        return search.findPath(start,end);
    }

    public Path findNearest(Node start, String type){
        BreadthFirstSearch search = new BreadthFirstSearch();
        return search.findPathBy(start, type);
    }

    public Path findPathPitStop(ArrayList<Node> stops){
        AStarSearch search = new AStarSearch();
        return search.findPathPitStop(stops);
    }
    //Cache for stuff
}