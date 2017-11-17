package map;

import java.util.ArrayList;

public class Path {
    private ArrayList<Node> path;
    private ArrayList<String> directions;

    public Path(){}

    public void addToPath(Node node){
        this.path.add(node);
    }

    public ArrayList<String> getDirections(){
        return null;
    }
}
