package algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model<map> {
    private int _nodeCount;
    private Exception _broken;
    private List<Node> nodes;
    private Map<String, Node> names;

    public Model(){
        nodes = new ArrayList<Node>();
        names = new HashMap<String, Node>();
    }


    //adds a node to the model
    public void addNode(String des) {
        String[] parts = des.split("\t");
        String name = parts[1];
        int weight = Integer.parseInt(parts[2].replaceAll("\\D+",""));
        Node node = new Node(weight, _nodeCount , name);
        nodes.add(node);
        names.put(name,node);
        _nodeCount++;
    }

    //adds a dependency to a model
    public void addDependency(String des){
        //separates weight from nodes
        String[] parts = des.split("\t");
        //separates node names
        String[] nodes = parts[1].replaceAll(" ","").split("->");
        String dependentNode = nodes[1];
        String requiredNode = nodes[0];
        //formats weight
        int weight = Integer.parseInt(parts[2].replaceAll("\\D+",""));
        names.get(dependentNode).addDependency(names.get(requiredNode),weight);
    }


    public void print(){
        for(Node node: nodes){
            System.out.println(node.toString());
            int x = node.numDependecies();
            if(node.hasDependency()){
                for(String string : node.dependenciesToString()){
                    System.out.println(string);
                }
            }
        }

    }

    public void setErr(IOException e) {
        _broken = e;
    }
}
