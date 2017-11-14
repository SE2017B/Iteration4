package ServicesTest;
/*
import a_star.Node;
import org.junit.*;
import service.FoodService;
import service.ServiceRequest;
import service.Staff;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FoodServiceTest {
    private FoodService foodService;
    private Staff John;
    private Staff Jane;
    private ServiceRequest foodRequest;
    private ServiceRequest orangeRequest;
    private ServiceRequest grapeRequest;
    Node location;

    @Before
    public void setup () {
        foodService = new FoodService();
        John = new Staff("johndoe", "abc", "Head cook", "John Doe", 123, foodService);
        Jane = new Staff("janedoe", "efg", "cook", "Jane Doe", 456, foodService);
        location = new Node();
        foodRequest = new ServiceRequest(foodService, 1, location, "apple");
        orangeRequest = new ServiceRequest(foodService, 2, location, "orange");
        grapeRequest = new ServiceRequest(foodService, 3, location, "grape");

        foodService.assignPerson(John);
        foodService.assignPerson(Jane);
    }

    //Add in any other method tests that are longer than 1 line
    //test backlog - 3 requests
    //test that staff is put back in available people after request is completed

    //fails - somehow requests are only being assigned to John Doe
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
    public void testFoodService() {
        assertTrue(foodService.getType().equals("Food Service"));
    }

    @Test
    public void testAssignPerson(){
        assertTrue(foodService.getPersonnel().contains(John));
    }

    @Test
    public void testAssignRequest(){
        foodService.addRequest(foodRequest);
        assertEquals(John.getCurrentRequest().getRequestID(), foodRequest.getRequestID());
    }

    @Test
    public void testFoodRequest(){
        assertTrue(foodRequest.getRequestID() == 1);
    }

    @Test
    public void testGiveService(){
        foodRequest.giveRequest();
        assertEquals(John.getCurrentRequest(), foodRequest);
    }
}
*/