package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor implements Cloneable {

    private int _id;
    private int _time;
    private Map<Node, Integer> _endTime = new HashMap<Node, Integer>();
    private List<Node> _tasks;

    public Processor(int coreNumber) {
        _id = coreNumber;
        _time = 0;
        _tasks = new ArrayList<Node>();
    }

    /**
     * format the string for writing to the output file
     * @param task
     * @return
     */
    public String writeString(Node task) {
        int weight = task.get_weight();
        return task.toString() + "\t[Weight=" + weight + ", Start="
                + (_endTime.get(task) - weight) + ", Processor=" + _id + "]";
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
        _time = endTime;
        _endTime.put(node, endTime);
    }

    /**
     * removes a task from the processor
     * @param node
     */
    public void removeTask(Node node) {
        _tasks.remove(node);
        if (_tasks.size() == 0) {
            _time = 0;
        } else {
            _time = _endTime.get(_tasks.get(_tasks.size() - 1));
        }
        _endTime.remove(node);
    }

    /**
     * returns the earliest time the processor becomes available
     * @param n
     * @return
     */
    public int getEnd(Node n) {
        Integer time = _endTime.get(n);
        if (time != null) {
            return time;
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

    /**
     * makes a deep copy of a Processor object
     *
     * @return deep copy of Processor
     * @throws CloneNotSupportedException
     */
    public Processor clone() throws CloneNotSupportedException {
        //create a shallow copy
        Processor p = (Processor) super.clone();

        //generate a list of the currently scheduled nodes
        List<Node> cloneTasks = new ArrayList<Node>();
        for (Node n : _tasks) {
            cloneTasks.add(n);
        }

        //set the cloned tasks into the new processor clone
        p._tasks = cloneTasks;

        //generate a new map for the end times
        Map<Node, Integer> cloneET = new HashMap<Node, Integer>();
        cloneET.putAll(_endTime);
        p._endTime = cloneET;
        return p;
    }


}
