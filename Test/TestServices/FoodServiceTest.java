package TestServices;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import service.FoodService;
import service.ServiceRequest;
import service.Staff;
import service.TransportService;

import java.util.ArrayList;

public class FoodServiceTest {
    private FoodService apple;
    private FoodService meal;
    private Staff John;
    private Staff Jane;
    private ArrayList<Staff> people;
    private ServiceRequest foodRequest;

    @Before
    public void setup () {
        //will be fixed after Vojta pushes his code
        //apple = new FoodService("apple");
        //meal = new FoodService("meal");
        John = new Staff("johndoe", "abc", "cook", "John Doe", 123);
        Jane = new Staff("janedoe", "efg", "cook", "Jane Doe", 456);
        //foodRequest = new ServiceRequest()

        people = new ArrayList<>();

        people.add(John);
        people.add(Jane);

        apple.assignPerson(John);
        meal.assignPeople(people);


    }

/*
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
*/
    //@Test
    //public void test
}
