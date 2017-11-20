package map;

import exceptions.InvalidNodeException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Path implements Comparable<Path> {
    private ArrayList<Node> path = new ArrayList<>();
    private ArrayList<String> directions = new ArrayList<>();
    private double distance;

    //Constructors
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
    public void addToPath(Path path){
        this.path.addAll(path.getPath());
    }
    public void addDirections(String direction){
        this.directions.add(direction);
    }
    public void addDistance(double distance) {
        this.distance += distance;
    }
    public Path findDirections(){
        int PVX = 0;
        int PVY = 0;
        int NVX = 0;
        int NVY = 0;
        int VX = 0;
        int VY = 0;
        boolean prevElevator = false;
        boolean prevStop = false;

        for(int i = 1; i < path.size() - 1; i++){
//            PVX = path.get(i-1).getX();
//            PVY = path.get(i-1).getY();
//            NVX = path.get(i+1).getX();
//            NVY = path.get(i+1).getY();
//            VX = path.get(i).getX();
//            VY = path.get(i).getY();
//
//
//            double numerator = (PVX - VX) * (VX - NVX) + (PVY - VY) * (VY - NVY);
//            double denominator = Math.sqrt(Math.pow(PVX - VX, 2) + Math.pow(PVY - VY, 2)) * Math.sqrt(Math.pow(VX - NVX, 2) + Math.pow(VY - NVX, 2));
//            double cos = numerator / denominator;
//            double angle = Math.acos(cos);

            int Vector1_2X = path.get(i).getX() - path.get(i-1).getX();
            int Vector1_2Y = path.get(i).getY() - path.get(i-1).getY();
            int Vector1_2Z = path.get(i).getFloor().getNodeMapping() - path.get(i-1).getFloor().getNodeMapping();
            int Vector2_3X = path.get(i+1).getX() - path.get(i).getX();
            int Vector2_3Y = path.get(i+1).getY() - path.get(i).getY();
            int Vector2_3Z = path.get(i+1).getFloor().getNodeMapping() - path.get(i).getFloor().getNodeMapping();

            double dot = Vector1_2X * Vector2_3X + Vector1_2Y * Vector2_3Y;
            double det = Vector1_2X * Vector2_3Y - Vector2_3X * Vector1_2Y;
            double angle = Math.toDegrees(Math.atan2(det, dot));



            if(Vector1_2Z > Vector2_3Z && !prevElevator){
                directions.add("Go up " + path.get(i).getShortName());
                prevElevator = true;
                continue;
            } else if (Vector1_2Z < Vector2_3Z && !prevElevator) {
                directions.add("Go down " + path.get(i).getShortName());
                prevElevator = true;
                continue;
            }

            if(path.get(i).equals(path.get(i-1))){
                directions.set(directions.size()-1, "Stop at " + path.get(i-1).getShortName());
                prevStop = true;
            }

            if(angle >= -160 && angle <= -20){
                if(prevElevator || prevStop) directions.add("Turn right from " + path.get(i).getShortName());
                 else directions.add("Take a right at this " + path.get(i).getShortName());
            }
            else if(angle >= 20 && angle <= 160){
                if(prevElevator || prevStop) directions.add("Turn left from " + path.get(i).getShortName());
                else directions.add("Take a left at this " + path.get(i).getShortName());
            }else if (angle > -20 && angle < 20){
                if(prevElevator || prevStop) directions.add("Go straight from " + path.get(i).getShortName());
                else directions.add("Go straight through " + path.get(i).getShortName());
            } else {
                directions.add("Turn around from " + path.get(i).getShortName());
            }
            prevElevator = false;
            prevStop = false;
        }
        directions.add("Stop at " + path.get(path.size()-1).getShortName());
        return this;
    }

    //Getters
    public ArrayList<Node> getPath() {
        return this.path;
    }
    public ArrayList<String> getDirections(){
        return this.directions;
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
