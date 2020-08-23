package algorithm;

import java.util.*;

public class FinalAlgorithm implements algorithm{

    private List<Processor> _processors;
    private List<Node> _tasks;
    private List<Node> _available;
    private int _bestWeight = 0;
    private List<String> _bestSchedule;

    /**
     * Constructor to pass in the Processors made and tasks from the DOT file.
     * @param processors
     * @param tasks
     */
    public FinalAlgorithm(List<Processor> processors, List<Node> tasks) {
        _tasks = tasks;
        _processors = processors;
        _available = new ArrayList<Node>();
    }

    @Override
    public List<Processor> execute() {

        /*
        the algorithm still produces a schedule that has all the tasks on one processor,
        because it starts on the same processor for every recursive call.
        we probably need the greedy algorithm here before the recursive one.
         */
        greedyAlg();
        //wipe processors, build up task list again
        recursiveAlg();

        return null;
    }

    private void greedyAlg() {
        //while tasks list is not empty
        while (_tasks.size() > 0) {
            //get list of available tasks
            _available = checkAvailability(_tasks);
            //bottom level sort
            Collections.sort(_available);

            //find dependencies
            //dependency end time + communication time, processor time

            //for all processors
            //get time
            //for all other processors
            //for list of dependencies
            //if processor contains this dependency
            //Node.getStart() + Node.getWeight() + communication time
            //end
            //end
            //end

        }
    }

    private void recursiveAlg() {
        //check the base case (if there are no more tasks to schedule). check the schedule against current best
        if (false) { return; }
        //check the partial schedule against the current best
        if (false) { return; }

        //get list of available tasks
        _available = checkAvailability(_tasks);

        //sort available tasks into order of highest bottom level, can use Node.getBottomLevel()

        //the code below assumes that sorting the available tasks returns a list
        //loop through available tasks
        for (Node task : _available) {
            //loop through processors and schedule
            for (Processor p : _processors) {
                //check if there are multiple empty processors and continue if required
                //schedule the task onto the processor
                //recursive call
                //remove the task from the processor, add back to _task, make a methods that does both
            }

        }

    }

    /**
     * write the current state of the processors to the string list and store the weight of the schedule.
     */
    private void updateBest() {

        _bestSchedule.clear();
        _bestSchedule.add("digraph \"outputGraph\" {");
        for (Processor proc : _processors){
            for (Node task : proc.getTasks()){
                _bestSchedule.add("\n\t\t"+task.toString());
                for (String dependent: task.dependenciesToString()){
                    _bestSchedule.add("\n\t\t"+dependent);
                }
            }
        }
        _bestSchedule.add("\n}");

        //loop through processors and store the latest end time as the weight
        for (Processor proc : _processors) {
            if (proc.getTime() > _bestWeight){
                _bestWeight = proc.getTime();
            }
        }

        //System.out.println(_bestWeight);
        //System.out.println(_currentBestSchedule);
    }

    /**
     * Method checks if the node's dependencies have been added to a processor.
     * All nodes with no dependencies gets returned.
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

    /**
     * This function iterates through each task/node to calculate the bottom level
     * for each task/node
     */
    public void nodeBottomLevel() {
        for (Node task : _tasks) {
            // calculating the bottom level of this task
            // if the task has no children the the bottom level is just the weight of the node
            if (task.getChildren().size() > 0) {
                int bottomLevel = calculateBottomLevel(task);
            } else {
                task.setBottomLevel(task.get_weight());
            }
        }
    }

    /**
     * Calculating the bottom level for a specific node/task using recussion
     * @param task
     * @return
     */
    public int calculateBottomLevel(Node task) {
        if ((task.getChildren().size()) > 0) {
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

    }
