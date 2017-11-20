package controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import map.FloorNumber;

import java.util.HashMap;


public class proxyImagePane extends StackPane {

    //pointers to images and their fxml files
    public static String floorL1 = "00_thelowerlever1.png";
    public static String floorL2 = "00_thelowerlever2.png";
    public static String floorG = "00_thegroundfloor.png";
    public static String floor1 = "01_thefirstfloor.png";
    public static String floor2 = "02_thesecondfloor.png";
    public static String floor3 = "03_thethirdfloor.png";

    private HashMap<FloorNumber,ImageView> floors;

    public proxyImagePane(){
        super();

    }
    private String getImage(FloorNumber floor){
        //return corresponding image type
        if(floor.getDbMapping()=="L2"){
            return floorL2;
        }
        if(floor.getDbMapping()=="L1"){
            return floorL1;
        }
        if(floor.getDbMapping()=="G"){
            return floorG;
        }
        if(floor.getDbMapping()=="1"){
            return floor1;
        }
        if(floor.getDbMapping()=="2"){
            return floor2;
        }
        if(floor.getDbMapping()=="3"){
            return floor3;
        }
        return null;

    }
    public ImageView getImagePane(FloorNumber floor){
        return floors.get(floor);
    }
    public boolean addImage(FloorNumber floor){
        //Todo: create new image view
        String name = this.getImage(floor);//get image name for floor
        Image img = new Image(name); //create new image
        ImageView imgView = new ImageView(img); //create new image view pane
        floors.put(floor,imgView);//add new image view to hash map floors
        return true;
    }
    public boolean setImage(FloorNumber floor){
        //Todo: Isn't set image and add image the same thing
        /**
        if(screens.containsKey(name)){
            if(!getChildren().isEmpty()){
                getChildren().remove(0);
            }
            getChildren().add(screens.get(name));
            controllers.get(name).onShow();
            return true;
        }
        System.out.println("Set Screen Failed");
        return false;
         **/
        return true;
    }
    public boolean removeImage(FloorNumber floor){
        floors.remove(floor);
        return true;
    }

}
