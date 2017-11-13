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
    public ArrayList<ServiceRequest> getRequests() { //should this one be in here since there's a getNextRequest?
        return backlog;
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
    void setDescription(String description){
        this.description = description;
    }
    //should we have the setType function be an abstract since it's in all of the others?
    public void setType(String type){ this.type = type; }
}