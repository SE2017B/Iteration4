package map;

import exceptions.InvalidNodeException;

import java.util.ArrayList;
import java.util.Collections;
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
    public void addToPath(Path path){
        this.path.addAll(path.getPath());
    }
    public void addDirections(String direction){
        this.directions.add(direction);
    }

    public ArrayList<String> getDirections(){
        return this.directions;
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
    public void addDistance(double distance) {
        this.distance += distance;
    }

    public Path findDirections(){
        int PVX = 0;
        int PVY = 0;
        int NVX = 0;
        int NVY = 0;

        for(int i = 1; i < path.size() - 1; i++){
//            PVX = path.get(i-1).getX();
//            PVY = path.get(i-1).getY();
//            NVX = path.get(i+1).getX();
//            NVY = path.get(i+1).getY();
//
//            int num = (PVX*NVX + PVY*NVY);
//            double den = (Math.sqrt(Math.pow(PVX, 2) + Math.pow(PVY, 2)) * (Math.sqrt(Math.pow(NVX, 2) + Math.pow(NVY, 2))) );
//            double cos =  num / den;
//            System.out.println(cos);
//            System.out.println(Math.acos(cos));
//            double angle = Math.acos(cos);
            double side1 = path.get(i-1).getEuclidianDistance(path.get(i));
            double side2 = path.get(i).getEuclidianDistance(path.get(i+1));
            double side3 = path.get(i-1).getEuclidianDistance(path.get(i+1));

            ArrayList<Double> sides = new ArrayList<>();
            sides.add(side1);
            sides.add(side2);
            sides.add(side3);
            sides.sort(Collections.reverseOrder());

            double cosBiggest = (Math.pow(side1, 2) + Math.pow(side2, 2) - Math.pow(side3, 2)) / (2 * sides.get(1) * sides.get(2));
            double angle = Math.toDegrees(Math.acos(cosBiggest));
            if (!sides.get(0).equals(side3)) {
                double sin = (side3 * Math.sin(angle)) / sides.get(0);
                angle = Math.toDegrees(Math.asin(sin));
            }

            if(angle >= 20 && angle <= 160){
                directions.add("take a right at this " + path.get(i).getShortName());
            } else if(angle >= 200 && angle <= 340){
                directions.add("take a left at this " + path.get(i).getShortName());
            }else{
                directions.add("go straight through " + path.get(i).getShortName());
            }

        }
        return this;
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
