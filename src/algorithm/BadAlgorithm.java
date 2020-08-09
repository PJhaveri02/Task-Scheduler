package algorithm;

import java.util.ArrayList;

public class BadAlgorithm implements algorithm {

    private ArrayList<Processor> _processors;
    private ArrayList<Tasks> _tasks;
    private ArrayList<Tasks> _avalible;

    /**
     * Constuctor to pass in the Processors made and tasks from the DOT file.
     * @param processors
     * @param tasks
     */
    public BadAlgorithm(ArrayList<Processor> processors, ArrayList<Tasks> tasks){
        _tasks = tasks;
        _processors = processors;
    }

    /**
     * execute method places all the tasks onto one processor in a valid format
     * @return ArrayList<Processor>
     */
    @Override
    public ArrayList<Processor> execute() {

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
