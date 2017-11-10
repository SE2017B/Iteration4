package TestServices;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import service.FoodService;
import service.Staff;

public class FoodServiceTest {
    public FoodServiceTest() {}

    FoodService apple = new FoodService("apple");
    //Staff Deb = new Staff("")

    @Test
    public void testApple() {
        assertTrue(apple.getFoodType() == "apple");
    }

    @Test
    public void testAssignPerson(){
        //assertTrue();
    }

}
