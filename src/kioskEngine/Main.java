/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/

package kioskEngine;

import database.edgeDatabase;
import database.mainDatabase;
import database.nodeDatabase;
import map.Node;
import controllers.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import service.FoodService;
import service.ServiceRequest;
import service.Staff;

import java.util.ArrayList;

public class Main extends Application {
    private static KioskEngine engine = new KioskEngine();
    @Override
    public void start(Stage primaryStage) throws Exception{
        ScreenController myScreenController = new ScreenController(engine);
        myScreenController.loadScreen(ScreenController.AddNodeID, ScreenController.AddNodeFile);
        myScreenController.loadScreen(ScreenController.AdminMenuID, ScreenController.AdminMenuFile);
        myScreenController.loadScreen(ScreenController.FilterID, ScreenController.FilterFile);
        myScreenController.loadScreen(ScreenController.LoginID, ScreenController.LoginFile);
        myScreenController.loadScreen(ScreenController.LogoutID, ScreenController.LogoutFile);
        myScreenController.loadScreen(ScreenController.MainID, ScreenController.MainFile);
        //myScreenController.loadScreen(ScreenController.NodeConfirmID, ScreenController.NodeConfirmFile);
        myScreenController.loadScreen(ScreenController.PathID, ScreenController.PathFile);
        myScreenController.loadScreen(ScreenController.RequestID, ScreenController.RequestFile);
        //myScreenController.loadScreen(ScreenController.ThankYouID, ScreenController.ThankYouFile);

        myScreenController.setScreen(ScreenController.MainID);

        Group root = new Group();
        root.getChildren().addAll(myScreenController);
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        nodeDatabase.readNodeCSV("MapHnodes.csv");
        nodeDatabase.readNodeCSV("MapWnodes.csv");

        edgeDatabase.readEdgesCSV("MapHedges.csv");
        edgeDatabase.readEdgesCSV("MapWedges.csv");

        Staff testAdmin =  new Staff("test","","Admin","Admin Test",1234, engine.getService("Food"));
        Staff testStaff =  new Staff("testStaff","","food stuff","Staff Test",1234, engine.getService("Food"));

        engine.addStaffLogin(testStaff, "Food");
        engine.addStaffLogin(testAdmin, "Food");



        for(Node node : mainDatabase.getNodes()){
            engine.getMap().addNode(node.getID(),node);
        }

        ServiceRequest req = new ServiceRequest(engine.getService("Food"),1,engine.getMap().getNodesForSearch().get(0),"This is a test");
        req.giveRequest();

        launch(args);


        nodeDatabase.outputNodesCSV();
        edgeDatabase.outputEdgesCSV();
    }
}
