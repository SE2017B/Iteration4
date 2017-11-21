/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Nicholas Fajardo, Tyrone Patterson, Leo Grande, Meghana Bhatia
* The following code
*/

//testing file for Node testing each method
package TestMap;

import exceptions.InvalidNodeException;
import map.FloorNumber;
import map.Node;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeTest {
    public NodeTest(){}

    private Node N1 = new Node("A1", "0", "100", "L1", "Tower", "Bathroom", "Long1", "Short1");
    private Node N1Copy = new Node("A1", "0", "100", "L1", "Tower", "Bathroom", "Long1", "Short1");
    private Node N2 = new Node("A2", "100","100", "L1", "Tower", "Restaurant", "Long2", "Short2");
    private Node N2Copy = new Node("A2", "100","100", "L1", "Tower", "Restaurant", "Long2", "Short2");
    private Node N3 = new Node("A3", "200", "200", "L1", "Tower", "Desk", "Long3", "Short3");
    private Node N4 = new Node("A4", "200", "200", "3", "Tower", "Desk", "Long4", "Short4");

    @Before
    public void initialize(){

    }

    /*
    @Test
    public void testNodeParameters(){
        assertEquals("A1", N1.getID());
        assertEquals(0, N1.getX());
        assertEquals("0", N1.getXString());
        assertEquals(100, N1.getY());
        assertEquals("100", N1.getYString());
        assertEquals("L1", N1.getFloor());
        assertEquals("Tower", N1.getBuilding());
        assertEquals("Bathroom", N1.getType());
        assertEquals("Long1", N1.getLongName());
        assertEquals("Short1", N1.getShortName());
    }
    */

    @Test
    public void testID(){
        assertEquals("A1", N1.getID());
    }

    //Test toString() override
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

    //Test equals() override
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

    @Test
    public void testEuclidean1D(){
        assertEquals(N1.getEuclidianDistance(N2), 100.0, 0.01);
    }

    @Test
    public void testEuclidean2D(){
        assertEquals(N2.getEuclidianDistance(N3), 223.61, 0.05);
    }

    @Test
    public void testEuclidean3D(){
    assertEquals(N1.getEuclidianDistance(N4), 374.17, 0.05);
    }

    //should be the same as Euclidean distance, for now
    @Test
    public void testCost() throws InvalidNodeException{
    //assertEquals(N1.getCostFromNode(N2), 100, 0.01);
    }

    //test the enum
    @Test
    public void testFloorNumber(){
        assertEquals(FloorNumber.FLOOR_LONE, N1.getFloor());
    }
}
