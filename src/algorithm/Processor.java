package algorithm;

import java.util.ArrayList;

public class Processor {

    //processor 'core' numbers
    private int _id;

    // internal timer of 'latest' task
    private int _time;

    //list of tasks which will be executed
    private ArrayList<Tasks> tasks;

    public ArrayList<Tasks> getTasks() {
        return tasks;
    }
}
