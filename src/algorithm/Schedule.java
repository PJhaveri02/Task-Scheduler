package algorithm;

import java.util.List;
import java.util.Map;

/**
 * Abstract class to represent a schedule. Schedule has processors which nodes are placed.
 */
public abstract class Schedule {

    // Field for comparing the best schedule
    public static Schedule _optimumSchedule;

    private Map<Processor, String[]> _processorToScheduledNodes;

    public Schedule(Map<Processor, String[]> _processorToScheduledNodes) {
        _processorToScheduledNodes = _processorToScheduledNodes;
    }

    public Map<Processor, String[]> getProcessorToScheduledNodes() {
        return _processorToScheduledNodes;
    }
}
