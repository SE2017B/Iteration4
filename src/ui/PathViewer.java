package ui;

import javafx.scene.shape.Shape;
import map.FloorNumber;
import map.Node;
import map.Path;

import java.util.ArrayList;

public class PathViewer {
    //attributes
    private Path path;
    private PathID pathID;
    private double scale;
    private ArrayList<Shape> shapes;

    public PathViewer(Path path){
        this.path=path;
        this.shapes=new ArrayList<Shape>();
        this.scale=0;//for now
        setScale();//control the length of the scale for the path used
    }
    public void setScale(){

    }
    private ArrayList<Integer> getEdgeDims(){
        ArrayList<Integer> ans = new ArrayList<Integer>();
        ans.add(this.path.getPath().get(0).getX());
        ans.add(this.path.getPath().get(0).getY());
        ans.add(this.path.getPath().get(0).getX());
        ans.add(this.path.getPath().get(0).getY());

        for(Node n : this.path.getPath()){
            if(ans.get(0)>n.getX()){
                ans.set(0,n.getX());
            }
            if(ans.get(1)>n.getY()){
                ans.set(1,n.getY());
            }
            if(ans.get(2)<n.getX()){
                ans.set(2,n.getX());
            }
            if(ans.get(3)<n.getY()){
                ans.set(3,n.getY());
            }
        }
        return ans;
    }
    public ArrayList<Integer> getCenter(){
        ArrayList<Integer> Dim =getEdgeDims();
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add((Dim.get(0)+Dim.get(2))/2);
        ans.add((Dim.get(1)+Dim.get(3))/2);
        return ans;
    }
    //setters
    public void addNode(Node n){
        this.path.getPath().add(n);
    }
    public void addShape(Shape s){
        this.shapes.add(s);
    }

    //getters
    public ArrayList<Shape> getShapes(){
        return this.shapes;
    }
    public Path getPath(){
        return this.path;
    }
    public ArrayList<Node> getNodes(){
        return this.path.getPath();
    }
    public double getScale(){
        return this.scale;
    }
    public FloorNumber getFloor(){
        return this.pathID.getFloor();
    }

}
