package controllers;


import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class LogoutController implements ControllableScreen {
    private ScreenController parent;

    public void setParentController(ScreenController parent){
        this.parent = parent;
    }

    public void init(){}

    public void onShow(){
        PauseTransition pause = new PauseTransition(Duration.millis(1000));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parent.setScreen(ScreenController.MainID);
            }
        });
        pause.play();
    }

}
