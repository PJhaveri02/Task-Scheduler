package Thread;

import algorithm.algorithm;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.List;

public class AlgorithmService extends Service<List<String>> {

    private algorithm _finalAlgorithm;

    public AlgorithmService(algorithm a) {
      _finalAlgorithm = a;
    }

    @Override
    protected Task<List<String>> createTask() {
        return new AlgorithmTask(_finalAlgorithm);
    }
}
