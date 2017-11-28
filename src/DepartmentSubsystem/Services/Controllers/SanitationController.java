package DepartmentSubsystem.Services.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

import javafx.event.ActionEvent;
import DepartmentSubsystem.Services.Sanitation;

public class SanitationController {
    private String sanSel;

    @FXML
    private SplitMenuButton sanitationMenu;

    @FXML
    private MenuItem roomPrep;

    @FXML
    private MenuItem bathRestock;

    @FXML
    private MenuItem roomClean;

    @FXML
    private MenuItem generalClean;

    @FXML
    void sanitationSelected(ActionEvent e) {
        sanSel = ((MenuItem) e.getSource()).getText();
        sanitationMenu.setText(sanSel);
        System.out.println("Language Selected " + sanSel);

    }

}