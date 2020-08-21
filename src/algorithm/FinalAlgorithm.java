package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinalAlgorithm implements algorithm{

    private List<Processor> _processors;
    private List<Node> _tasks;
    private List<Node> _available;

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
        //get list of available tasks
        _available = checkAvailability(_tasks);
        //inset method to sort available tasks into highest bottom level order

        //loop through the processors and recursively find the best schedule
        for (Processor p : _processors){
            //check if there are multiple empty processors
            //schedule the node into the processor
            //calculate the weight of the partial schedule
            //compare with weight of current best
            //if (partial schedule is bad) {continue}
            //recursive
        }
        return null;
    }

    private Schedule recursion(Schedule s, List<Node> available, List<Node> unscheduled) {

        return null;
    }

//    /**
//     * Method to crate a new partial schedule with the node assigning to the processor.
//     *
//     * @param currentSchedule
//     * @param processor
//     * @param node
//     * @return
//     */
//    public Schedule createPartialSchedule(Schedule currentSchedule, Processor processor, Node node) {
//        Map<Processor, List<String>> currentPToN = currentSchedule.getProcessorToScheduledNodes();
//        List<String> scheduleNodeInProc = currentPToN.get(processor);
//        int lengthOfSchNode = scheduleNodeInProc.size();
//        String nodeName = node.getName();
//        List<String> schPToN = new ArrayList<String>(scheduleNodeInProc);
//        schPToN.add(nodeName);
//
//        Map<Processor, List<String>> newPToN = new HashMap<Processor, List<String>>(currentPToN);
//        newPToN.put(processor, schPToN);
//        List<Node> newScheduledNodes = new ArrayList<Node>(currentSchedule.getAllScheduledNodes());
//
//        return new Schedule(newPToN, newScheduledNodes);
//    }

    /**
     *
     * @param currentScheduledNodes
     * @param unscheduledNodes
     * @return
     */
    public List<Node> getAvailableNodes(List<Node> currentScheduledNodes, List<Node> unscheduledNodes) {
        List<Node> newAvailableNodes = new ArrayList<Node>();
        for (Node unscheduledNode : unscheduledNodes) {
            boolean available = true;
            List<Node> dependentNodes = unscheduledNode.getDependencies();
            for (Node dependentNode : dependentNodes) {
                if (!currentScheduledNodes.contains(dependentNode)) {
                    available = false;
                }
            }
            if (available) {
                newAvailableNodes.add(unscheduledNode);
            }
        }
        return newAvailableNodes;
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

    }
