package service;

import java.util.ArrayList;

abstract class Service{
    protected String type;
    protected ArrayList<Staff> personnel;
    protected ArrayList<ServiceRequest> backlog;
    protected ArrayList<Staff> avaliblePer;

    abstract void assignPerson(Staff person); //Are these actually void? Just fixing errors -Travis
    abstract void assignPeople(ArrayList<Staff> people);
    abstract void addRequest(ServiceRequest request);
    abstract String getType();
    abstract String getDescription();
    abstract ArrayList<Staff> getPersonnel();

    abstract void setDescription(String description);

    public ServiceRequest getNextRequest() {
        if(backlog.isEmpty()) {
            return null;
        }
        return backlog.get(0);
    }

    public void addAvailable(Staff staff) {
        avaliblePer.add(staff);
    }
}