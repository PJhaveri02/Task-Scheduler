package algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model<map> {
    private int _nodeCount;
    private Exception _broken;
    private List<Node> _nodes;
    private Map<String, Node> _names;
    private String _name;

    public Model(String name){
        _name = name;
        _nodes = new ArrayList<Node>();
        _names = new HashMap<String, Node>();
    }


    //adds a node to the model
    public void addNode(String des) {
        String[] parts = des.split("\t");
        String name = parts[1];
        int weight = Integer.parseInt(parts[2].replaceAll("\\D+",""));
        Node node = new Node(weight, _nodeCount , name);
        _nodes.add(node);
        _names.put(name,node);
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
        _names.get(dependentNode).addDependency(_names.get(requiredNode),weight);
    }

    //returns a list of strings containing a description of the model in .dot format
    public List<String> asText(){
        //SOMEONE AGREE OR DISAGREE THAT TABS SHOULD BE ADDED HERE AND DISCUSS
        List<String> text = new ArrayList<String>();
        text.add("digraph \"" + _name + "\" {");
        for(Node node: _nodes){
            text.add("\t" + node.toString());
            int x = node.numDependecies();
            if(node.hasDependency()){
                for(String string : node.dependenciesToString()){
                    text.add("\t" + string);
                }
            }
        }
        text.add("}");
        return text;
    }

    //prints description in .dot format to console
    public void print(){
        List<String> lines = asText();
        for(String line: lines){
            System.out.println(line);
        }
    }

    public void setErr(IOException e) {
        _broken = e;
    }
}
