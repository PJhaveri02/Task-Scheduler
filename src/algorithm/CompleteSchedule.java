package algorithm;

import java.util.List;
import java.util.Map;

public class CompleteSchedule extends Schedule{

    public CompleteSchedule(Map<Processor, List<String>> _processorToScheduledNodes, List<Node> allScheduledNodes) {
        super(_processorToScheduledNodes, allScheduledNodes);
    }
}
