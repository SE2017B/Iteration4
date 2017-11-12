package TestServices;

import org.junit.Before;
import org.junit.Test;
import service.Staff;

import javax.xml.ws.Service;

import static org.junit.Assert.*;

public class StaffTest{

    private Staff cookBob;
    private Service docter;

    public StaffTest(){}

    @Before
    public void initialize() {
        docter = new
        cookBob = new Staff("bob", "bobby", "head cook", "Bobby Bob", 1);
    }

    @Test
    public void testGetters(){
        assertEquals(cookBob.getUsername(), "bob");
        assertEquals(cookBob.getPassword(), "bobby");
        assertEquals(cookBob.getJobTitle(), "Doctor");
        assertEquals(cookBob.getFullName(), "Bobby Bob");
        assertEquals(cookBob.getID(), 1);
    }

    @Test
    public void changePasswordTest(){
        cookBob.changePassword("abc", "bobby");
        assertEquals(cookBob.getPassword(), "abc");
    }
}