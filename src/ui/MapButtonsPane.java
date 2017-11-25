package ui;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import map.FloorNumber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;

public class MapButtonsPane extends Observable{
    private final int SPACER_WIDTH = 500;
    private final int SPACING = 10;
    private final int BUTTON_HEIGHT = 80;
    private final int BUTTON_WIDTH = 150;

    private ArrayList<String> buttonOrder;
    public FloorNumber currentFloor;
    private ScrollPane pane;

    private HBox container;
    private Pane spacerLeft;
    private Pane spacerRight;


    private proxyImagePane mapImage;


    public MapButtonsPane(){
        super();
        pane = new ScrollPane();
        buttonOrder = new ArrayList<String>();
        setContainer();
        pane.setContent(container);
        addFloor(FloorNumber.FLOOR_THREE);
        addFloor(FloorNumber.FLOOR_TWO);
        addFloor(FloorNumber.FLOOR_ONE);
        addFloor(FloorNumber.FLOOR_GROUND);
        addFloor(FloorNumber.FLOOR_LONE);
        addFloor(FloorNumber.FLOOR_LTWO);
        mapImage = new proxyImagePane();
        pane.getStyleClass().add("Pane");
        pane.setPannable(true);
        currentFloor = FloorNumber.FLOOR_ONE;
        setFloor(currentFloor);
        pane.setPrefWidth(1280);
        pane.setPrefHeight(150);
        System.out.println("Spacer Width: " + spacerLeft.getPrefWidth());

    }

    private void setContainer(){
        spacerLeft = new Pane();
        spacerRight = new Pane();
        spacerLeft.setPrefWidth(SPACER_WIDTH);
        spacerRight.setPrefWidth(SPACER_WIDTH);
        container = new HBox();
        container.setSpacing(SPACING);
        container.setPadding(new Insets(10,10,10,10));
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(spacerLeft,spacerRight);
    }

    private void addFloor(FloorNumber floor){
        JFXButton button = new JFXButton();
        button.setText(floor.getDbMapping());
        button.setMinSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        button.setOnAction(e -> floorButtonPressed(e));
        buttonOrder.add(0,button.getText());
        container.getChildren().add(1,button);
        int overallWidth = (SPACER_WIDTH*2 + buttonOrder.size()*(BUTTON_WIDTH+SPACING+SPACING));
        container.setPrefWidth(overallWidth);
    }

    public proxyImagePane getMapImage(){
        return mapImage;
    }

    public double getScale(){
        return  mapImage.getScale();
    }

    public void setScale(double scale){
        mapImage.setScale(scale);
    }

    public void floorButtonPressed(ActionEvent e){
        FloorNumber floor =  FloorNumber.fromDbMapping(((JFXButton)e.getSource()).getText());
        setFloor(floor);
    }


    public void setFloor(FloorNumber floor){
        mapImage.setImage(floor);
        Timeline slideButtons = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(pane.hvalueProperty(), pane.getHvalue())),
                new KeyFrame(new Duration(300),
                        new KeyValue(pane.hvalueProperty(),buttonOrder.indexOf(floor.getDbMapping())/(buttonOrder.size()-1.0)))
        );
        slideButtons.play();
    }

    public FloorNumber getFloor(){
        return currentFloor;
    }

    public ScrollPane getPane() {
        return pane;
    }

    private void clearButtons(){
        container.getChildren().clear();
        buttonOrder.clear();
        container.getChildren().addAll(spacerLeft,spacerRight);
    }

    public void setButtonsByFloor(List<FloorNumber> floors){
        clearButtons();
        for (FloorNumber floor: floors) {
            addFloor(floor);
        }
        setFloor(floors.get(0));
    }
}
