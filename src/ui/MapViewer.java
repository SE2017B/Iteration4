package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXSlider;
import controllers.ScreenController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import javafx.scene.image.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import map.FloorNumber;

import java.util.*;

public class MapViewer extends Observable{
    private int SCROLL_WIDTH = 2000;
    private int SPACER_WIDTH = 500;
    private final int SPACING = 10;
    private final int BUTTON_HEIGHT = 50;
    private final int BUTTON_WIDTH = 100;

    private ArrayList<String> buttonOrder;
    public FloorNumber currentFloor;
    private ScrollPane buttonScrollPane;
    private ScrollPane mapScrollPane;
    private Pane mapHolderPane;

    private Pane mapPane;

    private HBox container;
    private Pane spacerLeft;
    private Pane spacerRight;

    private AnchorPane mapViewerPane;

    private proxyImagePane mapImage;

    private Label prevFloor;
    private Label nextFloor;

    //ZOOM FUNCTIONALITY
    private GridPane zoomBar;
    private JFXSlider slideBarZoom;
    private JFXButton zoomIn;
    private JFXButton zoomOut;

    public MapViewer(Observer o, Pane parent){
        super();
        buttonScrollPane = new ScrollPane();
        mapScrollPane = new ScrollPane();
        mapPane = new Pane();
        buttonOrder = new ArrayList<String>();

        mapViewerPane = new AnchorPane();
        mapHolderPane = new Pane();
        prevFloor = new Label();
        nextFloor = new Label();

        prevFloor.setText("Previous Floor");
        prevFloor.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        prevFloor.setAlignment(Pos.CENTER);
        prevFloor.setPrefWidth(150);
        prevFloor.getStyleClass().add("text-on-white");
        prevFloor.setVisible(false);
        mapViewerPane.setBottomAnchor(prevFloor, 80.0);

        nextFloor.setText("Next Floor");
        nextFloor.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
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
        container.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null,null)));
        container.getStyleClass().add("buttonScrollPane");
        container.setPadding(new Insets(15, 0, 0, 0));

        buttonScrollPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null,null)));
        buttonScrollPane.setPannable(true);
        buttonScrollPane.setPrefViewportHeight(125);
        buttonScrollPane.setContent(container);


        System.out.println(container.getChildren());

        currentFloor = FloorNumber.FLOOR_ONE;
        setFloor(currentFloor,buttonOrder.indexOf(currentFloor.getDbMapping()));

        addObserver(o);

        buttonScrollPane.prefViewportWidthProperty().bind(parent.prefWidthProperty());
        mapScrollPane.prefViewportHeightProperty().bind(parent.prefHeightProperty());
        mapScrollPane.prefViewportWidthProperty().bind(parent.prefWidthProperty());


        mapViewerPane.prefWidthProperty().bind(parent.prefWidthProperty());
        mapViewerPane.prefHeightProperty().bind(parent.prefHeightProperty());


        mapViewerPane.prefWidthProperty().addListener( (arg, oldValue, newValue) -> resizeSpacers(newValue.intValue()));
        mapViewerPane.prefWidthProperty().addListener( (arg, oldValue, newValue) -> setScale(mapPane.getScaleX()));
        mapViewerPane.prefHeightProperty().addListener( (arg, oldValue, newValue) -> setScale(mapPane.getScaleX()));


        //ZOOM FUNCTIONALITY
        slideBarZoom = new JFXSlider();
        slideBarZoom.setMinSize(150, 55);
        slideBarZoom.setPrefSize(150, 55);
        slideBarZoom.setMaxSize(150, 55);
        //TODO FIX SLIDER BAR INTERACTIVITY
        //slideBarZoom.setOnMousePressed(e -> sliderChanged(e));
        //slideBarZoom.setRotate(90.0);
        slideBarZoom.setValue(1);
        slideBarZoom.setMajorTickUnit(25);
        slideBarZoom.setMinorTickCount(3);
        slideBarZoom.setMin(.2);
        slideBarZoom.setMax(1.8);
        slideBarZoom.setSnapToTicks(true);

        String urlToZoomIn = "images/Icons/circleZoomInWhite.png";
        ImageView zoomInImage = new ImageView(urlToZoomIn);
        zoomInImage.setFitWidth(75);
        zoomInImage.setFitHeight(75);
        zoomInImage.setScaleX(.5);
        zoomInImage.setScaleY(.5);
        zoomIn = new JFXButton("", zoomInImage);
        zoomIn.setContentDisplay(ContentDisplay.CENTER);
        zoomIn.setMinSize(50, 50);
        zoomIn.setPrefSize(50, 50);
        zoomIn.setMaxSize(50, 50);
        zoomIn.setAlignment(Pos.CENTER);
        zoomIn.setOnAction(e -> zoomInPressed(e));

        String urlToZoomOut = "images/Icons/circleZoomOutWhite.png";
        ImageView zoomOutImage = new ImageView(urlToZoomOut);
        zoomOutImage.setFitWidth(75);
        zoomOutImage.setFitHeight(75);
        zoomOutImage.setScaleX(.5);
        zoomOutImage.setScaleY(.5);
        zoomOut = new JFXButton("", zoomOutImage);
        zoomOut.setContentDisplay(ContentDisplay.CENTER);
        zoomOut.setMinSize(50, 50);
        zoomOut.setPrefSize(50, 50);
        zoomOut.setMaxSize(50, 50);
        zoomOut.setAlignment(Pos.CENTER);
        zoomOut.setOnAction(e -> zoomOutPressed(e));

        zoomBar = new GridPane();
        zoomBar.add(zoomOut, 0,  0);
        zoomBar.add(slideBarZoom, 1,  0);
        zoomBar.add(zoomIn, 2,  0);

        ColumnConstraints col0 = new ColumnConstraints(45);
        col0.setHalignment(HPos.CENTER);
        zoomBar.getColumnConstraints().add(col0); // column 0 (Zoom out) is 100 wide

        ColumnConstraints col1 = new ColumnConstraints(170);
        col1.setHalignment(HPos.CENTER);
        zoomBar.getColumnConstraints().add(col1); // column 1 (slider) is 200 wide

        ColumnConstraints col2 = new ColumnConstraints(45);
        col2.setHalignment(HPos.CENTER);
        zoomBar.getColumnConstraints().add(col2); // column 2 (Zoom in) is 100 wide

        RowConstraints row0 = new RowConstraints(65);
        row0.setValignment(VPos.CENTER);
        zoomBar.getRowConstraints().add(row0); // row 0 (All zoom) is 70 wide

        zoomBar.setPrefSize(300, 55);

        Pane zoomPane = new Pane();
        zoomPane.setMaxSize(362, 63);
        zoomPane.getChildren().add(zoomBar);

        //Add everything to the pane itself
        //buttonScrollPane.;

        mapViewerPane.getChildren().addAll(mapScrollPane, buttonScrollPane, zoomPane);
        mapViewerPane.setBottomAnchor(buttonScrollPane, 20.0);
        mapViewerPane.setBottomAnchor(zoomPane, 0.0);
        //TODO: FIX THIS
        mapViewerPane.setLeftAnchor(zoomPane, 500.0);
        mapViewerPane.setRightAnchor(zoomPane, 400.0);

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
        button.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
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
        mapViewerPane.getChildren().removeAll(prevFloor, nextFloor);
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
        prevFloor.setVisible(true);
        nextFloor.setVisible(true);
        if(buttonPose == 0){
            prevFloor.setVisible(false);
        }
        if(buttonPose == buttonOrder.size()-1){
            nextFloor.setVisible(false);
        }

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
        container.setPrefHeight(100);
        container.setPadding(new Insets(10,10,10,10));
        container.setAlignment(Pos.CENTER);
    }

    public void setScale(double scale){
        double min_scale = Math.max((mapScrollPane.getBoundsInLocal().getWidth()/5000),(mapScrollPane.getHeight()/3400));
        System.out.println("Hval before: " + mapScrollPane.getHvalue());
        System.out.println("Vval before: " + mapScrollPane.getVvalue());
        System.out.println();
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
        System.out.println("Hval after: " + mapScrollPane.getHvalue());
        System.out.println("Vval after: " + mapScrollPane.getVvalue());
        System.out.println();
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
        mapViewerPane.getChildren().addAll(prevFloor, nextFloor);
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

    //ZOOM FUNCTIONALITY LIES HERE

    //when + button is pressed zoom in map
    public void zoomInPressed(ActionEvent e){
        setZoom(slideBarZoom.getValue()+0.2);
    }

    //when - button pressed zoom out map
    public void zoomOutPressed(ActionEvent e){
        setZoom(slideBarZoom.getValue()-0.2);
    }

    public void setZoom(double zoom){
        this.slideBarZoom.setValue(zoom);
        this.setScale(zoom);
    }

    //adjusts map zoom through slider
    public void sliderChanged(MouseEvent e){
        this.setScale(slideBarZoom.getValue());
    }

    public JFXSlider getSlideBarZoom(){
        return slideBarZoom;
    }
}
