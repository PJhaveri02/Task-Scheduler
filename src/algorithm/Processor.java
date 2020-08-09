package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {

    //processor 'core' numbers
    private int _id;

    // internal timer of 'latest' task
    private int _time;

    //list of tasks which will be executed
    private Map<Node, Integer> _tasks;

    public Processor(){
        _time = 0;
        _tasks = new HashMap<Node, Integer>();
    }

    public int getTime() {
        return _time;
    }

    public boolean addTask(Node task, int time){
        if (time<_time){
            return false;
        }
        _tasks.put(task, time);

        _time = time + task.get_weight();
        return true;
    }


    public List<String> toStrings(){
        ArrayList strings = new ArrayList<String>();
        _tasks.forEach((k,v) -> strings.add("Task name: " + k.getName() + "   Start Time: " + v));
        return strings;
    }
    public void printToConsole(){
        for(String string: toStrings()){
            System.out.println(string);
        }
    }
}
