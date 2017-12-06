package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Path implements Comparable<Path> {
    private ArrayList<Node> path = new ArrayList<>();
    private ArrayList<String> directions = new ArrayList<>();
    private double distance;
    private double pixelsToMeters = .1;


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
    public void addToPath(Path path){
        this.path.addAll(path.getPath());
    }
    public void addToPath(Node node, int index){
        this.path.add(index, node);
    }
    public void addDistance(double distance) {
        this.distance += distance;
    }

    //Using the short names from the databse for names of the nodes to do things at
    public ArrayList<String> findDirections(){
        int PVX = 0;
        int PVY = 0;
        int NVX = 0;
        int NVY = 0;
        int VX = 0;
        int VY = 0;
        boolean prevElevator = false;
        boolean prevStop = false;
        boolean straight = false;
        int lastStraight = 0;

        if(path.get(0).getFloor().getNodeMapping() < path.get(1).getFloor().getNodeMapping()){
            prevElevator = true;
            directions.add(0, "Go up " + path.get(0).getShortName());
        } else if(path.get(0).getFloor().getNodeMapping() > path.get(1).getFloor().getNodeMapping()){
            prevElevator = true;
            directions.add(0, "Go down " + path.get(0).getShortName());
        } else {
            directions.add(0, "Go straight from " + path.get(0).getShortName());
        }

        for(int i = 1; i < path.size() - 1; i++){
            int Vector1_2X = path.get(i).getX() - path.get(i-1).getX();
            int Vector1_2Y = path.get(i).getY() - path.get(i-1).getY();
            int Vector1_2Z = path.get(i).getFloor().getNodeMapping() - path.get(i-1).getFloor().getNodeMapping();
            int Vector2_3X = path.get(i+1).getX() - path.get(i).getX();
            int Vector2_3Y = path.get(i+1).getY() - path.get(i).getY();
            int Vector2_3Z = path.get(i+1).getFloor().getNodeMapping() - path.get(i).getFloor().getNodeMapping();

            double dot = Vector1_2X * Vector2_3X + Vector1_2Y * Vector2_3Y;
            double det = Vector1_2X * Vector2_3Y - Vector2_3X * Vector1_2Y;
            double angle = Math.toDegrees(Math.atan2(det, dot));

//            System.out.println(angle);
//            System.out.println(prevElevator);
            System.out.println("Node 1: " + path.get(i-1).getID());
            System.out.println("Node 2: " + path.get(i).getID());
            System.out.println("Node 3: " + path.get(i+1).getID() + "\n");

            if(Vector2_3Z != 0) {
                if (path.get(i-1).getFloor().getNodeMapping() > path.get(i+1).getFloor().getNodeMapping() && !prevElevator) {
                    directions.add("Go down " + path.get(i).getShortName());
                    prevElevator = true;
                    continue;
                } else if (path.get(i-1).getFloor().getNodeMapping() < path.get(i+1).getFloor().getNodeMapping() && !prevElevator) {
                    directions.add("Go up " + path.get(i).getShortName());
                    prevElevator = true;
                    continue;
                }
            } else {
                if(path.get(i-1).getFloor().getNodeMapping() > path.get(i).getFloor().getNodeMapping() ||
                    path.get(i-1).getFloor().getNodeMapping() < path.get(i).getFloor().getNodeMapping()){
                    if(!prevElevator) directions.set(directions.size()-1, directions.get(directions.size()-1).substring(0, directions.get(directions.size()-1).length()-1).concat(path.get(i).getFloor().getDbMapping()));
                    else directions.set(directions.size()-1, directions.get(directions.size()-1).concat(" until floor " + path.get(i).getFloor().getDbMapping()));
                    directions.add("Exit " + path.get(i).getShortName() + " and continue towards " + path.get(i+1).getShortName());
                    prevElevator = false;
                    continue;
                }
            }

            if(path.get(i).equals(path.get(i-1))){
                directions.set(directions.size()-1, "Stop at " + path.get(i-1).getShortName());
                prevStop = true;
            }

            if(angle >= -155 && angle <= -25){
                if(straight) directions.add("Continue straight for " + path.get(lastStraight).getEuclidianDistance(path.get(i)) * pixelsToMeters + "m and take a left");
                else if(prevElevator) directions.set(directions.size()-1, directions.get(directions.size()-1).concat(" until floor " + path.get(i).getFloor().getDbMapping()));
                else if(prevStop) directions.add("Turn left from " + path.get(i).getShortName());
                else {
                    if(angle >= -40) directions.add("Take a slight left at " + path.get(i).getShortName());
                    else if(angle <= -140) directions.add("Take a sharp left at " + path.get(i).getShortName());
                    else directions.add("Take a left at " + path.get(i).getShortName());
                }
            }
            else if(angle >= 25 && angle <= 155){
                if(straight) directions.add("Continue straight for " + (int)(path.get(lastStraight).getEuclidianDistance(path.get(i)) * pixelsToMeters) + "m and take a right");
                else if(prevElevator) directions.set(directions.size()-1, directions.get(directions.size()-1).concat(" until floor " + path.get(i).getFloor().getDbMapping()));
                else if(prevStop) directions.add("Turn right from " + path.get(i).getShortName());
                else {
                    if(angle <= 40) directions.add("Take a slight right at " + path.get(i).getShortName());
                    else if(angle >= 140) directions.add("Take a sharp right at " + path.get(i).getShortName());
                    else directions.add("Take a right at " + path.get(i).getShortName());
                }
            }
            else if (angle > -25 && angle < 25){
                if(path.get(i).getType().equals("HALL")){
                    straight = true;
                    lastStraight = i-1;
                    continue;
                }
                if(prevElevator || prevStop) directions.add("Go straight from " + path.get(i).getShortName());
                else directions.add("Continue straight past " + path.get(i).getShortName());
            }
            else {
                directions.add("Turn around from " + path.get(i).getShortName());
            }
            prevElevator = false;
            prevStop = false;
            straight = false;
        }

        if(path.get(path.size()-2).getFloor().getNodeMapping() > path.get(path.size()-1).getFloor().getNodeMapping() ||
                path.get(path.size()-2).getFloor().getNodeMapping() < path.get(path.size()-1).getFloor().getNodeMapping()){
            directions.set(directions.size()-1, directions.get(directions.size()-1).substring(0, directions.get(directions.size()-1).length()-1).concat(path.get(path.size()-1).getFloor().getDbMapping()));
        }
        directions.add("Stop at " + path.get(path.size()-1).getShortName());

        //Eliminates multiple "go straights" in a row
//        for(int i = 1; i<directions.size(); i++) {
//            if(directions.get(i).contains("straight") && directions.get(i-1).contains("straight")) {
//                directions.remove(i);
//                i--;
//            }
//        }
        return directions;
    }

    //Retrieves the list of floors visited within a path
    public ArrayList<FloorNumber> floorNumberList() {
        ArrayList<FloorNumber> floorNumList = new ArrayList<>();

        for (Node aPath : path) {
            FloorNumber floor = aPath.getFloor();
            if (floorNumList.contains(floor)) {
                continue;
            }
            floorNumList.add(floor);
        }
        return floorNumList;
    }

    //Getters
    public ArrayList<Node> getPath() {
        return this.path;
    }
    public ArrayList<String> getDirections(){
        return this.directions;
    }
    private double getDistance(){
        for(int i=0;i<this.path.size()-1;i++){
            this.distance += this.path.get(i).getEdgeOf(this.path.get(i+1)).getCost();
        }
        return distance;
    }

    public Path getReverse(){
        Path reverse = new Path(this);
        reverse.reverseNodes();
        return reverse;
    }

    private void reverseNodes(){
        Collections.reverse(path);
    }

    @Override
    public String toString(){
        return this.getPath().toString();
    }

    @Override
    public int compareTo(Path path){
        if(this.getDistance() < path.getDistance()) return 1;
        else return -1;
    }
}
