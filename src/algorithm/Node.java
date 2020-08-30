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
    private int _level;
    private Map<Node,Integer> _dependentsAndWeight;
    private Map<Node, Integer> _childNodes;

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
        _dependentsAndWeight = new HashMap<Node, Integer>();
        _childNodes = new HashMap<Node, Integer>();
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

    /**
     * checks if the task has any dependencies
     * @return
     */
    public boolean hasDependency(){
        if(_dependentsAndWeight.isEmpty()){
            return false;
        }
        return true;
    }

    public void addDependency(Node requiredNode, int weight) {
        _dependentsAndWeight.put(requiredNode, weight);
    }

    public void addChildNodes(Node requiredNode, int weight) { _childNodes.put(requiredNode, weight); }

    public Map<Node, Integer> getDependentsAndWeight() {
        return _dependentsAndWeight;
    }

    public int numDependencies() {
        return _dependentsAndWeight.size();
    }

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
    public ArrayList<Node> getChildren() {return new ArrayList<Node>(_childNodes.keySet());}

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

}
