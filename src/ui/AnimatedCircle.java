package ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.sql.Time;

public class AnimatedCircle extends Circle {

    public AnimatedCircle(){
        Timeline timeline  = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(opacityProperty(), 1.0),
                        new KeyValue(radiusProperty(), 10) ),
                new KeyFrame(new Duration(1500),
                        new KeyValue(opacityProperty(), 0.8),
                        new KeyValue(radiusProperty(), 13))
        );

        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
