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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static map.FloorNumber.FLOOR_LONE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EdgeTest {
    public EdgeTest(){}

    private Node A;
    private Node B;
    private Node C;
    private Edge ABEdge;
    private Edge BCEdge;
    private Edge CAEdge;

    @Before
    public void initialize(){
        A = new Node("A1", "0", "100", "L1", "Tower", "Bathroom", "Long1", "Short1", "team");
        B = new Node("A2", "100","100", "L1", "Tower", "Restaurant", "Long2", "Short2", "team");
        C = new Node("A3", "200", "200", "L1", "Tower", "Desk", "Long3", "Short3", "team");

        ABEdge = new Edge(A, B);
        BCEdge = new Edge(B, C);
        CAEdge = new Edge(C, A);
    }

    //test Edge parameters
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

    @Test
    public void testGetOtherNode(){
        assertEquals(B, ABEdge.getOtherNode(A));
    }

    @Test
    public void testgetDir() throws InvalidNodeException{
        ABEdge.setDirOne("left");

        assertEquals("left", ABEdge.getDir(A));
    }

    @Test(expected = InvalidNodeException.class)
    public void testgetDirFail() throws InvalidNodeException{
        ABEdge.getDir(C);
    }

    @Test
    public void testReplaceNode(){

    }

    @Test
    public void testDeleteConnection(){

    }

}
