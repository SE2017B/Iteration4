//package TestTree;
//
//import search.AStarSearch;
//import map.HospitalMap;
//import map.Node;
//import org.junit.Before;
//import org.junit.Test;
//import service.FoodService;
//import service.Staff;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertTrue;
//
//
//public class MainTree {
//    private ArrayList<Node> Nodes;
//    private Staff Chidinma;
//    private FoodService foodService;
//    private HospitalMap Map = new HospitalMap();
//    //nodes
//    private Node MainNode;
//    private Node node1;
//    private Node node2;
//    private Node node3;
//    private Node node4;
//    @Before
//    public void initialize(){
//        foodService= new FoodService();
//        Chidinma= new Staff("bob", "bobby", "cook", "Bobby Bob", 1, foodService);
//        Nodes = new ArrayList<>();
//
//        //A create a bunch of nodes
//        MainNode = new Node();
//        node1=new Node();
//        node2=new Node();
//        node3=new Node();
//        node4=new Node();
//
//        //create sibling nodes
//        MainNode.addConnection(node1);
//        MainNode.addConnection(node2);
//        MainNode.addConnection(node3);
//        MainNode.addConnection(node4);
//
//        //add all nodes to map
//        Map.addNode("0",MainNode);
//        Map.addNode("1",node1);
//        Map.addNode("2",node2);
//        Map.addNode("3",node3);
//        Map.addNode("4",node4);
//
//    }
//    //Load objects from database
//    @Test
//    public void getNodes(){
//        Nodes=Map.getNodesForSearch();
//        assertTrue(Nodes.size()>0);
//    }
//    //maybe Load Object from CSV
//    @Test
//    public void getCSV(){
//
//    }
//
//    //Object Nodes can access sibling Node
//    @Test
//    public void canAccessSibling(){
//        assertTrue(MainNode.getConnections().containsValue(node1));
//    }
//
//    //Object can not access non sibling nodes
//    @Test
//    public void SiblingAccess(){
//        assertTrue(node2.getConnections().containsValue(MainNode));
//    }
//
//    //Path finding test
//    public void findPath(){
//        AStarSearch search = new AStarSearch();
//        ArrayList<Node> ans = new ArrayList<>();
//        assertTrue(search.findPath(MainNode,node3)==ans);//Todo: Adjust code
//    }
//    //display path
//    public void displayPath(){
//
//    }
//
//
//}
