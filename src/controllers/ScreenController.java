package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

public class ScreenController extends StackPane {

    public static String AddNodeID = "AddNode";
    public static String AddNodeFile = "/fxml/AddNode.fxml";
    public static String AdminMenuID = "AdminMenu";
    public static String AdminMenuFile = "/fxml/AdminMenu.fxml";
    public static String FilterID = "Filter";
    public static String FilterFile = "/fxml/Filter.fxml";
    public static String LoginID = "Login";
    public static String LoginFile = "/fxml/Login.fxml";
    public static String LogoutID = "LogoutID";
    public static String LogoutFile = "/fxml/Logout.fxml";
    public static String MainID = "Main";
    public static String MainFile = "/fxml/Main.fxml";
    public static String PathID = "Path";
    public static String PathFile = "/fxml/Path.fxml";
    public static String RequestID = "Request";
    public static String RequestFile = "/fxml/Request.fxml";
    public static String ThankYouID = "ThankYou";
    public static String ThankYouFile = "/fxml/ThankYou.fxml";

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
            controllerToLoad.init();
            controllerToLoad.setParentController(this);
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
                getChildren().remove(0);
            }
            getChildren().add(screens.get(name));
            controllers.get(name).onShow();
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
