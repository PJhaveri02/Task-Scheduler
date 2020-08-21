package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinalAlgorithm implements algorithm{
    @Override
    public List<Processor> execute() {
        return null;
    }

    /**
     *
     */
//    Method that assigns bottom levels to nodes
//
//    ImprovedAlgorithm(Schedule s, List processors,  List availableTasks, List unvisitedTasks) {
//        Input:
//        A partial schedule (Schedule class?)
//        List of processors
//        List of available tasks (ordered by bottom length)
//        List of unvisited nodes
//
//        Base case:
//        If available nodes list is <= 1
//        Return schedule
//
//        Recursive Algorithm:
//
//
//// sort available tasks in order of (highest) bottom level
//        -  Collections.sort
//        For all t in available Node
//        For all p in processors list (assume blank processors just one processor)
//
//
//        Schild  = createPartialSchedule(): by assigning task t into processor p - completed (method),
////check the partial schedule against the current best schedule (and bound if necessary ie break the iteration of the loop) (check that there is a schedule first)
//                availableTasknew  = getAvailableTasks(): get a new list of available tasks that can be executed - completed
//        Create unvisitedTasksnew: by removing t from unvisitedTask
//        new Schedule: ImprovedAlgorithm(schild, processors, availableTasknew, unvisitedTasksnew)	<------------- recursion
//        End
//                End

    /**
     * Method to crate a new partial schedule with the node assigning to the processor.
     *
     * @param currentSchedule
     * @param processor
     * @param node
     * @return
     */
    public Schedule createPartialSchedule(Schedule currentSchedule, Processor processor, Node node) {
        Map<Processor, List<String>> currentPToN = currentSchedule.getProcessorToScheduledNodes();
        List<String> scheduleNodeInProc = currentPToN.get(processor);
        int lengthOfSchNode = scheduleNodeInProc.size();
        String nodeName = node.getName();
        List<String> schPToN = new ArrayList<String>(scheduleNodeInProc);
        schPToN.add(nodeName);

        Map<Processor, List<String>> newPToN = new HashMap<Processor, List<String>>(currentPToN);
        newPToN.put(processor, schPToN);
        List<Node> newScheduledNodes = new ArrayList<Node>(currentSchedule.getAllScheduledNodes());

        return new Schedule(newPToN, newScheduledNodes);
    }

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

    }
