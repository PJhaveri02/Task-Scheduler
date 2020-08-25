package algorithm;

import java.util.*;

/**
 * class represents a task in the schedule i.e. a node in the digraph
 * can be renamed
 * contains edge information of edges where it is the dependent
 */
public class Node implements Comparable<Node>{

    private int _weight;
    private final int _id;
    private String _name;

    private int _bottomWeight;
//    private int _start;
    private int _level;
    private Processor _processor;
    private Map<Node,Integer> _dependentsAndWeight;

    // children of the current node
    private Map<Node, Integer> _childrenNodes;

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
        _childrenNodes = new HashMap<Node, Integer>();
    }

    @Override
    public String toString(){
        return _name;
    }

    public int get_weight() {
        return _weight;
    }

    public int getId() {
        return _id;
    }

    //start field appears to be removed
//    public int getStart() {
//        return _start;
//    }
//    public void setStart(int time) { _start = time; }

    //changed from return type processor to string
//    public String get_processor() {
//        return _processor.toString();
//    }

//    public void setProcessor(Processor processor) {
//        this._processor = processor;
//    }

    //TODO
    public ArrayList<Boolean>CheckDependencies() {
        //incomplete
        return null;
    }

//    public Processor getProcessor() {
//        return _processor;
//    }

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

    public void addChildNodes(Node requiredNode, int weight) { _childrenNodes.put(requiredNode, weight); }

    public Map<Node, Integer> getDependentsAndWeight() {
        return _dependentsAndWeight;
    }

    //returns number of dependencies
    //feels kind of bad compared to hasDependency
    public int numDependecies() {
        return _dependentsAndWeight.size();
    }

    //returns node description in .dot format

    // needs to return start and processor number
//    public String toDescription(){
////        return _name + "   [Weight = " + _weight + ", Start= " + _start +", Processor="+ this.get_processor()+"];";
//        return _name + "   [Weight = " + _weight + ", Start= " + _start +", Processor="+_processor.toString()+"];";
//    }

    /**
     *
     * @return description of dependencies in .dot format
     */
    public List<String> dependenciesToString(){
        ArrayList<String> strings = new ArrayList<String>();
         _dependentsAndWeight.forEach((k,v) -> strings.add(k.toString() + " -> " + _name + "   [Weight = " + v + "];"));
         return strings;
    }

    /**
     * Obtain an arraylist of all the dependent nodes of the current node.
     * @return an arraylist of nodes
     */
    public ArrayList<Node> getDependencies() {
        return new ArrayList<Node>(_dependentsAndWeight.keySet());
    }

    /**
     * Obtain arraylist of all the children nodes of the current node.
     * @return an arraylist of nodes
     */
    public ArrayList<Node> getChildren() {return new ArrayList<Node>(_childrenNodes.keySet());}

    /**
     * Set the bottom weight of this particular node
     * @param bottomWeight
     */
    public void setBottomLevel(int bottomWeight) {
        _bottomWeight = bottomWeight;
    }

    /**
     * Get the bottom weight of this particular node
     * @return _bottomWeight
     */
    public int getBottomLevel() {
        return _bottomWeight;
    }

    /**
     * A compare to method that sorts the Nodes/Tasks based on their bottom level
     * @param n A Node
     * @return
     */
    public int compareTo(Node n) {
        return n._bottomWeight - this._bottomWeight;
    }

    public int getEdgeWeight(Node n){
        return _dependentsAndWeight.get(n);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            if (this.getId() == node.getId()) {
                return true;
            }
        }
        return false;
    }

    public int getLevel(){
        return _level;
    }

    public void setLevel(int level){
        _level = level;
    }
}
