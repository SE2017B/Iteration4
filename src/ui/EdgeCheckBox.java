package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import map.Edge;
import map.HospitalMap;

import java.util.Map;
import java.util.stream.Collectors;

public class EdgeCheckBox extends Line {
    private Edge edge;
    private HospitalMap map;
    private boolean selected;

    public EdgeCheckBox(Edge edge) {
        super();
        map = HospitalMap.getMap();
        this.edge = edge;
        selected = false;
        int startX = map.getNodeMap().stream().filter(n1->n1.equals(edge.getNodeOne())).collect(Collectors.toList()).get(0).getX();
        int startY = map.getNodeMap().stream().filter(n1->n1.equals(edge.getNodeOne())).collect(Collectors.toList()).get(0).getY();
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.setStartX(0);
        this.setStartY(0);
        this.setEndX((map.getNodeMap().stream().filter(n1->n1.equals(edge.getNodeTwo())).collect(Collectors.toList()).get(0).getX() - startX));
        this.setEndY((map.getNodeMap().stream().filter(n1->n1.equals(edge.getNodeTwo())).collect(Collectors.toList()).get(0).getY() - startY));
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
