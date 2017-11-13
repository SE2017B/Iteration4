package a_star;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class HospitalMap{
    private Dictionary<String, Node> map;
    private List<Node> frontier;
    private List<Node> explored;

    public HospitalMap(){}

    public void addNode(String id, Node node){
        map.put(id, node);
    }

    public ArrayList<Node> findPath(Node start, Node end){
        //TODO
        //Also determine heuristic..should be manhattan but we can also use euclydian...up to the team
        return getNodesAsArrayList();

    }

    public void setDefault(Node defaultNode){
        //TODO
        //also ask what this does. Default = kiosk location?
    }

    public int getDistance(Node start, Node end){
        //Gonna assume this is euclydian
        double xDeltaSquared = Math.pow((end.getX()-start.getX()), 2);
        double yDeltaSquared = Math.pow((end.getY()-start.getY()), 2);
        double distance = Math.sqrt(xDeltaSquared + yDeltaSquared);
        return (int)Math.round(distance);
    }
    public List<Node> readCSV(String FileName ){
        ArrayList<Node> Ans = new ArrayList<>();
        Path pathToFile = Paths.get(FileName);
        //loop through file
        try(BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)){
            // read the first line
            String line= br.readLine();
            //loop until all lines are read
            while(line!=null){
                //array of attributes
                String[] attributes = line.split(",");
                Ans.add(createCSVNode(attributes));
            }

        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
        //now return value
        return Ans;

    }
    public Node createCSVNode(String[] a){
        //Todo: Add content
        return new Node();
    }

    public ArrayList<Node> getNodesAsArrayList(){
        //STUB
        ArrayList<Node> stub = new ArrayList<Node>();
        Node a = new Node(400,200);
        a.setName("a");
        stub.add(a);
        Node b = new Node(600,200);
        b.setName("b");
        stub.add(b);
        Node c = new Node(600,400);
        c.setName("c");
        stub.add(c);
        Node d = new Node(400,400);
        d.setName("d");
        stub.add(d);
        return stub;

    }
}