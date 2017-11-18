package map;

public class Edge {
    private String ID;
    private Node StartNode;
    private Node EndNode;

    public Edge(String id, Node a, Node b){
        this.ID = id;
        this.StartNode=a;
        this.EndNode=b;
    }

    //some methods
    public String toString(){
        return this.ID;
    }

    public String getId(){
        return this.ID;
    }
    public Node getStartNode(){
        return this.StartNode;
    }
    public Node getEndNode(){
        return this.EndNode;
    }
    //get a different node from the one given to it
    public Node getOtherNode(Node n){
        if(StartNode==n){
            return EndNode;
        }
        return StartNode;
    }
    public int hashcode(){
        return 0; //for now
    }
    public boolean equals(Edge e){
        if(e==this){
            return true;
        }
        return false;
    }

}
