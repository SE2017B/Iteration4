package ui;

import com.jfoenix.controls.JFXCheckBox;
import map.Node;
import javafx.fxml.FXML;

public class NodeCheckBox extends JFXCheckBox {
    private Node node;

    public NodeCheckBox(Node node, double scale){
        super();
        setNode(node,scale);
    }

    public void setNode(Node node, double scale) {
        this.node = node;
        this.setLayoutX((node.getX()-18)/scale);
        this.setLayoutY((node.getY()-18)/scale);
        this.setAccessibleHelp(node.getLongName());
    }

    public Node getNode() {
        return node;
    }

}
