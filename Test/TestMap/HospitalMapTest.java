package TestMap;

import a_star.*;
import org.junit.Before;
import org.junit.Test;

import static java.lang.Math.sqrt;
import static org.junit.Assert.assertEquals;

public class HospitalMapTest {

    //set up example map for testing
    public HospitalMapTest() {
    }

    private HospitalMap smallArea = new HospitalMap();
    private Node N1 = new Node("Node 1", "1", "Bathroom",  1, 0, 400);
    private Node N2 = new Node("Node 2", "2", "Room",  1, 0, 200);
    private Node N3 = new Node("Node 3", "3", "Desk",  1, 0, 0);
    private Node N4 = new Node("Node 4", "4", "Office", 1, 100, 300);
    private Node N5 = new Node("Node 5", "5", "Stairs",  1, 100, 100);
    private Node N6 = new Node("Node 6", "6", "Bathrooom",  1, 200, 400);
    private Node N7 = new Node("Node 7", "7", "Stairs",  1, 200, 300);
    private Node N8 = new Node("Node 8", "8", "Bathrooom",  1, 200, 100);
    private Node N9 = new Node("Node 9", "9", "Elevator",  1, 200, 0);
    private Node N10 = new Node("Node 10", "10", "Desk", 1, 300, 300);
    private Node N11 = new Node("Node 11", "11", "Bathroom", 1, 300, 100);
    private Node N12 = new Node("Node 12", "12", "Desk", 1, 400, 400);
    private Node N13 = new Node("Node 13", "13", "Desk", 1, 400, 300);
    private Node N14 = new Node("Node 14", "14", "Desk", 1, 400, 100);
    private Node N15 = new Node("Node 15", "15", "Stairs", 1, 400, 0);
    private Node N16 = new Node("Node 16", "16", "Office", 1, 600, 400);
    private Node N17 = new Node("Node 17", "17", "Office", 1, 600, 300);
    private Node N18 = new Node("Node 18", "18", "Bathroom", 1, 500, 100);
    private Node N19 = new Node("Node 19", "19", "Stairs", 1, 500, 0);
    private Node N20 = new Node("Node 20", "20", "Elevator", 1, 700, 300);
    private Node N21 = new Node("Node 21", "21", "Stairs", 1, 700, 100);
    private Node N22 = new Node("Node 22", "22", "Desk", 1, 700, 0);
    private Node N23 = new Node("Node 23", "23", "Desk", 1, 800, 0);


    @Before
    public void initialize() {
        //Creating nodes for the map

        //Add connections for nodeOne
        N1.addConnection(N2);
        N1.addConnection(N6);

        //Add connections for nodeTwo
        N2.addConnection(N3);
        N2.addConnection(N1);

        //Add connections for nodeThree
        N3.addConnection(N2);
        N3.addConnection(N9);

        //Add connections for nodeFour
        N4.addConnection(N7);
        N4.addConnection(N5);

        //Add connections for nodeFive
        N5.addConnection(N8);
        N5.addConnection(N4);

        //Add connections for nodeSix
        N6.addConnection(N1);
        N6.addConnection(N7);

        //Add connections for nodeSeven
        N7.addConnection(N4);
        N7.addConnection(N6);
        N7.addConnection(N8);
        N7.addConnection(N10);

        //Add connections for nodeEight
        N8.addConnection(N7);
        N8.addConnection(N9);
        N8.addConnection(N5);

        //Add connections for nodeNine
        N9.addConnection(N3);
        N9.addConnection(N8);
        N9.addConnection(N15);

        //Add connections for nodeTen
        N10.addConnection(N7);
        N10.addConnection(N11);

        //Add connections for nodeEleven
        N11.addConnection(N10);
        N11.addConnection(N14);

        //Add connections for nodeTwelve
        N12.addConnection(N13);
        N12.addConnection(N16);

        //Add connections for nodeThirteen
        N13.addConnection(N12);
        N13.addConnection(N14);
        N13.addConnection(N17);

        //Add connections for nodeFourteen
        N14.addConnection(N11);
        N14.addConnection(N13);
        N14.addConnection(N15);
        N14.addConnection(N18);

        //Add connections for nodeFifteen
        N15.addConnection(N9);
        N15.addConnection(N14);

        //Add connections for nodeSixteen
        N16.addConnection(N14);
        N16.addConnection(N17);

        //Add connections for nodeSeventeen
        N17.addConnection(N13);
        N17.addConnection(N16);
        N17.addConnection(N20);

        //Add connections for nodeEighteen
        N18.addConnection(N14);
        N18.addConnection(N19);
        N18.addConnection(N21);

        //Add connections for nodeNineteen
        N19.addConnection(N18);
        N19.addConnection(N22);

        //Add connections for nodeTwenty
        N20.addConnection(N17);
        N20.addConnection(N21);

        //Add connections for nodeTwentyOne
        N21.addConnection(N18);
        N21.addConnection(N20);
        N21.addConnection(N22);
        N21.addConnection(N23);

        //Add connections for nodeTwentyTwo
        N22.addConnection(N19);
        N22.addConnection(N21);

        //Add connections for nodeTwentyThree
        N23.addConnection(N21);
    }

    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    // findPath tests

