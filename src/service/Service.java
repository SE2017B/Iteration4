package service;

import java.util.ArrayList;

abstract class Service{
    protected String type;
    protected ArrayList<Staff> personnel;
    protected ArrayList<ServiceRequest> backlog;
    protected ArrayList<Staff> availablePer;
    protected String description;

    abstract void assignPerson(Staff person); //Are these actually void? Just fixing errors -Travis
    abstract void assignPeople(ArrayList<Staff> people);


    public void addRequest(ServiceRequest request){
        if(availablePer.isEmpty()) {
            backlog.add(request);
        }else{
            Staff avaStaff = availablePer.get(0);
            if(avaStaff.isBusy()) {
                avaStaff.setCurrentRequest(request);
            }
        }

    }
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
        availablePer.add(staff);
    }
}