package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import map.Edge;




public class EdgeCheckBox extends Line {
    private Edge edge;
    private boolean selected;


    public EdgeCheckBox(Edge edge) {
        super();
        this.edge = edge;
        selected = false;
        this.setStartX(edge.getNodeOne().getX());
        this.setStartY(edge.getNodeOne().getY());
        this.setEndX(edge.getNodeTwo().getX());
        this.setEndY(edge.getNodeTwo().getY());
        this.setStrokeWidth(5);

        this.setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent e){
                if(e.getSource() instanceof EdgeCheckBox){
                    ((EdgeCheckBox)e.getSource()).select();
                }
            }
        });

    }

    public boolean isSelected() {
        return selected;
    }

    public void select(){
        this.selected = !this.selected;
    }


    public Edge getEdge(){
        return edge;

    }
}
