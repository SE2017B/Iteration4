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
    private ArrayList<Shape> shapes;
    private ArrayList<Integer> dimensions;

    public PathViewer(Path path){
        this.path=path;
        this.shapes=new ArrayList<Shape>();
        dimensions = getEdgeDims();
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
    public FloorNumber getFloor(){
        return this.path.getPath().get(0).getFloor();
    }

    public int getWidth(){
        ArrayList<Integer> dimensions = getEdgeDims();
        return dimensions.get(2) - dimensions.get(0);
    }

    public int getHeight(){
        return dimensions.get(3) - dimensions.get(1);
    }

    public double getScale(){
        return Math.min(Math.pow((5000-getWidth())/5000.0,2.0), Math.pow((3400-getHeight())/3400.0,2)) ;
    }

}
