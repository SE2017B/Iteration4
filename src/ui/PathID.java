package ui;

import map.FloorNumber;

public class PathID {
    private FloorNumber floor;
    private int id;

    public void PathID(FloorNumber floor, int id){
        this.floor = floor;
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public FloorNumber getFloor(){
        return floor;
    }
}
