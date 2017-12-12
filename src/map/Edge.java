package map;

import exceptions.InvalidNodeException;

public class Edge{
    private Node nodeOne;
    private Node nodeTwo;
    private String ID;
    private double cost;
    private String dirOne;
    private String dirTwo;

    //Constructors
    public Edge(String ID, Node nodeOne, Node nodeTwo){
        this.ID = ID;
        this.nodeOne = nodeOne;
        this.nodeTwo = nodeTwo;
        this.dirOne = "";
        this.dirTwo = "";
        setUp();
    }

    public Edge(Node nodeOne, Node nodeTwo) {
        this.nodeOne = nodeOne;
        this.nodeTwo = nodeTwo;
        this.dirOne = "";
        this.dirTwo = "";
        this.ID = nodeOne.getID() + "_ " + nodeTwo.getID();
        setUp();
    }

    private void setUp(){
        this.cost = this.nodeOne.getEuclidianDistance(this.nodeTwo);
        this.nodeOne.addConnection(this);
        this.nodeTwo.addConnection(this);
    }

    public void replaceNode(Node oldNode, Node newNode) throws InvalidNodeException {
        if(oldNode.equals(nodeOne)){
            nodeOne.removeConnection(this);
            nodeOne = newNode;
        }else if(oldNode.equals(nodeTwo)){
            nodeTwo.removeConnection(this);
            nodeTwo = newNode;
        }else{
            throw new InvalidNodeException("this node is not part of this edge");
        }
        setUp();
    }

    public void deleteConnection() {
        nodeOne.removeConnection(this);
        nodeTwo.removeConnection(this);
    }

    //Getters
    public Node getNodeOne(){
        return this.nodeOne;
    }
    public Node getNodeTwo(){
        return this.nodeTwo;
    }
    public String getID(){
        return ID;
    }
    public double getCost() {
        return cost;
    }

    public Node getOtherNode(Node node){
        if(node.equals(nodeOne)){
            return nodeTwo;
        }else if(node.equals(nodeTwo)){
            return nodeOne;
        }else{
            System.out.println("This node is not part of this edge");
            return null;
        }
    }

    public String getDir(Node node) throws InvalidNodeException{
        if(node.equals(nodeOne)){
            return dirOne;
        }else if(node.equals(nodeTwo)){
            return dirTwo;
        }else{
            throw new InvalidNodeException("this node is not part of this edge");
        }
    }

    //Setters
    public void setDirOne(String dirOne) {
        this.dirOne = dirOne;
    }
    public void setDirTwo(String dirTwo) {
        this.dirTwo = dirTwo;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null) return false;
        if(!(obj instanceof Edge)) return false;
        Edge other = (Edge)obj;
        if(!this.getID().equals(other.getID())) return false;
        return true;
    }

    @Override
    public String toString(){
        return this.ID;
    }
}
