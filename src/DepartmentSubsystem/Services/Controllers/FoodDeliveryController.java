package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.DepartmentSubsystem;
import DepartmentSubsystem.Services.Sanitation;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import DepartmentSubsystem.Services.FoodDelivery;

import javafx.event.ActionEvent;

import java.util.ArrayList;

public class FoodDeliveryController {
    private String foodSel;
    DepartmentSubsystem DSS = DepartmentSubsystem.getSubsystem();

    public void init(){
        foodChoiceBox.setItems(FXCollections.observableList(((FoodDelivery)((DSS.getDepartment("Food").getServices().get(0)))).getMenuItems()));
    }

    @FXML
    private ChoiceBox<String> foodChoiceBox;

    @FXML
    private JFXTextField allergiesTextFiled;

    public String getAllergy(){
        return allergiesTextFiled.getText();
    }

    @FXML
    void foodSelected(ActionEvent e) {
        foodSel = foodChoiceBox.getValue();
    }

    public String getFoodSelected(){
        return foodSel;
    }

}
