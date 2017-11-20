package search;

import exceptions.InvalidNodeException;
import map.Node;
import map.Path;

import java.util.*;

public class BreadthFirstSearch implements SearchStrategy {
    public BreadthFirstSearch(){}

    @Override
    public Path findPath(Node start, Node end){
        ArrayList<Path> frontier = new ArrayList<>();
        Path first = new Path();
        first.addToPath(start);
        frontier.add(first);

        while(frontier.size() != 0){
            Path currentPath = frontier.get(0);
            Node currentNode = currentPath.getPath().get(currentPath.getPath().size()-1);
            frontier.remove(currentPath);
            if(currentNode == end) return currentPath;
            ArrayList<Path> newPaths = new ArrayList<>();
            for(int i=0;i<currentNode.getConnections().size();i++){
                Node neighbor = currentNode.getConnections().get(i).getOtherNode(currentNode);
                if(currentPath.getPath().contains(neighbor)) continue;
                Path add = new Path(currentPath);
                add.addToPath(neighbor);
                newPaths.add(add);
            }
            Collections.sort(newPaths);
            frontier.addAll(newPaths);
        }
        return new Path();
    }

    public Path findPathBy(Node start, String type){

        ArrayList<Path> frontier = new ArrayList<>();
        Path first = new Path();
        first.addToPath(start);
        frontier.add(first);

        while(frontier.size() != 0){
            Path currentPath = frontier.get(0);
            Node currentNode = currentPath.getPath().get(currentPath.getPath().size()-1);
            frontier.remove(currentPath);
            if(currentNode.getType().equals(type)) return currentPath;
            ArrayList<Path> newPaths = new ArrayList<>();
            for(int i=0;i<currentNode.getConnections().size();i++){
                Node neighbor = currentNode.getConnections().get(i).getOtherNode(currentNode);
                if(currentPath.getPath().contains(neighbor)) continue;
                Path add = new Path(currentPath);
                add.addToPath(neighbor);
                newPaths.add(add);
            }
            Collections.sort(newPaths);
            frontier.addAll(newPaths);
        }
        return new Path();
    }
}