    @Test
    //Node 1 to Node 9
    public void testMap1() throws InterruptedException {
        smallArea.setStart(N1);
        smallArea.setEnd(N9);

        String answer = smallArea.findPath(N9).toString();
        System.out.println(answer);
        assertEquals("[Node 1, Node 2, Node 3, Node 9]", answer);
    }
    @Test
    //Node 9 to Node 1
    public void testMap1Reverse() throws InterruptedException{
        smallArea.setStart(N9);
        smallArea.setEnd(N1);

        String answer = smallArea.findPath(N1).toString();
        System.out.println(answer);
        assertEquals("[Node 9, Node 8, Node 7, Node 6, Node 1]", answer);
    }

    @Test
    //Node 1 to Node 10
    public void testMap2() throws InterruptedException{
        smallArea.setStart(N1);
        smallArea.setEnd(N10);

        String answer = smallArea.findPath(N10).toString();
        System.out.println(answer);
        assertEquals("[Node 1, Node 6, Node 7, Node 10]", answer);
    }
    @Test
    //Node 10 to Node 1
    public void testMap2Reverse() throws InterruptedException{
        smallArea.setStart(N10);
        smallArea.setEnd(N1);

        String answer = smallArea.findPath(N1).toString();
        System.out.println(answer);
        assertEquals("[Node 10, Node 7, Node 6, Node 1]", answer);
    }

    @Test
    //Node 1 to Node 12
    public void testMap3() throws InterruptedException{
        smallArea.setStart(N1);
        smallArea.setEnd(N12);

        String answer = smallArea.findPath(N12).toString();
        System.out.println(answer);
        assertEquals("[Node 1, Node 6, Node 7, Node 10, Node 11, Node 14, Node 13, Node 12]", answer);
    }
    @Test
    //Node 12 to Node 1
    public void testMap3Reverse() throws InterruptedException{
        smallArea.setStart(N12);
        smallArea.setEnd(N1);

        String answer = smallArea.findPath(N1).toString();
        System.out.println(answer);
        assertEquals("[Node 12, Node 16, Node 14, Node 11, Node 10, Node 7, Node 6, Node 1]", answer);
    }

    @Test
    //Node 1 to Node 20
    public void testMap4() throws InterruptedException{
        smallArea.setStart(N1);
        smallArea.setEnd(N20);

        String answer = smallArea.findPath(N20).toString();
        System.out.println(answer);
        assertEquals("[Node 1, Node 6, Node 7, Node 10, Node 11, Node 14, Node 18, Node 21, Node 20]", answer);
    }
    @Test
    //Node 20 to Node 1
    public void testMap4Reverse() throws InterruptedException{
        smallArea.setStart(N20);
        smallArea.setEnd(N1);

        System.out.println(smallArea.findPath(N1).toString());
    }

    @Test
    //Node 1 to Node 23
    public void testMap5() throws InterruptedException{
        smallArea.setStart(N1);
        smallArea.setEnd(N23);

        String answer = smallArea.findPath(N23).toString();
        System.out.println(answer);
        assertEquals("[Node 1, Node 6, Node 7, Node 10, Node 11, Node 14, Node 18, Node 21, Node 23]", answer);
    }
    @Test
    //Node 23 to Node 1
    public void testMap5Reverse() throws InterruptedException{
        smallArea.setStart(N23);
        smallArea.setEnd(N1);

        String answer = smallArea.findPath(N1).toString();
        System.out.println(answer);
        assertEquals("[Node 23, Node 21, Node 18, Node 14, Node 11, Node 10, Node 7, Node 6, Node 1]", answer);
    }

