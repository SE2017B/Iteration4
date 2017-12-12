package ui;

import map.Edge;
import map.Node;

import java.util.ArrayList;

public class MapEditorMemento {
    private ArrayList<Node> nodes;
    public ArrayList<Edge> edges;
    public MapEditorMemento(ArrayList<Node> nodes, ArrayList<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }
    public ArrayList<Node> getSavedNodeState(){
        return nodes;
    }
    public ArrayList<Edge> getSavedEdgeState(){
        return edges;
    }
}
