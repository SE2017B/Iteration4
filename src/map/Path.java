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
    public double getDistance() throws InvalidNodeException{
        double runningDistance = 0;
        for(int i=0;i<this.path.size()-1;i++){
            runningDistance += path.get(i).getEdgeOf(path.get(i+1)).getCost();
        }
        return runningDistance;
    }

    @Override
    public String toString(){
        return this.getPath().toString();
    }

    @Override
    public int compareTo(Path path){
        try{
            if(this.getDistance() < path.getDistance()) return -1;
            else return 1;
        }catch(InvalidNodeException e){
            System.out.println("Invalid node in path");
            return 0;
        }

    }
}
