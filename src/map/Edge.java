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
