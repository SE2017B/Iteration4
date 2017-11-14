import a_star.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

public class HospitalMapTest {

    public HospitalMapTest() {
    }

    private HospitalMap smallArea = new HospitalMap();
    private Node N1 = new Node("Node 1", "111", "Bathroom", 8, 1, 0, 4);
    private Node N2 = new Node("Node 2", "222", "Room", 5, 1, 0, 2);
    private Node N3 = new Node("Node 3", "333", "Desk", 3, 1, 0, 0);
    private Node N4 = new Node("Node 4", "444", "Office", 6, 1, 1, 3);
    private Node N5 = new Node("Node 5", "555", "Stairs", 3, 1, 1, 1);
    private Node N6 = new Node("Node 6", "666", "Bathrooom", 5, 1, 2, 4);
    private Node N7 = new Node("Node 7", "555", "Stairs", 4, 1, 2, 3);
    private Node N8 = new Node("Node 8", "666", "Bathrooom", 2, 1, 2, 1);
    private Node N9 = new Node("Node 9", "555", "Elevator", 0, 1, 2, 0);

    @Before
    public void initialize() {
        //Creating nodes for the map

        //Add connections for nodeOne
        N1.addConnection(2, N2);
        N1.addConnection(2, N6);

        //Add connections for nodeTwo
        N2.addConnection(2, N3);
        N2.addConnection(2, N1);

        //Add connections for nodeThree
        N3.addConnection(2, N2);
        N3.addConnection(3, N9);

        //Add connections for nodeFour
        N4.addConnection(2, N7);
        N4.addConnection(2, N5);

        //Add connections for nodeFive
        N5.addConnection(2, N8);
        N5.addConnection(2, N4);

        //Add connections for nodeSix
        N6.addConnection(2, N1);
        N6.addConnection(1, N7);

        //Add connections for nodeSeven
        N7.addConnection(2, N4);
        N7.addConnection(1, N6);
        N7.addConnection(2, N8);

        //Add connections for nodeEight
        N8.addConnection(2, N7);
        N8.addConnection(1, N9);
        N8.addConnection(2, N5);

        //Add connections for nodeNine
        N9.addConnection(3, N3);
        N9.addConnection(1, N8);
    }

    @Test
    public void testMap() throws InterruptedException {
        smallArea.setStart(N1);
        smallArea.setEnd(N9);

        String answer = smallArea.findPath(N9).toString();
        System.out.println(answer);
    }

    @Test
    public void testMap1() throws InterruptedException {
        smallArea.setStart(N3);
        smallArea.setEnd(N6);

        String answer = smallArea.findPath(N6).toString();
        System.out.println(answer);

    }
    @Test
    public void testMap2() throws InterruptedException {
        smallArea.setStart(N4);
        smallArea.setEnd(N2);

        String answer = smallArea.findPath(N2).toString();
        System.out.println(answer);
    }


}
