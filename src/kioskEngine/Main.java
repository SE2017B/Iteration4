/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/

package kioskEngine;

import database.mainDatabase;
import map.HospitalMap;
import map.Node;
import controllers.ScreenController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DepartmentSubsystem.ServiceRequest;
import DepartmentSubsystem.Staff;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ScreenController myScreenController = new ScreenController();
        myScreenController.loadScreen(ScreenController.AddNodeID, ScreenController.AddNodeFile);
        myScreenController.loadScreen(ScreenController.LogoutID, ScreenController.LogoutFile);
        myScreenController.loadScreen(ScreenController.MainID, ScreenController.MainFile);
        myScreenController.loadScreen(ScreenController.PathID, ScreenController.PathFile);
        myScreenController.loadScreen(ScreenController.RequestID, ScreenController.RequestFile);
        myScreenController.loadScreen(ScreenController.LoginID, ScreenController.LoginFile);

        myScreenController.setScreen(ScreenController.MainID);

        Group root = new Group();
        root.getChildren().addAll(myScreenController);
        Scene scene = new Scene(root, 1280,800);
        String  style= getClass().getResource("/fxml/SceneStyle.css").toExternalForm();
        scene.getStylesheets().add(style);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        mainDatabase.readNodeCSV("MapHnodes.csv");
        mainDatabase.readNodeCSV("MapWnodes.csv");
        mainDatabase.readEdgesCSV("MapHedges.csv");
        mainDatabase.readEdgesCSV("MapWedges.csv");



        launch(args);


        mainDatabase.outputNodesCSV();
        mainDatabase.outputEdgesCSV();
    }
}
