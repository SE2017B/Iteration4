/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/

package TestServices;

import map.Node;
import org.junit.*;
import DepartmentSubsystem.ServiceRequest;
import DepartmentSubsystem.Staff;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//This class tests the FoodService class as well as the inherited methods from the Service class

public class FoodServiceTest {
    private FoodService foodService;
    private Staff John;
    private Staff Jane;
    private ServiceRequest foodRequest;
    private ServiceRequest orangeRequest;
    private ServiceRequest grapeRequest;
    private ServiceRequest kiwiRequest;
    Node location;

    @Before
    public void setup () {
        foodService = new FoodService();
        John = new Staff("johndoe", "abc", "Head cook", "John Doe", 123, foodService);
        Jane = new Staff("janedoe", "efg", "cook", "Jane Doe", 456, foodService);
        location = new Node(200,400);
        foodRequest = new ServiceRequest(foodService, 1, location, "apple");
        orangeRequest = new ServiceRequest(foodService, 2, location, "orange");
        grapeRequest = new ServiceRequest(foodService, 3, location, "grape");
        kiwiRequest = new ServiceRequest(foodService, 4, location, "kiwi");

        foodService.assignPerson(John);
        foodService.assignPerson(Jane);
    }

    /*
    tests that the FoodService constructor works properly
     */
    @Test
    public void testFoodService() {
        assertTrue(foodService.getType().equals("Food Service"));
    }

    @Test
    public void testAssignPerson(){
        assertTrue(foodService.getPersonnel().contains(John));
    }

    /*
    tests that RequestService constructor works properly
     */
    @Test
    public void testFoodRequest(){
        assertTrue(foodRequest.getRequestID() == 1);
    }

    @Test
    public void testAssignRequest(){
        foodService.addRequest(foodRequest);

        assertEquals(John.getCurrentRequest().getRequestID(), foodRequest.getRequestID());
    }

    @Test
    public void testGiveService(){
        foodRequest.giveRequest();

        assertEquals(John.getCurrentRequest(), foodRequest);
    }

    /*
    tests that backlog receives all requests that can't be assigned to a staff member
     */
    @Test
    public void testBacklog(){
        foodService.addRequest(foodRequest);
        foodService.addRequest(orangeRequest);
        foodService.addRequest(grapeRequest);

        ArrayList<ServiceRequest> backlog = new ArrayList<>();
        backlog.add(grapeRequest);

        assertEquals(foodService.getBacklog(), backlog);
    }

    @Test
    public void testGetNextRequest(){
        foodService.addRequest(foodRequest);
        foodService.addRequest(orangeRequest);
        foodService.addRequest(grapeRequest);

        assertEquals(foodService.getNextRequest(), grapeRequest);
    }
}
