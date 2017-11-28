package DepartmentSubsystem.Services.Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import DepartmentSubsystem.Services.FoodDelivery;

import javafx.event.ActionEvent;

public class FoodDeliveryController {
    private String foodSel;

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

        foodSel = ((MenuItem) e.getSource()).getText();
        foodMenu.setText(foodSel);
        System.out.println("Food Selected " + foodSel);
    }

}
