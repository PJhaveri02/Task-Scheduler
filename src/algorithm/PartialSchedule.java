package algorithm;

import java.util.List;
import java.util.Map;

public class PartialSchedule extends Schedule {


    public PartialSchedule(Map<Processor, List<String>> _processorToScheduledNodes, List<Node> allScheduledNodes) {
        super(_processorToScheduledNodes, allScheduledNodes);
    }
}
