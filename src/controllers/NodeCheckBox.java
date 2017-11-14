package controllers;

import a_star.Node;
import javafx.scene.control.CheckBox;

public class NodeCheckBox extends CheckBox {
    private Node node;

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }
}
