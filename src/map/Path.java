package map;

import java.util.ArrayList;

public class Path {
    private ArrayList<Node> path = new ArrayList<>();
    private ArrayList<String> directions = new ArrayList<>();

    public Path(){}

    public void addToPath(Node node){
        this.path.add(node);
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
}
