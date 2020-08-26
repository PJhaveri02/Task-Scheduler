package algorithm;

import java.util.*;

public class FinalAlgorithm implements algorithm {

    private int _processorsNum;
    private List<Node> _tasks;
    private int _bestTime =-1;
    private List<Processor> _bestProcess;
    private List<List<Processor>> _allProcessCombinations = new ArrayList<List<Processor>>();
    long counter=0;

    /**
     * returns a blank list of processors
     * @return
     */
    public List<Processor> createProcessors() {
        List<Processor> processorList = new ArrayList<>();
            for (int i = 1; i <= _processorsNum; i++){
                processorList.add(new Processor(i));
            }
        return processorList;
    }

    /**
     * Constructor to pass in the Processors made and tasks from the DOT file.
     *
     * @param tasks
     */
    public FinalAlgorithm(int numProc, List<Node> tasks) {
        _tasks = tasks;
        _processorsNum = numProc;
    }

    public List<Node> createTaskList(){
        List<Node> freshCopy = new ArrayList<Node>();
        for (Node node :_tasks){
            freshCopy.add(node);
        }
        return freshCopy;
    }

    @Override
    public List<Processor> execute() {


        //sort via bottom levels
        nodeBottomLevel();
        Collections.sort(_tasks);



        /*
        the algorithm still produces a schedule that has all the tasks on one processor,
        because it starts on the same processor for every recursive call.
        we probably need the greedy algorithm here before the recursive one.
         */
//        _bestProcess =
        greedyAlg();
//        _bestTime = getBestTime(_bestProcess);

        //wipe processors, build up task list again
        List<Processor> processorCopy = createProcessors();
        List<Node> taskCopy = createTaskList();

        //MY MEME VERSION KEKW

        //check if only 1 root
        List<Node> doable = checkAvailability(taskCopy);
        if (doable.size()==1){
            processorCopy.get(0).scheduleTask(doable.get(0),0);
            taskCopy.removeAll(doable);
        }
        System.out.println(taskCopy.size());
        System.out.println(_tasks.size());
        recursiveAlg(processorCopy, taskCopy);

        return _bestProcess;
    }

    /**
     * find the earliest start time on a given processor for a specific node
     */
    private int startTime(Processor p, Node node, List<Processor> processors) {
        int current = p.getTime();
        for (Node n : node.getDependencies()) {
            if (!p.getTasks().contains(n)) {
                for (Processor proc : processors) {
                    //minor major jank (should work tho)
                    int end = proc.getEnd(n) + node.getEdgeWeight(n);
                    if (end > current) {
                        current = end;
                    }
                }
            }
        }
        return current;
    }

    private void greedyAlg() {
        List<Node> taskRemain = createTaskList();
        List<Node> taskDoable = new ArrayList<Node>();
        List<Processor> procs = createProcessors();

        //while tasks list is not empty
        while (taskDoable.size() > 0 || taskRemain.size() > 0) {
            //get list of available tasks
            taskDoable = checkAvailability(taskRemain);

            //may not need
            Collections.sort(taskDoable);

            int time = 0;
            Processor earliestP = null;
            for (Processor p : procs) {
                int compare = startTime(p, taskDoable.get(0), procs);

                if (compare <= time || time == 0) {
                    earliestP = p;
                    time = compare;
                }
            }
            //add node into processor
            (earliestP).scheduleTask(taskDoable.get(0), time);
            taskRemain.remove(taskDoable.get(0));
            taskDoable.remove(0);
//            }
        }
        _bestProcess =  procs;
        _bestTime = getBestTime(procs);
    }

    private int getBestTime(List<Processor> pr){
        int curTime = 0;
        for (Processor check : pr) {
            if (check.getTime() > curTime) {
                curTime = check.getTime();
            }
        }
        return curTime;
    }

    private void recursiveAlg(List<Processor> pr, List<Node> task) {
        if (task.isEmpty()) {
            counter++;
            System.out.println(counter);
            //check time
            int time = 0;
            for (Processor check : pr) {
                if (check.getTime() > time) {
                    time = check.getTime();
                }
            }
            if (time < _bestTime || _bestTime == -1) {
                _bestTime = time;
                List<Processor> sadness = new ArrayList<Processor>();
                for (Processor gah : pr) {
                    try {
                        sadness.add(gah.clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
//                _allProcessCombinations.add(sadness);
                _bestProcess = sadness;
            }

        } else if(getBestTime(pr)>_bestTime &&  _bestTime != -1) {
            return;
        }else {
            List<Node> doable = checkAvailability(task);

            //get availablee
            for (Node n : doable) {
                for (Processor p : pr) {
//                    for (Processor pTime : pr) {
                    int time = startTime(p, n, pr);
//                    }
                    p.scheduleTask(n, time);
                    List<Node> newList = task.subList(0, task.size());
                    newList.remove(n);
                    recursiveAlg(pr, newList);
                    p.removeTask(n);
                    newList.add(n);


                }
            }
        }

    }


    /**
     * Method checks if the node's dependencies have been added to a processor.
     * All nodes with no dependencies gets returned.
     *
     * @param tasksList a list of nodes that has not been assigned to a processor yet.
     * @return A list of nodes that can be assigned (all dependencies executed).
     */
    private List<Node> checkAvailability(List<Node> tasksList) {
        List<Node> availableTasks = new ArrayList<Node>();
        for (Node node : tasksList) {
            // Get a list of the node dependencies
            List<Node> dependentNodes = node.getDependencies();
            boolean dependenciesComplete = true;
            for (Node dependentNode : dependentNodes) {
                if (tasksList.contains(dependentNode)) {
                    dependenciesComplete = false;
                }
            }
            if (dependenciesComplete) {
                availableTasks.add(node);
            }
        }
        return availableTasks;
    }

    /**
     * This function iterates through each task/node to calculate the bottom level
     * for each task/node
     */
    public void nodeBottomLevel() {
        for (Node task : _tasks) {
            // calculating the bottom level of this task

            if (task.getChildren().size() > 0) {
                int bottomLevel = calculateBottomLevel(task);
            } else {
                task.setBottomLevel(task.get_weight());
            }
        }
    }

    /**
     * Calculating the bottom level for a specific node/task using recussion
     *
     * @param task
     * @return
     */
    public int calculateBottomLevel(Node task) {
        if (task.getChildren().size() > 0) {
            int maxChildLevel = 0;
            for (Node childNode : task.getChildren()) {
                maxChildLevel = Math.max(calculateBottomLevel(childNode), maxChildLevel);
            }
            task.setBottomLevel(task.get_weight() + maxChildLevel);
        } else {
            task.setBottomLevel(task.get_weight());
        }
        return task.getBottomLevel();
    }

}
