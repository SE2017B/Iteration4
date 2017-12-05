package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import map.Edge;

public class EdgeCheckBox extends Line {
    private Edge edge;
    private boolean selected;

    public EdgeCheckBox(Edge edge) {
        super();
        this.edge = edge;
        selected = false;
        int startX = edge.getNodeOne().getX();
        int startY = edge.getNodeOne().getY();
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.setStartX(0);
        this.setStartY(0);
        this.setEndX((edge.getNodeTwo().getX() - startX));
        this.setEndY((edge.getNodeTwo().getY() - startY));
        this.setStrokeWidth(8);
        this.setVisible(false);
    }

    public boolean isSelected() {
        return selected;
    }

    public void select(){
        this.selected = !this.selected;
        if(selected){
            this.setStroke(Color.YELLOW);
        }
        else{
            this.setStroke(Color.BLACK);
        }
    }

    public Edge getEdge(){
        return edge;
    }

    public void setSelected(boolean sel){
        if(selected != sel){
            select();
        }
    }
}
