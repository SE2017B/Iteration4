package TestTree;

import a_star.HospitalMap;
import a_star.Node;
import controllers.PathController;
import org.junit.Before;
import org.junit.Test;
import service.FoodService;
import service.Staff;

import static org.junit.Assert.*;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;


public class MainTree {
    private ArrayList<Node> Nodes;
    private Staff Chidinma;
    private FoodService foodService;
    private HospitalMap Map = new HospitalMap();
    //nodes
    Node MainNode;
    Node node1;
    Node node2;
    Node node3;
    Node node4;
    @Before
    public void initialize(){
        foodService= new FoodService();
        Chidinma= new Staff("bob", "bobby", "cook", "Bobby Bob", 1, foodService);
        Nodes = new ArrayList<Node>();

        //A create a bunch of nodes
        MainNode = new Node();
        node1=new Node();
        node2=new Node();
        node3=new Node();
        node4=new Node();

        //create sibling nodes
        MainNode.addConnection(node1);
        MainNode.addConnection(node2);
        MainNode.addConnection(node3);
        MainNode.addConnection(node4);

        //add all nodes to map
        Map.addNode("0",MainNode);
        Map.addNode("1",node1);
        Map.addNode("2",node2);
        Map.addNode("3",node3);
        Map.addNode("4",node4);

    }
    //Load objects from database
    @Test
    public void getNodes(){
        Nodes=Map.getNodesAsArrayList();
        assertTrue(Nodes.size()>0);
    }
    //maybe Load Object from CSV
    @Test
    public void getCSV(){

    }

    //Object Nodes can access sibling Node
    @Test
    public void canAccessSibling(){
        assertTrue(MainNode.getConnections().contains(node1));
    }

    //Object can not access non sibling nodes
    @Test
    public void SiblingAccess(){
        assertTrue(node2.getConnections().contains(MainNode));
    }

    //Path finding test
    public void findPath(){
        ArrayList<Node> ans = new ArrayList<>();
        ans.add(MainNode);
        ans.add(node3);
        assertTrue(Map.findPath(MainNode,node3)==ans);//Todo: Adjust code
    }
    //display path
    public void displayPath(){

    }


}
