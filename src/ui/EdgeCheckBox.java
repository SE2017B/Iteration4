package ui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import map.Edge;
import map.Node;
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
        edge = map.getEdge(edge);
        this.edge = edge;
        selected = false;
        if(edge.getID().equals("FHALL03201_FDEPT00601")) {
            System.out.println(edge.getNodeOne());
            System.out.println(edge.getNodeOne().getX());
            System.out.println(edge.getNodeTwo());
            System.out.println(edge.getNodeTwo().getX());
        }
//        Node nodeOne = map.getNodeMap().stream().filter(n1->n1.equals(edge.getNodeOne())||n1.equals(edge.getNodeTwo())).collect(Collectors.toList()).get(0);
//        Node nodeTwo = map.getNodeMap().stream().filter(n1->(n1.equals(edge.getNodeOne())||n1.equals(edge.getNodeTwo()))&&(!n1.equals(nodeOne))).collect(Collectors.toList()).get(0);
//        int startX = map.getNodeMap().stream().filter(n1->n1.equals(edge.getNodeOne())).collect(Collectors.toList()).get(0).getX();
//        int startY = map.getNodeMap().stream().filter(n1->n1.equals(edge.getNodeOne())).collect(Collectors.toList()).get(0).getY();
        int startX = edge.getNodeOne().getX();
        int startY = edge.getNodeOne().getY();
        this.setLayoutX(startX);
        this.setLayoutY(startY);
        this.setStartX(0);
        this.setStartY(0);
//        this.setEndX((map.getNodeMap().stream().filter(n1->n1.equals(edge.getNodeTwo())).collect(Collectors.toList()).get(0).getX() - startX));
//        this.setEndY((map.getNodeMap().stream().filter(n1->n1.equals(edge.getNodeTwo())).collect(Collectors.toList()).get(0).getY() - startY));
        this.setEndX(edge.getNodeTwo().getX() - startX);
        this.setEndY(edge.getNodeTwo().getY() - startY);
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
