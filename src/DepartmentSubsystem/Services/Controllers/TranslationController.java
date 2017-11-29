package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.DepartmentSubsystem;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.event.ActionEvent;
import DepartmentSubsystem.Services.Translation;

public class TranslationController {
    DepartmentSubsystem DSS = DepartmentSubsystem.getSubsystem();
    private String languageSel;

    public void init(){
        languageChoiceBox.setItems(FXCollections.observableList(((Translation)(DSS.getDepartment("Translation Department").getServices().get(0))).getLanguages()));
    }

    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private JFXTextField durationTextFiled;

    @FXML
    void languagePicked(ActionEvent e) {
        this.languageSel = languageChoiceBox.getValue();
    }

    public String getLanguageSel() {
        return languageSel;
    }

    public String getDuration(){
        return durationTextFiled.getText();
    }
}
