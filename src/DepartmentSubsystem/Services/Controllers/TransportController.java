package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.Services.Translation;
import controllers.ControllableScreen;
import controllers.ScreenController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.event.ActionEvent;
import map.HospitalMap;
import map.Node;
import DepartmentSubsystem.*;
import DepartmentSubsystem.Services.Transport;


public class TransportController implements ControllableScreen{
    private HospitalMap map;

    @Override
    public void init(){
        map = HospitalMap.getMap();
        endLocationChoiceBox.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));
    }

    @Override
    public void setParentController(ScreenController parent) {

    }


    @FXML
    private ChoiceBox<Node> endLocationChoiceBox;

    public void onShow()
    {

    }

    public Node returnNode(){
        return endLocationChoiceBox.getValue();
    }
}