package ui;

import com.jfoenix.controls.JFXCheckBox;
import map.Node;
import javafx.fxml.FXML;

public class NodeCheckBox extends JFXCheckBox {
    private Node node;
    private double orgX;
    private double orgY;

    private double WIDTH = 24;

    public NodeCheckBox(Node node){
        super();
        setNode(node);
        setWidth(WIDTH);
        setHeight(WIDTH);
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
        this.setLayoutX((node.getX()-WIDTH/2));
        this.setLayoutY((node.getY()-WIDTH/2));
        this.setAccessibleHelp(node.getLongName());
    }

    public void setOrgX(double x){
        orgX = x;
    }

    public double getOrgX() {
        return orgX;
    }

    public void setOrgY(double orgY) {
        this.orgY = orgY;
    }

    public double getOrgY() {
        return orgY;
    }
}
