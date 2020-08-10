package algorithm;

import java.util.ArrayList;
import java.util.List;

public class Processor {

    //processor 'core' numbers
    private int _id;

    // internal timer of 'latest' task
    private int _time;

    //list of tasks which will be executed
    private List<Node> _tasks;

    public Processor(int coreNumber) {
        _id = coreNumber;
        _time = 0;
        _tasks = new ArrayList<Node>();
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
}
