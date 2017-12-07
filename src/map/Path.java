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
            directions.add(0, "Go up " + getBetterName(path.get(0)));
        } else if(path.get(0).getFloor().getNodeMapping() > path.get(1).getFloor().getNodeMapping()){
            prevElevator = true;
            directions.add(0, "Go down " + getBetterName(path.get(0)));
        } else {
            directions.add(0, "Go straight from " + getBetterName(path.get(0)));
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

            System.out.println("Node 1: " + path.get(i-1).getShortName());
            System.out.println("Node 2: " + path.get(i).getShortName());
            System.out.println("Node 3: " + path.get(i+1).getShortName() + "\n");

            if(Vector2_3Z != 0) {
                if (path.get(i-1).getFloor().getNodeMapping() > path.get(i+1).getFloor().getNodeMapping() && !prevElevator) {
                    directions.add("Go down " + getBetterName(path.get(i)));
                    prevElevator = true;
                    continue;
                } else if (path.get(i-1).getFloor().getNodeMapping() < path.get(i+1).getFloor().getNodeMapping() && !prevElevator) {
                    directions.add("Go up " + getBetterName(path.get(i)));
                    prevElevator = true;
                    continue;
                }
            } else {
                if(path.get(i-1).getFloor().getNodeMapping() > path.get(i).getFloor().getNodeMapping() ||
                    path.get(i-1).getFloor().getNodeMapping() < path.get(i).getFloor().getNodeMapping()){
                    if(!prevElevator) directions.set(directions.size()-1, directions.get(directions.size()-1).substring(0, directions.get(directions.size()-1).length()-1).concat(path.get(i).getFloor().getDbMapping()));
                    else directions.set(directions.size()-1, directions.get(directions.size()-1).concat(" until floor " + path.get(i).getFloor().getDbMapping()));
                    directions.add("Exit " + getBetterName(path.get(i)) + " and continue towards " + getBetterName(path.get(i+1)));
                    prevElevator = false;
                    continue;
                }
            }

            if(path.get(i).equals(path.get(i-1))){
                directions.set(directions.size()-1, "Stop at " + getBetterName(path.get(i)));
                prevStop = true;
            }

            if(angle >= -155 && angle <= -25){
                if(straight) directions.add("Continue straight for " + (int)(path.get(lastStraight).getEuclidianDistance(path.get(i)) * pixelsToMeters) + "m and take a left");
                else if(prevElevator) directions.set(directions.size()-1, directions.get(directions.size()-1).concat(" until floor " + path.get(i).getFloor().getDbMapping()));
                else if(prevStop) directions.add("Turn left from " + getBetterName(path.get(i)));
                else {
                    if(!path.get(i).getType().equals("HALL")) {
                        if (angle >= -40) directions.add("Take a slight left at " + getBetterName(path.get(i)));
                        else if (angle <= -140) directions.add("Take a sharp left at " + getBetterName(path.get(i)));
                        else directions.add("Take a left at " + getBetterName(path.get(i)));
                    } else {
                        if (angle >= -40) directions.add("Take a slight left in " + getBetterName(path.get(i)));
                        else if (angle <= -140) directions.add("Take a sharp left in " + getBetterName(path.get(i)));
                        else directions.add("Take a left in " + getBetterName(path.get(i)));
                    }
                }
            }
            else if(angle >= 25 && angle <= 155){
                if(straight) directions.add("Continue straight for " + (int)(path.get(lastStraight).getEuclidianDistance(path.get(i)) * pixelsToMeters) + "m and take a right");
                else if(prevElevator) directions.set(directions.size()-1, directions.get(directions.size()-1).concat(" until floor " + path.get(i).getFloor().getDbMapping()));
                else if(prevStop) directions.add("Turn right from " + getBetterName(path.get(i)));
                else {
                    if(!path.get(i).getType().equals("HALL")) {
                        if (angle <= 40) directions.add("Take a slight right at " + getBetterName(path.get(i)));
                        else if (angle >= 140) directions.add("Take a sharp right at " + getBetterName(path.get(i)));
                        else directions.add("Take a right at " + getBetterName(path.get(i)));
                    } else {
                        if (angle <= 40) directions.add("Take a slight right in " + getBetterName(path.get(i)));
                        else if (angle >= 140) directions.add("Take a sharp right in " + getBetterName(path.get(i)));
                        else directions.add("Take a right in " + getBetterName(path.get(i)));
                    }
                }
            }
            else if (angle > -25 && angle < 25){
                if(path.get(i).getType().equals("HALL")){
                    if(!straight) lastStraight = i-1;
                    straight = true;
                    continue;
                }
                if(prevElevator || prevStop) directions.add("Go straight from " + getBetterName(path.get(i)));
                else directions.add("Continue straight past " + getBetterName(path.get(i)));
            }
            else {
                directions.add("Turn around from " + getBetterName(path.get(i)));
            }
            prevElevator = false;
            prevStop = false;
            straight = false;
        }

        if(prevElevator) directions.set(directions.size()-1, directions.get(directions.size()-1).concat(" until floor " + path.get(path.size()-1).getFloor().getDbMapping()));
        if(path.get(path.size()-2).getFloor().getNodeMapping() > path.get(path.size()-1).getFloor().getNodeMapping() ||
                path.get(path.size()-2).getFloor().getNodeMapping() < path.get(path.size()-1).getFloor().getNodeMapping()){
            directions.set(directions.size()-1, directions.get(directions.size()-1).substring(0, directions.get(directions.size()-1).length()-1).concat(path.get(path.size()-1).getFloor().getDbMapping()));
        }
        directions.add("Stop at " + getFullType(path.get(path.size()-1)));

        return directions;
    }

    private String getFullType(Node node){
        String type = node.getType();
        String string;
        switch(type){
            case "REST":
                string = "your destination Restroom";
                break;
            case "ELEV":
                string = "your destination Elevator";
                break;
            case "LABS":
                string = node.getShortName();
                break;
            case "DEPT":
                string = node.getShortName();
                break;
            case "INFO":
                string = "the " + node.getLongName();
                break;
            case "STAI":
                string = "your destination Stairwell";
                break;
            case "EXIT":
                string = "your destination Exit";
                break;
            case "RETL":
                string = node.getShortName();
                break;
            case "CONF":
                string = node.getShortName();
                break;
            case "SERV":
                string = "the " + node.getLongName();
                break;
            default: string = "";
        }
        return string;
    }

    private String getBetterName(Node node){
        String string;
        if(node.getType().equals("HALL")) string = "the Hallway";
        else string = node.getShortName();
        return string;
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