    @Test
    //Node 6 to Node 15
    public void testMap6() throws InterruptedException{
        smallArea.setStart(N6);
        smallArea.setEnd(N15);

        String answer = smallArea.findPath(N15).toString();
        System.out.println(answer);
        assertEquals("[Node 6, Node 7, Node 8, Node 9, Node 15]", answer);
    }
    @Test
    //Node 15 to Node 6
    public void testMap6Reverse() throws InterruptedException{
        smallArea.setStart(N15);
        smallArea.setEnd(N6);

        String answer = smallArea.findPath(N6).toString();
        System.out.println(answer);
        assertEquals("[Node 15, Node 14, Node 11, Node 10, Node 7, Node 6]", answer);
    }

    @Test
    //Node 3 to Node 22
    public void testMap7() throws InterruptedException{
        smallArea.setStart(N3);
        smallArea.setEnd(N22);

        String answer = smallArea.findPath(N22).toString();
        System.out.println(answer);
        assertEquals("[Node 3, Node 9, Node 15, Node 14, Node 18, Node 19, Node 22]", answer);
    }
    @Test
    //Node 22 to Node 3
    public void testMap7Reverse() throws InterruptedException{
        smallArea.setStart(N22);
        smallArea.setEnd(N3);

        String answer = smallArea.findPath(N3).toString();
        System.out.println(answer);
        assertEquals("[Node 22, Node 19, Node 18, Node 14, Node 15, Node 9, Node 3]", answer);
    }

    @Test
    //Node 4 to Node 2
    public void testMap8() throws InterruptedException {
        smallArea.setStart(N4);
        smallArea.setEnd(N2);

        String answer = smallArea.findPath(N2).toString();
        System.out.println(answer);
        assertEquals("[Node 4, Node 7, Node 6, Node 1, Node 2]", answer);
    }
    @Test
    //Node 2 to Node 4
    public void testMap8Reverse() throws InterruptedException {
        smallArea.setStart(N2);
        smallArea.setEnd(N4);

        String answer = smallArea.findPath(N4).toString();
        System.out.println(answer);
        assertEquals("[Node 2, Node 1, Node 6, Node 7, Node 4]", answer);
    }

    @Test
    //Node 7 to Node 3
    public void testMap9() throws InterruptedException {
        smallArea.setStart(N7);
        smallArea.setEnd(N3);

        String answer = smallArea.findPath(N3).toString();
        System.out.println(answer);
        assertEquals("[Node 7, Node 8, Node 9, Node 3]", answer);
    }
    @Test
    //Node 3 to Node 7
    public void testMap9Reverse() throws InterruptedException {
        smallArea.setStart(N3);
        smallArea.setEnd(N7);

        String answer = smallArea.findPath(N7).toString();
        System.out.println(answer);
        assertEquals("[Node 3, Node 9, Node 8, Node 7]", answer);
    }

    @Test
    //Node 6 to Node 6
    public void testMap1Same() throws InterruptedException{
        smallArea.setStart(N6);
        smallArea.setEnd(N6);

        String answer = smallArea.findPath(N6).toString();
        System.out.println(answer);
        assertEquals("[Node 6]", answer);
    }
    @Test
    //Node 18 to Node 18
    public void testMap2Same() throws InterruptedException{
        smallArea.setStart(N18);
        smallArea.setEnd(N18);

        String answer = smallArea.findPath(N18).toString();
        System.out.println(answer);
        assertEquals("[Node 18]", answer);
    }
    @Test
    //Node 9 to Node 9
    public void testMap3Same() throws InterruptedException{
        smallArea.setStart(N9);
        smallArea.setEnd(N9);

        String answer = smallArea.findPath(N9).toString();
        System.out.println(answer);
        assertEquals("[Node 9]", answer);

    }


    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    // findPath tests (2 Nodes given)

    @Test
    //Node 1 to Node 17
    public void testMap10() throws InterruptedException{
        smallArea.setStart(N1);
        smallArea.setEnd(N17);

        String answer = smallArea.findPath(N1, N17).toString();
        System.out.println(answer);
        assertEquals("[Node 1, Node 6, Node 7, Node 10, Node 11, Node 14, Node 13, Node 17]", answer);
    }
    @Test
    //Node 17 to Node 1
    public void testMap10Reverse() throws InterruptedException{
        smallArea.setStart(N17);
        smallArea.setEnd(N1);

        String answer = smallArea.findPath(N17, N1).toString();
        System.out.println(answer);
        assertEquals("[Node 17, Node 16, Node 14, Node 11, Node 10, Node 7, Node 6, Node 1]", answer);
    }

