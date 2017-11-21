/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Nicholas Fajardo, Tyrone Patterson, Leo Grande, Meghana Bhatia
* The following code
*/

package TestMap;

import exceptions.InvalidNodeException;
import map.Edge;
import map.HospitalMap;
import map.Node;
import org.junit.Before;
import org.junit.Test;
import search.AStarSearch;

import java.util.ArrayList;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;

public class HospitalMapTest {

    //set up example map for testing
    public HospitalMapTest() {}

    private HospitalMap easyMap = HospitalMap.getMap();
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

    //nodes for easyMap
    private Node A = new Node("A", "0", "100", "L1", "Tower", "Bathroom", "LongA", "ShortA", "team");
    private Node B = new Node("B", "100","100", "L1", "Tower", "Restaurant", "LongB", "ShortB", "team");
    private Node C = new Node("C", "200", "200", "L1", "Tower", "Desk", "LongC", "ShortC", "team");
    private Node D = new Node("D", "100", "200", "L1", "Tower", "Desk", "LongD", "ShortD", "team");

    private AStarSearch search = new AStarSearch();

    @Before
    public void initialize() {
        //Add just a few nodes to easyMap
        easyMap.addNode(A);
        easyMap.addNode(B);
        easyMap.addNode(C);
        easyMap.addNode(D);

        //create edges between nodes and add to map
        easyMap.addEdge(A, B);
        easyMap.addEdge(B, C);
        easyMap.addEdge(B, D);
        easyMap.addEdge(C, D);

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
        N6.addConnection(N7);

        //Add connections for nodeSeven
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

    //------------------------BEGIN TEST HOSPITALMAP PARAMS------------------------//
    @Test
    public void testGetNodeMap(){
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(A);
        nodes.add(B);
        nodes.add(C);
        nodes.add(D);

        assertEquals(nodes, easyMap.getNodeMap());
    }

    @Test
    public void testGetEdgeMap(){
        Edge AB = new Edge(A, B);
        Edge BC = new Edge(B, C);
        Edge BD = new Edge(B, D);
        Edge CD = new Edge(C, D);
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(AB);
        edges.add(BC);
        edges.add(BD);
        edges.add(CD);

        assertEquals(edges, easyMap.getEdgeMap());
    }
    //------------------------END TEST HOSPITALMAP PARAMS------------------------//

    @Test
    public void testGetNodesByFloor(){
        easyMap.addNode("E", "0", "0", "3", "Tower", "Stairs", "LongE", "ShortE", "team");
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(A);
        nodes.add(B);
        nodes.add(C);
        nodes.add(D);

        assertEquals(nodes, easyMap.getNodesByFloor(2));
    }

    /*
    @Test
    public void testSetSearchStrategy(){
        map.setSearchStrategy(what goes here?);
    }
    */

    @Test
    public void testEditNode(){
        easyMap.editNode(A, "0", "100", "L1", "Tower", "Elevator", "LongA", "ShortA");

        assertEquals("Elevator", A.getType());
    }

    @Test
    public void testRemoveNode(){
        easyMap.removeNode(A);
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(B);
        nodes.add(C);
        nodes.add(D);

        assertEquals(nodes, easyMap.getNodeMap());
    }

    //fails - removeNode does not remove corresponding edge from edge map
    @Test
    public void testRemoveNode2(){
        easyMap.removeNode(A);
        Edge BC = new Edge(B, C);
        Edge BD = new Edge(B, D);
        Edge CD = new Edge(C, D);
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(BC);
        edges.add(BD);
        edges.add(CD);

        assertEquals(edges, easyMap.getEdgeMap());
    }

    @Test
    public void testRemoveNode3(){
        easyMap.removeNode(A);

        //show that an edge has been deleted
        assertEquals(2, B.getConnections().size());
        //show that the other nodes attached to B are NOT A
        assertEquals(C, B.getConnections().get(0).getNodeTwo());
        assertEquals(D, B.getConnections().get(1).getNodeTwo());
    }

    @Test
    public void testRemoveEdge(){

    }

    @Test
    public void testEditEdge(){

    }

    @Test
    public void testFindNode(){

    }

    //------------------------BEGIN TEST findPath()------------------------//
    @Test
    //Node 1 to Node 9
    public void testMap1() throws InterruptedException {
        String answer = null;
        try {
            answer = search.findPath(N1, N9).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short1, Short2, Short3, Short9]", answer);
    }

    @Test
    //Node 9 to Node 1
    //Fails: gets [Short9, Short3, Short2, Short1]
    public void testMap1Reverse() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N9, N1).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short9, Short3, Short2, Short1]", answer);
}

    @Test
    //Node 1 to Node 10
    public void testMap2() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N1, N10).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short1, Short6, Short7, Short10]", answer);
    }

    @Test
    //Node 10 to Node 1
    public void testMap2Reverse() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N10, N1).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short10, Short7, Short6, Short1]", answer);
    }

    @Test
    //Node 1 to Node 12
    public void testMap3() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N1, N12).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short1, Short6, Short7, Short10, Short11, Short14, Short13, Short12]", answer);
    }

    @Test
    //Node 12 to Node 1
    public void testMap3Reverse() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N12, N1).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short12, Short13, Short14, Short11, Short10, Short7, Short6, Short1]", answer);
    }

    @Test
    //Node 1 to Node 20
    public void testMap4() throws InterruptedException {
        String answer = null;
        try {
            answer = search.findPath(N1, N20).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short1, Short6, Short7, Short10, Short11, Short14, Short13, Short17, Short20]", answer);
    }

    @Test
    //Node 20 to Node 1
    public void testMap4Reverse() throws InterruptedException{
        String answer = null;
        try {
            answer = (search.findPath(N20, N1).toString());
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short20, Short17, Short13, Short14, Short11, Short10, Short7, Short6, Short1]", answer);
    }

    @Test
    //Node 1 to Node 23
    public void testMap5() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N1, N23).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short1, Short6, Short7, Short10, Short11, Short14, Short18, Short21, Short23]", answer);
    }

    @Test
    //Node 23 to Node 1
    //Fails: gets [Short23, Short21, Short18, Short14, Short15, Short9, Short3, Short2, Short1]
    public void testMap5Reverse() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N23, N1).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short23, Short21, Short18, Short14, Short15, Short9, Short3, Short2, Short1]", answer);
    }

    @Test
    //Node 6 to Node 15
    public void testMap6() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N6, N15).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short6, Short7, Short8, Short9, Short15]", answer);
    }

    @Test
    //Node 15 to Node 6
    //Fails: gets [Short15, Short9, Short8, Short7, Short6]
    public void testMap6Reverse() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N15, N6).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short15, Short9, Short8, Short7, Short6]", answer);
    }

    @Test
    //Node 3 to Node 22
    public void testMap7() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N3, N22).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short3, Short9, Short15, Short14, Short18, Short19, Short22]", answer);
    }

    @Test
    //Node 22 to Node 3
    public void testMap7Reverse() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N22, N3).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short22, Short19, Short18, Short14, Short15, Short9, Short3]", answer);
    }

    @Test
    //Node 4 to Node 2
    public void testMap8() throws InterruptedException {
        String answer = null;
        try {
            answer = search.findPath(N4, N2).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short4, Short7, Short6, Short1, Short2]", answer);
    }

    @Test
    //Node 2 to Node 4
    public void testMap8Reverse() throws InterruptedException {
        String answer = null;
        try {
            answer = search.findPath(N2, N4).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short2, Short1, Short6, Short7, Short4]", answer);
    }

    @Test
    //Node 7 to Node 3
    public void testMap9() throws InterruptedException {
        String answer = null;
        try {
            answer = search.findPath(N7, N3).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short7, Short8, Short9, Short3]", answer);
    }

    @Test
    //Node 3 to Node 7
    public void testMap9Reverse() throws InterruptedException {
        String answer = null;
        try {
            answer = search.findPath(N3, N7).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short3, Short9, Short8, Short7]", answer);
    }

    @Test
    //Node 7 to Node 3
    public void testMap9Repeat() throws InterruptedException{
        String answer1 = null;
        try {
            answer1 = search.findPath(N7, N3).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        String answer2 = null;
        try {
            answer2 = search.findPath(N7, N3).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer1);

        assertEquals(answer1, answer2);
        assertEquals("[Short7, Short8, Short9, Short3]", answer1);
    }

    @Test
    //Node 6 to Node 6
    public void testMap1Same() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N6, N6).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short6]", answer);
    }

    @Test
    //Node 18 to Node 18
    public void testMap2Same() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N18, N18).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short18]", answer);
    }

    @Test
    //Node 9 to Node 9
    public void testMap3Same() throws InterruptedException{
        String answer = null;
        try {
            answer = search.findPath(N9, N9).toString();
        } catch (InvalidNodeException e) {
            e.printStackTrace();
        }
        System.out.println(answer);

        assertEquals("[Short9]", answer);
    }
    //------------------------END TEST findPath()------------------------//

    @Test
    public void testFindNearest(){

    }

    @Test
    public void testFindPathPitStop(){

    }

    //------------------------BEGIN TEST getEuclideanDistance()------------------------//
    @Test
    //Node 3 to Node 14
    public void testGetEuclideanDistance1(){
        assertEquals(sqrt(170000), search.getEuclideanDistance(N3, N14), .01);
    }

    @Test
    //Node 14 to Node 3
    public void testGetEuclideanDistance1Reverse(){
        assertEquals(sqrt(170000), search.getEuclideanDistance(N14, N3), .01);
    }

    @Test
    //Node 11 to Node 23
    public void testGetEuclideanDistance2(){
        assertEquals(sqrt(260000), search.getEuclideanDistance(N11, N23), .01);
    }

    @Test
    //Node 23 to Node 11
    public void testGetEuclideanDistance2Reverse(){
        assertEquals(sqrt(260000), search.getEuclideanDistance(N23, N11), .01);
    }

    @Test
    //Node 1 to Node 6
    public void testGetEuclideanDistance3(){
        assertEquals(sqrt(40000), search.getEuclideanDistance(N1, N6), .01);
    }

    @Test
    //Node 6 to Node 1
    public void testGetEuclideanDistance3Reverse(){
        assertEquals(sqrt(40000), search.getEuclideanDistance(N6, N1), .01);
    }

    @Test
    //Node 5 to Node 5
    public void testGetEuclideanDistanceSame(){
        assertEquals(0, search.getEuclideanDistance(N5, N5), .01);
    }
    //------------------------END TEST getEuclideanDistance()------------------------//
}

