package DepartmentSubsystem.Services.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;

import javafx.event.ActionEvent;

public class SanitationController {

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
        System.out.println("Test");

    }

}