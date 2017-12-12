/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Erica Snow, Vojta Mosby, Tyrone Patterson
* The following code
*/

package kioskEngine;

import DepartmentSubsystem.Feedback;
import controllers.ScreenController;
import database.edgeDatabase;
import database.nodeDatabase;
import database.serviceDatabase;
import database.staffDatabase;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root);
        root.minWidth(800);
        root.minHeight(400);



        String  style= getClass().getResource("/fxml/SceneStyle.css").toExternalForm();
        scene.getStylesheets().add(style);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);
        primaryStage.show();


        ScreenController myScreenController = new ScreenController();
        EventHandler reset = new EventHandler<InputEvent>() {
            @Override
            public void handle(InputEvent event){
                myScreenController.resetTimeout();
            }};
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, reset);
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, reset);
        scene.addEventFilter(KeyEvent.KEY_TYPED, reset);

        root.getChildren().setAll(myScreenController);
        myScreenController.prefHeightProperty().bind(scene.heightProperty());
        myScreenController.prefWidthProperty().bind(scene.widthProperty());


        myScreenController.loadScreen(ScreenController.AddNodeID, ScreenController.AddNodeFile);
        myScreenController.loadScreen(ScreenController.LogoutID, ScreenController.LogoutFile);
        myScreenController.loadScreen(ScreenController.MainID, ScreenController.MainFile);
        myScreenController.loadScreen(ScreenController.PathID, ScreenController.PathFile);
        myScreenController.loadScreen(ScreenController.RequestID, ScreenController.RequestFile);
        myScreenController.loadScreen(ScreenController.LoginID, ScreenController.LoginFile);
        myScreenController.loadScreen(ScreenController.HelpID, ScreenController.HelpFile);
        myScreenController.loadScreen(ScreenController.FeedbackID, ScreenController.FeedbackFile);
        myScreenController.loadScreen(ScreenController.DirectionHelpID, ScreenController.DirectionHelpFile);
//
//        //mini fxml files
//        myScreenController.loadScreen(ScreenController.TranslationID, ScreenController.TranslationFile);
//        myScreenController.loadScreen(ScreenController.TransportID, ScreenController.TransportFile);
//        myScreenController.loadScreen(ScreenController.FoodDeliveryID, ScreenController.FoodDeliveryFile);
//        myScreenController.loadScreen(ScreenController.SanitationID, ScreenController.SanitationFile);

        myScreenController.setScreen(ScreenController.MainID);
        myScreenController.saveState();
    }

    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        edgeDatabase.deleteEdgeTable();
        nodeDatabase.deleteNodeTable();
        staffDatabase.deleteAdminTable();
        staffDatabase.deleteStaffTable();
        serviceDatabase.deleteFeedbackTable();

        nodeDatabase.createNodeTable();
        edgeDatabase.createEdgeTable();
        staffDatabase.createStaffTable();
        staffDatabase.createAdminTable();
        serviceDatabase.createFeedbackTable();

        nodeDatabase.readNodeCSV("/csv/MapAnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapBnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapCnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapDnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapENodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapFNodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapGNodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapHnodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapInodes.csv");
        nodeDatabase.readNodeCSV("/csv/MapWnodes.csv");

        edgeDatabase.readEdgesCSV("/csv/MapAedges.csv");
        edgeDatabase.readEdgesCSV("/csv/MapBedges.csv");
        edgeDatabase.readEdgesCSV("/csv/MapCedges.csv");
        edgeDatabase.readEdgesCSV("/csv/MapDedges.csv");
        edgeDatabase.readEdgesCSV("/csv/MapEEdges.csv");
        edgeDatabase.readEdgesCSV("/csv/MapFEdges.csv");
        edgeDatabase.readEdgesCSV("/csv/MapGEdges.csv");
        edgeDatabase.readEdgesCSV("/csv/MapHedges.csv");
        edgeDatabase.readEdgesCSV("/csv/MapIedges.csv");
        edgeDatabase.readEdgesCSV("/csv/MapWedges.csv");

        nodeDatabase.insertNodesFromCSV();
        edgeDatabase.insertEdgesFromCSV();

        nodeDatabase.cntNodes();

        staffDatabase.readStaffCSV("/csv/staffMembers.csv");
        staffDatabase.insertStaffFromCSV();

        serviceDatabase.addFeedback(new Feedback(1, 2, "Nothing Much"));
        serviceDatabase.addFeedback(new Feedback(2, 2, "IDK"));
        serviceDatabase.addFeedback(new Feedback(3, 2, "Haha"));
        serviceDatabase.addFeedback(new Feedback(4, 2, "Yep"));
        serviceDatabase.addFeedback(new Feedback(5, 2, "I like the app"));
        serviceDatabase.addFeedback(new Feedback(6, 3, "mhm"));
        serviceDatabase.addFeedback(new Feedback(7, 3, "Example"));
        serviceDatabase.addFeedback(new Feedback(8, 4, "Nothing"));
        serviceDatabase.addFeedback(new Feedback(9, 1, "Much"));
        serviceDatabase.addFeedback(new Feedback(10, 5, "Not Much"));

        launch(args);

        nodeDatabase.outputNodesCSV();
        edgeDatabase.outputEdgesCSV();
        staffDatabase.outputStaffCSV();
    }
}
