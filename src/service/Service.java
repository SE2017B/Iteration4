package service;

import java.util.ArrayList;

abstract class Service{
    protected String type;
    protected ArrayList<Staff> personnel;

    abstract void assignPerson(Staff person); //Are these actually void? Just fixing errors -Travis
    abstract void assignPeople(ArrayList<Staff> people);
    abstract String getType();
    abstract ArrayList<Staff> getPersonnel();
}