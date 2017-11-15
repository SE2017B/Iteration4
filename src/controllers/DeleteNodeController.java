/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import kioskEngine.KioskEngine;
import map.Node;

import java.util.ArrayList;

public class DeleteNodeController implements ControllableScreen {
    private ScreenController parent;


    //Nodes in the map
    private ArrayList<NodeCheckBox> nodeCheckBoxes = new ArrayList<NodeCheckBox>();


    //setter for parent
    public void setParentController(ScreenController parent) {
        this.parent = parent;
    }


    @FXML
    private Button btnEnter;

    @FXML
    private Button btnCancel;


    @FXML
    private Pane mapPane;

    @FXML
    private ListView<Node> deleteList;

    public void init() {

    }

    public void onShow() {

        ArrayList<Node> nodes = parent.getEngine().getMap().getNodesForEdit();

        for (Node node : nodes) {
            NodeCheckBox box = new NodeCheckBox();
            box.setNode(node); //sets checkbox to node location

            box.setVisible(true);
            box.setOnAction(event -> nodeForDelete(event));
            mapPane.getChildren().add(box);
            nodeCheckBoxes.add(box);

        }
    }

    public void nodeForDelete(ActionEvent e) {
        deleteList.getItems().add(((NodeCheckBox) e.getSource()).getNode());
    }


    public void enterPressed(ActionEvent e) {
        System.out.println("Delete Nodes");
        parent.getEngine().deleteNodes(deleteList.getItems());
    }

    //comands for button cancel press
    public void cancelPressed(ActionEvent e) {
        System.out.println("Cancel Pressed");
        parent.setScreen(ScreenController.AdminMenuID);
    }
}
