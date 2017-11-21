package search;

import exceptions.InvalidNodeException;
import map.Edge;
import map.Node;
import map.Path;

import java.util.*;

public class AStarSearch implements SearchStrategy {
    public AStarSearch(){}

    @Override
    public Path findPath(Node start, Node end)throws InvalidNodeException{
        ArrayList<Path> frontier = new ArrayList<>();
        Path first = new Path();
        first.addToPath(start);
        frontier.add(first);

        while(frontier.size() != 0){
            Path currentPath = frontier.get(0);
            frontier.remove(currentPath);

            if(currentPath.getPath().get(currentPath.getPath().size()-1) == end){
                return currentPath;
            }

            ArrayList<Path> newPaths = new ArrayList<>();
            for(Edge e : currentPath.getPath().get(currentPath.getPath().size()-1).getConnections()){
                Node node = e.getOtherNode(currentPath.getPath().get(currentPath.getPath().size()-1));
                if(currentPath.getPath().contains(node)) continue;

                Path add = new Path(currentPath);
                add.addToPath(node);
                add.addDistance(node.getEuclidianDistance(end));
                newPaths.add(add);
            }

            Collections.sort(newPaths);
            frontier.addAll(newPaths);

        }
        return new Path();
    }

    public Path findPathPitStop(ArrayList<Node> stops){
        Path path = new Path();
        for(int i=0;i<stops.size()-1;i++){
            path.addToPath(this.findPath(stops.get(i), stops.get(i+1)));
        }
        return path;
    }

    public double getEuclideanDistance(Node start, Node end){
        double xDeltaSquared = Math.pow((end.getX()-start.getX()), 2);
        double yDeltaSquared = Math.pow((end.getY()-start.getY()), 2);
        return Math.sqrt(xDeltaSquared + yDeltaSquared);
    }

    @Override
    public String toString(){
        return "AStar";
    }
}
