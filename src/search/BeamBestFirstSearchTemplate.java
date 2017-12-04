package search;

import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BeamBestFirstSearchTemplate {
    public abstract void initialize(Node aNode, ArrayList<Node> frontier);
    public abstract Path formThePath(Node start, Node end, ArrayList<Node> frontier, ArrayList<Node> explored, HashMap<Node, Node> cameFrom);
    public abstract Path pathComplete(Path path);
    public Path findPath(Node start, Node end){
        //Variables
        ArrayList<Node> frontier = new ArrayList<>();
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
        Path path = new Path();

        //Populate and initialize where needed (frontier add)
        initialize(start, frontier);

        //Formulate the path
        path = formThePath(start, end, frontier, explored, cameFrom);

        //Return the path
        return pathComplete(path);
    }
}
