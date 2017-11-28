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
        addFloor(FloorNumber.FLOOR_THREE);
        addFloor(FloorNumber.FLOOR_TWO);
        addFloor(FloorNumber.FLOOR_ONE);
        addFloor(FloorNumber.FLOOR_GROUND);
        addFloor(FloorNumber.FLOOR_LONE);
        addFloor(FloorNumber.FLOOR_LTWO);
        mapImage = new proxyImagePane();
        pane.getStyleClass().add("pane");
        container.getStyleClass().add("pane");
        pane.setPannable(true);
        currentFloor = FloorNumber.FLOOR_ONE;
        setFloor(currentFloor);
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
        buttonOrder.add(0,button.getText());
        container.getChildren().add(1,button);
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
        setFloor(floor);
        setChanged();
        notifyObservers(new PathID(floor, id));
    }

    private void clearButtons(){
        container.getChildren().clear();
        buttonOrder.clear();
        container.getChildren().addAll(spacerLeft,spacerRight);
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
        container.getChildren().addAll(spacerLeft,spacerRight);
    }

    public void setScale(double scale){
        mapImage.setScale(scale);
    }

    public void setButtonsByFloor(List<FloorNumber> floors){
        clearButtons();
        for (int i = floors.size()-1; i >= 0; i--) {
            addFloor(floors.get(i), i);
        }
        setFloor(floors.get(0));
    }
}
