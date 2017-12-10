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

import java.lang.reflect.Array;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static sun.swing.MenuItemLayoutHelper.max;

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
        for (Node attachedNode : attachedNodes) {
            addEdge(temp, attachedNode);
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
        for (Node aNodeMap : nodeMap) {
            if (aNodeMap.getFloor().getNodeMapping() == floor) {
                output.add(aNodeMap);
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
        return this.nodeMap.stream().filter(function::apply).collect(Collectors.toList());
    }

    public List<Node> getNodesByText(String text){
        HashMap<Node, Integer> distances = new HashMap<>();
        for(Node n : nodeMap) distances.put(n, DLDistance(text, n));
        System.out.println(distances.keySet().stream().sorted(Comparator.comparing(distances::get)).collect(Collectors.toList()));
        List<Integer> numbers = distances.values().stream().sorted().collect(Collectors.toList());
        System.out.println(numbers);
        return this.nodeMap.stream().filter(n1 -> !n1.getLongName().toLowerCase().contains("hall")).sorted(Comparator.comparing(distances::get)).collect(Collectors.toList());
    }

    //Setters
    public void setSearchStrategy(SearchStrategy searchStrategy){
        search.setStrategy(searchStrategy);
    }
    public void setKioskLocation(Node node){
        kioskLocation = node;
    }

    private int AREA_SIZE = 120;
    private int AREA_WINDOW = 25;
    public List<Node> getNodesInHorizontal(int x, int y, FloorNumber floor){
        return getNodesBy( n -> n.getFloor().equals(floor) && (n.getX() > (x-AREA_SIZE)) && (n.getX() < (x+AREA_SIZE))
                                && (n.getY() > (y - AREA_WINDOW))  && (n.getY() < (y + AREA_WINDOW)));
    }

    public List<Node> getNodesInVertical(int x, int y, FloorNumber floor){
        return getNodesBy(n -> n.getFloor().equals(floor) && (n.getX() > (x-AREA_WINDOW)) && (n.getX() < (x+AREA_WINDOW))
                                && (n.getY() > (y - AREA_SIZE))  && (n.getY() < (y + AREA_SIZE)));
    }

    //Fuzzy Search Algorithm

    public int DLDistance(String text, Node node) {
        String newText = text.toLowerCase();
        int runningMax = 10000000;
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(node.getID(), node.getLongName(), node.getShortName(), node.getBuilding()));
        for(String string : strings) {
            if (text.length() == 0) return runningMax;
            String newString = string.toLowerCase();
            int[][] distance = new int[text.length() + 1][string.length() + 1];
            for(int i=0;i<=newText.length();i++) distance[i][0] = i;
            for(int i=0;i<=newString.length();i++) distance[0][i] = i;
            for(int i=1;i<=newText.length();i++){
                for(int j=1;j<=newString.length();j++){
                    int cost;
                    if(newText.charAt(i-1) == newString.charAt(j-1)) cost = 0;
                    else cost = 1;
                    distance[i][j] = Math.min(Math.min(distance[i-1][j]+1, distance[i][j-1]+1), distance[i-1][j-1] + cost);
                }
            }
            runningMax = Math.min(runningMax, distance[text.length()][string.length()]);
        }
        return runningMax;
    }
}