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
import search.SearchContext;
import search.SearchStrategy;

import java.util.*;

public class HospitalMap{
    private static ArrayList<Node> nodeMap;
    private static ArrayList<Edge> edgeMap;
    private static SearchContext search;

    //Constructors
    public HospitalMap() {
        nodeMap = new ArrayList<>();
        edgeMap = new ArrayList<>();
        search = new SearchContext(new AStarSearch());
    }

    //Helper Methods

    //Method to add a new node to the map

    public void addNode(String ID, String x, String y, String floor, String building, String type, String longName, String shortName){
        this.addNode(new Node(ID,x,y,floor,building,type,longName,shortName));
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


    public void addEdge(Node nodeOne,Node nodeTwo){

        this.addEdge(new Edge(nodeOne,nodeTwo));

    }





    public void editEdge(Edge edge,Node oldNode, Node newNode) throws InvalidNodeException {
        try {
            edge.replaceNode(oldNode, newNode);
        } catch (Exception e){
            throw new InvalidNodeException("the edge does not contain the old node");
        }
    }

    public ArrayList<Node> getNodeMap() {
        return nodeMap;
    }

//
//    public List<Node> getNodesBy(Function<Node, Boolean> function){
//        return this.map.values().stream().filter(function::apply).collect(Collectors.toList());
//    }


    public Node findNode(String nodeID){
        int tempLoc = nodeMap.indexOf(new Node(nodeID,null,null,null,null,null,null,null));

        return nodeMap.get(tempLoc);
    }
    //somedata base functions
    //generate Ids from
    private String generateNodeId(Node node){
        // Todo pass in a node without a node without a node id
        // Todo generate a node Id
        return "none";
    }

    private String generateEdgeId(Edge edge){
        // Todo pass in a edge without a edge without a node id
        // Todo generate a edge Id
        return "none";
    }
    //Method to add a new node to the map
    public static void addNode( Node node){

        nodeMap.add(node);
        //add to datebase
        nodeDatabase.addNode(node.getID(),node.getXString(),node.getYString(),node.getFloor().toString(),node.getBuilding(),node.getType(),node.getShortName());
        System.out.println("Node Added successfully");
        //add egdes
        for(Edge e: node.getConnections()){
            addEdge(e);
        }
    }

    public static void addEdge(Edge edge){
        edgeMap.add(edge);
        //add to database
        edgeDatabase.addEdge(edge.getNodeOne().getID(),edge.getNodeTwo().getID());//Todo modify line
        System.out.println("Edge Added successfully");

    }

    public void deleteNode(Node node){
        //delete the node first
        nodeMap.remove(node);
        nodeDatabase.deleteNode(node);

        //delete the edges associated with it
        for(Edge e : node.getConnections() ){
            edgeMap.remove(e);
            edgeDatabase.deleteAnyEdge(e.getID());
        }

    }



    public void EditNode(String id,Node node){
        //replace the informations of the node with that of the new node
    }



    public static void DeleteEdge(Edge edge){
        edgeMap.remove(edge);
        edgeDatabase.deleteAnyEdge(edge.getID());

    }

    public void loadMap(){

    }

    public void saveMapToCSV(){

    }

    public static Collection<Node> getNodes(){
        return nodeMap;
    }

    public void setSearchStrategy(SearchStrategy searchStrategy){
        search.setStrategy(searchStrategy);
    }

    public static Path findPath(Node start, Node end)throws InvalidNodeException{
        return search.findPath(start,end);
    }
    //Cache for stuff
    public static ArrayList<Node> getNodesForSearch(){
        return new ArrayList<Node>(); //Todo return all node to be se
    }
}