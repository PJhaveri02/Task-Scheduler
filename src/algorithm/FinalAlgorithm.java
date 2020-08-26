package algorithm;

import java.util.*;

public class FinalAlgorithm implements algorithm {

    private List<Processor> _processors;
    private List<Node> _tasks;
    private List<Node> _available;
    private int _bestTime =-1;
    private List<String> _bestSchedule;
    private List<Processor> _bestProcess;
    private List<List<Processor>> _allProcessCombinations = new ArrayList<List<Processor>>();
    long counter = 0;

    /**
     * Constructor to pass in the Processors made and tasks from the DOT file.
     *
     * @param processors
     * @param tasks
     */
    public FinalAlgorithm(List<Processor> processors, List<Node> tasks) {
        _tasks = tasks;
        _processors = processors;
        _available = new ArrayList<Node>();
    }

    @Override
    public List<Processor> execute() {

        nodeBottomLevel();
        Collections.sort(_tasks);

        //wipe processors, build up task list again
        List<Processor> processorCopy = new ArrayList<Processor>();
        List<Processor> processorCopyG = new ArrayList<Processor>();
        for (Processor copy : _processors){
            try {
                processorCopy.add(copy.clone());
                processorCopyG.add(copy.clone());
            }catch(Exception e){
                System.out.println("kms");
            }
        }
        List<Node> taskCopy = _tasks.subList(0,_tasks.size());
        List<Node> taskCopyG = new ArrayList<Node>();//_tasks.subList(0,_tasks.size());
        for (Node task : _tasks){
            taskCopyG.add(task);
        }

        //sort via bottom levels


        /*
        the algorithm still produces a schedule that has all the tasks on one processor,
        because it starts on the same processor for every recursive call.
        we probably need the greedy algorithm here before the recursive one.
         */
//        _bestProcess =
        greedyAlg(processorCopyG, taskCopyG);
//        _bestTime = getBestTime(_bestProcess);
        System.out.println(_bestTime);

//        while (_bestTime ==-1){
//            //kekw
//            int i =0;
//        }
        //MY MEME VERSION KEKW
//        recursiveAlg(processorCopy, taskCopy);
//        System.out.println(_bestTime);
        return _bestProcess;
    }

    /**
     * find the earliest start time on a given processor for a specific node
     */
    private int startTime(Processor p, Node node) {
        int current = p.getTime();
        for (Node n : node.getDependencies()) {
            if (!p.getTasks().contains(n)) {
                for (Processor proc : _processors) {
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

    private void greedyAlg(List<Processor> procs, List<Node> taskRemain) {
        List<Node> taskDoable = new ArrayList<Node>();

        taskDoable = checkAvailability(taskRemain);
        //while tasks list is not empty
        while (taskDoable.size() > 0){// || taskRemain.size() > 0) {

            //may not need
            Collections.sort(taskDoable);

            int time = 0;
            Processor earliestP = null;
            for (Processor p : procs) {
                int compare = startTime(p, taskDoable.get(0));

//                    System.out.println("Compare"+compare);
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
//        get list of available tasks
        taskDoable = checkAvailability(taskRemain);
        }
        //set time

        _bestProcess= procs;
        _bestTime=getBestTime(procs);
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
        //check time
//        int curTime = 0;
//        for (Processor check : pr) {
//            if (check.getTime() > curTime) {
//                curTime = check.getTime();
//            }
//        }
        int curTime = getBestTime(pr);

        if (task.isEmpty()) {
            counter++;
            System.out.println(counter);
            //check time
//            int time = 0;
//            for (Processor check : pr) {
//                if (check.getTime() > time) {
//                    time = check.getTime();
//                }
//            }
            if (curTime < _bestTime || _bestTime == -1) {
                _bestTime = curTime;
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

        } else if (curTime<_bestTime || _bestTime == -1){
            List<Node> doable = checkAvailability(task);
//            System.out.println(doable.size());
            //get availablee
            for (Node n : doable) {
                for (Processor p : pr) {
//                    for (Processor pTime : pr) {
                    int time = startTime(p, n);
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
     * write the current state of the processors to the string list and store the weight of the schedule.
     */
    private void updateBest() {

        _bestSchedule.clear();
        _bestSchedule.add("digraph \"outputGraph\" {");
        for (Processor proc : _processors) {
            for (Node task : proc.getTasks()) {
                _bestSchedule.add("\n\t\t" + task.toString());
                for (String dependent : task.dependenciesToString()) {
                    _bestSchedule.add("\n\t\t" + dependent);
                }
            }
        }
        _bestSchedule.add("\n}");

        //loop through processors and store the latest end time as the weight
        for (Processor proc : _processors) {
            if (proc.getTime() > _bestTime) {
                _bestTime = proc.getTime();
            }
        }

        //System.out.println(_bestWeight);
        //System.out.println(_currentBestSchedule);
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
