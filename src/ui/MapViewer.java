package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import controllers.ScreenController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import map.FloorNumber;

import java.util.*;

public class MapViewer extends Observable{
    private int SCROLL_WIDTH = 2000;
    private int SPACER_WIDTH = 500;
    private final int SPACING = 10;
    private final int BUTTON_HEIGHT = 80;
    private final int BUTTON_WIDTH = 150;

    private ArrayList<String> buttonOrder;
    public FloorNumber currentFloor;
    private ScrollPane buttonScrollPane;
    private ScrollPane mapScrollPane;

    private Pane mapPane;

    public JFXDrawer buttonDrawer;
    public JFXDrawersStack drawersStack;
    private HBox container;
    private Pane spacerLeft;
    private Pane spacerRight;

    private AnchorPane mapViewerPane;

    private proxyImagePane mapImage;

    public MapViewer(Observer o, Pane parent){
        super();
        buttonScrollPane = new ScrollPane();
        mapScrollPane = new ScrollPane();
        mapPane = new Pane();
        buttonOrder = new ArrayList<String>();
        buttonDrawer = new JFXDrawer();
        mapViewerPane = new AnchorPane();
        drawersStack = new JFXDrawersStack();
        setContainer();
        mapScrollPane.setContent(mapPane);

        addFloor(FloorNumber.FLOOR_LTWO);
        addFloor(FloorNumber.FLOOR_LONE);
        addFloor(FloorNumber.FLOOR_GROUND);
        addFloor(FloorNumber.FLOOR_ONE);
        addFloor(FloorNumber.FLOOR_TWO);
        addFloor(FloorNumber.FLOOR_THREE);

        container.getChildren().add(0,spacerLeft);
        container.getChildren().add(spacerRight);
        container.getStyleClass().add("buttonScrollPane");

        mapImage = new proxyImagePane();
        mapPane.getChildren().add(mapImage);

        buttonScrollPane.getStyleClass().add("buttonScrollPane");
        buttonScrollPane.setPannable(true);
        buttonScrollPane.setPrefViewportHeight(100);
        buttonScrollPane.setContent(container);

        currentFloor = FloorNumber.FLOOR_ONE;
        setFloor(currentFloor,buttonOrder.indexOf(currentFloor.getDbMapping()));

        addObserver(o);

        buttonScrollPane.prefViewportWidthProperty().bind(parent.prefWidthProperty());
        mapScrollPane.prefViewportHeightProperty().bind(parent.prefHeightProperty());
        mapScrollPane.prefViewportWidthProperty().bind(parent.prefWidthProperty());

        mapViewerPane.prefWidthProperty().bind(parent.prefWidthProperty());
        mapViewerPane.prefHeightProperty().bind(parent.prefHeightProperty());

        mapViewerPane.prefWidthProperty().addListener( (arg, oldValue, newValue) -> resizeSpacers(newValue));


        mapViewerPane.getChildren().addAll(mapScrollPane, buttonScrollPane);
        mapViewerPane.setBottomAnchor(buttonScrollPane, 0.0);



    }

    public void resetView(){
        clearButtons();
        addFloor(FloorNumber.FLOOR_LTWO);
        addFloor(FloorNumber.FLOOR_LONE);
        addFloor(FloorNumber.FLOOR_GROUND);
        addFloor(FloorNumber.FLOOR_ONE);
        addFloor(FloorNumber.FLOOR_TWO);
        addFloor(FloorNumber.FLOOR_THREE);

        container.getChildren().add(0,spacerLeft);
        container.getChildren().add(spacerRight);
        currentFloor = FloorNumber.FLOOR_ONE;
        setFloor(currentFloor,buttonOrder.indexOf(currentFloor.getDbMapping()));
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

    private void resizeSpacers(Number width){
        int paneWidth = width.intValue();
        SPACER_WIDTH = (paneWidth - 170) / 2;
        spacerRight.setPrefWidth(SPACER_WIDTH);
        spacerLeft.setPrefWidth(SPACER_WIDTH);

        SCROLL_WIDTH = (SPACER_WIDTH*2 + buttonOrder.size()*(BUTTON_WIDTH+SPACING+SPACING));
        container.setPrefWidth(SCROLL_WIDTH);

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
    public ScrollPane getButtonScrollPane() {
        return buttonScrollPane;
    }
    public proxyImagePane getMapImage(){
        return mapImage;
    }
    public double getScale(){
        return  mapImage.getScale();
    }

    public Pane getMapPane() { return mapPane;}

    public AnchorPane getMapViewerPane() {
        return mapViewerPane;
    }

    //Setters
    private void setFloor(FloorNumber floor, int buttonPose){
        mapImage.setImage(floor);
        Timeline slideButtons = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(buttonScrollPane.hvalueProperty(), buttonScrollPane.getHvalue())),
                new KeyFrame(new Duration(300),
                        new KeyValue(buttonScrollPane.hvalueProperty(),buttonPose/(buttonOrder.size()-1.0)))
        );
        slideButtons.play();
    }

    public void setFloor(FloorNumber floor){
        setFloor(floor,buttonOrder.indexOf(floor.getDbMapping()));
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
