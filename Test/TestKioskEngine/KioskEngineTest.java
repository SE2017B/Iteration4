package TestKioskEngine;

import exceptions.InvalidLoginException;
import kioskEngine.KioskEngine;
import org.junit.Before;
import org.junit.Test;
import service.FoodService;
import service.Staff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.*;

public class KioskEngineTest {
    public KioskEngineTest(){}

    private KioskEngine engine;
    private Staff bob;
    private FoodService foodService;

    @Before
    public void initialize(){
        bob = new Staff("bobby", "bob", "chef", "Bobby Bob", 1, foodService);
        engine = new KioskEngine();
    }

    @Test
    public void testAddLogin(){
        engine.addStaffLogin(bob);
        assertTrue(engine.getLoginInfo().containsKey("bobby"));
        assertTrue(engine.getLoginInfo().containsValue(bob));
    }

    @Test
    public void testLogin(){
        engine.addStaffLogin(bob);
        try{
            assertTrue(engine.login("bobby", "bob"));
        }
        catch (InvalidLoginException e) {
            assertFalse(true);
        }
    }


    @Test(expected = InvalidLoginException.class)
    public void testLoginFail() throws InvalidLoginException{
        engine.addStaffLogin(bob);
        engine.login("bobb", "bob");
    }
}
