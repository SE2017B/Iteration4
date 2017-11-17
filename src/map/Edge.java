package map;

public class Edge {

    private Node nodeOne;
    private Node nodeTwo;
    private String ID;
    private double cost;

    public Edge(String ID, Node nodeOne, Node nodeTwo){
        this.ID = ID;
        this.nodeOne = nodeOne;
        this.nodeTwo = nodeTwo;
        cost = this.nodeOne.getEuclidianDistance(this.nodeTwo);
        this.nodeOne.addConnection(this);
        this.nodeTwo.addConnection(this);
    }

    public String getID(){
        return ID;
    }

    public double getCost() {
        return cost;
    }


}
