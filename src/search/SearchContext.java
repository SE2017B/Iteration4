package search;

import exceptions.InvalidNodeException;
import map.Node;
import map.Path;

import java.util.ArrayList;

public class SearchContext {
    private SearchStrategy strategy;

    public SearchContext(SearchStrategy strategy){
        this.strategy = strategy;
    }

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public SearchStrategy getStrategy(){ return strategy;}

    public Path findPath(Node start, Node end){
        try {
            return strategy.findPath(start, end);
        } catch (InvalidNodeException e) {
            e.printStackTrace();
            return new Path();
        }
    }

    public Path findNearest(Node start, String type){
        DijkstrasSearch search = new DijkstrasSearch();
        return search.findPathBy(start, type);
    }

    public Path findPathPitStop(ArrayList<Node> stops) throws InvalidNodeException {
        AStarSearch search = new AStarSearch();
        return search.findPathPitStop(stops);
    }
}
