/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

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

}
