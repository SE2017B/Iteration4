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

    public void assignPerson(Staff person){
        this.personnel.add(person);
        this.availablePer.add(person);
    }

    public void assignPeople(ArrayList<Staff> people){
        this.personnel.addAll(people);
        this.availablePer.addAll(people);
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