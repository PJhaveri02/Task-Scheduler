package algorithm;

import gui.ProcGraphController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ParallelFinalAlgorithm extends FinalAlgorithm {

    private final int MAX_CORES;
    private int _numProcessors;
    private List<Node> _tasks;
    private ProcGraphController _visuals;

    // These variables need to be atomic as they will be accessed by multiple threads
    private AtomicInteger _bestTime = new AtomicInteger(-1);
    private AtomicInteger _counter = new AtomicInteger(0);
    private AtomicReference<List<Processor>> _bestProcess;

    private ForkJoinPool pool;

    public ParallelFinalAlgorithm(int numProcessors, List<Node> tasks, int maximumCores) {
        super(numProcessors, tasks);
        MAX_CORES = maximumCores;
        _tasks = tasks;
//        MAX_CORES = 30;
        _numProcessors = numProcessors;
    }

    public void addListener(ProcGraphController oddlySpecificScuffedListener){
        _visuals = oddlySpecificScuffedListener;
    }

    private class RecursiveFork extends RecursiveAction {
        private static final int THRESHOLD = 8;

        private List<Processor> _processors;
        private List<Node> _tasks1;

        private RecursiveFork(List<Processor> processors, List<Node> tasks) {
            _processors = processors;
            _tasks1 = tasks;
        }

        @Override
        protected void compute() {

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
//                            List<Node> newList = _tasks1.subList(0, _tasks1.size());
//                            newList.remove(n);

                            List<Node> newList = _tasks1.subList(0, _tasks1.size());
                            newList.remove(n);

//                            if (doable.size()==1){
//                                List<Node> nextDoable = checkAvailability(newList);
//                                if (nextDoable.size()==1){
//
//                                }
//                            }


                            RecursiveFork forkJob = new RecursiveFork(_processors, newList);
                            if (newList.size() > THRESHOLD) {
                                //invoke recursions
                                invoke();
                            } else {
                                forkJob.compute();
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
        if (doable.size()==1){
            processorCopy.get(0).scheduleTask(doable.get(0),0);
            taskCopy.removeAll(doable);
        }

        // invoke recursive call
        pool = new ForkJoinPool(MAX_CORES);
        RecursiveFork forkJob = new RecursiveFork(processorCopy, taskCopy);
        pool.invoke(forkJob);

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        postVisual();
        return _bestProcess.get();
    }

    private void postVisual(){
        if(_visuals!=null) {
            _visuals.update(_bestProcess.get());
        }
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
