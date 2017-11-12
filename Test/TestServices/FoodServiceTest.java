package TestServices;

import org.junit.*;
import service.FoodService;
import service.Staff;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class FoodServiceTest {
    private FoodService apple;
    private FoodService meal;
    private Staff John;
    private Staff Jane;
    private ArrayList<Staff> people;

    @Before
    public void setup () {
        apple = new FoodService("apple");
        meal = new FoodService("meal");
        John = new Staff("johndoe", "abc", "cook", "John Doe", 123,meal);
        Jane = new Staff("janedoe", "efg", "cook", "Jane Doe", 456,meal);
        people = new ArrayList<>();

        people.add(John);
        people.add(Jane);

        apple.assignPerson(John);
        meal.assignPeople(people);
    }


    @Test
    public void testApple() {
        assertTrue(apple.getFoodType().equals("apple"));
    }

    @Test
    public void testAssignPerson(){
        assertTrue(apple.getPersonnel().contains(John));
    }

    @Test
    public void testAssignPeople(){
        assertTrue(meal.getPersonnel().contains(John) && meal.getPersonnel().contains(Jane));
    }
}
