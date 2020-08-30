package test;

import algorithm.FinalAlgorithm;
import algorithm.Node;
import algorithm.Processor;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.fail;

/**
 * Class to test the algorithms validity. This is done by checking if the schedule
 * adheres to both processor and precedence constraint.
 */
public class TestAlgorithmValidity {

    private List<Node> _nodesList;
    private int _numProcessor;
    private Map<Node, List<Node>> _dependentNodesMap;
    List<Processor> _validSchedule;
    FinalAlgorithm _finalAlgorithm;

    /**
     * Set up prior to execution.
     * Includes adding: nodes, number of processors, dependencies, and bottom level.
     */
    @Before
    public void setUp() {
        _nodesList = new ArrayList<Node>();
        _dependentNodesMap = new HashMap<Node, List<Node>>();

        // Create and add nodes to list
        Node node0 = new Node(5, 0, "node0");
        Node node1 = new Node(4, 1, "node1");
        Node node2 = new Node(6, 2, "node2");
        Node node3 = new Node(6, 3, "node3");
        _nodesList.add(node0);
        _nodesList.add(node1);
        _nodesList.add(node2);
        _nodesList.add(node3);

        // Add dependency
        node1.addDependency(node0, 1);
        node2.addDependency(node0, 2);
        node3.addDependency(node1, 2);
        node3.addDependency(node2, 2);

        // Place dependency into map for simplicity
        _dependentNodesMap.put(node1, Arrays.asList(node0));
        _dependentNodesMap.put(node2, Arrays.asList(node0));
        _dependentNodesMap.put(node3, Arrays.asList(node1, node2));

        // Set bottom level
        node0.setBottomLevel(17);
        node1.setBottomLevel(10);
        node2.setBottomLevel(12);
        node3.setBottomLevel(6);

        //
        _numProcessor = 2;

        _finalAlgorithm = new FinalAlgorithm(_numProcessor, _nodesList);
        _validSchedule = _finalAlgorithm.execute();

    }

    /**
     * Tests the validity by checking if the schedule adheres to the processor constraint.
     */
    @Test
    public void testValidityProcessorC() {
        for (Processor p :_validSchedule) {
            List<Node> scheduledNodes = p.getTasks();
                for (int i = 0; i < scheduledNodes.size() - 1; i++) {
                    Node n1 = scheduledNodes.get(i);
                    Node n2 = scheduledNodes.get(i + 1);
                    if (!testProcessorConstraint(p, n1, n2)) {
                        fail(("Schedule does not follow processor constraint"));
                    }
                }
        }
    }

    /**
     * Tests the validity by checking if the schedule adheres to the precedence constraint.
     */
    @Test
    public void testValidityPrecedenceC() {

        for (Node n1 : _dependentNodesMap.keySet()) {
            List<Node> dependentNodes = _dependentNodesMap.get(n1);
            Processor p2 = returnNodeInProcessor(_validSchedule, n1);
            for (Node n2 : dependentNodes) {
                Processor p1 = returnNodeInProcessor(_validSchedule, n2);
                boolean followsPC = testPrecedenceConstraint(p1, n2, p2, n1);
                if (!followsPC) {
                    fail("Two nodes do not follow precedence constraint");
                }

            }
        }
    }

    /**
     * Helper method to return the node in the processor.
     * @param pList
     * @param n
     * @return
     */
    private Processor returnNodeInProcessor(List<Processor> pList, Node n) {
        for (Processor p : pList) {
            if (p.getTasks().contains(n)) {
                return p;
            }
        }

        return null;
    }

    /**
     * Helper method to check the processor constraint
     * @param processor the processor both nodes are scheduled under
     * @param n1 a node under the processor
     * @param n2 another node under the processor
     * @return boolean value true if it satisfies the processor constraint.
     */
    private boolean testProcessorConstraint(Processor processor, Node n1, Node n2) {
        int endN1 = processor.getEnd(n1);
        int endN2 = processor.getEnd(n2);
        int startN1 = endN1 - n1.get_weight();
        int startN2 = endN2 - n2.get_weight();

        return (endN1 >= startN2 || endN2 >= startN1);

    }

    /**
     * Helper method to test the validity of the schedule by checking if two nodes abide by the precedence constraint.
     *
     * @param pN1 the processor of the node 1 schedule under
     * @param n1  the node parent node
     * @param pN2 the processor of the node 2 schedule under
     * @param n2  the child node
     * @return boolean value true if it follows the precedence constraint, false otherwise.
     */
    private boolean testPrecedenceConstraint(Processor pN1, Node n1, Processor pN2, Node n2) {
        int n1End = pN1.getEnd(n1);
        int n2Weight = n2.get_weight();
        int n2End = pN2.getEnd(n2);
        int n2Start = n2End - n2Weight;

        boolean sameProcessor = false;
        int n2StartBoundary;

        if (pN1.equals(pN2)) {
            sameProcessor = true;
        }

        // If same processor, the start boundary would be equal to or greater than ending time of n1.
        // Else, would be equal to the ending time of n1 and communication time.
        if (sameProcessor) {
            n2StartBoundary = n1End;
        } else {
            int edgeWeight = n2.getDependentsAndWeight().get(n1);
            n2StartBoundary = n1End + edgeWeight;
        }
        return n2Start >= n2StartBoundary;
    }



}
