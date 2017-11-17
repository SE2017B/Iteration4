package map;

public class Edge {

    private Node nodeOne;
    private Node nodeTwo;
    private String ID;

    public Edge(String ID, Node nodeOne, Node nodeTwo){
        this.ID = ID;
        this.nodeOne = nodeOne;
        this.nodeTwo = nodeTwo;
    }

    public String getID(){
        return ID;
    }
}
