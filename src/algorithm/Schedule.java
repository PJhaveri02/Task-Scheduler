package algorithm;

import java.util.List;
import java.util.Map;

/**
 * Abstract class to represent a schedule. Schedule has processors which nodes are placed.
 */
public class Schedule {

    // Field for comparing the best schedule
    public static Schedule _optimumSchedule;

    private Map<Processor, List<Node>> _processorToScheduledNodes;
    private List<Node> _allScheduledNodes;

    public Schedule(Map<Processor, List<Node>> processorToScheduledNodes, List<Node> allScheduledNodes) {
        _processorToScheduledNodes = processorToScheduledNodes;
        _allScheduledNodes = allScheduledNodes;
    }

    public Map<Processor, List<Node>> getProcessorToScheduledNodes() {
        return _processorToScheduledNodes;
    }

    public List<Node> getAllScheduledNodes() {
        return _allScheduledNodes;
    }

    //method to get weight
    //need to factor in edge weights if child nodes are scheduled under another processor
}
