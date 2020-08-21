package algorithm;

import java.util.*;

/**
 * class represents a task in the schedule i.e. a node in the digraph
 * can be renamed
 * contains edge information of edges where it is the dependent
 */
public class Node {

    private int _weight;
    //uncommented again as some method used it
    private int _start;
    private int _level;
    private Processor _processor;
    private int _id;
    private String _name;
    private Map<Node,Integer> _dependentsAndWeight;
    //private Map<Node,Integer> _incompleteDependents;

    /**
     * Constructor of the Task. Map is used to connect the cost of jumping to another processor
     * @param weight
     * @param id
     * @param name
     */
    public Node(int weight, int id, String name){
        _weight = weight;
        _id = id;
        _name = name;
        //using hashmap for now, if we need insertion order iteration, linkedHashMap is the better choice
        _dependentsAndWeight = new HashMap<Node, Integer>();
    }


    public String getName(){
        return _name;
    }

    public int get_weight() {
        return _weight;
    }

    //might be the other check
//    public Processor get_processor() {
//        return _processor;
//    }
    //start field appears to be removed
    public int getStart() {
        return _start;
    }

    public void setStart(int time) { _start = time; }

    //changed from return type processor to string
    public String get_processor() {
        return _processor.toString();
    }

    public void setProcessor(Processor processor) {
        this._processor = processor;
    }

    public ArrayList<Boolean>CheckDependencies(){
        //incomplete
        return null;
    }


    //maybe redundant
    public boolean hasDependency(){
        if(_dependentsAndWeight.isEmpty()){
            return false;
        }
        return true;
    }

    public void addDependency(Node requiredNode, int weight) {
        _dependentsAndWeight.put(requiredNode, weight);
    }

    //returns number of dependencies
    //feels kind of bad compared to hasDependency
    public int numDependecies() {
        return _dependentsAndWeight.size();
    }

    //returns node description in .dot format
    // needs to return start and processor number
    @Override
    public String toString(){
//        return _name + "   [Weight = " + _weight + ", Start= " + _start +", Processor="+ this.get_processor()+"];";
        return _name + "   [Weight = " + _weight + ", Start= " + _start +", Processor="+_processor.toString()+"];";

    }

    /**
     *
     * @return description of dependencies in .dot format
     */
    public List<String> dependenciesToString(){
        ArrayList<String> strings = new ArrayList<String>();
         _dependentsAndWeight.forEach((k,v) -> strings.add(k.getName() + " -> " + _name + "   [Weight = " + v + "];"));
         return strings;
    }

    /**
     * Obtain an arraylist of all the dependent nodes of the current node.
     * @return an arraylist of nodes
     */
    public ArrayList<Node> getDependencies() {
        return new ArrayList<Node>(_dependentsAndWeight.keySet());
    }

    public int getLevel(){
        return _level;
    }
}
