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

import java.util.*;
import java.util.HashMap;
public class HospitalMap{

    private static ArrayList<Node> nodeMap;
    private static ArrayList<Edge> edgeMap;



    //Constructors
    public HospitalMap() {
        nodeMap = new ArrayList<Node>();
        edgeMap= new ArrayList<Edge>();

    }

    //Method to find the path from a pre-determined starting node to an end node
    //specified in the parameters of  the function


    //Method that takes the HashMap containing where each Node came from and generates
    // A stack containing a path from the start Node to the end Node


    public ArrayList<Node> getNodesForSearch(){
        ArrayList<Node> output = new ArrayList<Node>();
        for(Node node : nodeMap){
            if(!node.getType().equals("HALL") && node.getFloor().equals("2") && node.getTeam().equals("Team H")){
                output.add(node);
            }
        }
        return output;
    }

    public ArrayList<Node> getNodesForEdit(){
        ArrayList<Node> output = new ArrayList<Node>();
        for(Node node : nodeMap){
            if(node.getFloor().equals("2") && node.getTeam().equals("Team H")){
                output.add(node);
            }
        }
        return output;
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
    public void addNode( Node node){

        nodeMap.add(node);
        //add to datebase
        nodeDatabase.addNode(node.getID(),node.getXString(),node.getYString(),node.getFloor(),node.getBuilding(),node.getType(),node.getShortName());
        System.out.println("Node Added successfully");
        //add egdes
        for(Edge e: node.getConnections()){
            addEdge(e);
        }
    }

    public void addEdge(Edge edge){
        edgeMap.add(edge);
        //add to database
        edgeDatabase.addEdge(edge.getStartNode().getLongName(),edge.getEndNode().getLongName());//Todo modify line
        System.out.println("Edge Added successfully");

    }
    public ArrayList<Node> getNodeByFloor(String floor){
        ArrayList<Node> ans = new ArrayList<Node>();
        for(Node n : nodeMap){
            if(n.getFloor()==floor){
                ans.add(n);
            }
        }
        return ans;

    }

    public void deleteNode(Node node){
        //delete the node first
        nodeMap.remove(node);
        nodeDatabase.deleteNode(node);

        //delete the edges associated with it
        for(Edge e : node.getConnections() ){
            edgeMap.remove(e);
            edgeDatabase.deleteAnyEdge(e.getId());
        }

    }



    public void EditNode(String id,Node node){
        //replace the informations of the node with that of the new node
    }



    public void DeleteEdge(Edge edge){
        edgeMap.remove(edge);
        edgeDatabase.deleteAnyEdge(edge.getId());

    }

    public void loadMap(){

    }

    public void saveMapToCSV(){

    }

    public static Collection<Node> getNodes(){
        return nodeMap;
    }

}