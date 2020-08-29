package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ParallelFinalAlgorithm extends FinalAlgorithm {

    private int MAX_CORES;
    private int _numProcessors;
    private List<Node> _tasks;
    private static int tasksCreated = 1;

    // These variables need to be atomic as they will be accessed by multiple threads
    private AtomicInteger _bestTime = new AtomicInteger(-1);
    private AtomicInteger _counter = new AtomicInteger(0);
    private AtomicReference<List<Processor>> _bestProcess;

    private ForkJoinPool pool;

    public ParallelFinalAlgorithm(int numProcessors, List<Node> tasks, int maximumCores) {
        super(numProcessors, tasks);
        MAX_CORES = maximumCores;
        _tasks = tasks;
        _numProcessors = numProcessors;
    }

    private class RecursiveFork extends RecursiveAction {
        // private static final int THRESHOLD = 8;

        private List<Processor> _processors;
        private List<Node> _tasks1;

        private RecursiveFork(List<Processor> processors, List<Node> tasks) {
            _processors = processors;
            _tasks1 = tasks;
        }

        @Override
        protected void compute() {
            compute1();
        }


        private void compute1() {

            if (_tasks1.isEmpty()) {
                //check time
                int time = 0;
                for (Processor check : _processors) {
                    if (check.getTime() > time) {
                        time = check.getTime();
                    }
                }
                if (time < _bestTime.get() || _bestTime.get() == -1) {
                    _bestTime.set(time);
                    List<Processor> sadness = new ArrayList<Processor>();
                    for (Processor gah : _processors) {
                        try {
                            sadness.add(gah.clone());
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                    _bestProcess.set(sadness);
                }
            } else if (getBestTime(_processors) > _bestTime.get() && _bestTime.get() != -1) { // bound based on greedy
                return;
            } else {
                List<Node> doable = checkAvailability(_tasks1);

                for (Node n : doable) {
                    for (Processor p : _processors) {

                        // need to change time
                        int time1 = startTime(p, n, _processors);

                        p.scheduleTask(n, time1);

                        List<Node> newList = _tasks1.subList(0, _tasks1.size());
                        newList.remove(n);

                        RecursiveFork forkJob = new RecursiveFork(_processors, newList);
                        int num_threads_running =  pool.getActiveThreadCount();
                       // System.out.println(num_threads_running);
                        //System.out.println("Global Tasks: " + _tasks.size() + " New List Size: " + newList.size());
                        if ( num_threads_running < MAX_CORES && newList.size() > (_tasks.size() * 0.8)) {
                          //  System.out.println("Task sent to pool");
                           // tasksCreated++;
                            pool.invoke(forkJob);
                        } else {
                            forkJob.compute1();
                        }

                        p.removeTask(n);
                        newList.add(n);

                        //check if blank, stops wasted repeats
                        if (p.getTime() == 0) {
                            break;
                        }
                    }
                }
            }
        }
    }


    @Override
    public List<Processor> execute() {
        long startTime = System.currentTimeMillis();

        //sort via bottom levels
        nodeBottomLevel();
        Collections.sort(_tasks);
        greedyAlg();

        //wipe processors, build up task list again
        List<Processor> processorCopy = createProcessors();
        List<Node> taskCopy = createTaskList();

        //check if only 1 root
        List<Node> doable = checkAvailability(taskCopy);
        if (doable.size() == 1) {
            processorCopy.get(0).scheduleTask(doable.get(0), 0);
            taskCopy.removeAll(doable);
        }

        System.out.println("Greedy output: " + _bestTime);
        // invoke recursive call

        int no_cores = Runtime.getRuntime().availableProcessors();
        if (MAX_CORES > no_cores) {
            MAX_CORES = no_cores;
        }
        pool = new ForkJoinPool(MAX_CORES);
        RecursiveFork forkJob = new RecursiveFork(processorCopy, taskCopy);
        pool.invoke(forkJob);

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        System.out.println("Best time: " + _bestTime);
        return _bestProcess.get();
    }


    /**
     * Modified greedy algorithm for parallelisation
     */
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

        _bestProcess = new AtomicReference<List<Processor>>(procs);
        _bestTime.set(getBestTime(procs));
    }
}
