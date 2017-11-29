package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.DepartmentSubsystem;
import com.jfoenix.controls.JFXTextField;
import controllers.ControllableScreen;
import controllers.ScreenController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import DepartmentSubsystem.Services.FoodDelivery;
import javafx.event.ActionEvent;

public class FoodDeliveryController implements ControllableScreen{
    private String foodSel;
    private DepartmentSubsystem DSS;

    @Override
    public void init() {
       }

    @Override
    public void onShow(){
        DSS = DepartmentSubsystem.getSubsystem();
        System.out.println((DSS + "PLS"));

        foodChoiceBox.setItems(FXCollections.observableList(((FoodDelivery)((DSS.getDepartment("Food").getServices().get(0)))).getMenuItems()));
        System.out.println((DSS));
    }

    @Override
    public void setParentController(ScreenController parent) {

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
