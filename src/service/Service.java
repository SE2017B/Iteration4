package service;

import java.util.ArrayList;

abstract class Service{
    protected String type;
    protected String description;
    protected ArrayList<Staff> personnel;
    protected ArrayList<ServiceRequest> requests;

    abstract void assignPerson(Staff person); //Are these actually void? Just fixing errors -Travis
    abstract void assignPeople(ArrayList<Staff> people);
    abstract void addRequest(ServiceRequest request);
    abstract String getType();
    abstract String getDescription();
    abstract ArrayList<Staff> getPersonnel();

    abstract void setDescription(String description);
}