package ui;

import com.sun.org.apache.regexp.internal.RE;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import map.FloorNumber;

import java.util.HashMap;

public class RealImagePane extends StackPane {
    private static RealImagePane instance = new RealImagePane();
    //pointers to images and their fxml files
    private String floorL1 = "images/00_thelowerlevel1.png";
    private String floorL2 = "images/00_thelowerlevel2.png";
    private String floorG = "images/00_thegroundfloor.png";
    private String floor1 = "images/01_thefirstfloor.png";
    private String floor2 = "images/02_thesecondfloor.png";
    private String floor3 = "images/03_thethirdfloor.png";

    private HashMap<FloorNumber,Image> floors = new HashMap<>();
    private double scale = 0.5;

    private RealImagePane(){
        super();
        System.out.println("Loading map images");
        addImage(FloorNumber.fromDbMapping("L2"));
        addImage(FloorNumber.fromDbMapping("L1"));
        addImage(FloorNumber.fromDbMapping("G"));
        addImage(FloorNumber.fromDbMapping("1"));
        addImage(FloorNumber.fromDbMapping("2"));
        addImage(FloorNumber.fromDbMapping("3"));
    }
    public static RealImagePane getInstance(){
        return instance;
    }
    public boolean addImage(FloorNumber floor){
        //create new image view
        if(floor!=null){
            String name = this.getImage(floor);//get image name for floor
            Image img = new Image(name); //create new image
//            ImageView imgView = new ImageView(img); //create new image view pane;
//            imgView.setVisible(true);
            floors.put(floor,img);//add new image view to hash map floors
            return true;
        }
        return false;
    }
    private String getImage(FloorNumber floor){
        //return corresponding image type
        if(floor.getDbMapping().equals("L2")){
            return "images/00_thelowerlevel2.png";
        }
        if(floor.getDbMapping().equals("L1")){
            return "images/00_thelowerlevel1.png";
        }
        if(floor.getDbMapping().equals("G")){
            return "images/00_thegroundfloor.png";
        }
        if(floor.getDbMapping().equals("1")){
            return "images/01_thefirstfloor.png";
        }
        if(floor.getDbMapping().equals("2")){
            return "images/02_thesecondfloor.png";
        }
        if(floor.getDbMapping().equals("3")){
            return "images/03_thethirdfloor.png";
        }
        return null;
    }

    public HashMap<FloorNumber, Image> getFloors() {
        return floors;
    }
}
