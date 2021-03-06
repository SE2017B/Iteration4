/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 4
* Original author(s): Nicholas Fajardo, Meghana Bhatia
* The following code
*/

package DepartmentSubsystem;

import DepartmentSubsystem.Exceptions.PasswordException;
import DepartmentSubsystem.Exceptions.UsernameException;
import DepartmentSubsystem.Services.FoodDelivery;
import DepartmentSubsystem.Services.Transport;
import DepartmentSubsystem.Services.Sanitation;
import DepartmentSubsystem.Services.Translation;
import Email.EmailServer;
import database.staffDatabase;
import exceptions.InvalidPasswordException;

import javax.mail.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentSubsystem {
    private Staff currentlyLoggedIn = null;     //get currently logged in user
    ArrayList<Service> services;
    //Admins cannot perform a service, so they are stored outside of the services
    ArrayList<Staff> admin;
    private ArrayList<Staff> allStaff;  //array of staff
    private HashMap<String, String> loginCheck; //check login
    private EmailServer emailServer = EmailServer.getEmailServer(); //get email server

    //Singleton class of DepartmentSubsystem
    private static DepartmentSubsystem singleton;
    private DepartmentSubsystem(){init();}
    private void init(){
        this.services = new ArrayList<>();
        //System.out.println("Making the staff");
        this.allStaff = staffDatabase.getStaff();
        populateLoginCheck(this.allStaff);
        //System.out.println("Staff has been made");

        Service translation = new Translation("Translation");
        translation.setURL("/fxml/Translation.fxml");
        services.add((Service)translation);

        Service transport = new Transport("Transport");
        transport.setURL("/fxml/Transport.fxml");
        services.add(transport);

        Service sanitation = new Sanitation("Sanitation");
        sanitation.setURL("/fxml/Sanitation.fxml");
        services.add(sanitation);

        Service foodDelivery = new FoodDelivery("Food Delivery");
        foodDelivery.setURL("/fxml/FoodDelivery.fxml");
        services.add(foodDelivery);

        //Place each staff member in a service
        staffPlacement();

        //Language Stuff goes here
        populateLanguages();
    }

    //populate array of lanuguage
    private void populateLanguages() {

    }
    //get subsystem
    public static DepartmentSubsystem getSubsystem(){
        if(singleton == null){
            singleton =  new DepartmentSubsystem();
        }
        return singleton;

    }
    //hashmap for login info
    private void populateLoginCheck(ArrayList<Staff> members){
        this.loginCheck = new HashMap<>();
        for(Staff person: members){
            loginCheck.put(person.getUsername(), person.getPassword());
            if(person.isAdmin()){
                this.admin.add(person);
            }
        }

    }

    //Reads the DB, and puts the staff in their corresponding places
    private void staffPlacement(){
        for(Staff person: this.allStaff){
            String title = person.getJobTitle();
            for(Service currentService: this.services) {
                if (title.equalsIgnoreCase("TRANSLATOR") && currentService.toString().equalsIgnoreCase("TRANSLATION")) {
                    currentService.addEligibleStaff(person);
                }
                else if (title.equalsIgnoreCase("JANITOR") && currentService.toString().equalsIgnoreCase("SANITATION")) {
                    currentService.addEligibleStaff(person);
                }
                else if ((title.equalsIgnoreCase("CHEF") || title.equalsIgnoreCase("FOOD DELIVERY")) && currentService.toString().equalsIgnoreCase("FOOD DELIVERY")) {
                    currentService.addEligibleStaff(person);
                }
                else if (title.equalsIgnoreCase("TRANSPORT STAFF") && currentService.toString().equalsIgnoreCase("TRANSPORT")) {
                    currentService.addEligibleStaff(person);
                }
            }
        }
    }
    //getter for list of services
    public ArrayList<Service> getServices() {
        replaceUsedService();
        return this.services;
    }

    //login function for staff members
    public Staff login(String username, String password) throws UsernameException,  PasswordException {
        if(!this.loginCheck.containsKey(username)){
            throw new UsernameException();
        }
        if(!this.loginCheck.get(username).equals(password)){
            throw new PasswordException();
        }
        for(Staff person: this.allStaff){
            if(username.equalsIgnoreCase(person.getUsername())){
                this.currentlyLoggedIn = person;
                return person;
            }
        }
        //TODO Change this to an actual exception to be thrown
        return null;
    }
    //get the staff member who is currently logged in
    public Staff getCurrentLoggedIn(){
        return this.currentlyLoggedIn;
    }

    //Staff modifiers
    public void addStaff(String username, String password, String jobTitle, String fullName, int id, int admin){
        //staffDatabase.setStaffCounter(1);
        Staff newPerson = new Staff(username, password, jobTitle, fullName, id, admin);
        //ser.addEligibleStaff(newPerson);
        staffDatabase.addStaff(newPerson);
    }
    //modify staff
    public void modifyStaff(Staff person, String username, String password, String jobTitle, String fullName, int ID, int admin) throws InvalidPasswordException {

        person.updateCredentials(username, password, jobTitle, fullName, ID, admin);
        staffDatabase.modifyStaff(person, new Staff(username, password, jobTitle, fullName, ID, admin));
    }
    //delete staff from DB
    public void deleteStaff(String aFullName, String userName){
        Staff person = findPerson(userName);
        staffDatabase.deleteStaff(person);
    }

    //locate a specific username for a staff
    private Staff findPerson(String username) {
        for(Staff person: this.allStaff){
            if(username.equalsIgnoreCase(person.getUsername())){
                return person;
            }
        }
        return null;
    }
    //submitting service requests
    public void submitServiceRequest(ServiceRequest sr){
        //TODO do something with SR...
    }
    public void submitServiceRequest(ServiceRequest sr, String email) throws Email.EmailFormatException, MessagingException {
        //TODO do something with SR...probably email it to someone at some point in time
        emailServer.sendEmail(email, sr.toString());
    }

    //////////////////////
    //  Helper Methods  //
    //////////////////////
    private void replaceUsedService() {
        for(Service service: this.services){
            if(service.isUsed()){
                ArrayList<Staff> tempList = service.getStaff();
                if(service.getName().equals("Translation")){
                    Service translation = new Translation("Translation");
                    translation.setURL("/fxml/Translation.fxml");
                    translation.setStaff(tempList);
                    service = translation;
                }
                else if(service.getName().equals("Transport")){
                    Service transport = new Transport("Transport");
                    transport.setURL("/fxml/Transport.fxml");
                    transport.setStaff(tempList);
                    service = transport;
                }
                else if(service.getName().equals("Sanitation")){
                    Service sanitation = new Sanitation("Sanitation");
                    sanitation.setURL("/fxml/Sanitation.fxml");
                    sanitation.setStaff(tempList);
                    service = sanitation;
                }
                else if(service.getName().equals("Food Delivery")){
                    Service foodDelivery = new FoodDelivery("Food Delivery");
                    foodDelivery.setURL("/fxml/FoodDelivery.fxml");
                    foodDelivery.setStaff(tempList);
                    service = foodDelivery;
                }
            }
        }
    }
}