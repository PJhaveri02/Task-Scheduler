package algorithm;

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
    private List<Node> _nodes;
    private Map<String, Node> _names;
    private String _name;
    private List<List<Node>> _levels = new ArrayList<List<Node>>();

    /**
     * constructor for the class
     * @param name
     */
    public Model(String name){
        _name = name;
        _nodes = new ArrayList<Node>();
        _names = new HashMap<String, Node>();
    }

    public List<List<Node>> getLevels(){
        return _levels;
    }

    /**
     * Adds a node to the model
     * @param line
     */
    public void addNode(String line) {
        String[] parts = line.split("\t");
        String name = parts[1];
        int weight = Integer.parseInt(parts[2].replaceAll("\\D+",""));
        Node node = new Node(weight, _nodeCount , name);
        _nodes.add(node);
        _names.put(name,node);
        _nodeCount++;
    }

    /**
     * Add a dependency to the model
     * @param edge
     */
    public void addDependency(String edge){
        //separates weight from nodes
        String[] parts = edge.split("\t");
        //separates node names
        String[] nodes = parts[1].replaceAll(" ","").split("->");
        String dependentNode = nodes[1];
        String requiredNode = nodes[0];
        //formats weight
        int weight = Integer.parseInt(parts[2].replaceAll("\\D+",""));
        _names.get(dependentNode).addDependency(_names.get(requiredNode),weight);
    }

    /**
     * Add a child node to a node
     * @param child
     */
    public void addChildNode(String child) {
        //separates weight from nodes
        String[] parts = child.split("\t");
        //separates node names
        String[] nodes = parts[1].replaceAll(" ","").split("->");
        String childNode = nodes[1];
        String parentNode = nodes[0];
        //formats weight
        int weight = Integer.parseInt(parts[2].replaceAll("\\D+",""));
        _names.get(parentNode).addChildNodes(_names.get(childNode), weight);

    }

    /**
     * returns a list of strings containing a description of the model in .dot format
     * @return
     */
    public List<String> asText(){
        List<String> text = new ArrayList<String>();
        text.add("digraph \"" + _name + "\" {");
        for(Node node: _nodes){
            text.add("\t" + node.toString());
            int x = node.numDependencies();
            if(node.hasDependency()){
                for(String string : node.dependenciesToString()){
                    text.add("\t" + string);
                }
            }
        }
        text.add("}");
        return text;
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

        while(!_avaliable.isEmpty() || !_unavaliable.isEmpty() ){
            List<Node> newLevel = new ArrayList<Node>();
            for (Node n : _avaliable){
                newLevel.add(n);
            }

            _avaliable.clear();
            List<Node> availableNodes = checkAvailability(_unavaliable);
            _avaliable.addAll(availableNodes);
            _unavaliable.removeAll(availableNodes);
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
