package algorithm;

import java.util.List;

/**
 * Abstract class to represent a schedule. Schedule has processors which nodes are placed.
 */
public abstract class Schedule {

    // Field for comparing the best schedule
    public static Schedule _optimumSchedule;

    private List<Processor> _processorList;

    public Schedule(List<Processor> processorList) {
        _processorList = processorList;
    }
}
