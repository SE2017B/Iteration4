package search;

import map.Node;
import map.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BeamSearch implements SearchStrategy{
    private int beam;
    public BeamSearch(int beam){
        this.beam = beam;
    }

    @Override
    public Path findPath(Node start, Node end) {
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
            newPaths.sort((o1, o2) -> (int) (o1.getPath().get(o1.getPath().size() - 1).getEuclidianDistance(end) - o2.getPath().get(o2.getPath().size() - 1).getEuclidianDistance(end)));
            if(newPaths.size() < beam) frontier.addAll(newPaths);
            else frontier.addAll(newPaths.subList(0, beam));
        }
        return new Path();
    }
}
