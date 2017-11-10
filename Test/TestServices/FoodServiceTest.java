package TestServices;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import service.FoodService;
import service.Staff;

import java.util.ArrayList;
import java.util.List;

public class FoodServiceTest {
    private FoodService apple;
    private FoodService meal;
    private Staff John;
    private Staff Jane;
    private List<Staff> people;

    @Before
    public void setup () {
        apple = new FoodService("apple");
        meal = new FoodService("meal");
        John = new Staff("johndoe", "abc", "cook", "John Doe", 123);
        Jane = new Staff("janedoe", "efg", "cook", "Jane Doe", 456);
        people = new ArrayList<>();

        people.add(John);
        people.add(Jane);

        apple.assignPerson(John);
        meal.assignPeople(people);
    }


    @Test
    public void testApple() {
        assertTrue(apple.getFoodType() == "apple");
    }

    @Test
    public void testAssignPerson(){
        assertTrue(apple.getPersonel().contains(John));
    }

    @Test
    public void testAssignPeople(){
        assertTrue(meal.getPersonel().contains(John) && meal.getPersonel().contains(Jane));
    }
}