    @Test
    //Node 3 to Node 23
    public void testMap11() throws InterruptedException{
        smallArea.setStart(N3);
        smallArea.setEnd(N23);

        String answer = smallArea.findPath(N3, N23).toString();
        System.out.println(answer);
        assertEquals("[Node 3, Node 9, Node 15, Node 14, Node 18, Node 21, Node 23]", answer);
    }
    @Test
    //Node 23 to Node 3
    public void testMap11Reverse() throws InterruptedException{
        smallArea.setStart(N23);
        smallArea.setEnd(N3);

        String answer = smallArea.findPath(N23, N3).toString();
        System.out.println(answer);
        assertEquals("[Node 23, Node 21, Node 18, Node 14, Node 15, Node 9, Node 3]", answer);
    }

    @Test
    //Node 4 to Node 19
    public void testMap12() throws InterruptedException{
        smallArea.setStart(N4);
        smallArea.setEnd(N19);

        String answer = smallArea.findPath(N4, N19).toString();
        System.out.println(answer);
        assertEquals("[Node 4, Node 7, Node 10, Node 11, Node 14, Node 18, Node 19]", answer);
    }
    @Test
    //Node 19 to Node 4
    public void testMap12Reverse() throws InterruptedException{
        smallArea.setStart(N19);
        smallArea.setEnd(N4);

        String answer = smallArea.findPath(N19, N4).toString();
        System.out.println(answer);
        assertEquals("[Node 19, Node 18, Node 14, Node 11, Node 10, Node 7, Node 4]", answer);
    }

    @Test
    //Node 15 to Node 15
    public void testMap4Same() throws InterruptedException{
        smallArea.setStart(N15);
        smallArea.setEnd(N15);

        String answer = smallArea.findPath(N15, N15).toString();
        System.out.println(answer);
        assertEquals("[Node 15]", answer);
    }
    @Test
    //Node 20 to Node 20
    public void testMap5Same() throws InterruptedException{
        smallArea.setStart(N20);
        smallArea.setEnd(N20);

        String answer = smallArea.findPath(N20, N20).toString();
        System.out.println(answer);
        assertEquals("[Node 20]", answer);
    }
    @Test
    //Node 3 to Node 3
    public void testMap6Same() throws InterruptedException{
        smallArea.setStart(N3);
        smallArea.setEnd(N3);

        String answer = smallArea.findPath(N3, N3).toString();
        System.out.println(answer);
        assertEquals("[Node 3]", answer);
    }

    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    // getEuclideanDistance tests

    @Test
    //Node 3 to Node 14
    public void testGetEuclideanDistance1(){
        assertEquals(sqrt(170000),smallArea.getEuclidianDistance(N3, N14), .01);
    }
    @Test
    //Node 14 to Node 3
    public void testGetEuclideanDistance1Reverse(){
        assertEquals(sqrt(170000), smallArea.getEuclidianDistance(N14, N3), .01);
    }

    @Test
    //Node 11 to Node 23
    public void testGetEuclideanDistance2(){
        assertEquals(sqrt(260000), smallArea.getEuclidianDistance(N11, N23), .01);
    }
    @Test
    //Node 23 to Node 11
    public void testGetEuclideanDistance2Reverse(){
        assertEquals(sqrt(260000), smallArea.getEuclidianDistance(N23, N11), .01);
    }

    @Test
    //Node 1 to Node 6
    public void testGetEuclideanDistance3(){
        assertEquals(sqrt(40000), smallArea.getEuclidianDistance(N1, N6), .01);
    }
    @Test
    //Node 6 to Node 1
    public void testGetEuclideanDistance3Reverse(){
        assertEquals(sqrt(40000), smallArea.getEuclidianDistance(N6, N1), .01);
    }

    @Test
    //Node 5 to Node 5
    public void testGetEuclideanDistanceSame(){
        assertEquals(0, smallArea.getEuclidianDistance(N5, N5), .01);
    }


    @Test
    //Node 6 to Node 1
    public void testTwoPaths(){
        assertEquals(smallArea.findPath(N4, N1),smallArea.findPath(N4, N1));
    }

}