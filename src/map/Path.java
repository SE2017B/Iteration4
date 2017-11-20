package map;

import exceptions.InvalidNodeException;

import java.util.ArrayList;
import java.util.Stack;

public class Path implements Comparable<Path> {
    private ArrayList<Node> path = new ArrayList<>();
    private ArrayList<String> directions = new ArrayList<>();
    private double distance;

    public Path(){
        distance = 0;
    }

    public Path(Path path){
        this.path.addAll(path.getPath());
    }

    public void addToPath(Node node){
        this.path.add(node);
    }
    public void addToPath(Node node, int position){
        this.path.add(position, node);
    }
    public void addDirections(String direction){
        this.directions.add(direction);
    }

    public ArrayList<String> getDirections(){
        return null;
    }
    public ArrayList<Node> getPath() {
        return this.path;
    }
    public double getDistance(){
        for(int i=0;i<this.path.size()-1;i++){
            this.distance += this.path.get(i).getEdgeOf(this.path.get(i+1)).getCost();
        }
        return distance;
    }

    @Override
    public String toString(){
        return this.getPath().toString();
    }

    @Override
    public int compareTo(Path path){
        if(this.getDistance() < path.getDistance()) return -1;
        else return 1;
    }
}
