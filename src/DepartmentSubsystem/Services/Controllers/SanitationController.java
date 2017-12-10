package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.Services.Sanitation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class SanitationController extends CurrentServiceController {
    //private String sanSel;

    @Override
    public void onShow(){
        sanitationChoiceBox.setItems(FXCollections.observableList(((Sanitation)((DSS.getServices().get(2)))).getServiceTypes()));
    }

    @FXML
    private ChoiceBox<String> sanitationChoiceBox;

    @FXML
    public String getInputData() {
        inputData = "Type: " + sanitationChoiceBox.getValue();
        return inputData;
    }
}
