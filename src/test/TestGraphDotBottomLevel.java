package test;

import algorithm.BadAlgorithm;
import algorithm.Model;
import algorithm.Node;
import algorithm.Processor;
import org.junit.Before;
import org.junit.Test;
import read_inputs.TerminalReader;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class tests the bottom level method for the graph.dot file.
 * The contents are:
 * digraph "example" {
 * 	a	[Weight = 2];
 * 	b	[Weight = 2];
 * 	a -> b	[Weight = 1];
 * 	c	[Weight = 3];
 * 	a -> c	[Weight = 2];
 * 	d	[Weight = 2];
 * 	b -> d	[Weight = 2];
 * 	c -> d	[Weight = 1];
 * }
 */
public class TestGraphDotBottomLevel {

    public static final String[] INPUT = {"./dot/graph.dot", "2"};
    Model _model;
    List<Processor> processorList;
    List<Node> nodeList;

    /**
     * Sets up the terminal reader and turns it into a model class.
     * Method executes before every test case.
     */
    @Before
    public void setUp() {

        try {
            TerminalReader tr = new TerminalReader(INPUT);
            tr.validateInputs();
            _model = tr.readInput();
            nodeList = _model.getNodes();
            processorList = tr.createProcessors();
        } catch (Exception e) {
            fail("Cannot read from file");
        }
    }

    /**
     * Tests the bottom level method for node A.
     * Passes if the expected is 7.
     */
    @Test
    public void testBottomLevelNodeA() {
        BadAlgorithm algorithm = new BadAlgorithm(processorList, nodeList);
        int bottomLevel = algorithm.calculateBottomLevel(nodeList.get(0));
        assertEquals(7,bottomLevel);
    }

    /**
     * Tests the bottom level method for node B.
     * Passes if the expected is 4.
     */
    @Test
    public void testBottomLevelNodeB() {
        BadAlgorithm algorithm = new BadAlgorithm(processorList, nodeList);
        int bottomLevel = algorithm.calculateBottomLevel(nodeList.get(1));
        assertEquals(4,bottomLevel);
    }

    /**
     * Tests the bottom level method for node C.
     * Passes if the expected is 5.
     */
    @Test
    public void testBottomLevelNodeC() {
        BadAlgorithm algorithm = new BadAlgorithm(processorList, nodeList);
        int bottomLevel = algorithm.calculateBottomLevel(nodeList.get(2));
        assertEquals(5,bottomLevel);
    }

    /**
     * Tests the bottom level method for node D.
     * Passes if the expected is 2.
     */
    @Test
    public void testBottomLevelNodeD() {
        BadAlgorithm algorithm = new BadAlgorithm(processorList, nodeList);
        int bottomLevel = algorithm.calculateBottomLevel(nodeList.get(3));
        assertEquals(2,bottomLevel);
    }

    /**
     * Tests the method sorts the bottom level accordingly to the bottom level.
     * Order is: Highest --> lowest.
     * Passes if the expected is 7.
     */
    @Test
    public void testSortByBottomLevel(){
        BadAlgorithm algorithm = new BadAlgorithm(processorList, nodeList);

        for (Node n : nodeList) {
            algorithm.calculateBottomLevel(n);
        }

        Collections.sort(nodeList);

        int currentNode;
        int nextNode;

        for (int i = 0; i < nodeList.size() - 1; i++) {
            currentNode = nodeList.get(i).getBottomLevel();
            nextNode = nodeList.get(i + 1).getBottomLevel();
            if (currentNode < nextNode) {
                fail("Not sorted according to bottom Level");
            }
        }
    }

}
