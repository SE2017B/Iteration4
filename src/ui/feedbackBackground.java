package ui;

import controllers.ScreenController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class feedbackBackground extends ImageView {

    private static Image hospitalFile = new Image("/images/brigham-womens-hospital-building.jpg");
    private Pane parent;

    public feedbackBackground(Pane parent){
        super(hospitalFile);
        setPreserveRatio(true);
        setFitWidth(parent.getWidth());
        setFitHeight(parent.getWidth());
        this.parent = parent;
        parent.widthProperty().addListener( observable -> scaleImage());
        parent.heightProperty().addListener( observable -> scaleImage());

    }

    private void scaleImage(){
        double size = Double.max(parent.getHeight()*1.334, parent.getWidth());
        setFitWidth(size);
        setFitHeight(size);
    }
}