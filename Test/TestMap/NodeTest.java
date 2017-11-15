package TestMap;

import a_star.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NodeTest {
    public NodeTest(){}

    private Node N1 = new Node("Node 1", "1", "Bathroom", 1, 0, 100);
    private Node N1Copy = new Node("Node 1", "1", "Bathroom", 1, 0, 100);
    private Node N2 = new Node("Node 2", "2", "Restaurant", 1, 100, 100);
    private Node N2Copy = new Node("Node 2", "2", "Restaurant", 1, 100, 100);
    private Node N3 = new Node("Node 3", "3", "Desk", 1, 200, 200);

    @Before
    public void initialize(){
        // Add connections for Node 1
        N1.addConnection(N2);
        N1.addConnection(N3);

        // Add connections for Node 2
        N2.addConnection(N1);
        N2.addConnection(N3);

        // Add connections for Node 3
        N3.addConnection(N2);
        N3.addConnection(N1);
    }

    @Test
    public void testNodeParameters(){
        assertEquals("Node 1", N1.getName());
        assertEquals("1", N1.getID());
        assertEquals("Bathroom", N1.getType());
        assertEquals(1, N1.getFloor());
        assertEquals(0, N1.getX());
        assertEquals(100, N1.getY());
    }

    //Test toString() override
    @Test
    public void testNodeToString1(){
        assertEquals("Node 1", N1.toString());
    }
    @Test
    public void testNodeToString2(){
        assertEquals("Node 2", N2.toString());
    }
    @Test
    public void testNodeToString3(){
        assertEquals("Node 3", N3.toString());
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

    //Test getCostFromNode()
    @Test
    //Node 1 to Node 2
    public void testGetCostFromNode1(){
        assertEquals(sqrt(10000), N1.getCostFromNode(N2), 1);
    }
    @Test
    //Node 2 to Node 1
    public void testGetCostFromNode1Reverse(){
        assertEquals(sqrt(10000), N2.getCostFromNode(N1), 1);
    }
    @Test
    //Node 1 to Node 3
    public void testGetCostFromNode2(){
        assertEquals(sqrt(50000), N1.getCostFromNode(N3), 1);
    }
    @Test
    //Node 3 to Node 1
    public void testGetCostFromNode2Reverse(){
        assertEquals(sqrt(50000), N3.getCostFromNode(N1), 1);
    }

    //Test getEuclideanDistance
    @Test
    //Node 1 to Node 2
    public void testGetEuclideanDistance1(){
        assertEquals(sqrt(10000), N1.getEuclidianDistance(N1,N2), 1);
    }
    @Test
    //Node 2 to Node 1
    public void testGetEuclideanDistance1Reverse(){
        assertEquals(sqrt(10000), N2.getEuclidianDistance(N2, N1), 1);
    }
    @Test
    //Node 1 to Node 3
    public void testGetEuclideanDistance2(){
        assertEquals(sqrt(50000), N1.getEuclidianDistance(N1, N3), 1);
    }
    @Test
    public void testGetEuclideanDistance2Reverse(){
        assertEquals(sqrt(50000), N3.getEuclidianDistance(N3, N1), 1);
    }

}
