/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 2
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

import javax.mail.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DepartmentSubsystem {
    private Staff currentlyLoggedIn = null;
    ArrayList<Service> services;
    //Admins cannot perform a service, so they are stored outside of the services
    ArrayList<Staff> admin;
    private ArrayList<Staff> allStaff;
    private HashMap<String, String> loginCheck;
    private EmailServer emailServer = EmailServer.getEmailServer();

    //Singleton Stuff
    private static DepartmentSubsystem singleton;
    private DepartmentSubsystem(){init();}
    private void init(){
        this.allStaff = staffDatabase.getStaff();

        populateLoginCheck(this.allStaff);

        Service translation = new Translation("Translation");
        translation.setURL("/fxml/Translation.fxml");
        services.add(translation);

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

    private void populateLanguages() {

    }

    public static DepartmentSubsystem getSubsystem(){
        if(singleton == null){
            singleton =  new DepartmentSubsystem();
        }
        return singleton;

    }
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
    public Staff getCurrentLoggedIn(){
        return this.currentlyLoggedIn;
    }

    //Staff modifiers
    public void addStaff(Service ser,String username, String password, String jobTitle, String fullName, int id, int admin){
        //staffDatabase.setStaffCounter(1);
        Staff newPerson = new Staff(username, password, jobTitle, fullName, id, admin);
        ser.addEligibleStaff(newPerson);
        staffDatabase.addStaff(newPerson);
    }
    public void modifyStaff(Staff person, String username, String password, String jobTitle, String fullName, int ID){
        person.updateCredidentials(username, password, jobTitle, fullName, ID);
        staffDatabase.modifyStaff(person);
    }
    public void deleteStaff(Service ser, String userName){
        Staff person = new Staff(userName,null, null,null, 0, 0);
        ser.removeEligibleStaff(person);
        staffDatabase.deleteStaff(person);
    }

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