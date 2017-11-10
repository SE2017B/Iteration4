package service;

import java.util.ArrayList;
import java.util.List;

abstract class Service{
    protected String type;
    protected  List<Staff> personel;

    abstract String getType();
    abstract ArrayList<Staff> getPersonel();

    abstract void assignPerson(Staff person); //Are these actually void? Just fixing errors -Travis
    abstract void assignPeople(List<Staff> people);
}