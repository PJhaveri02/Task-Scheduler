package algorithm;

import gui.ProcGraphController;

import java.util.List;

public interface algorithm {

    /**
     * @return an list of processors containing the Tasks assigned to it.
     */
    public List<Processor> execute();


}
