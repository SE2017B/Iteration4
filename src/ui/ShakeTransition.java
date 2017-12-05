package ui;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Control;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class ShakeTransition {
    private TranslateTransition t;

    public ShakeTransition(){

    }

    public void shake(Control c){
        t = new TranslateTransition(Duration.millis(250), c);
        t.setByX(25f);
        t.setCycleCount(4);
        t.setAutoReverse(true);
        t.setDelay(Duration.millis(350));
        t.playFromStart();
    }

    /*
    public void shakeTextField(TextField m){
        t.setByX(25f);
        t.setCycleCount(4);
        t.setAutoReverse(true);
        t.setDelay(Duration.millis(350));
        t.playFromStart();
    }
    */
}
