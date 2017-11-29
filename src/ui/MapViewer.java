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

import java.util.*;

public class MapViewer extends Observable{
    private int PANE_WIDTH = 1280;
    private int SCROLL_WIDTH = 2000;
    private int SPACER_WIDTH = 500;
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

    public MapViewer(Observer o){
        super();
        pane = new ScrollPane();
        buttonOrder = new ArrayList<String>();
        setContainer();
        pane.setContent(container);

        addFloor(FloorNumber.FLOOR_LTWO);
        addFloor(FloorNumber.FLOOR_LONE);
        addFloor(FloorNumber.FLOOR_GROUND);
        addFloor(FloorNumber.FLOOR_ONE);
        addFloor(FloorNumber.FLOOR_TWO);
        addFloor(FloorNumber.FLOOR_THREE);

        container.getChildren().add(0,spacerLeft);
        container.getChildren().add(spacerRight);
        mapImage = new proxyImagePane();
        pane.getStyleClass().add("pane");
        container.getStyleClass().add("pane");
        pane.setPannable(true);
        currentFloor = FloorNumber.FLOOR_ONE;
        setFloor(currentFloor,buttonOrder.indexOf(currentFloor.getDbMapping()));
        pane.setPrefWidth(PANE_WIDTH);
        pane.setPrefHeight(150);
        addObserver(o);
    }

    private JFXButton addFloor(FloorNumber floor){
        JFXButton button = new JFXButton();
        button.setText(floor.getDbMapping());
        button.setMinSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        button.setOnAction(e -> floorButtonPressed(e));
        button.setId("-1");
        buttonOrder.add(button.getText());
        container.getChildren().add(button);
        SCROLL_WIDTH = (SPACER_WIDTH*2 + buttonOrder.size()*(BUTTON_WIDTH+SPACING+SPACING));
        container.setPrefWidth(SCROLL_WIDTH);
        return button;
    }

    private JFXButton addFloor(FloorNumber floor, int id){
        JFXButton button = addFloor(floor);
        button.setId(Integer.toString(id));
        return button;
    }

    public void floorButtonPressed(ActionEvent e){
        FloorNumber floor =  FloorNumber.fromDbMapping(((JFXButton)e.getSource()).getText());
        int id = Integer.parseInt((((JFXButton) e.getSource()).getId()));
        if(id == -1) {
            setFloor(floor,buttonOrder.indexOf(floor.getDbMapping()));
        }
        else {
            setFloor(floor,id);
        }
        setChanged();
        notifyObservers(new PathID(floor, id));
    }

    private void clearButtons(){
        container.getChildren().clear();
        buttonOrder.clear();
    }

    //Getters
    public FloorNumber getFloor(){
        return currentFloor;
    }
    public ScrollPane getPane() {
        return pane;
    }
    public proxyImagePane getMapImage(){
        return mapImage;
    }
    public double getScale(){
        return  mapImage.getScale();
    }

    //Setters
    private void setFloor(FloorNumber floor, int buttonPose){
        mapImage.setImage(floor);
        Timeline slideButtons = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(pane.hvalueProperty(), pane.getHvalue())),
                new KeyFrame(new Duration(300),
                        new KeyValue(pane.hvalueProperty(),buttonPose/(buttonOrder.size()-1.0)))
        );
        slideButtons.play();
    }

    public void setFloor(FloorNumber floor){
        setFloor(floor,buttonOrder.indexOf(floor.getDbMapping()));
    }

    public void setSpacerWidth(int width){
        SPACER_WIDTH = width;
        spacerLeft.setPrefWidth(SPACER_WIDTH);
        spacerRight.setPrefWidth(SPACER_WIDTH);
        SCROLL_WIDTH = (SPACER_WIDTH*2 + buttonOrder.size()*(BUTTON_WIDTH+SPACING+SPACING));
        container.setPrefWidth(SCROLL_WIDTH);
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
    }

    public void setScale(double scale){
        mapImage.setScale(scale);
    }

    public void setButtonsByFloor(List<FloorNumber> floors){
        clearButtons();
        if(floors.size() == 1){
            SPACER_WIDTH = 560;
        }
        else{
            SPACER_WIDTH = 500;
        }
        for (int i = 0; i < floors.size(); i++) {
            addFloor(floors.get(i), i);
        }

        container.getChildren().add(0,spacerLeft);
        container.getChildren().add(spacerRight);
        setFloor(floors.get(0),0);
    }
}
