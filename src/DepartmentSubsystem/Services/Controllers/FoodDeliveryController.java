package DepartmentSubsystem.Services.Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.event.ActionEvent;

public class FoodDeliveryController {

    @FXML
    private MenuButton foodMenu;

    @FXML
    private MenuItem hamburger;

    @FXML
    private MenuItem cheburek;

    @FXML
    private JFXTextField alergiesTextFiled;

    @FXML
    void foodSelected(ActionEvent e) {
        System.out.println("Test");

    }

}
