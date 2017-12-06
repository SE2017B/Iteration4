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
    //animation variables
    private ArrayList<Integer> goal;
    private ArrayList<Integer> speed;
    private ArrayList<Integer> pos;
    public boolean isAnimating;
    private int animationCount;

    public PathViewer(Path path){
        this.path=path;
        this.shapes=new ArrayList<Shape>();
        dimensions = getEdgeDims();
        //set up animations
        goal= new ArrayList<>();
        speed = new ArrayList<>();
        pos = new ArrayList<>();
        animationCount=0;
        goal.add(0);//x
        goal.add(0);//y
        speed.add(0);
        speed.add(0);
        pos.add(0);
        pos.add(0);
        isAnimating=false;
    }
    public void initAnimation(int startx, int starty, int endx, int endy){
        double length = Math.pow((Math.pow((startx-starty),2)+Math.pow((endx-endy),2)),0.5);

        //set up speed
        System.out.println("The speed is"+speed);
        animationCount=50;
        speed.set(0,((endx-startx)/animationCount));
        speed.set(1,(endy-starty)/animationCount);
        goal.set(0,endx);
        goal.set(1,endy);
        pos.set(0,startx);
        pos.set(1,starty);
        isAnimating=true;

    }
    public ArrayList<Integer> getPos(int x,int y){
        //update position
        pos.set(0,pos.get(0)+speed.get(0));
        pos.set(1,pos.get(1)+speed.get(1));
        if((Math.abs((pos.get(0))-goal.get(0))<10) || animationCount<=0){
            isAnimating=false;
            return goal;
        }
        animationCount--;
        return pos;
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
