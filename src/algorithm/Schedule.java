package algorithm;

import java.util.List;
import java.util.Map;

/**
 * Abstract class to represent a schedule. Schedule has processors which nodes are placed.
 */
public abstract class Schedule {

    // Field for comparing the best schedule
    public static Schedule _optimumSchedule;

    private Map<Processor, List<String>> _processorToScheduledNodes;
    private List<Node> _allScheduledNodes;

    public Schedule(Map<Processor, List<String>> _processorToScheduledNodes, List<Node> allScheduledNodes) {
        _processorToScheduledNodes = _processorToScheduledNodes;
        _allScheduledNodes = allScheduledNodes;
    }

    public Map<Processor, List<String>> getProcessorToScheduledNodes() {
        return _processorToScheduledNodes;
    }

    public List<Node> getAllScheduledNodes() {
        return _allScheduledNodes;
    }
}
