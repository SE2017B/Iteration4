/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import kioskEngine.KioskEngine;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController extends StackPane {

    public static String AddNodeID = "AddNode";
    public static String AddNodeFile = "/fxml/AddNode.fxml";
    public static String LogoutID = "LogoutID";
    public static String LogoutFile = "/fxml/Logout.fxml";
    public static String MainID = "Main";
    public static String MainFile = "/fxml/Main.fxml";
    public static String PathID = "Path";
    public static String PathFile = "/fxml/Path.fxml";
    public static String RequestID = "Request";
    public static String RequestFile = "/fxml/Request.fxml";
    public static String LoginID = "Login";
    public static String LoginFile = "/fxml/Login.fxml";


    private HashMap<String, Node> screens = new HashMap<String, Node>();
    private HashMap<String, ControllableScreen> controllers = new HashMap<String, ControllableScreen>();
    private KioskEngine engine;

    public ScreenController(KioskEngine engine){
        super();
        this.engine = engine;
    }

    //add a new screen to the screens HashMap
    public void addScreen(String name, Node screen, ControllableScreen controller){
        screens.put(name,screen);
        controllers.put(name, controller);
    }



    //return a screen from the screens HashMap
    public Node getScreen(String name){
        return screens.get(name); //stub for function headers
    }


    public ControllableScreen getController(String name){
        return controllers.get(name); //stub for function headers
    }

    //load a screen into the overall ui.
    //does not display the screen
    public boolean loadScreen (String name, String file) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(file));
            Parent fxmlToLoad = (Parent) fxmlLoader.load();
            ControllableScreen controllerToLoad = ((ControllableScreen) fxmlLoader.getController());
            controllerToLoad.setParentController(this);
            controllerToLoad.init();
            addScreen(name, fxmlToLoad, controllerToLoad);
            return true;
        }
        catch (Exception e) {
            System.out.println("ERROR " + e.getMessage());
            throw e;
        }

    }

    //set the screen currently being displayed on the pane
    public boolean setScreen(String name){
        if(screens.containsKey(name)){

            if(!getChildren().isEmpty()){
                controllers.get(name).onShow();
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacityProperty(),
                                1.0)),
                        new KeyFrame(new Duration(200), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                getChildren().remove(0);                    //remove the displayed screen
                                getChildren().add(0, screens.get(name));     //add the screen
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacityProperty(), 0.0)),
                                        new KeyFrame(new Duration(100), new KeyValue(opacityProperty(), 1.0)));
                                fadeIn.play();

                            }
                        }, new KeyValue(opacityProperty(), 0.0)));
                fade.play();
            }
            else {
                getChildren().add(screens.get(name));
                controllers.get(name).onShow();
            }
            return true;



        }
        System.out.println("Set Screen Failed");
        return false;
    }

    //unloads a screen from the ui
    public boolean unloadScreen(String name) {
        screens.remove(name);
        return true;
    }

    public KioskEngine getEngine() {
        return engine;
    }
}
