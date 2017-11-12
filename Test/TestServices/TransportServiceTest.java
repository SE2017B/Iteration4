package TestServices;

import org.junit.Before;
import org.junit.Test;
import service.Staff;
import service.TransportService;

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
        wheelchairJim = new Staff("jim", "abc", "Transport", "Jim Man", 3);
        wheelchairMary = new Staff("mary", "abc", "Transport", "Mary Woman", 4);
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
