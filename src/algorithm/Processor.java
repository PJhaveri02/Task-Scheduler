package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor implements Cloneable {

    //processor 'core' numbers
    private int _id;
    // internal timer of ending time of the 'latest' task
    private int _time;
    private Map<Node, Integer> _endTime = new HashMap<Node, Integer>();

    //list of tasks which will be executed
    private List<Node> _tasks;

    public Processor(int coreNumber) {
        _id = coreNumber;
        _time = 0;
        _tasks = new ArrayList<Node>();
    }

//[Weight = 2, Start= 0, Processor=1];
    public String writeString(Node task){
        int weight = task.get_weight();
        return task.toString() + "\t[Weight=" + weight +", Start="
                + (_endTime.get(task)-weight) +", Processor=" + _id + "]";
    }

    public String toString() {
        return Integer.toString(_id);
    }

    public List<Node> getTasks() {
        return _tasks;
    }

    /**
     * Adds the task to the processor and increment the internal timer of latest task as well.
     * Delegates the processor to the node as well.
     * NOTE: THIS METHOD WAS USED IN BAD ALGORITHM. USE THE NEXT METHOD TO SCHEDULE!
     *
     * @param node an available node ready to be executed
     */
    public void scheduleTask(Node node, int startTime) {
        _tasks.add(node);
        int endTime = startTime + node.get_weight();
//        System.out.println(endTime);
        _time = endTime;
        _endTime.put(node, endTime);
//        node.setProcessor(this);
        //node.setStart(startTime);
    }

    public int getEnd(Node n){
        Integer sad = _endTime.get(n);
        if(sad!=null){
            return sad;
        }
        return -1;
    }

    public void setTime(int time) {
        _time = time;
    }


    /**
     * returns the ending time of the last task scheduled under the processor
     *
     * @return
     */
    public int getTime() {
        return _time;
    }

    public void addTask(Node n) {
        _tasks.add(n);
    }


    //unsure about its purpose
    /**
     * Essentially a duplicate of the scheduleTask but takes into consideration of switching processors.
     * Follows the 'Precedence Constraint'
     * Adds the task to the processor and increment the internal timer of latest task.
     * Delegates the processor to the node as well.
     *
     * @param toAssignNode an available node ready to be assigned
     * @param prevNode     the previously assigned node
     */
//    public void scheduleTaskOnProcessor(Node toAssignNode, Node prevNode) {
//
//        // Set the initial start time to the internal processor time
//        int startTime = 0;
//
//        int startDepTime = prevNode.getStart();
//        int executionDepTime = prevNode.get_weight();
//        int communicationTime = toAssignNode.getDependentsAndWeight().get(prevNode);
//
//        // Set start time of the tasks. Follows the 'Precedence Constraint' here
//        if (prevNode.getProcessor().equals(this)) {
//            startTime = _time;
//        } else {
//            startTime = startDepTime + executionDepTime + communicationTime;
//        }
//
//        _tasks.add(toAssignNode);
//        _time += toAssignNode.get_weight();
//        toAssignNode.setProcessor(this);
//        toAssignNode.setStart(startTime);
//    }


    /**
     * makes a deep copy of a Processor object
     *
     * @return deep copy of Processor
     * @throws CloneNotSupportedException
     */
//    public Object clone() throws CloneNotSupportedException {
//        //create a shallow copy
//        Processor p = (Processor) super.clone();
//
//        //make a deep copy by creating new list of tasks from the old list
//        List<Node> cloneTasks = new ArrayList<Node>();
//        //make copy of each task, add it to the new list
//        for (Node task : this._tasks) {
//            //this method only sets weight, ID, and name fields for the Node object.
//            //it does NOT (yet) set the other fields in 'Node', such as _bottomWeight, _start, or _processor.
//            //need _start, _processor, _dependenciesAndWeight, etc for writing the cloned Processors in the output file
//            Node cloneTask = new Node(task.get_weight(), task.getId(), task.toString());
//            cloneTasks.add(cloneTask);
//
//            // set the tasks starting time and bottom level
//            cloneTask.setStart(task.getStart());
//            cloneTask.setBottomLevel(task.getBottomLevel());
//        }
//
//        // Once the task objects are cloned, set the _dependenciesAndWeight field.
//        // Can only be done once all the tasks are instantiated.
//        //needs review
////        for (Node task : _tasks) {
////            // Get the cloned task corresponding to the task
////            Node cloneTask = cloneTasks.get(_tasks.indexOf(task));
////
////            Map<Node, Integer> dependentsAndWeight = task.getDependentsAndWeight();
////            for (Node depTask : dependentsAndWeight.keySet()) {
////                Node depClonedTask = cloneTasks.get(cloneTasks.indexOf(depTask));
////                cloneTask.addDependency(depClonedTask, dependentsAndWeight.get(depTask));
////            }
////        }
//
//        //set the cloned task list in the cloned Processor
//        p._tasks = cloneTasks;
//        return p;
//    }


}
