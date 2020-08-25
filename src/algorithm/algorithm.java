package algorithm;

import java.util.List;

public interface algorithm {

    /**
     * @return an list of processors containing the Tasks assigned to it.
     */
    public List<Processor> execute();

    /**
     * adds a node into a processor. imports its calculated start time
     * and updates the processors new start time
     * @param p
     * @param node
     */
    public void addTask(Processor p, Node node);

}
