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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import map.FloorNumber;

import java.util.*;

public class MapViewer extends Observable{
    private int SCROLL_WIDTH = 2000;
    private int SPACER_WIDTH = 500;
    private final int SPACING = 10;
    private final int BUTTON_HEIGHT = 50;
    private final int BUTTON_WIDTH = 150;

    private ArrayList<String> buttonOrder;
    public FloorNumber currentFloor;
    private ScrollPane buttonScrollPane;
    private ScrollPane mapScrollPane;
    private Pane mapHolderPane;

    private Pane mapPane;

    public JFXDrawer buttonDrawer;
    public JFXDrawersStack drawersStack;
    private HBox container;
    private Pane spacerLeft;
    private Pane spacerRight;

    private AnchorPane mapViewerPane;

    private proxyImagePane mapImage;

    private Label prevFloor;
    private Label nextFloor;

    public MapViewer(Observer o, Pane parent){
        super();
        buttonScrollPane = new ScrollPane();
        mapScrollPane = new ScrollPane();
        mapPane = new Pane();
        buttonOrder = new ArrayList<String>();
        buttonDrawer = new JFXDrawer();
        mapViewerPane = new AnchorPane();
        mapHolderPane = new Pane();
        prevFloor = new Label();
        nextFloor = new Label();

        prevFloor.setText("Previous Floor");
        prevFloor.setAlignment(Pos.CENTER);
        prevFloor.setPrefWidth(150);
        prevFloor.getStyleClass().add("text-on-white");
        prevFloor.setVisible(false);
        mapViewerPane.setBottomAnchor(prevFloor, 80.0);

        nextFloor.setText("Next Floor");
        nextFloor.setAlignment(Pos.CENTER);
        nextFloor.setPrefWidth(150);
        nextFloor.getStyleClass().add("text-on-white");
        nextFloor.setVisible(false);
        mapViewerPane.setBottomAnchor(nextFloor, 80.0);

        mapImage = new proxyImagePane();
        mapPane.getChildren().add(mapImage);
        mapHolderPane.getChildren().add(mapPane);
        setContainer();
        mapScrollPane.setContent(mapHolderPane);
        mapScrollPane.setPannable(true);

        addFloor(FloorNumber.FLOOR_LTWO);
        addFloor(FloorNumber.FLOOR_LONE);
        addFloor(FloorNumber.FLOOR_GROUND);
        addFloor(FloorNumber.FLOOR_ONE);
        addFloor(FloorNumber.FLOOR_TWO);
        addFloor(FloorNumber.FLOOR_THREE);

        container.getChildren().add(0,spacerLeft);
        container.getChildren().add(spacerRight);
        container.getStyleClass().add("buttonScrollPane");
        container.setPadding(new Insets(20, 0, 0, 0));





        buttonScrollPane.getStyleClass().add("buttonScrollPane");
        buttonScrollPane.setPannable(true);
        buttonScrollPane.setPrefViewportHeight(100);
        buttonScrollPane.setContent(container);

        currentFloor = FloorNumber.FLOOR_ONE;
        setFloor(currentFloor,buttonOrder.indexOf(currentFloor.getDbMapping()));

        addObserver(o);

        buttonScrollPane.prefViewportWidthProperty().bind(parent.prefWidthProperty());
        mapScrollPane.prefViewportHeightProperty().bind(parent.prefHeightProperty().subtract(98));
        mapScrollPane.prefViewportWidthProperty().bind(parent.prefWidthProperty());


        mapViewerPane.prefWidthProperty().bind(parent.prefWidthProperty());
        mapViewerPane.prefHeightProperty().bind(parent.prefHeightProperty());


        mapViewerPane.prefWidthProperty().addListener( (arg, oldValue, newValue) -> resizeSpacers(newValue.intValue()));
        mapViewerPane.prefWidthProperty().addListener( (arg, oldValue, newValue) -> setScale(mapPane.getScaleX()));
        mapViewerPane.prefHeightProperty().addListener( (arg, oldValue, newValue) -> setScale(mapPane.getScaleX()));


        mapViewerPane.getChildren().addAll(mapScrollPane, buttonScrollPane, prevFloor, nextFloor);
        mapViewerPane.setBottomAnchor(buttonScrollPane, 0.0);

        mapScrollPane.setPannable(true);
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

    private void resizeSpacers(int width){
        SPACER_WIDTH = (width - 200) / 2;
        spacerRight.setPrefWidth(SPACER_WIDTH);
        spacerLeft.setPrefWidth(SPACER_WIDTH);
        SCROLL_WIDTH = (SPACER_WIDTH*2 + buttonOrder.size()*(BUTTON_WIDTH+SPACING*2));
        container.setPrefWidth(SCROLL_WIDTH);
        double w = mapViewerPane.getWidth();
        prevFloor.setLayoutX((w/2) - 227);
        nextFloor.setLayoutX((w/2) + 77);
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
        prevFloor.setVisible(false);
        nextFloor.setVisible(false);
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
        return  mapPane.getScaleX();
    }

    public ScrollPane getMapScrollPane() {
        return mapScrollPane;
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
        double min_scale = Math.max((mapScrollPane.getBoundsInLocal().getWidth()/5000),(mapScrollPane.getHeight()/3400));
        if(scale < min_scale){
            scale = min_scale;
        }
        else if (scale > 2){
            scale = 2;
        }

        mapPane.setScaleX(scale);
        mapPane.setScaleY(scale);

        mapPane.setTranslateX((scale - 1)/2 * 5000);
        mapPane.setTranslateY((scale - 1)/2 * 3400);

        mapHolderPane.setPrefSize(mapPane.getBoundsInLocal().getWidth() * scale, mapPane.getBoundsInLocal().getHeight() * scale);
    }
    public double checkScale(double scale){
        double min_scale = Math.max((mapScrollPane.getBoundsInLocal().getWidth()/5000),(mapScrollPane.getHeight()/3400));
        if(scale < min_scale){
            scale = min_scale;
        }
        else if (scale > 2){
            scale = 2;
        }
        return scale;
    }

    public void setButtonsByFloor(List<FloorNumber> floors){
        clearButtons();

        for (int i = 0; i < floors.size(); i++) {
            addFloor(floors.get(i), i);
        }

        resizeSpacers((int)mapViewerPane.getBoundsInLocal().getWidth());
        container.getChildren().add(0,spacerLeft);
        container.getChildren().add(spacerRight);
        setFloor(floors.get(0),0);
        prevFloor.setVisible(true);
        nextFloor.setVisible(true);
    }

    public void centerView(double x, double y) {

        x =  (x * mapPane.getScaleX());
        y =  (y * mapPane.getScaleY());

        //height
        double h = mapScrollPane.getContent().getBoundsInLocal().getHeight();
        double v = mapScrollPane.getViewportBounds().getHeight();
        //width
        double w = mapScrollPane.getContent().getBoundsInLocal().getWidth();
        double H = mapScrollPane.getViewportBounds().getWidth();

        mapScrollPane.setVvalue(((y - 0.5 * v) / (h - v)));
        mapScrollPane.setHvalue(((x - 0.5 * H) / (w - H)));
    }
    public void animateCenter(int x, int y){
        x = (int) (x * mapPane.getScaleX());
        y = (int) (y * mapPane.getScaleY());

        //height
        double h = mapScrollPane.getContent().getBoundsInLocal().getHeight();
        double v = mapScrollPane.getViewportBounds().getHeight();
        //width
        double w = mapScrollPane.getContent().getBoundsInLocal().getWidth();
        double H = mapScrollPane.getViewportBounds().getWidth();

        double vValue = ((y - 0.5 * v) / (h - v));
        double hValue = ((x - 0.5 * H) / (w - H));

        Timeline zoomPath = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(mapScrollPane.hvalueProperty(), mapScrollPane.getHvalue())),
                new KeyFrame(Duration.ZERO,
                        new KeyValue(mapScrollPane.vvalueProperty(), mapScrollPane.getVvalue())),
                new KeyFrame(new Duration(1000),
                        new KeyValue(mapScrollPane.hvalueProperty(),hValue)),
                new KeyFrame(new Duration(1000),
                        new KeyValue(mapScrollPane.vvalueProperty(),vValue))
        );
        zoomPath.play();


    }

    public ArrayList<Double> getCenter(){
        ArrayList<Double> ans = new ArrayList<>();
        //height
        double h = mapScrollPane.getContent().getBoundsInLocal().getHeight();
        double v = mapScrollPane.getViewportBounds().getHeight();
        //width
        double w = mapScrollPane.getContent().getBoundsInLocal().getWidth();
        double H = mapScrollPane.getViewportBounds().getWidth();

        double y = (mapScrollPane.getVvalue()*(h-v)) + (0.5*v);
        double x = (mapScrollPane.getHvalue()*(w - H)) + (0.5*H);
        ans.add(x);
        ans.add(y);
        return ans;
    }
    public ArrayList<Double> getCenterAt(double scale){
        double cScale = getScale();
        setScale(scale);//set temporary scale
        ArrayList<Double> ans = getCenter();
        setScale(cScale);//revert back to normal scale
        return ans;
    }

    public void setScroller(double v, double h){
        mapScrollPane.setVvalue(v);
        mapScrollPane.setHvalue(h);
    }
}
