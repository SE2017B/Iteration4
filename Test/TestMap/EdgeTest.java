/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration2
* Original author(s): Erika Snow
* The following code
*/

package TestMap;

import exceptions.InvalidNodeException;
import map.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class EdgeTest {
    public EdgeTest(){}

    private Node A;
    private Node B;
    private Node C;
    private Node D;
    private Edge ABEdge;
    private Edge BCEdge;
    private Edge CAEdge;

    @Before
    public void initialize(){
        A = new Node("A1", "0", "100", "L1", "Tower", "Bathroom", "Long1", "Short1", "team");
        B = new Node("A2", "100","100", "L1", "Tower", "Restaurant", "Long2", "Short2", "team");
        C = new Node("A3", "200", "200", "L1", "Tower", "Desk", "Long3", "Short3", "team");
        D = new Node("A4", "200", "200", "3", "Tower", "Desk", "Long4", "Short4", "team");

        ABEdge = new Edge(A, B);
        BCEdge = new Edge(B, C);
        CAEdge = new Edge(C, A);
    }

    //---------------------------BEGIN TEST EDGE PARAMS-------------------------------------------//
    @Test
    public void testNode1(){
        assertEquals(A, ABEdge.getNodeOne());
    }

    @Test
    public void testNode2(){
        assertEquals(B, ABEdge.getNodeTwo());
    }

    @Test
    public void testID(){
        assertEquals("A1:A2", ABEdge.getID());
    }

    @Test
    public void testCost(){
        assertEquals(100, ABEdge.getCost(), 0.01);
    }

    @Test
    public void testSetUp(){
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(ABEdge);
        edges.add(CAEdge);

        assertEquals(edges, A.getConnections());
    }
    //---------------------------END TEST EDGE PARAMS-------------------------------------------//

    @Test
    public void testGetOtherNode() throws InvalidNodeException{
        assertEquals(B, ABEdge.getOtherNode(A));
    }

    @Test
    public void testgetDir() throws InvalidNodeException{
        ABEdge.setDirOne("left");

        assertEquals("left", ABEdge.getDir(A));
    }

    @Test(expected = InvalidNodeException.class)
    public void testGetDirFail() throws InvalidNodeException{
        ABEdge.getDir(C);
    }

    //---------------------------BEGIN TEST replaceNode()-------------------------------------------//
    @Test
    public void testNodeReplaced() throws InvalidNodeException{
        //replace B node with D node
        ABEdge.replaceNode(B, D);

        assertEquals(D, ABEdge.getNodeTwo());
    }

    @Test
    public void testConnectionAdded() throws InvalidNodeException{
        //replace B node with D node
        ABEdge.replaceNode(B, D);
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(ABEdge);

        //check that ABEdge was added to D's list of edges
        assertEquals(edges, D.getConnections());
    }

    @Test
    public void testConnectionRemoved() throws InvalidNodeException{
        //replace B node with D node
        ABEdge.replaceNode(B, D);
        ArrayList<Edge> edges = new ArrayList<>();
        //ABEdge should be removed from B's connections
        edges.add(BCEdge);

        //check that ABEdge was removed from B's list of edges
        assertEquals(edges, B.getConnections());
    }

    @Test(expected = InvalidNodeException.class)
    public void testReplaceNodeFail() throws InvalidNodeException{
        ABEdge.replaceNode(C, D);
    }
    //---------------------------END TEST EDGE replaceNode()-------------------------------------------//

    @Test
    public void testDeleteConnection(){
        ABEdge.deleteConnection();
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(BCEdge);

        assertEquals(edges, B.getConnections());
    }
}
