package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import map.HospitalMap;
import service.Staff;


public class LoginPane extends Pane {
    private GridPane grid;
    private JFXTextField username;
    private JFXPasswordField password;
    private JFXButton enter;
    private MainController parent;



    public LoginPane(MainController parent){
        this.parent = parent;
        grid = new GridPane();
        username = new JFXTextField();
        password = new JFXPasswordField();
        enter = new JFXButton();

        username.setText("Username");
        password.setText("Password");
        enter.setText("Enter");

        grid.add(username, 1,1,2,1);
        grid.add(password,1,2,2,1);
        grid.add(enter,3,4,1,1);

        this.getChildren().add(grid);

        enter.setOnAction(e -> enterPressed(e));
    }

    public void enterPressed(ActionEvent e){
        //todo check credentials
        System.out.println("Login success");
        parent.successfulLogin(new Staff("BRoss","","Painter","Bob Ross",1234,null));

    }

}
