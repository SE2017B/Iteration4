package TestServices;

import a_star.Node;
import exceptions.InvalidPasswordException;
import org.junit.*;
import service.*;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class StaffTest{

    private Staff Bob;
    private FoodService foodService;
    private ServiceRequest appleRequest;
    private ServiceRequest orangeRequest;
    Node location;

    public StaffTest(){}

    @Before
    public void initialize() {
        foodService = new FoodService();
        Bob = new Staff("bob", "bobby", "cook", "Bobby Bob", 1, foodService);
        appleRequest = new ServiceRequest(foodService, 1, location, "apple");
        orangeRequest = new ServiceRequest(foodService, 2, location, "orange");
    }

    /*
    tests that Staff constructor works properly
     */
    @Test
    public void testBob(){
        assertEquals(Bob.getUsername(), "bob");
    }

    /*
    tests password changes with correct old password input
     */
    @Test
    public void changePasswordTest() throws InvalidPasswordException{
        Bob.changePassword("abc", "bobby");
        assertTrue(Bob.getPassword().equals("abc"));
    }

    /*
    tests password does not change with incorrect old password input
     */
    @Test
    public void changePasswordFail() throws InvalidPasswordException{
        Bob.changePassword("abc", "bobb");
        assertFalse(Bob.getPassword().equals("abc"));
    }

    @Test
    public void testSetCurrentRequest(){
        foodService.assignPerson(Bob);
        Bob.setCurrentRequest(appleRequest);
        assertEquals(Bob.getCurrentRequest(), appleRequest);
    }

    @Test
    public void testCompleteCurRec(){
        foodService.assignPerson(Bob);
        foodService.addRequest(appleRequest);
        Bob.completeCurRec();
        assertTrue(Bob.getCurrentRequest() == null);
    }

    /*
    tests staff member is added to list of available people when they complete a request
    and there are no requests in the backlog
     */
    @Test
    public void testCompleteCurRec2(){
        foodService.assignPerson(Bob);
        foodService.addRequest(appleRequest);
        Bob.completeCurRec();
        ArrayList<Staff> available = new ArrayList<>();
        available.add(Bob);
        assertEquals(foodService.getAvailablePer(), available);
    }

    /*
    tests staff member is declared not busy when they complete a request and there are
    no new requests in the backlog
     */
    @Test
    public void testCompleteCurRec3(){
        foodService.assignPerson(Bob);
        foodService.addRequest(appleRequest);
        Bob.completeCurRec();
        assertFalse(Bob.isBusy()); //Bob should not be busy now
    }

    /*
    tests staff member is given new request when the backlog is not empty
     */
    @Test
    public void testCompleteCurRec4(){
        foodService.assignPerson(Bob);
        foodService.addRequest(appleRequest);
        foodService.addRequest(orangeRequest);
        Bob.completeCurRec();
        assertEquals(Bob.getCurrentRequest(), orangeRequest);
    }
}