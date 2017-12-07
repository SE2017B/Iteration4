package DepartmentSubsystem.Services.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import map.HospitalMap;
import map.Node;

public class TransportController extends CurrentServiceController {
    private HospitalMap map;

    @FXML
    private ChoiceBox<Node> endLocationChoiceBox;

    @Override
    public void onShow()
    {
        map = HospitalMap.getMap();
        endLocationChoiceBox.setItems(FXCollections.observableList(
                map.getNodesBy(n -> !n.getType().equals("HALL"))));
    }

    public String getInputData(){
        inputnode = endLocationChoiceBox.getValue();
        return "Where: "+inputnode.getShortName();
    }
}