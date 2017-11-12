package TestServices;

import org.junit.*;
import service.FoodService;
import service.Staff;

import javax.xml.ws.Service;

import static org.junit.Assert.*;

public class StaffTest{

    private Staff Bob;
    private FoodService foodService;

    public StaffTest(){}

    @Before
    public void initialize() {
        foodService = new FoodService();
        Bob = new Staff("bob", "bobby", "cook", "Bobby Bob", 1, foodService);
    }

    @Test
    public void testBob(){
        assertEquals(Bob.getUsername(), "bob");
    }

    @Test
    public void changePasswordTest(){
        Bob.changePassword("abc", "bobby");
        assertEquals(Bob.getPassword(), "abc");
    }
}