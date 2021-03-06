/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration1
* Original author(s): Travis Norris, Andrey Yuzvik
* The following code
*/

package controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import ui.ScreenMomento;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ScreenController extends StackPane implements Initializable {
    private Duration transitionTime = new Duration(500);
    private Duration shortTransistionTime = new Duration(250);
    private Duration transitionDelay = new Duration(0);
    private HashMap<String, Node> screens = new HashMap<String, Node>();
    private HashMap<String, ControllableScreen> controllers = new HashMap<String, ControllableScreen>();
    private String state;
    private ScreenMomento screenMomento;
    PauseTransition timeout;
    boolean isPaused;

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
    public static String HelpID = "Help";
    public static String HelpFile = "/fxml/AnimatedHelp.fxml";
    public static String FeedbackID = "Feedback";
    public static String FeedbackFile = "/fxml/Feedback.fxml";
    public static String DirectionHelpID = "DirectionHelp";
    public static String DirectionHelpFile = "/fxml/DirectionHelp.fxml";
    public static String LoadID = "Load";
    public static String LoadFile = "/fxml/Loading.fxml";


    public ScreenController(){
        super();
        timeout = new PauseTransition(Duration.millis(30000));
        isPaused = false;
        timeout.setOnFinished(e -> {
            if(state != screenMomento.getState())
                while(getChildren().size() > 1){
                    getChildren().remove(0);
                }
                setScreen(screenMomento.getState());
            timeout.play();
        });
        timeout.play();
    }

    //add a new screen to the screens HashMap
    public void addScreen(String name, Node screen, ControllableScreen controller){
        screens.put(name,screen);
        controllers.put(name, controller);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource((ScreenController.LoadFile)));
            getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                setScreen(ScreenController.MainID);
            });

        } catch (IOException ex) {
        }
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
            Parent fxmlToLoad = fxmlLoader.load();
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
        int yPos = -1 * (int)getHeight();
        if(direction.equals("DOWN"))
            yPos = -1 * yPos;
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

    public boolean HelpTransitionIn(String name) {
        int start = -1 * (int)getHeight();
        int end = 0;
        getChildren().add(1,screens.get(name));
        controllers.get(name).onShow();
        Timeline slide = new Timeline(
                new KeyFrame(Duration.ZERO, // set start position at 0
                        new KeyValue(getChildren().get(1).translateYProperty(), start)
                ),
                new KeyFrame(transitionTime, // set end position at 40s
                        new KeyValue(getChildren().get(1).translateYProperty(), end)
                )
        );

        slide.delayProperty().set(transitionDelay);
        slide.play();
        return true;
    }


    public boolean setScreen(String name) {
        return setScreen(name,"FADE");
    }

    public boolean setScreen(String name, String transition){
        if(screens.containsKey(name) && !getChildren().contains(screens.get(name))) {
            state = name;
            if (transition.equals("HELP_IN")) {
                HelpTransitionIn(name);
            }
            else if (transition.equals("HELP_OUT")){
                slideVerticalTransition(name, "UP");
            }
            else {
                if (!getChildren().isEmpty()) {
                    getChildren().add(0, screens.get(name));
                    controllers.get(name).onShow();
                    if (transition.equals("RIGHT")) {
                        return slideHorizontalTransition(name, transition);
                    } else if (transition.equals("LEFT")) {
                        return slideHorizontalTransition(name, transition);
                    } else if (transition.equals("LEFT")) {
                        return slideHorizontalTransition(name, transition);
                    } else if (transition.equals("UP")) {
                        return slideVerticalTransition(name, transition);
                    } else if (transition.equals("DOWN")) {
                        return slideVerticalTransition(name, transition);
                    } else {
                        return fadeTransition(name);
                    }
                } else {
                    getChildren().add(screens.get(name));
                    controllers.get(name).onShow();
                }
                return true;
            }
        }

        return false;
    }

    //unloads a screen from the ui
    public boolean unloadScreen(String name) {
        screens.remove(name);
        return true;
    }

    public void resetTimeout(){
        if(!isPaused) {
            timeout.stop();
            timeout.play();
        }
    }
    public void pauseTimeout(){
        timeout.stop();
        isPaused = true;
    }

    public void resumeTimeout(){
        timeout.play();;
        isPaused = false;

    }

    public void setTimeoutLength(double length){
        timeout.setDuration(new Duration(length));
    }


    public Double getTimeoutLength(){
        return timeout.getDuration().toMillis();
    }

    public void saveState(){
        screenMomento = new ScreenMomento(state);
    }
}
