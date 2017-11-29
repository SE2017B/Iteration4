package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.DepartmentSubsystem;
import DepartmentSubsystem.Services.Translation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

import javafx.event.ActionEvent;
import DepartmentSubsystem.Services.Sanitation;

public class SanitationController {
    private String sanSel;
    DepartmentSubsystem DSS = DepartmentSubsystem.getSubsystem();

    public void init(){
        sanitationMenu.setItems(FXCollections.observableList(((Sanitation)((DSS.getDepartment("Facilities").getServices().get(0)))).getServiceTypes()));
    }

    @FXML
    private ChoiceBox<String> sanitationMenu;

    @FXML
    void sanitationSelected(ActionEvent e) {
        sanSel = sanitationMenu.getValue();
    }

    public String getSanSel() {
        return sanSel;
    }
}