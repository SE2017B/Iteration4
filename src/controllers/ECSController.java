package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ECSController implements ControllableScreen{
    private ScreenController parent;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    @FXML
    private Button btncancel;

    @FXML
    private Button btnaccept;

    public void init(){}

    public void onShow(){}
}
