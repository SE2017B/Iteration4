package TestSearch;

import map.HospitalMap;
import map.Node;
import org.junit.Before;
import org.junit.Test;
import search.AStarSearch;
import search.BreadthFirstSearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BreadthFirstSearchTest {
    private HospitalMap map = new HospitalMap();
    private Node N1 = new Node("A1", "0", "400", "L1", "Tower", "Bathroom", "Long1", "Short1", "H");
    private Node N2 = new Node("A2", "0", "200", "L1", "Tower", "Desk", "Long2", "Short2", "H");
    private Node N3 = new Node("A3", "0", "0", "L1", "Tower", "Desk", "Long3", "Short3", "H");
    private Node N4 = new Node("A4", "100", "300", "L1", "Tower", "Desk", "Long4", "Short4", "H");
    private Node N5 = new Node("A5", "100", "100", "L1", "Tower", "Stairs", "Long5", "Short5", "H");
    private Node N6 = new Node("A6", "200", "400", "L1", "Tower", "Desk", "Long6", "Short6", "H");
    private Node N7 = new Node("A7", "200", "300", "L1", "Tower", "Desk", "Long7", "Short7", "H");
    private Node N8 = new Node("A8", "200", "100", "L1", "Tower", "Bathroom", "Long8", "Short8", "H");
    private Node N9 = new Node("A9", "200", "0", "L1", "Tower", "Elevator", "Long9", "Short9", "H");
    private Node N10 = new Node("A10", "300", "300", "L1", "Tower", "Desk", "Long10", "Short10", "H");
    private Node N11 = new Node("A11", "300", "100", "L1", "Tower", "Bathroom", "Long11", "Short11", "H");
    private Node N12 = new Node("A12", "400", "400", "L1", "Tower", "Desk", "Long12", "Short12", "H");
    private Node N13 = new Node("A13", "400", "100", "L1", "Tower", "Desk", "Long13", "Short13", "H");
    private Node N14 = new Node("A14", "400", "100", "L1", "Tower", "Desk", "Long14", "Short14", "H");
    private Node N15 = new Node("A15", "400", "0", "L1", "Tower", "Stairs", "Long15", "Short15", "H");
    private Node N16 = new Node("A16", "600", "400", "L1", "Tower", "Office", "Long16", "Short16", "H");
    private Node N17 = new Node("A17", "600", "300", "L1", "Tower", "Office", "Long17", "Short17", "H");
    private Node N18 = new Node("A18", "500", "100", "L1", "Tower", "Bathroom", "Long18", "Short18", "H");
    private Node N19 = new Node("A19", "500", "0", "L1", "Tower", "Stairs", "Long19", "Short19", "H");
    private Node N20 = new Node("A20", "700", "300", "L1", "Tower", "Elevator", "Long20", "Short20", "H");
    private Node N21 = new Node("A21", "700", "100", "L1", "Tower", "Stairs", "Long21", "Short21", "H");
    private Node N22 = new Node("A22", "700", "0", "L1", "Tower", "Desk", "Long22", "Short22", "H");
    private Node N23 = new Node("A23", "800", "0", "L1", "Tower", "Desk", "Long23", "Short23", "H");

    private BreadthFirstSearch search = new BreadthFirstSearch();

    @Before
    public void initialize() {
        //Creating nodes for the map

        //Add connections for nodeOne
        N1.addConnection(N2);
        N1.addConnection(N6);

        //Add connections for nodeTwo
        N2.addConnection(N3);

        //Add connections for nodeThree
        N3.addConnection(N9);

        //Add connections for nodeFour
        N4.addConnection(N7);
        N4.addConnection(N5);

        //Add connections for nodeFive
        N5.addConnection(N8);

        //Add connections for nodeSix

        //Add connections for nodeSeven
        N7.addConnection(N6);
        N7.addConnection(N8);
        N7.addConnection(N10);

        //Add connections for nodeEight
        N8.addConnection(N9);

        //Add connections for nodeNine
        N9.addConnection(N15);

        //Add connections for nodeTen
        N10.addConnection(N11);

        //Add connections for nodeEleven
        N11.addConnection(N14);

        //Add connections for nodeTwelve
        N12.addConnection(N13);
        N12.addConnection(N16);

        //Add connections for nodeThirteen
        N13.addConnection(N14);
        N13.addConnection(N17);

        //Add connections for nodeFourteen
        N14.addConnection(N15);
        N14.addConnection(N18);

        //Add connections for nodeFifteen

        //Add connections for nodeSixteen
        N16.addConnection(N17);

        //Add connections for nodeSeventeen
        N17.addConnection(N20);

        //Add connections for nodeEighteen
        N18.addConnection(N19);
        N18.addConnection(N21);

        //Add connections for nodeNineteen
        N19.addConnection(N22);

        //Add connections for nodeTwenty
        N20.addConnection(N21);

        //Add connections for nodeTwentyOne
        N21.addConnection(N23);
        N21.addConnection(N22);

        //Add connections for nodeTwentyTwo

        //Add connections for nodeTwentyThree
    }

    @Test public void testSearch1(){    //Node 1 to Node 10
        String answer = search.findPath(N1, N10).toString();
        System.out.println(answer);
        assertEquals("[Short1, Short6, Short7, Short10]", answer);
    }
    @Test public void testSearch1Reverse(){    //Node 10 to Node 1
        String answer = search.findPath(N10, N1).toString();
        System.out.println(answer);
        assertEquals("[Short10, Short7, Short6, Short1]", answer);
    }

    @Test public void testSearch2(){    //Node 2 to Node 17
        String answer = search.findPath(N2, N17).toString();
        System.out.println(answer);
        assertEquals("[Short2, Short3, Short9, Short15, Short14, Short13, Short17]", answer);
    }
    @Test public void testSearch2Reverse(){     //Node 17 to Node 2
        String answer = search.findPath(N17, N2).toString();
        System.out.println(answer);
        assertEquals("[Short17, Short13, Short14, Short15, Short9, Short3, Short2]", answer);
    }

    @Test public void testSearch3(){    //Node 4 to Node 23
        String answer = search.findPath(N4, N23).toString();
        System.out.println(answer);
        assertEquals("[Short4, Short5, Short8, Short9, Short15, Short14, Short18, Short21, Short23]", answer);
    }
    @Test public void testSearch3Reverse(){
        String answer = search.findPath(N23, N4).toString();
        System.out.println(answer);
        assertEquals("[Short23, Short21, Short18, Short14, Short11, Short10, Short7, Short4]", answer);
    }

    @Test public void testSearch4(){    //Node 5 to Node 19
        String answer = search.findPath(N5, N19).toString();
        System.out.println(answer);
        assertEquals("[Short5, Short8, Short7, Short10, Short11, Short14, Short18, Short19]", answer);
    }
    @Test public void testSearch4Reverse(){     //Node 19 to Node 5
        String answer = search.findPath(N19, N5).toString();
        System.out.println(answer);
        assertEquals("[Short19, Short18, Short14, Short11, Short10, Short7, Short8, Short5]", answer);
    }
}
