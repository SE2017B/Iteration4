package search;

import map.Node;

import java.util.ArrayList;

public class SearchContext {
    private SearchStrategy strategy;

    public SearchContext(SearchStrategy strategy){
        this.strategy = strategy;
    }

    public ArrayList<Node> findPath(Node start, Node end){
        return strategy.findPath(start, end);
    }
}
