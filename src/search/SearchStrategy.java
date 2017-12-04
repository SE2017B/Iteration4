package search;

import exceptions.InvalidNodeException;
import map.Node;
import map.Path;

public interface SearchStrategy {
    Path findPath(Node start, Node end) throws InvalidNodeException;
}
