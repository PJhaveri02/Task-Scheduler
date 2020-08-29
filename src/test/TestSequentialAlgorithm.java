package test;

import algorithm.*;
import gui.GraphCreator;
import org.junit.Before;
import org.junit.Test;
import read_inputs.TerminalReader;


import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class TestSequentialAlgorithm {

    public static String[] _INPUT = {"./dot/graph.dot", "2"};
    private static Model model;
    private static List<Processor> _scheduledProcessors;
    private static algorithm _alg;

    /**
     * Method that is called for each test
     * @param INPUT Terminal arguments
     */
    public void setUp(String[] INPUT) {
        TerminalReader terminalReader = new TerminalReader(INPUT);
        try {
            terminalReader.validateInputs();
            model = terminalReader.readInput();
            model.addLevels();
            List<Node> nodesList = model.getNodes();
            GraphCreator graph = new GraphCreator(model);
            _alg = new FinalAlgorithm(terminalReader.getProcNum(), nodesList);
            _scheduledProcessors = _alg.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Test whether the FinalAlgorithm works for the basic graph given at the beginning of the assignment
     * The algorithm should provide the optimal solution
     */
    @Test
    public void testBasicDotGraph() {
        _INPUT[0] = "./dot/graph.dot";
        _INPUT[1] = "2";
        setUp(_INPUT);
        int time = 0;
        for (Processor p : _scheduledProcessors) {
            if (p.getTime() > time) {
                time = p.getTime();
            }
        }
        assertEquals(time, 8);
    }

    /**
     * Test whether the FinalAlogrithm provides the correct schedule if one processor is provided by the user
     */
    @Test
    public void testBasicDotGraphOneProcessor() {
        _INPUT[0] = "./dot/graph.dot";
        _INPUT[1] = "1";
        setUp(_INPUT);
        int time = 0;
        for (Processor p : _scheduledProcessors) {
            if (p.getTime() > time) {
                time = p.getTime();
            }
        }
        assertEquals(time, 9);
    }

    /**
     * Test whether the sequential algorithm provides the optimal schedule for Nodes_11_OutTree.dot when given
     * 4 processors as the input
     */
    @Test
    public void testNodes11FourProcessors() {
        _INPUT[0] = "./dot/Nodes_11_OutTree.dot";
        _INPUT[1] = "4";
        setUp(_INPUT);
        int time = 0;
        for (Processor p : _scheduledProcessors) {
            if (p.getTime() > time) {
                time = p.getTime();
            }
        }
        assertEquals(time, 227);
    }

    /**
     * Test whether the sequential algorithm provides the optimal schedule for Nodes_11_OutTree.dot when given
     * 2 processors as the input
     */
    @Test
    public void testNodes11TwoProcessors() {
        _INPUT[0] = "./dot/Nodes_11_OutTree.dot";
        _INPUT[1] = "2";
        setUp(_INPUT);
        int time = 0;
        for (Processor p : _scheduledProcessors) {
            if (p.getTime() > time) {
                time = p.getTime();
            }
        }
        assertEquals(time, 350);
    }


    /**
     * Test whether the sequential algorithm provides the optimal schedule for Nodes_9_SeriesParallel.dot when given
     * 5 processors as the input
     */
    @Test
    public void testNodes9FiveProcessors() {
        _INPUT[0] = "./dot/Nodes_9_SeriesParallel.dot";
        _INPUT[1] = "5";
        setUp(_INPUT);
        int time = 0;
        for (Processor p : _scheduledProcessors) {
            if (p.getTime() > time) {
                time = p.getTime();
            }
        }
        assertEquals(time, 55);
    }


    /**
     * Test whether the sequential algorithm provides the optimal schedule for Nodes_7_OutTree.dot when given
     * 2 processors as the input
     */
    @Test
    public void testNodes7TwoProcessors() {
        _INPUT[0] = "./dot/Nodes_7_OutTree.dot";
        _INPUT[1] = "2";
        setUp(_INPUT);
        int time = 0;
        for (Processor p : _scheduledProcessors) {
            if (p.getTime() > time) {
                time = p.getTime();
            }
        }
        assertEquals(time, 28);
    }

    /**
     * Test whether the sequential algorithm provides the optimal schedule for Nodes_8_SeriesParallel.dot when given
     * 3 processors as the input
     */
    @Test
    public void testNodes8ThreeProcessors() {
        _INPUT[0] = "./dot/Nodes_8_Random.dot";
        _INPUT[1] = "3";
        setUp(_INPUT);
        int time = 0;
        for (Processor p : _scheduledProcessors) {
            if (p.getTime() > time) {
                time = p.getTime();
            }
        }
        assertEquals(time, 581);
    }

    /**
     * Test whether the sequential algorithm provides the optimal schedule for Nodes_10_Random.dot when given
     * 6 processors as the input
     */
    @Test
    public void testNodes10SixProcessors() {
        _INPUT[0] = "./dot/Nodes_10_Random.dot";
        _INPUT[1] = "6";
        setUp(_INPUT);
        int time = 0;
        for (Processor p : _scheduledProcessors) {
            if (p.getTime() > time) {
                time = p.getTime();
            }
        }
        assertEquals(time, 50);
    }
}
