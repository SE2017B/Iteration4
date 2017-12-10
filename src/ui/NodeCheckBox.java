package ui;

import com.jfoenix.controls.JFXCheckBox;
import map.Node;
import javafx.fxml.FXML;

public class NodeCheckBox extends JFXCheckBox {
    private Node node;
    private double orgX;
    private double orgY;

    public NodeCheckBox(Node node){
        super();
        setNode(node);
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
        this.setLayoutX((node.getX()-9));
        this.setLayoutY((node.getY()-9));
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
