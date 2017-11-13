package service;

import java.util.ArrayList;

public class TranslatorService extends Service{
    private int endTime;

    public TranslatorService (){
        this.type = "TranslatorService";
        this.personnel = new ArrayList<>();
        this.backlog = new ArrayList<>();
        this.description = "";
    }

    public TranslatorService(String description, int endTime){
        this.type = "TranslatorService";
        this.personnel = new ArrayList<>();
        this.backlog = new ArrayList<>();
        this.description = description;
        this.endTime = endTime;
    }

    //getters
    public int getEndTime(){
        return this.endTime;
    }

    //setters
    public void setEndTime(int endTime){
        this.endTime = endTime;
    }
}