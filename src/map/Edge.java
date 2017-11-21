package map;
import exceptions.InvalidNodeException;

public class Edge {
    private Node nodeOne;
    private Node nodeTwo;
    private String ID;
    private double cost;
    private String dirOne;
    private String dirTwo;

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
        this.ID = nodeOne.getID() + ":" + nodeTwo.getID();
        setUp();
    }

    private void setUp(){
        this.cost = this.nodeOne.getEuclidianDistance(this.nodeTwo);
        this.nodeOne.addConnection(this);
        this.nodeTwo.addConnection(this);
    }

    //Getters
    public String getID(){
        return ID;
    }
    public double getCost() {
        return cost;
    }
    public Node getNodeOne(){
        return this.nodeOne;
    }
    public Node getNodeTwo(){
        return this.nodeTwo;
    }

    public Node getOtherNode(Node node) {
        if(node.equals(nodeOne)){
            return nodeTwo;
        }else if(node.equals(nodeTwo)){
            return nodeOne;
        }else{
            return null;
        }
    }

    public String getDir(Node node) throws InvalidNodeException {
        if(node.equals(nodeOne)){
            return dirOne;
        }else if(node.equals(nodeTwo)){
            return dirTwo;
        }else{
            throw new InvalidNodeException("this node is not part of this edge");
        }
    }

    public void setDirOne(String dirOne) {
        this.dirOne = dirOne;
    }

    public void setDirTwo(String dirTwo) {
        this.dirTwo = dirTwo;
    }

    public void replaceNode(Node oldNode,Node newNode) throws InvalidNodeException {
        if(oldNode.equals(nodeOne)){
            nodeOne = newNode;
            nodeOne.removeConnection(this);
        }else if(oldNode.equals(nodeTwo)){
            nodeTwo = newNode;
            nodeTwo.removeConnection(this);
        }else{
            throw new InvalidNodeException("this node is not part of this edge");
        }

        setUp();
    }

    public void deleteConnection() {
        nodeOne.removeConnection(this);
        nodeTwo.removeConnection(this);
    }

}
