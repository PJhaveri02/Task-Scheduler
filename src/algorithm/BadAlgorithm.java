package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BadAlgorithm implements algorithm {

    private List<Processor> _processors= new ArrayList<Processor>();
    private List<Node> _tasks;
    private List<Node> _available;

    /**
     * Constructor to pass in the Processors made and tasks from the DOT file.
     *
     * @param processors
     * @param tasks
     */
    public BadAlgorithm(int processors, List<Node> tasks) {
        _tasks = tasks;
        _processors.add( new Processor(0)) ;
        _available = new ArrayList<Node>();
    }

    /**
     * This function iterates through each task/node to calculate the bottom level
     * for each task/node
     */
    public void nodeBottomLevel() {
        for (Node task : _tasks) {
            // calculating the bottom level of this task

            if (task.getChildren().size() > 0) {
                int bottomLevel = calculateBottomLevel(task);
            } else {
                task.setBottomLevel(task.get_weight());
            }
        }
    }

    /**
     * Calculating the bottom level for a specific node/task using recussion
     *
     * @param task
     * @return
     */
    public int calculateBottomLevel(Node task) {
        if (task.getChildren().size() > 0) {
            int maxChildLevel = 0;
            for (Node childNode : task.getChildren()) {
                maxChildLevel = Math.max(calculateBottomLevel(childNode), maxChildLevel);
            }
            task.setBottomLevel(task.get_weight() + maxChildLevel);
        } else {
            task.setBottomLevel(task.get_weight());
        }
        return task.getBottomLevel();
    }

    public void addTask(Processor p, Node node) {
        int current = 0;
        for (Node n : node.getDependencies()) {
            if (!p.getTasks().contains(n)) {
                for (Processor killmeplease : _processors) {
                    //minor major jank (should work tho)
                    int end = killmeplease.getEnd(n) + node.getEdgeWeight(n);
                    if (end > current) {
                        current = end;
                    }
                }
            }
        }
        p.scheduleTask(node, current);
    }

    /**
     * execute method places all the tasks onto one processor in a valid format
     *
     * @return ArrayList<Processor>
     */
    @Override
    public List<Processor> execute() {

        this.nodeBottomLevel();
        Collections.sort(_tasks);
        //printing bottom level : success
//        for (Node task : _tasks) {
//            System.out.println(task.toString() + ": " + task.getBottomLevel());
//        }

        Processor processor = _processors.get(0);
//        Node rootNode = findRootNode(_tasks);
//        processor.scheduleTask(rootNode, rootNode.getStart());
//        _tasks.remove(rootNode);

        // check if tasks is not empty
        while (!_tasks.isEmpty() || !_available.isEmpty()) {
            if (!_available.isEmpty()) {
                // Place all the tasks in the processor
                for (Node node : _available) {
                    // add on processor
                    addTask(processor, node);
                    // add finishing time on processor map
                    //update processor 'start time'


//                    node.setStart(processor.getTime());
//                    processor.scheduleTask(node, node.getStart());
                }
                _available.clear();
            } else {
                // Obtain a list of available nodes
                List<Node> availableNodes = checkAvailability(_tasks);
                _available.addAll(availableNodes);
                _tasks.removeAll(availableNodes);
            }
        }
        return _processors;
    }


    /**
     * Method finds the root node of the graph. This is done by finding the node with no dependencies.
     * Note: This is assuming the graph only has one root node!
     *
     * @param tasksList a complete list of nodes in the graph
     * @return
     */
    private Node findRootNode(List<Node> tasksList) {
        for (Node node : tasksList) {
            if (!node.hasDependency()) {
                return node;
            }
        }
        return null;
    }

    /**
     * Method checks if the node's dependencies have been added to a processor.
     * All nodes with no dependencies gets returned.
     *
     * @param tasksList a list of nodes that has not been assigned to a processor yet.
     * @return A list of nodes that can be assigned (all dependencies executed).
     */
    private List<Node> checkAvailability(List<Node> tasksList) {
        List<Node> availableTasks = new ArrayList<Node>();
        for (Node node : tasksList) {
            // Get a list of the node dependencies
            List<Node> dependentNodes = node.getDependencies();
            boolean dependenciesComplete = true;
            for (Node dependentNode : dependentNodes) {
                if (_tasks.contains(dependentNode)) {
                    dependenciesComplete = false;
                }
            }
            if (dependenciesComplete) {
                availableTasks.add(node);
            }
        }
        return availableTasks;
    }

}
