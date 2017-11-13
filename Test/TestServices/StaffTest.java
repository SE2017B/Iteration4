package TestServices;

import a_star.Node;
import exceptions.InvalidPasswordException;
import org.junit.*;
import service.*;


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

    //Add in any other method tests that are longer than 1 line
    //test that staff is put back in available people after request is completed
    @Test
    public void testBob(){
        assertEquals(Bob.getUsername(), "bob");
    }

    @Test
    public void changePasswordTest() throws InvalidPasswordException{
        Bob.changePassword("abc", "bobby");
        assertTrue(Bob.getPassword().equals("abc"));
    }

    @Test
    public void changePasswordFail() throws InvalidPasswordException{
        Bob.changePassword("abc", "bobb");
        assertFalse(Bob.getPassword().equals("abc"));
    }

    /*
    @Test
    testSetCurrentRequest(){

    }*/
}