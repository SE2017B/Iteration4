/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/

package TestServices;

import org.junit.Before;
import org.junit.Test;
import DepartmentSubsystem.Staff;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class TransportServiceTest {

    public TransportServiceTest(){}

    private Staff wheelchairJim;
    private Staff wheelchairMary;
    private ArrayList<Staff> transports;
    private TransportService service;

    @Before
    public void initialize(){
        wheelchairJim = new Staff("jim", "abc", "Transport", "Jim Man", 3, service);
        wheelchairMary = new Staff("mary", "abc", "Transport", "Mary Woman", 4,service);
        transports = new ArrayList<>();
        service = new TransportService("Wheelchair");
    }

    @Test
    public void assignPersonTest(){
        service.assignPerson(wheelchairJim);
        assertTrue(service.getPersonnel().contains(wheelchairJim));
    }

    @Test
    public void assignPeopleTest(){
        transports.add(wheelchairJim);
        transports.add(wheelchairMary);
        service.assignPeople(transports);
        assertTrue(service.getPersonnel().contains(wheelchairJim) && service.getPersonnel().contains(wheelchairMary));
    }
}
