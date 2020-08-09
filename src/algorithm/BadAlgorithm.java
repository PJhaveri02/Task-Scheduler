package algorithm;

import java.util.ArrayList;
import java.util.List;


/**
 * note: BadAlgorithm.execute() is destructive to the model
 */
public class BadAlgorithm implements algorithm {

    private ArrayList<Processor> _processors;
    private Model _model;
    private List<Node> _available;

    /**
     * Constuctor to pass in the Model generated from the DOT file as well as number of processors
     *
     *
     */
    public BadAlgorithm(int numProcessors, Model model){
        _model = model;
        _processors = new ArrayList<Processor>();
        for(int i =0; i<numProcessors; i++){
            _processors.add(new Processor());
        }
    }

    //ewwwwwww
    private int procMin(){
        int min = _processors.get(0).getTime();
        for(Processor proc : _processors){
            int a = proc.getTime();
            if(a<min){
                a=min;
            }
        }
        return min;
    }

    /**
     * execute method places all the tasks onto one processor in a valid format
     * @return ArrayList<Processor>
     */
    @Override
    public ArrayList<Processor> execute() {
        //PHASE 1: initial population of available node list
        _model.iterateAvailable();
        int time = 0;
        while(_model.hasAvailableTask()) {
            time = procMin();
            for (Processor processor : _processors) {
                if (time >= processor.getTime()) {
                    processor.addTask(_model.start(), time);
                }
            }
        }
        for (Processor processor : _processors) {
            processor.printToConsole();
        }
    // time int

        // check if tasks is not empty
        // move tasks which can be executed to avli
            // while avli is not empty
            // add tasks to processor, increment local time variable



//        While(!_tasks.isEmpty()){
//            if
//            _processors.get(0).add(task,,,,);
//        }

        return _processors;
    }
}
