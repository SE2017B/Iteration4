package ui;

import map.FloorNumber;
import map.Node;

import java.util.ArrayList;

public class PathViewer {
    //attributes
    private ArrayList<Node> path;
    private FloorNumber floor;
    private PathID pathID;
    private double scale;
    public PathViewer(ArrayList<Node> path){
        this.path=path;
        this.floor=path.get(0).getFloor();
        this.scale=0;//for now
    }
    public void setScale(){

    }
    public void getScale(){
        
    }
}
