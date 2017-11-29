package DepartmentSubsystem.Services.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.event.ActionEvent;
import map.HospitalMap;
import map.Node;
import DepartmentSubsystem.*;
import DepartmentSubsystem.Services.Transport;


public class TransportController {
    private HospitalMap map;

    @FXML
    private ChoiceBox<Node> endLocationChoiceBox;

    public void init()
    {
        map = HospitalMap.getMap();
        endLocationChoiceBox.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));
    }

    public Node returnNode(){
        return endLocationChoiceBox.getValue();
    }
}