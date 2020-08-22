package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Processor implements Cloneable{

    //processor 'core' numbers
    private int _id;

    // internal timer of ending time of the 'latest' task
    private int _time;

    //list of tasks which will be executed
    private List<Node> _tasks;

    public Processor(int coreNumber) {
        _id = coreNumber;
        _time = 0;
        _tasks = new ArrayList<Node>();
    }

    public String toString(){
        return Integer.toString(_id);
    }

    public List<Node> getTasks() {
        return _tasks;
    }

    /**
     * Adds the task to the processor and increment the internal timer of latest task as well.
     * Delegates the processor to the node as well.
     * @param node an available node ready to be executed
     */
    public void scheduleTask(Node node) {
        _tasks.add(node);
        _time += node.get_weight();
        node.setProcessor(this);
    }

    /**
     * returns the ending time of the last task scheduled under the processor
     * @return
     */
    public int getTime(){ return _time; }

    /**
     * makes a deep copy of a Processor object
     * @return deep copy of Processor
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException{
        //create a shallow copy
        Processor p = (Processor)super.clone();

        //make a deep copy by creating new list of tasks from the old list
        List<Node> cloneTasks = new ArrayList<Node>();
        //make copy of each task, add it to the new list
        for (Node task : this._tasks){
            //this method only sets weight, ID, and name fields for the Node object.
            //it does NOT (yet) set the other fields in 'Node', such as _bottomWeight, _start, or _processor.
            //need _start, _processor, _dependenciesAndWeight, etc for writing the cloned Processors in the output file
            Node cloneTask = new Node(task.get_weight(), task.getId(), task.getName());
            cloneTasks.add(cloneTask);

            // set the tasks starting time and bottom level
            cloneTask.setStart(task.getStart());
            cloneTask.setBottomLevel(task.getBottomLevel());
        }

        // Once the task objects are cloned, set the _dependenciesAndWeight field.
        // Can only be done once all the tasks are instantiated.
        for (Node task : _tasks) {
            // Get the cloned task corresponding to the task
            Node cloneTask = cloneTasks.get(cloneTasks.indexOf(task));

            Map<Node, Integer> dependentsAndWeight = task.getDependentsAndWeight();
            for (Node depTask : dependentsAndWeight.keySet()) {
                Node depClonedTask = cloneTasks.get(cloneTasks.indexOf(depTask));
                cloneTask.addDependency(depClonedTask, dependentsAndWeight.get(depTask));
            }
        }

        //set the cloned task list in the cloned Processor
        p._tasks = cloneTasks;
        return p;
    }


}
