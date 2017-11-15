/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/


package service;

import java.util.ArrayList;

public class TransportService extends Service{

    public TransportService (){
        this.type = "TransportationService";
        this.personnel = new ArrayList<>();
        this.backlog = new ArrayList<>();
        this.description = "";
    }

    public TransportService(String description){
        this.type = "TransportationService";
        this.personnel = new ArrayList<>();
        this.backlog = new ArrayList<>();
        this.description = description;
    }
}