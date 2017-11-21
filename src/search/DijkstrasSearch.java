package search;

import map.Edge;
import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.Collections;

public class DijkstrasSearch implements SearchStrategy {
    public DijkstrasSearch(){}

    @Override
    public Path findPath(Node start, Node end) {
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
            newPaths.add(add);
        }

        Collections.sort(newPaths);
        frontier.addAll(newPaths);

    }
        return new Path();
    }
}
