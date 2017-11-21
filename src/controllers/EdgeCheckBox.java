package controllers;

import javafx.scene.shape.Line;
import map.Edge;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.beans.EventHandler;

public class EdgeCheckBox extends Line {
    private Edge edge;
    private boolean selected;

    public EdgeCheckBox(Edge edge) {
        super();
        this.edge = edge;
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean sel){
        this.selected = sel;
    }


    public Edge getEdge(){
        return edge;

    }
}
