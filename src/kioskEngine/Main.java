/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/

package kioskEngine;

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
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {

        Node a = new Node(400,200);
            a.setLongName("a");
            a.setID("aaaa");

        Node b = new Node(600,200);
            b.setLongName("b");
            b.setID("bbbb");


        Node c = new Node(600,400);
            c.setLongName("c");
            c.setID("cccc");


        Node d = new Node(400,400);
            d.setLongName("d");
            d.setID("dddd");


        ArrayList<Node> aConn = new ArrayList<Node>();
        aConn.add(b);

        ArrayList<Node> bConn = new ArrayList<Node>();
        aConn.add(c);
        aConn.add(a);

        ArrayList<Node> cConn = new ArrayList<Node>();
        aConn.add(b);
        aConn.add(d);

        ArrayList<Node> dConn = new ArrayList<Node>();
        aConn.add(c);


        Staff testAdmin =  new Staff("test","","Admin","Admin Test",1234, engine.getService("Food"));
        Staff testStaff =  new Staff("testStaff","","food stuff","Staff Test",1234, engine.getService("Food"));
        ServiceRequest req = new ServiceRequest(engine.getService("Food"),1,a,"This is a test");
        engine.addStaffLogin(testStaff, "Food");
        engine.addStaffLogin(testAdmin, "Food");
        req.giveRequest();
        engine.addNode(a,aConn);
        engine.addNode(b,bConn);
        engine.addNode(c,cConn);
        engine.addNode(d,dConn);



        launch(args);
    }
}
