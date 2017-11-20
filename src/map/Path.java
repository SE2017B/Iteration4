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
    public void addToPath(Path path){
        this.path.addAll(path.getPath());
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

    public void findDirections(){
        int PVX = 0;
        int PVY = 0;
        int NVX = 0;
        int NVY = 0;

        for(int i = 1; i < path.size() - 1; i++){
            PVX = path.get(i-1).getX();
            PVY = path.get(i-1).getY();
            NVX = path.get(i+1).getX();
            NVY = path.get(i+1).getY();

            int num = (PVX*NVX + PVY*NVY);
            double den = (Math.sqrt(Math.pow(PVX, 2) + Math.pow(PVY, 2)) * (Math.sqrt(Math.pow(NVX, 2) + Math.pow(NVY, 2))) );
            double cos =  num / den;
            System.out.println(cos);
            System.out.println(Math.acos(cos));
            double angle = Math.acos(cos);
            if(angle >= 20 || angle <= 160){
                directions.add("take a right at this " + path.get(i).getShortName());
            } else if(angle >= 200 || angle <= 340){
                directions.add("take a left at this " + path.get(i).getShortName());
            }else{
                directions.add("go straight through " + path.get(i).getShortName());
            }

        }

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
