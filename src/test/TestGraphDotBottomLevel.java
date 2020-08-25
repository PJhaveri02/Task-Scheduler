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

public class TestGraphDotBottomLevel {

    public static final String[] INPUT = {"./dot/graph.dot", "2"};
    Model _model;
    List<Processor> processorList;
    List<Node> nodeList;

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

    @Test
    public void testBottomLevelNodeA() {
        BadAlgorithm algorithm = new BadAlgorithm(processorList, nodeList);
        int bottomLevel = algorithm.calculateBottomLevel(nodeList.get(0));
        assertEquals(7,bottomLevel);
    }

    @Test
    public void testBottomLevelNodeB() {
        BadAlgorithm algorithm = new BadAlgorithm(processorList, nodeList);
        int bottomLevel = algorithm.calculateBottomLevel(nodeList.get(1));
        assertEquals(4,bottomLevel);
    }

    @Test
    public void testBottomLevelNodeC() {
        BadAlgorithm algorithm = new BadAlgorithm(processorList, nodeList);
        int bottomLevel = algorithm.calculateBottomLevel(nodeList.get(2));
        assertEquals(5,bottomLevel);
    }

    @Test
    public void testBottomLevelNodeD() {
        BadAlgorithm algorithm = new BadAlgorithm(processorList, nodeList);
        int bottomLevel = algorithm.calculateBottomLevel(nodeList.get(3));
        assertEquals(2,bottomLevel);
    }

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
            System.out.println(currentNode);
            nextNode = nodeList.get(i + 1).getBottomLevel();
            if (currentNode < nextNode) {
                fail("Not sorted according to bottom Level");
            }
        }
    }

}
