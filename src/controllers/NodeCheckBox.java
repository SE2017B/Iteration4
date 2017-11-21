package controllers;

import com.jfoenix.controls.JFXCheckBox;
import map.Node;
import javafx.fxml.FXML;

public class NodeCheckBox extends JFXCheckBox {
    private Node node;

    public NodeCheckBox(Node node){
        super();
        setNode(node);
    }

    public void setNode(Node node) {
        this.node = node;
        this.setLayoutX((node.getX()-10)/2);
        this.setLayoutY((node.getY()-10)/2);
        this.setAccessibleHelp(node.getLongName());
    }

    public Node getNode() {
        return node;
    }

}
