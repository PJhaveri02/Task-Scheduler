package algorithm;

import gui.ProcGraphController;

import java.util.List;

public interface algorithm {

    /**
     * @return an list of processors containing the Tasks assigned to it.
     */
    List<Processor> execute();

    /**
     * add a listener for visualisation
     * @param visController
     */
    void addListener(ProcGraphController visController);
}
