/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

import DepartmentSubsystem.Services.FoodDelivery;
import DepartmentSubsystem.Services.Sanitation;
import DepartmentSubsystem.Services.Translation;
import DepartmentSubsystem.Services.Transport;
import database.staffDatabase;

import java.util.ArrayList;

//import java.mail.*;
//import java.mail.internet.*;

public class DepartmentSubsystem {
//    private boolean initRan = false;
    ArrayList<Department> departments = new ArrayList<Department>();
    private Staff currentlyLoggedIn = null;
//    private static int count = 0;

    ArrayList<Service> services = new ArrayList<Service>();
    private static int count = 0;
    /**
     * The order of method calls should be this for DSS:
     * Login - The user submits their credentials, and we check if they have a matching pair
     * GetDepartments - The user selects the department to see what services they hold
     * GetServices - The user selects a specific service they want
     * FROM SERVICE: GetURL - This is for UI, so the specific Service field can have its specific attributes
     * submitRequest - The user submits the request, and we process it
     */

    private static DepartmentSubsystem singleton;
    private DepartmentSubsystem(){init();}
    private void init(){
        //If the init method was ran, then we dont do it again
        //if(initRan){ return; }

        //TODO assign staff to departments and services
        Department translationDepartment = new Department("Translation Department");
        Service translation = new Translation("Translation service");
        translation.setURL("/fxml/Translation.fxml");
        services.add(translation);
        translationDepartment.addService(translation);
        departments.add(translationDepartment);

        Department transportationDepartment = new Department("Transportation Department");
        Service transport = new Transport("Transport service");
        transport.setURL("/fxml/Transport.fxml");
        services.add(transport);
        transportationDepartment.addService(transport);
        departments.add(transportationDepartment);

        Department facilities = new Department("Facilities");
        Service sanitation = new Sanitation("Sanitation");
        sanitation.setURL("/fxml/Sanitation.fxml");
        services.add(sanitation);
        facilities.addService(sanitation);
        departments.add(facilities);

        Department food = new Department("Food");
        Service foodDelivery = new FoodDelivery("Food Delivery Service");
        foodDelivery.setURL("/fxml/FoodDelivery.fxml");
        services.add(foodDelivery);
        food.addService(foodDelivery);
        departments.add(food);

        //Comment this out if you want to use test
        staffPlacement();
        //initRan = true;
    }

