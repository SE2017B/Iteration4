package DepartmentSubsystem.Services.Controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import javafx.event.ActionEvent;
import DepartmentSubsystem.Services.Translation;

public class TranslationController {
    private String languageSel;

    @FXML
    private MenuButton languageDropDown;

    @FXML
    private MenuItem spanish;

    @FXML
    private MenuItem czech;

    @FXML
    private MenuItem russian;

    @FXML
    private MenuItem hindi;

    @FXML
    private JFXTextField durationTextFiled;

    @FXML
    void languagePicked(ActionEvent e) {
        languageSel = ((MenuItem) e.getSource()).getText();
        languageDropDown.setText(languageSel);
        System.out.println("Language Selected " + languageSel);

    }

}
