package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.Services.Translation;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class TranslationController extends CurrentServiceController {

    @Override
    public void onShow(){
       languageChoiceBox.setItems(FXCollections.observableList(((Translation)(DSS.getServices().get(0))).getLanguages()));
    }


    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private JFXTextField durationTextFiled;

    public String getInputData() {
        inputData = "Language:" + languageChoiceBox.getValue() + "\n\n Time(min):" + durationTextFiled.getText();
        return inputData;
    }
}
