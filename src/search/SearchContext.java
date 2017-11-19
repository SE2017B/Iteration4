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

    public Path findPath(Node start, Node end)throws InvalidNodeException{
        return strategy.findPath(start, end);
    }
}
