package TestSearch;

import map.Node;
import map.Path;
import org.junit.Before;
import org.junit.Test;
import search.AStarSearch;

import java.util.ArrayList;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;
/**
public class AStarSearchTest {
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

    private Node N1_2 = new Node("A1_2", "0", "400", "L2", "Tower", "Bathroom", "Long1_2", "Short1_2", "H");
    private Node N2_2 = new Node("A2_2", "0", "200", "L2", "Tower", "Desk", "Long2_2", "Short2_2", "H");
    private Node N3_2 = new Node("A3_2", "0", "0", "L2", "Tower", "Desk", "Long3_2", "Short3_2", "H");
    private Node N4_2 = new Node("A4_2", "100", "300", "L2", "Tower", "Desk", "Long4_2", "Short4_2", "H");
    private Node N5_2 = new Node("A5_2", "100", "100", "L2", "Tower", "Stairs", "Long5_2", "Short5_2", "H");
    private Node N6_2 = new Node("A6_2", "200", "400", "L2", "Tower", "Desk", "Long6_2", "Short6_2", "H");
    private Node N7_2 = new Node("A7_2", "200", "300", "L2", "Tower", "Desk", "Long7_2", "Short7_2", "H");
    private Node N8_2 = new Node("A8_2", "200", "100", "L2", "Tower", "Bathroom", "Long8_2", "Short8_2", "H");
    private Node N9_2 = new Node("A9_2", "200", "0", "L2", "Tower", "Elevator", "Long9_2", "Short9_2", "H");
    private Node N10_2 = new Node("A10_2", "300", "300", "L2", "Tower", "Desk", "Long10_2", "Short10_2", "H");
    private Node N11_2 = new Node("A11_2", "300", "100", "L2", "Tower", "Bathroom", "Long11_2", "Short11_2", "H");
    private Node N12_2 = new Node("A12_2", "400", "400", "L2", "Tower", "Desk", "Long12_2", "Short12_2", "H");
    private Node N13_2 = new Node("A13_2", "400", "100", "L2", "Tower", "Desk", "Long13_2", "Short13_2", "H");
    private Node N14_2 = new Node("A14_2", "400", "100", "L2", "Tower", "Desk", "Long14_2", "Short14_2", "H");
    private Node N15_2 = new Node("A15_2", "400", "0", "L2", "Tower", "Stairs", "Long15_2", "Short15_2", "H");
    private Node N16_2 = new Node("A16_2", "600", "400", "L2", "Tower", "Office", "Long16_2", "Short16_2", "H");
    private Node N17_2 = new Node("A17_2", "600", "300", "L2", "Tower", "Office", "Long17_2", "Short17_2", "H");
    private Node N18_2 = new Node("A18_2", "500", "100", "L2", "Tower", "Bathroom", "Long18_2", "Short18_2", "H");
    private Node N19_2 = new Node("A19_2", "500", "0", "L2", "Tower", "Stairs", "Long19_2", "Short19_2", "H");
    private Node N20_2 = new Node("A20_2", "700", "300", "L2", "Tower", "Elevator", "Long20_2", "Short20_2", "H");
    private Node N21_2 = new Node("A21_2", "700", "100", "L2", "Tower", "Stairs", "Long21_2", "Short21_2", "H");
    private Node N22_2 = new Node("A22_2", "700", "0", "L2", "Tower", "Desk", "Long22_2", "Short22_2", "H");
    private Node N23_2 = new Node("A23_2", "800", "0", "L2", "Tower", "Desk", "Long23_2", "Short23_2", "H");

    private AStarSearch search = new AStarSearch();

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
        N9.addConnection(N9_2);

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
        N20.addConnection(N20_2);

        //Add connections for nodeTwentyOne
        N21.addConnection(N23);
        N21.addConnection(N22);

        //Add connections for nodeTwentyTwo

        //Add connections for nodeTwentyThree

        //Add connections for nodeOne_1
        N1_2.addConnection(N2_2);
        N1_2.addConnection(N6_2);

        //Add connections for nodeTwo_2
        N2_2.addConnection(N3_2);

        //Add connections for nodeThree_2
        N3_2.addConnection(N9_2);

        //Add connections for nodeFour_2
        N4_2.addConnection(N7_2);
        N4_2.addConnection(N5_2);

        //Add connections for nodeFive_2
        N5_2.addConnection(N8_2);

        //Add connections for nodeSix_2

        //Add connections for nodeSeven_2
        N7_2.addConnection(N6_2);
        N7_2.addConnection(N8_2);
        N7_2.addConnection(N10_2);

        //Add connections for nodeEight_2
        N8_2.addConnection(N9_2);

        //Add connections for nodeNine_2
        N9_2.addConnection(N15_2);

        //Add connections for nodeTen_2
        N10_2.addConnection(N11_2);

        //Add connections for nodeEleven_2
        N11_2.addConnection(N14_2);

        //Add connections for nodeTwelve_2
        N12_2.addConnection(N13_2);
        N12_2.addConnection(N16_2);

        //Add connections for nodeThirteen_2
        N13_2.addConnection(N14_2);
        N13_2.addConnection(N17_2);

        //Add connections for nodeFourteen_2
        N14_2.addConnection(N15_2);
        N14_2.addConnection(N18_2);

        //Add connections for nodeFifteen_2

        //Add connections for nodeSixteen_2
        N16_2.addConnection(N17_2);

        //Add connections for nodeSeventeen_2
        N17_2.addConnection(N20_2);

        //Add connections for nodeEighteen_2
        N18_2.addConnection(N19_2);
        N18_2.addConnection(N21_2);

        //Add connections for nodeNineteen_2
        N19_2.addConnection(N22_2);

        //Add connections for nodeTwenty_2
        N20_2.addConnection(N21_2);

        //Add connections for nodeTwentyOne_2
        N21_2.addConnection(N23_2);
        N21_2.addConnection(N22_2);

        //Add connections for nodeTwentyTwo_2

        //Add connections for nodeTwentyThree_2

    }

    // findPath tests

    @Test
    //Node 1 to Node 9
    public void testMap1() throws InterruptedException {
        String answer = search.findPath(N1, N9).toString();
        System.out.println(answer);
        String directions = search.findPath(N1, N9).findDirections().toString();
        System.out.println(directions);
        assertEquals("[Short1, Short2, Short3, Short9]", answer);
        assertEquals("[Go straight from Short1, Go straight through Short2, Take a left at this Short3, Stop at Short9]", directions);
    }
    @Test
    //Node 9 to Node 1
    public void testMap1Reverse() throws InterruptedException{
        String answer = search.findPath(N9, N1).toString();
        System.out.println(answer);
        assertEquals("[Short9, Short3, Short2, Short1]", answer);
    }

    @Test
    //Node 1 to Node 10
    public void testMap2() throws InterruptedException{
        String answer = search.findPath(N1, N10).toString();
        System.out.println(answer);
        assertEquals("[Short1, Short6, Short7, Short10]", answer);
    }
    @Test
    //Node 10 to Node 1
    public void testMap2Reverse() throws InterruptedException{
        String answer = search.findPath(N10, N1).toString();
        System.out.println(answer);
        assertEquals("[Short10, Short7, Short6, Short1]", answer);
    }

    @Test
    //Node 1 to Node 12
    public void testMap3() throws InterruptedException{
        String answer = search.findPath(N1, N12).toString();
        System.out.println(answer);
        String directions = search.findPath(N1, N12).findDirections().toString();
        System.out.println(directions);
        assertEquals("[Short1, Short6, Short7, Short10, Short11, Short14, Short13, Short12]", answer);
    }
    @Test
    //Node 12 to Node 1
    public void testMap3Reverse() throws InterruptedException{
        String answer = search.findPath(N12, N1).toString();
        System.out.println(answer);
        assertEquals("[Short12, Short13, Short14, Short11, Short10, Short7, Short6, Short1]", answer);
    }

    @Test
    //Node 12 to Node 1
    public void testMap3StepByStep() throws InterruptedException{
        ArrayList<String> answer = search.findPath(N12, N1).findDirections();
        System.out.println(answer);
        //assertEquals("[Short12, Short13, Short14, Short11, Short10, Short7, Short6, Short1]", answer);
    }

    @Test
    //Node 1 to Node 20
    public void testMap4() throws InterruptedException{
        String answer = search.findPath(N1, N20).toString();
        System.out.println(answer);
        assertEquals("[Short1, Short6, Short7, Short10, Short11, Short14, Short13, Short17, Short20]", answer);
    }
    @Test
    //Node 20 to Node 1
    public void testMap4Reverse() throws InterruptedException{
        String answer = (search.findPath(N20, N1).toString());
        System.out.println(answer);
        assertEquals("[Short20, Short17, Short13, Short14, Short11, Short10, Short7, Short6, Short1]", answer);
    }

    @Test
    //Node 1 to Node 23
    public void testMap5() throws InterruptedException{
        String answer = search.findPath(N1, N23).toString();
        System.out.println(answer);
        assertEquals("[Short1, Short6, Short7, Short10, Short11, Short14, Short18, Short21, Short23]", answer);
    }
    @Test
    //Node 23 to Node 1
    public void testMap5Reverse() throws InterruptedException{
        String answer = search.findPath(N23, N1).toString();
        System.out.println(answer);
        assertEquals("[Short23, Short21, Short18, Short14, Short15, Short9, Short3, Short2, Short1]", answer);
    }

    @Test
    //Node 6 to Node 15
    public void testMap6() throws InterruptedException{
        String answer = search.findPath(N6, N15).toString();
        System.out.println(answer);
        assertEquals("[Short6, Short7, Short8, Short9, Short15]", answer);
    }
    @Test
    //Node 15 to Node 6
    public void testMap6Reverse() throws InterruptedException{
        String answer = search.findPath(N15, N6).toString();
        System.out.println(answer);
        assertEquals("[Short15, Short9, Short8, Short7, Short6]", answer);
    }

    @Test
    //Node 3 to Node 22
    public void testMap7() throws InterruptedException{
        String answer = search.findPath(N3, N22).toString();
        System.out.println(answer);
        assertEquals("[Short3, Short9, Short15, Short14, Short18, Short19, Short22]", answer);
    }
    @Test
    //Node 22 to Node 3
    public void testMap7Reverse() throws InterruptedException{
        String answer = search.findPath(N22, N3).toString();
        System.out.println(answer);
        assertEquals("[Short22, Short19, Short18, Short14, Short15, Short9, Short3]", answer);
    }

    @Test
    //Node 4 to Node 2
    public void testMap8() throws InterruptedException {
        String answer = search.findPath(N4, N2).toString();
        System.out.println(answer);
        assertEquals("[Short4, Short7, Short6, Short1, Short2]", answer);
    }
    @Test
    //Node 2 to Node 4
    public void testMap8Reverse() throws InterruptedException {
        String answer = search.findPath(N2, N4).toString();
        System.out.println(answer);
        assertEquals("[Short2, Short1, Short6, Short7, Short4]", answer);
    }

    @Test
    //Node 7 to Node 3
    public void testMap9() throws InterruptedException {
        String answer = search.findPath(N7, N3).toString();
        System.out.println(answer);
        assertEquals("[Short7, Short8, Short9, Short3]", answer);
    }
    @Test
    //Node 3 to Node 7
    public void testMap9Reverse() throws InterruptedException {
        String answer = search.findPath(N3, N7).toString();
        System.out.println(answer);
        assertEquals("[Short3, Short9, Short8, Short7]", answer);
    }
    @Test
    //Node 7 to Node 3
    public void testMap9Repeat() throws InterruptedException{
        String answer1 = search.findPath(N7, N3).toString();
        String answer2 = search.findPath(N7, N3).toString();
        System.out.println(answer1);
        assertEquals(answer1, answer2);
        assertEquals("[Short7, Short8, Short9, Short3]", answer1);
    }

    @Test
    //Node 6 to Node 6
    public void testMap1Same() throws InterruptedException{
        String answer = search.findPath(N6, N6).toString();
        System.out.println(answer);
        assertEquals("[Short6]", answer);
    }
    @Test
    //Node 18 to Node 18
    public void testMap2Same() throws InterruptedException{
        String answer = search.findPath(N18, N18).toString();
        System.out.println(answer);
        assertEquals("[Short18]", answer);
    }
    @Test
    //Node 9 to Node 9
    public void testMap3Same() throws InterruptedException{
        String answer = search.findPath(N9, N9).toString();
        System.out.println(answer);
        assertEquals("[Short9]", answer);

    }

    //MultiFloor Pathfinding Tests
    @Test
    public void testSearchFloor1(){   //Node 1 to Node 19_2
        String answer = search.findPath(N1, N19_2).toString();
        System.out.println(answer);
        String directions = search.findPath(N1, N19_2).findDirections().toString();
        System.out.println(directions);
        assertEquals("[Short1, Short2, Short3, Short9, Short9_2, Short15_2, Short14_2, Short18_2, Short19_2]", answer);
    }
    @Test
    public void testSearchFloor1Reverse(){    //Node 19_2 to Node 1
        String answer = search.findPath(N19_2, N1).toString();
        System.out.println(answer);
        assertEquals("[Short19_2, Short18_2, Short14_2, Short15_2, Short9_2, Short9, Short3, Short2, Short1]", answer);
    }

    @Test
    public void testSearchFloor2(){   //Node 3 to Node 23_2
        String answer = search.findPath(N3, N23_2).toString();
        System.out.println(answer);
        assertEquals("[Short3, Short9, Short9_2, Short15_2, Short14_2, Short18_2, Short21_2, Short23_2]", answer);
    }
    @Test
    public void testSearchFloor2Reverse(){    //Node 23_2 to Node 3
        String answer = search.findPath(N23_2, N3).toString();
        System.out.println(answer);
        assertEquals("[Short23_2, Short21_2, Short18_2, Short14_2, Short15_2, Short9_2, Short9, Short3]", answer);
    }

    @Test
    public void testSearchFloor3(){   //Node 17 to Node 4_2
        String answer = search.findPath(N17, N4_2).toString();
        System.out.println(answer);
        assertEquals("[Short17, Short13, Short14, Short15, Short9, Short9_2, Short8_2, Short5_2, Short4_2]", answer);
    }


    @Test
    public void testSearchFloor3Reverse(){    //Node 4_2 to Node 17
        String answer = search.findPath(N4_2, N17).toString();
        System.out.println(answer);
        assertEquals("[Short4_2, Short7_2, Short8_2, Short9_2, Short9, Short15, Short14, Short13, Short17]", answer);
    }

    // findPathPitStop tests
    @Test
    public void testSearchPitStop1(){     //Node 1 to Node 6 to Node 19_2
        ArrayList<Node> stops = new ArrayList<>();
        stops.add(N1);
        stops.add(N6);
        stops.add(N19_2);
        String answer = search.findPathPitStop(stops).toString();
        System.out.println(answer);
        String directions = search.findPathPitStop(stops).findDirections().toString();
        System.out.println(directions);
        assertEquals("[Short1, Short6, Short6, Short7, Short8, Short9, Short9_2, Short15_2, Short14_2, Short18_2, Short19_2]", answer);
    }

    @Test
    public void testSearchPitStop2(){     //Node 17 to Node 4_2 to Node 6_2
        ArrayList<Node> stops = new ArrayList<>();
        stops.add(N17);
        stops.add(N4_2);
        stops.add(N6_2);
        String answer = search.findPathPitStop(stops).toString();
        System.out.println(answer);
        String directions = search.findPathPitStop(stops).findDirections().toString();
        System.out.println(directions);
        assertEquals("[Short17, Short13, Short14, Short15, Short9, Short9_2, Short8_2, Short5_2, Short4_2, Short4_2, Short7_2, Short6_2]", answer);
    }

    @Test
    public void testSearchPitStop3(){     //Node 11 to Node 2 to Node 10 to Node 3
        ArrayList<Node> stops = new ArrayList<>();
        stops.add(N1);
        stops.add(N2);
        stops.add(N10);
        stops.add(N3);
        String answer = search.findPathPitStop(stops).toString();
        System.out.println(answer);
        assertEquals("[Short1, Short2, Short2, Short1, Short6, Short7, Short10, Short10, Short7, Short8, Short9, Short3]", answer);
    }

    // getEuclideanDistance tests
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
}
**/