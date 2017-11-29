package DepartmentSubsystem.Services.Controllers;

import DepartmentSubsystem.*;
import com.jfoenix.controls.JFXTextField;
import controllers.ControllableScreen;
import controllers.ScreenController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.event.ActionEvent;
import DepartmentSubsystem.Services.Translation;

public class TranslationController implements ControllableScreen {
    DepartmentSubsystem DSS = DepartmentSubsystem.getSubsystem();
    private String languageSel;

    @Override
    public void init(){
        languageChoiceBox.setItems(FXCollections.observableList(((Translation)(DSS.getDepartment("Translation Department").getServices().get(0))).getLanguages()));
    }

    @Override
    public void onShow(){
       // languageChoiceBox.setItems(FXCollections.observableList(((Translation)(DSS.getDepartment("Translation Department").getServices().get(0))).getLanguages()));
    }

    @Override
    public void setParentController(ScreenController parent) {

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
