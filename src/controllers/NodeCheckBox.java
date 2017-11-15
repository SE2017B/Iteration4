package controllers;

import map.Node;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class NodeCheckBox extends CheckBox {
    private Node node;

    public void setNode(Node node) {
        this.node = node;
        this.setLayoutX(node.getX()-10);
        this.setLayoutY(node.getY()-10);
        this.setAccessibleHelp(node.getLongName());
    }

    public Node getNode() {
        return node;
    }

}
