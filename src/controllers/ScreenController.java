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


import java.io.IOException;
import java.util.HashMap;

public class ScreenController extends StackPane {
    private Duration transitionTime = new Duration(800);
    private Duration shortTransistionTime = new Duration(400);
    private Duration transitionDelay = new Duration(200);

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


    public ScreenController(){
        super();
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

    public boolean fadeTransition(String name) {
        Timeline fade = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(getChildren().get(1).opacityProperty(), 1.0)
                ),
                new KeyFrame(shortTransistionTime,
                        new KeyValue(getChildren().get(1).opacityProperty(),0)
                )
        );
        fade.onFinishedProperty().set(e ->  {
            getChildren().get(1).opacityProperty().set(1.0);
            getChildren().remove(1);
        });
        fade.delayProperty().set(transitionDelay);
        fade.play();
        return true;
    }

    public boolean slideHorizontalTransition(String name, String direction) {
        int xPos = -1300;
        if(direction.equals("RIGHT"))
            xPos = 1300;
        Timeline slide = new Timeline(
                new KeyFrame(Duration.ZERO, // set start position at 0
                        new KeyValue(getChildren().get(1).translateXProperty(), 0)
                ),
                new KeyFrame(transitionTime, // set end position at 40s
                        new KeyValue(getChildren().get(1).translateXProperty(), xPos)
                )
        );
        //Hide and reset the orginal pane
        slide.onFinishedProperty().set(e -> {
            getChildren().get(1).translateXProperty().set(0);
            getChildren().remove(1);
        });
        slide.delayProperty().set(transitionDelay);
        slide.play();
        return true;
    }


    public boolean slideVerticalTransition(String name, String direction) {
        int yPos = -800;
        if(direction.equals("DOWN"))
            yPos = 800;
        Timeline slide = new Timeline(
                new KeyFrame(Duration.ZERO, // set start position at 0
                        new KeyValue(getChildren().get(1).translateYProperty(), 0)
                ),
                new KeyFrame(transitionTime, // set end position at 40s
                        new KeyValue(getChildren().get(1).translateYProperty(), yPos)
                )
        );
        //Hide and reset the orginal pane
        slide.onFinishedProperty().set(e -> {
            getChildren().get(1).translateYProperty().set(0);
            getChildren().remove(1);
        });
        slide.delayProperty().set(transitionDelay);
        slide.play();
        return true;
    }
    public boolean setScreen(String name) {
        return setScreen(name,"FADE");
    }

    public boolean setScreen(String name, String transition){
        if(screens.containsKey(name)){
            if(!getChildren().isEmpty()){
                getChildren().add(0,screens.get(name));
                controllers.get(name).onShow();
                if(transition.equals("RIGHT")){
                    return slideHorizontalTransition(name,transition);
                }
                else if (transition.equals("LEFT")){
                    return slideHorizontalTransition(name,transition);
                }
                else if (transition.equals("LEFT")){
                    return slideHorizontalTransition(name,transition);
                }
                else if (transition.equals("UP")){
                    return slideVerticalTransition(name,transition);
                }
                else if (transition.equals("DOWN")){
                    return slideVerticalTransition(name,transition);
                }
                else{
                    return fadeTransition(name);
                }
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

}
