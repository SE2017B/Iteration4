package service;

import java.util.ArrayList;

abstract class Service{
    protected String type;
    protected ArrayList<Staff> personnel;
    protected ArrayList<ServiceRequest> backlog;
    protected ArrayList<Staff> availablePer;
    protected String description;

    public Service(){
        personnel = new ArrayList<>();
        backlog = new ArrayList<>();
        availablePer = new ArrayList<>();
    }

    public void assignPerson(Staff person){
        this.personnel.add(person);
        this.availablePer.add(person);
    }

    public void assignPeople(ArrayList<Staff> people){
        this.personnel.addAll(people);
        this.availablePer.addAll(people);
    }

    //can we get rid of the print statements now that we know this works?
    public void addRequest(ServiceRequest request){
        System.out.println(request.getRequestID());
        if(availablePer.isEmpty()) {
            System.out.println("false");
            backlog.add(request);
        }else{
            System.out.println("true");
            Staff avaStaff = availablePer.get(0);
            if(!avaStaff.isBusy()) {
                System.out.println("yes");
                avaStaff.setCurrentRequest(request);
                availablePer.remove(avaStaff);
            }
        }
    }

    public void addAvailable(Staff staff) {
        availablePer.add(staff);
    }

    //getters
    public String getType(){
        return this.type;
    }
    public ArrayList<Staff> getPersonnel(){
        return this.personnel;
    }
    public ArrayList<ServiceRequest> getBacklog() {
        return backlog;
    }
    public ArrayList<Staff> getAvailablePer() {
        return availablePer;
    }
    public String getDescription(){
        return this.description;
    }
    public ServiceRequest getNextRequest() {
        if(backlog.isEmpty()) {
            return null;
        }
        return backlog.get(0);
    }

    //setters
    public void setType(String type){ this.type = type; }
    public void setDescription(String description){
        this.description = description;
    }
}