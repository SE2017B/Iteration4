package ui;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import map.FloorNumber;

import java.util.ArrayList;
import java.util.Observable;

public class MapButtonsPane extends ScrollPane{
    private final int OVERALL_WIDTH = 1800;
    private final int SPACING = 10;
    private final int BUTTON_HEIGHT = 60;
    private final int BUTTON_WIDTH = 150;

    private ArrayList<JFXButton> buttons;
    public FloorNumber currentFloor;

    private HBox container;
    private Pane spacerLeft;
    private Pane spacerRight;


    private proxyImagePane mapImage;


    public MapButtonsPane(){
        super();
        buttons = new ArrayList<JFXButton>();
        setContainer();
        setContent(container);
        addFloor(FloorNumber.FLOOR_THREE);
        addFloor(FloorNumber.FLOOR_TWO);
        addFloor(FloorNumber.FLOOR_ONE);
        addFloor(FloorNumber.FLOOR_GROUND);
        addFloor(FloorNumber.FLOOR_LONE);
        addFloor(FloorNumber.FLOOR_LTWO);
        mapImage = new proxyImagePane();
        getStyleClass().add("Pane");
        setPannable(true);
        currentFloor = FloorNumber.FLOOR_ONE;
        setFloor(currentFloor);
    }

    private void setContainer(){
        spacerLeft = new Pane();
        spacerRight = new Pane();
        container = new HBox();
        container.setFillHeight(false);
        container.setPrefWidth(OVERALL_WIDTH);
        container.setSpacing(SPACING);
        container.getChildren().addAll(spacerLeft,spacerRight);
    }

    private void addFloor(FloorNumber floor){
        JFXButton button = new JFXButton();
        button.setText(floor.getDbMapping());
        button.setMinSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        button.setOnAction(e -> floorButtonPressed(e));
        buttons.add(0,button);
        container.getChildren().add(1,button);
        int spacerWidth = (OVERALL_WIDTH - (buttons.size()*(BUTTON_WIDTH+SPACING+SPACING))/2);
        spacerLeft.setPrefWidth(spacerWidth);
        spacerRight.setPrefWidth(spacerWidth);
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
                        new KeyValue(hvalueProperty(), getHvalue())),
                new KeyFrame(new Duration(300),
                        new KeyValue(hvalueProperty(),(floor.getNodeMapping() - 1)/(buttons.size()-1)))
        );
        slideButtons.play();
    }

    public FloorNumber getFloor(){
        return currentFloor;
    }

    public void setButtonsByFloor(ArrayList<FloorNumber> floors){
        //todo clear all current buttons and set to new ones
    }
}
