package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.Services.FoodDelivery;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class FoodDeliveryController extends CurrentServiceController {

    @Override
    public void onShow(){
        foodChoiceBox.setItems(FXCollections.observableList(((FoodDelivery)((DSS.getServices().get(3)))).getMenuItems()));
    }

    @FXML
    private ChoiceBox<String> foodChoiceBox;

    @FXML
    private JFXTextField alergiesTextFiled;

    public String getInputData(){
        inputData = "Food Choice: "+foodChoiceBox.getValue() + "\n Allergies: " + alergiesTextFiled.getText();
        return inputData;
    }

}
