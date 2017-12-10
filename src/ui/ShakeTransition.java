package ui;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ShakeTransition {
    private TranslateTransition t;

    public ShakeTransition(){}

    public void shake(Control c){
        t = new TranslateTransition(Duration.millis(100), c);
        t.setByX(15f);
        t.setCycleCount(4);
        t.setAutoReverse(true);
        t.setDelay(Duration.millis(350));
        t.playFromStart();
    }
}
