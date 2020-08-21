package algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class represents programs internal model of a .dot file in terms of nodes and dependencies(as edges)
 *
 */
public class Model {
    private int _nodeCount;
    private Exception _broken;
    private List<Node> _nodes;
    private Map<String, Node> _names;
    private String _name;
    private List<List<Node>> _levels = new ArrayList<List<Node>>();



    public Model(String name){
        _name = name;
        _nodes = new ArrayList<Node>();
        _names = new HashMap<String, Node>();
    }


    public List<List<Node>> getLevels(){
        return _levels;
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
            text.add("\t" + node.toDescription());
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

    /**
     * Getter method to return a list of all nodes
     * @return a List of nodes
     */
    public List<Node> getNodes() {
        return _nodes;
    }

    public void addLevels(){
        List<Node> _avaliable = new ArrayList<Node>();
        List<Node> _unavaliable = new ArrayList<Node>();

        // get root level nodes
        for(Node n: _nodes){
            if (!n.hasDependency()){
                _avaliable.add(n);
            } else {
                _unavaliable.add(n);
            }
        }

        int level = 0;
        while(!_avaliable.isEmpty() || !_unavaliable.isEmpty() ){
//            for (Node n : _avaliable){
//                n.setLevel(level);
//            }
            List<Node> newLevel = new ArrayList<Node>();
            for (Node n : _avaliable){
                newLevel.add(n);
            }

            _avaliable.clear();
            List<Node> availableNodes = checkAvailability(_unavaliable);
            _avaliable.addAll(availableNodes);
            _unavaliable.removeAll(availableNodes);
            level++;
            _levels.add(newLevel);
        }
//        System.out.println("check");
    }

    private List<Node> checkAvailability(List<Node> _unavaliable) {
        List<Node> availableTasks = new ArrayList<Node>();
        for (Node n : _unavaliable) {
            List<Node> dependentNodes = n.getDependencies();
            boolean avaliable = true;
            for (Node dependentNode: dependentNodes){
                if (_unavaliable.contains(dependentNode)){
                    avaliable = false;
                }
            }
            if (avaliable){
                availableTasks.add(n);
            }
        }
        return availableTasks;
    }
}