    //Reads the DB, and puts the staff in their corresponding places
    private void staffPlacement(){
        ArrayList<Staff> allStaff = staffDatabase.getStaff();

        for(Staff person: allStaff){
            String title = person.getJobTitle();

            for(Service currentService: this.getAllServices()) {

                if (title.equalsIgnoreCase("TRANSLATOR") && currentService.toString().equalsIgnoreCase("TRANSLATION SERVICE")) {
                    currentService.addEligibleStaff(person);
                }
                else if (title.equalsIgnoreCase("JANITOR") && currentService.toString().equalsIgnoreCase("SANITATION")) {
                    currentService.addEligibleStaff(person);
                }
                else if ((title.equalsIgnoreCase("CHEF") || title.equalsIgnoreCase("FOOD DELIVERY")) && currentService.toString().equalsIgnoreCase("FOOD DELIVERY SERVICE")) {
                    currentService.addEligibleStaff(person);
                }
                else if (title.equalsIgnoreCase("TRANSPORT STAFF") && currentService.toString().equalsIgnoreCase("TRANSPORT SERVICE")) {
                    currentService.addEligibleStaff(person);
                }

            }
        }
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    //Singleton Stuff (init HAS to be run)
    public static DepartmentSubsystem getSubsystem(){
        if(singleton == null){
            singleton =  new DepartmentSubsystem();
        }
        return singleton;

    }

    //login function for staff members
    public boolean login(String username, String password){
        ArrayList<Staff> allStaff = staffDatabase.getStaff();
        for(Staff member: allStaff){
            System.out.println(member.getUsername() + " == "+ username );
            if(member.getUsername().equals(username)){
                if(member.getPassword().equals(password)){
                    this.currentlyLoggedIn = member;
                    return true;
                }
                else
                    break;
            }
        }
        return false;
    }

    //Getters
    public Department getDepartment(String departmentName){
        for(Department d: this.departments){
            if(d.toString().equals(departmentName)){
                return d;
            }
        }
        return null;
    }
//    public ArrayList<Service> getServices(String department){
//        //This method should return new services every time, to make sure that the specific service is not reused
//        ArrayList<Service> returnList = new ArrayList<Service>();
//        for(Service service: getDepartment(department).getServices()){
//            //If its not used, we just add it to the list and move on
//            if(!service.isUsed()){
//                returnList.add(service);
//                continue;
//            }
//            if(service instanceof FoodDelivery)
//                returnList.add(new FoodDelivery(this.getDepartment("Food"), "Food Delivery Service"));
//            else if(service instanceof Sanitation)
//                returnList.add(new Sanitation(this.getDepartment("Facilities"), "Sanitation"));
//            else if(service instanceof Translation)
//                returnList.add(new Translation(this.getDepartment("Translation Department"), "Translation service"));
//            else if(service instanceof Transport)
//                returnList.add(new Transport(this.getDepartment("Transportation Department"), "Transport service"));
//        }
//
//        return returnList;
//    }

    public Staff getCurrentLoggedIn(){
        return this.currentlyLoggedIn;
    }
    public ArrayList<Service> getAllServices(){
        ArrayList<Service> allServices = new ArrayList<Service>();
        for(Department dept: this.getDepartments()){
            allServices.addAll(dept.getServices());
        }
        return allServices;
    }
    public ArrayList<Department> getDepartments() {
        return departments;
    }
    public ArrayList<Staff> getStaff(Service service){
        return service.getStaff();
    }
//    public ArrayList<Staff> getStaff(String service){
//        for(Department dept: this.departments) {
//            ArrayList<Service> temp = dept.getServices();
//            for(Service ser: temp){
//                if(ser.toString().equals(service)){
//                    return ser.getStaff();
//                }
//            }
//        }
//        return null;
//    }

    //Assign a service request
//    public void submitRequest(Service service, String time, String date, Node location, Staff person, int RID, boolean email, String emailRecipient){
//        Department temp = new Department("TEMPORARY");
//        for(Department dept: departments){
//            if(dept.getServices().contains(service)){
//                temp = dept;
//            }
//        }
//        ServiceRequest request = new ServiceRequest(service, RID, location, time, date, person);
//        person.addRequest(request);
//        temp.addRequest(RID, request);
//        if(email){
//            processEmailRequest(emailRecipient, request);
//        }
//    }
//
//    private boolean processEmailRequest(String recipient, ServiceRequest request){
////        //TODO make a gmail for the team so we can do this
////        String username = "";
////        String password = "";
////        String message = request.toString();
////        sendFromGMail(username, password, recipient, "HOSPITAL SERVICE REQUEST AUTOMATED MESSAGE", message);
//       return false;
//    }

    //Staff modifiers
    public void addStaff(Service ser,String username, String password, String jobTitle, String fullName, int id){
        //staffDatabase.setStaffCounter(1);
        Staff newPerson = new Staff(username, password, jobTitle, fullName, id);
//        if(jobTitle.equals("Chef") || jobTitle.equals("Food Delivery")){
//            departments.get(3).addPersonel(newPerson);
//            departments.get(3).getServices().get(0).addEligibleStaff(newPerson);
//        }else if(jobTitle.equals("Translator")){
//            departments.get(0).addPersonel(newPerson);
//            departments.get(0).getServices().get(0).addEligibleStaff(newPerson);
//        }else if(jobTitle.equals("Transport Staff")){
//            departments.get(1).addPersonel(newPerson);
//            departments.get(1).getServices().get(0).addEligibleStaff(newPerson);
//        }else if(jobTitle.equals("Janitor")){
//            departments.get(2).addPersonel(newPerson);
//            departments.get(2).getServices().get(0).addEligibleStaff(newPerson);
//        }
        //dep.addPersonel(newPerson);
        ser.addEligibleStaff(newPerson);
        staffDatabase.addStaff(newPerson);
    }

    public void modifyStaff(Staff person, String username, String password, String jobTitle, String fullName, int ID){
        person.setNewVars(username, password, jobTitle, fullName, ID);
        staffDatabase.modifyStaff(person);
    }

    public void deleteStaff(Service ser, String userName){
        Staff person = new Staff(userName,null, null,null, 0);
//        if(jobTile.equals("Chef") || jobTile.equals("Food Delivery")){
//            departments.get(3).removePersonel(person);
//            departments.get(3).getServices().get(0).removeEligibleStaff(person);
//        }else if(jobTile.equals("Translator")){
//            departments.get(0).removePersonel(person);
//            departments.get(0).getServices().get(0).removeEligibleStaff(person);
//        }else if(jobTile.equals("Transport Staff")){
//            departments.get(1).removePersonel(person);
//            departments.get(1).getServices().get(0).removeEligibleStaff(person);
//        }else if(jobTile.equals("Janitor")){
//            departments.get(2).removePersonel(person);
//            departments.get(2).getServices().get(0).removeEligibleStaff(person);
//        }

        //dep.removePersonel(person);
        ser.removeEligibleStaff(person);

        staffDatabase.deleteStaff(person);
    }
}