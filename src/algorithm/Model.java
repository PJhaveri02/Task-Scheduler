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
public class Model{
    enum Style{
        FAST,
        SLOW,
        STEP
    }
    private int _nodeCount;

    //one of these gotta go --------------------------------------!!!!!!
    private List<Node> _nodes;
//    private Map<String, Node> _names;


    private List<Node> _available;
    private String _name;
    private Style _style;


    public Model(String name){
        _name = name;
        _nodes = new ArrayList<Node>();
        _available = new ArrayList<Node>();
//        _names = new HashMap<String, Node>();
        _style = Style.FAST;
    }


    //adds a node to the model
    public void addNode(String des) {
        String[] parts = des.split("\t");
        String name = parts[1];
        int weight = Integer.parseInt(parts[2].replaceAll("\\D+",""));
        Node node = new Node(weight, _nodeCount , name);
        _nodes.add(node);
//        _names.put(name,node);
        //node count currently serves as ID, may not need
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
//        _names.get(dependentNode).addDependency(_names.get(requiredNode),weight);
    }

    //returns a list of strings containing a description of the model in .dot format
    // should probably be made to return data from reading processor
    public List<String> asText(){
        //SOMEONE AGREE OR DISAGREE THAT TABS SHOULD BE ADDED HERE AND DISCUSS
        //indenting is added here as it feels more appropriate compared to inside the nodes/dependices toStrings()
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

    // add processor generator


    //prints description in .dot format to console
    public void print(){
        List<String> lines = asText();
        for(String line: lines){
            System.out.println(line);
        }
    }

    //one of names or nodes needs to go
    public void makeAvailable(Node task){
//        _names.remove(task.getName(), task);
        _nodes.remove(task);
        _available.add(task);
    }

    //checks nodes for dependencies and moves them to available list if none are found
    //needs rename, should only be called once
    public void iterateAvailable(){
        List<Node> nodes = new ArrayList<Node>();
        for(Node task : _nodes){
            if (!task.hasDependency()){
               nodes.add(task);
            }
        }
        for(Node node : nodes) {
            makeAvailable(node);
        }
    }

    //checks nodes for dependencies on a task and removes them
    //iterate available built in
    public void done(Node finishedtask){
        for(Node task : _names.values()){
            if (task.rmDependency(finishedtask)){
                makeAvailable(task);
            }
        }
    }

    public boolean hasAvailableTask(){
        return !_available.isEmpty();
    }

    //selects a random available task and starts it
    public Node start(){
        Node task = _available.get(0);
        _available.remove(task);
        return task;
    }

    private void visualise(){
    switch(_style){
        case FAST:

            break;
        case SLOW:

            break;
        case STEP:

            break;
    }

    }
}

