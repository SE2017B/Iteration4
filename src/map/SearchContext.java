package map;

import java.util.Stack;

public class SearchContext {
    private SearchStrategy strategy;

    public SearchContext(SearchStrategy strategy){
        this.strategy = strategy;
    }

    public Stack<Node> findPath(Node start, Node end){
        return strategy.findPath(start, end);
    }
}
