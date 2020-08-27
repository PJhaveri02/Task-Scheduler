package Thread;

import algorithm.FinalAlgorithm;
import algorithm.algorithm;
import algorithm.Processor;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmTask extends Task<List<String>> {

    private algorithm _finalAlgorithm;
    private List<Processor> processorList;

    public AlgorithmTask(algorithm finalAlgorithm) {
        _finalAlgorithm = finalAlgorithm;
    }

    @Override
    protected List<String> call() throws Exception {
        List<Processor> processorsList =_finalAlgorithm.execute();
        List<String> PL_str = new ArrayList<String>();
        for (Processor p : processorsList) {
            PL_str.add(p.toString());
        }

        // Return updates to the background thread
        //updateValue(null);
        return PL_str;
    }

    // Used for updating the progress of the algorithm.
    // Expressed as a ratio: v/v1
    @Override
    protected void updateProgress(double v, double v1) {
        super.updateProgress(v, v1);
    }
}
