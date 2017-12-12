package ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class TransitionCircle extends Circle {
    private Timeline waitTimeline;
    private Timeline moveTimeline;

    private int x;
    private int y;

    public TransitionCircle(int x, int y){
        this.x=x;
        this.y=y;
        //position circle
        setCenterX(x);
        setCenterY(y);
        //set up timelines
        waitTimeline  = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(translateXProperty(),10)),
                new KeyFrame(new Duration(1500),
                        new KeyValue(translateXProperty(),-10))
        );

        waitTimeline.setAutoReverse(true);
        waitTimeline.setCycleCount(Timeline.INDEFINITE);
        waitTimeline.play();

    }
    public void moveTo(int cx, int cy){
        moveTimeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(centerXProperty(), x),
                        new KeyValue(centerYProperty(), y) ),
                new KeyFrame(new Duration(1500),
                        new KeyValue(centerXProperty(), cx),
                        new KeyValue(centerYProperty(), cy) )
        );
        moveTimeline.play();
        //update coordinate
        x=cx;
        y=cy;
        setCenterX(x);
        setCenterY(y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
