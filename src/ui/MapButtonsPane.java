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

public class MapButtonsPane extends ScrollPane{
    private ArrayList<JFXButton> buttons;

    private HBox container;
    private Pane spacerLeft;
    private Pane spacerRight;


    private proxyImagePane mapImage;


    public MapButtonsPane(proxyImagePane mapImage){
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
        this.mapImage = mapImage;
        getStyleClass().add("Pane");
    }

    private void setContainer(){
        spacerLeft = new Pane();
        spacerRight = new Pane();
        container = new HBox();
        container.setFillHeight(false);
        container.setPrefWidth(1200);
        container.setSpacing(10);
        //container.getChildren().addAll(spacerLeft,spacerRight);
        //HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        //HBox.setHgrow(spacerRight, Priority.ALWAYS);
    }

    private void addFloor(FloorNumber floor){
        JFXButton button = new JFXButton();
        button.setText(floor.getDbMapping());
        button.setPrefSize(150,80);
        button.setOnAction(e -> floorButtonPressed(e));
        buttons.add(button);
        container.getChildren().add(button);
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

    public void setButtonsByFloor(ArrayList<FloorNumber> floors){

    }
}
