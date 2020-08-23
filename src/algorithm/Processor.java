package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Processor implements Cloneable {

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
    public void scheduleTask(Node node) {
        _tasks.add(node);
        _time += node.get_weight();
        node.setProcessor(this);
    }

    /**
     * Essentially a duplicate of the scheduleTask but takes into consideration of switching processors.
     * Follows the 'Precedence Constraint'
     * Adds the task to the processor and increment the internal timer of latest task.
     * Delegates the processor to the node as well.
     *
     * @param toAssignNode an available node ready to be assigned
     * @param prevNode the previously assigned node
     */
    public void scheduleTaskOnProcessor(Node toAssignNode, Node prevNode) {

        // Set the initial start time to the internal processor time
        int startTime = 0;

        int startDepTime = prevNode.getStart();
        int executionDepTime = prevNode.get_weight();
        int communicationTime = toAssignNode.getDependentsAndWeight().get(prevNode);

        // Set start time of the tasks. Follows the 'Precedence Constraint' here
        if (prevNode.getProcessor().equals(this)) {
            startTime = _time;
        } else {
            startTime = startDepTime + executionDepTime + communicationTime;
        }

        _tasks.add(toAssignNode);
        _time += toAssignNode.get_weight();
        toAssignNode.setProcessor(this);
        toAssignNode.setStart(startTime);
    }


    /**
     * returns the ending time of the last task scheduled under the processor
     *
     * @return
     */
    public int getTime() {
        return _time;
    }

}
