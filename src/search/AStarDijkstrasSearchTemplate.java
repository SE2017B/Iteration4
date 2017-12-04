package search;

import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AStarDijkstrasSearchTemplate {
    public abstract void initialize(ArrayList<Node> frontier, ArrayList<Node> explored, HashMap<Node, Node> cameFrom,
                                    HashMap<Node, Integer> greedy, HashMap<Node, Double> fSCore, Node start, Node end);
    public abstract Path sortFrontier(ArrayList<Node> frontier, ArrayList<Node> explored, HashMap<Node, Node> cameFrom,
                                      HashMap<Node, Integer> greedy, HashMap<Node, Double> fScore, Node start, Node end, Path path);
    public abstract Path returnThePath(Path path);

    public final Path findPath(Node start, Node end){
        ArrayList<Node> frontier = new ArrayList<>();
        ArrayList<Node> explored = new ArrayList<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();
        HashMap<Node, Integer> greedy = new HashMap<>();
        HashMap<Node, Double> fScore = new HashMap<>();
        Path path = new Path();

        //Initialize the variables and populate as needed
        initialize(frontier, explored, cameFrom, greedy, fScore, start, end);
        //Sort the frontier list and form the path accordingly
        path = sortFrontier(frontier, explored, cameFrom, greedy, fScore, start, end, path);
        //Return the proper path
        return returnThePath(path);

    }
}
