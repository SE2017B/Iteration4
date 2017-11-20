/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Nicholas Fajardo, Tyrone Patterson, Leo Grande, Meghana Bhatia
* The following code
*/

//testing file for Node testing each method
package TestMap;

import map.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static map.FloorNumber.FLOOR_LONE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NodeTest {
    public NodeTest(){}

    private Node N1 = new Node("A1", "0", "100", "L1", "Tower", "Bathroom", "Long1", "Short1", "team");
    private Node N1Copy = new Node("A1", "0", "100", "L1", "Tower", "Bathroom", "Long1", "Short1", "team");
    private Node N2 = new Node("A2", "100","100", "L1", "Tower", "Restaurant", "Long2", "Short2", "team");
    private Node N2Copy = new Node("A2", "100","100", "L1", "Tower", "Restaurant", "Long2", "Short2", "team");
    private Node N3 = new Node("A3", "200", "200", "L1", "Tower", "Desk", "Long3", "Short3", "team");
    private Node N4 = new Node("A4", "200", "200", "3", "Tower", "Desk", "Long4", "Short4", "team");

    private Edge N1N2Edge;
    private Edge N2N3Edge;
    private Edge N3N1Edge;

    @Before
    public void initialize(){
        N1N2Edge = new Edge(N1, N2);
        N2N3Edge = new Edge(N2, N3);
        N3N1Edge = new Edge(N3, N1);
    }

    //---------------------------BEGIN TEST NODE PARAMS-------------------------------------------//
    @Test
    public void testID(){
        assertEquals("A1", N1.getID());
    }

    @Test
    public void testGetX(){
        assertEquals(0, N1.getX());
    }

    @Test
    public void testGetXString(){
        assertEquals("0", N1.getXString());
    }

    @Test
    public void testGetY(){
        assertEquals(100, N1.getY());
    }

    @Test
    public void testGetYString(){
        assertEquals("100", N1.getYString());
    }

    //tests enum as well
    @Test
    public void testGetFloor(){
        assertEquals(FLOOR_LONE, N1.getFloor());
    }

    @Test
    public void testGetBuilding() {
        assertEquals("Tower", N1.getBuilding());
    }

    @Test
    public void testGetType() {
        assertEquals("Bathroom", N1.getType());
    }

    @Test
    public void testGetLongName() {
        assertEquals("Long1", N1.getLongName());
    }

    @Test
    public void testGetShortName() {
        assertEquals("Short1", N1.getShortName());
    }

    @Test
    public void testGetTeam(){
        assertEquals("team", N1.getTeam());
    }
    //---------------------------END TEST EDGE PARAMS-------------------------------------------//

    @Test
    public void testAddConnectionNode(){
        //make an edge between N1 and N4
        N1.addConnection(N4);

        //check that the last edge added to array list contains N4
        assertEquals(N1.getConnections().get(2).getNodeTwo(), N4);
    }

    //"No suitable driver found for jdbc:derby:teamHDB;create=true
    @Test
    public void testDeleteNode(){
        N1.deleteNode();
        ArrayList<Edge> edges = new ArrayList<>();

        //check that N1 no longer has any edges
        assertEquals(edges, N1.getConnections());
    }

    //----------------------BEGIN TEST toString() OVERRIDE-------------------------------------//
    @Test
    public void testNodeToString1(){
        assertEquals("Short1", N1.toString());
    }
    @Test
    public void testNodeToString2(){
        assertEquals("Short2", N2.toString());
    }
    @Test
    public void testNodeToString3(){
        assertEquals("Short3", N3.toString());
    }
    //------------------------END TEST toString() OVERRIDE-------------------------------------//

    ////----------------------BEGIN TEST equals() OVERRIDE-------------------------------------//
    @Test
    public void testNodeEquals1(){
        assertTrue(N1.equals(N1Copy));
    }
    @Test
    public void testNodeEquals2(){
        assertTrue(N2.equals(N2Copy));
    }
    @Test
    public void testNodeEqualsFail1(){
        assertFalse(N1.equals(N2));
    }
    @Test
    public void testNodeEqualsFail2(){
        assertFalse(N2.equals(N1Copy));
    }
    //-------------------------END TEST equals() OVERRIDE-------------------------------------//

    //------------------------BEGIN TEST getEuclideanDistance()-------------------------------//
    @Test
    public void testEuclidean1D(){
        assertEquals(N1.getEuclidianDistance(N2), 100.0, 0.01);
    }

    @Test
    public void testEuclidean2D(){
        assertEquals(N2.getEuclidianDistance(N3), 141.42, 0.05);
    }

    @Test
    public void testEuclidean3D(){
    assertEquals(N1.getEuclidianDistance(N4), 374.17, 0.05);
    }
    //------------------------END TEST getEuclideanDistance()-------------------------------//
}
