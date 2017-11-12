package TestKioskEngine;

import kioskEngine.KioskEngine;
import org.junit.Before;
import org.junit.Test;
import service.FoodService;
import service.Staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(engine.login("bobby", "bob"));
        assertFalse(engine.login("bobb", "bob"));
        assertFalse(engine.login("bobby", "bo"));
    }

}
