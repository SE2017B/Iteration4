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
import search.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HospitalMap{
    private ArrayList<Node> nodeMap;
    private ArrayList<Edge> edgeMap;
    private ArrayList<SearchStrategy> posSerchStrat;
    private SearchContext search;
    private static HospitalMap map;
    private Node kioskLocation;

    //Constructors
    private HospitalMap() {
        nodeMap = new ArrayList<>();
        edgeMap = new ArrayList<>();
        search = new SearchContext(new AStarSearch());
        posSerchStrat = new ArrayList<>();
        posSerchStrat.add(new AStarSearch());
        posSerchStrat.add(new BeamSearch(3));
        posSerchStrat.add(new BreadthFirstSearch());
        posSerchStrat.add(new DepthFirstSearch());
        posSerchStrat.add(new DijkstrasSearch());
        nodeMap.addAll(nodeDatabase.getNodes());
        edgeMap.addAll(edgeDatabase.getEdges());

        try {
            // place the kiosk at the main info desk
            kioskLocation = getNodesBy(n -> n.getID().equals("FINFO00101")).get(0);
        }
        catch (IndexOutOfBoundsException e){
            if (!nodeMap.isEmpty()){
                //add a some an arbitrary node from the map if that one doesn't exist
               kioskLocation = nodeMap.get(0);
            }
            else{
                System.out.println("No nodes found for kiosk location");
                kioskLocation = null;
            }
        }

    }

    public static HospitalMap getMap() {
        if(map == null){
            map = new HospitalMap();
        }
        return map;
    }

    //Helper Methods

    //Methods to add a new, isolated node to the map
    public void addNode(Node node){
        nodeMap.add(node);
        nodeDatabase.addNode(node);
    }

    public void addNode(String ID, String x, String y, String floor, String building, String type, String longName,
                        String shortName, String team){
        Node newNode = new Node(ID ,x, y, floor, building, type, longName, shortName, team);
        nodeMap.add(newNode);
        nodeDatabase.addNode(newNode);
    }

    public void addNode(String x, String y, String floor, String building, String type, String longName,
                        String shortName, String team){
        Node newNode = new Node(x, y, floor, building, type, longName, shortName, team);
        nodeMap.add(newNode);
        nodeDatabase.addNode(newNode);
    }

    public void addNodeandEdges(String x, String y, String floor, String building, String type, String longName,
                                String shortName, String team, ArrayList<Node> attachedNodes){
        Node temp = new Node(x, y, floor, building, type, longName, shortName, team);
        nodeMap.add(temp);
        for(int i = 0; i < attachedNodes.size(); i++){
            addEdge(temp,attachedNodes.get(i));
        }
        nodeDatabase.addNode(new Node(x,y,floor,building,type,longName,shortName, team));
    }

    public void editNode(Node node, String x, String y, String floor, String building, String type, String longName, String shortName){
        node.setBuilding(building);
        node.setFloor(floor);
        node.setLongName(longName);
        node.setShortName(shortName);
        node.setX(x);
        node.setY(y);
        node.setType(type);
        nodeDatabase.modifyNode(node);
    }

    public void removeNode(Node node){
        //get all connections and remove from edgeMap
        int size = node.getConnections().size();
        for(int i = 0; i < size; i++){
            //have to delete 0 index each time because array shrinks after each iteration
            removeEdge(node.getConnections().get(0));
        }
        nodeMap.remove(node);
        nodeDatabase.deleteNode(node);
    }

    public void addEdge(Edge edge){
        edgeMap.add(edge);
    }

    //this is the one used by the engine and therefore the tests
    public void addEdge(Node nodeOne,Node nodeTwo){
        Edge tempEdge = new Edge(nodeOne,nodeTwo);
        edgeMap.add(tempEdge);
        edgeDatabase.addEdge(tempEdge);
    }

    public void removeEdge(Edge edge){
        edge.deleteConnection();
        edgeMap.remove(edge);
        edgeDatabase.deleteAnyEdge(edge);
    }

    public void editEdge(Edge edge, Node oldNode, Node newNode) throws InvalidNodeException {
        try {
            edge.replaceNode(oldNode, newNode);
        } catch (Exception e){
            throw new InvalidNodeException("the edge does not contain the old node");
        }
    }

    public Node findNode(String nodeID){
        int tempLoc = nodeMap.indexOf(new Node(nodeID,null,null,null,null,null,null,null, null));

        return nodeMap.get(tempLoc);
    }

    public Path findPath(Node start, Node end){
        return search.findPath(start,end);
    }

    public Path findNearest(Node start, String type){
        DijkstrasSearch search = new DijkstrasSearch();
        return search.findPathBy(start, type);
    }

    public Path findPathPitStop(ArrayList<Node> stops) throws InvalidNodeException {
        AStarSearch search = new AStarSearch();
        return search.findPathPitStop(stops);
    }

    //Getters
    public ArrayList<Node> getNodeMap() {
        return nodeMap;
    }
    public ArrayList<Edge> getEdgeMap() {
        return edgeMap;
    }
    public ArrayList<Node> getNodesByFloor(int floor){
        ArrayList<Node> output = new ArrayList<>();
        for(int i = 0; i < nodeMap.size(); i++){
            if(nodeMap.get(i).getFloor().getNodeMapping() == floor){
                output.add(nodeMap.get(i));
            }
        }
        return output;
    }

    public SearchStrategy getSearchStrategy() {return search.getStrategy();}
    public ArrayList<SearchStrategy> getSearches() {
        return posSerchStrat;
    }

    public Node getKioskLocation(){
        return kioskLocation;
    }
    public List<Node> getNodesBy(Function<Node, Boolean> function){
        List<Node> nodes = this.nodeMap.stream().filter(function::apply).collect(Collectors.toList());
        Collections.sort(nodes, Comparator.comparing(n -> n.getEuclidianDistance(this.kioskLocation)));
        return nodes;
    }

    //Setters
    public void setSearchStrategy(SearchStrategy searchStrategy){
        search.setStrategy(searchStrategy);
    }
    public void setKioskLocation(Node node){
        kioskLocation = node;
    }
}