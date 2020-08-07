package algorithm;

import java.util.ArrayList;
import java.util.Map;

public class Tasks {

    private int _weight;
    private int _start;
    private Processor _processor;
    private Map<Tasks,Integer> _dependentsAndWeight;

    public Tasks(int weight, int start, Map<Tasks,Integer> dependentsToWeights){
        _weight = weight;
        _start = start;
        _dependentsAndWeight = dependentsToWeights;
    }

    public int get_weight() {
        return _weight;
    }

    public int get_start() {
        return _start;
    }

    public Processor get_processor() {
        return _processor;
    }

    public void setProcessor(Processor processor) {
        this._processor = processor;
    }

    public ArrayList<boolean>CheckDependencies()
}
