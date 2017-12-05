package ui;

import map.Node;

import java.util.ArrayList;

/**
 * Class to help provide alternative node options during text search
 */
public class NodeSearcher {
    private ArrayList<Node> nodes;
    private String searchby; //Variable to determine the parameter to be used to search the nodes
    public NodeSearcher(ArrayList<Node> Nodes){
        this.nodes= Nodes;
        searchby="NodeID";
    }

    /**
     *
     * @param text
     * @return nodes the contain such text in their parameters
     */
    public ArrayList<Node> Search(String text){
        ArrayList<Node> ans = new ArrayList<Node>();
        //Naive implementation
        for(Node n : this.nodes){
            if(check(n,text)){
                ans.add(n);
            }
        }
        return ans;
    }

    private boolean contains(String text,String name){
        return name.contains(text);
    }
    private boolean check(Node n, String text){
        if(searchby=="NodeID"){
            if(n.getID().contains(text)){
                return true;
            }
        }
        return false;
    }
}
