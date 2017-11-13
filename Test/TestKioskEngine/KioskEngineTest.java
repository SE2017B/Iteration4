package TestKioskEngine;

import exceptions.InvalidLoginException;
import exceptions.InvalidPasswordException;
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
    public void testLogin() throws InvalidLoginException{
        engine.addStaffLogin(bob);
        assertTrue(engine.login("bobby", "bob"));
    }

    @Test
    public void testLoginFail() throws InvalidLoginException{
        engine.addStaffLogin(bob);
        assertFalse(engine.login("bobb", "bob"));
    }
}
