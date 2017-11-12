package TestServices;

import org.junit.Before;
import org.junit.Test;
import service.Staff;
import static org.junit.Assert.*;

public class StaffTest{

    private Staff doctorBob;

    public StaffTest(){}

    @Before
    public void initialize() {
        doctorBob = new Staff("bob", "bobby", "Doctor", "Bobby Bob", 1);
    }

    @Test
    public void testGetters(){
        assertEquals(doctorBob.getUsername(), "bob");
        assertEquals(doctorBob.getPassword(), "bobby");
        assertEquals(doctorBob.getJobTitle(), "Doctor");
        assertEquals(doctorBob.getFullName(), "Bobby Bob");
        assertEquals(doctorBob.getID(), 1);
    }

    @Test
    public void changePasswordTest(){
        doctorBob.changePassword("abc", "bobby");
        assertEquals(doctorBob.getPassword(), "abc");
    }
}