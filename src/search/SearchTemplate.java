//package search;
//
//import exceptions.InvalidNodeException;
//import map.Node;
//import map.Path;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public abstract class SearchStrategy {
//
//    public SearchStrategy(){}
//
//   public final Path findPath(Node start, Node end) throws InvalidNodeException {
//        //Initialize
//        ArrayList<Node> frontier = new ArrayList<>();
//        frontier = theInitialize(start);
//
//        //Finding the path and returning it
//        return findAndReturn(start, end, frontier);
//    }
//
//    protected ArrayList<Node> theInitialize(Node aStartNode) {
//        ArrayList<Node> frontier = new ArrayList<>();
//        frontier.add(aStartNode);
//
//        return  frontier;
//    }
//
//    Path findAndReturn(Node aStartNode, Node anEndNode, ArrayList<Node> frontier) {
//        return new Path();
//    }
//}
