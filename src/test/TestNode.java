package test;

import algorithm.Node;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestNode {

    private Node _testNode;

    @Before
    public void setUp() {
        _testNode = new Node(5, 1, "testNode");
    }

    /**
     * Test whether the weight of the Node is being correctly assigned to its Node
     */
    @Test
    public void testNodeWeight() {
        assertEquals(5, _testNode.get_weight());
    }

    /**
     * Test whether the Dependencies are being allocated correctly to its corresponding Node
     */
    @Test
    public void testNodeDependencies() {
        for (int i = 0; i < 3; i++) {
            _testNode.addDependency(new Node(5 - i, i, "testDependency" + i), 6 + i);
        }
        List<Node> dependency = _testNode.getDependencies();
        if (dependency.size() != 3) {
            fail("Incorrect Dependencies in Node");
        }
    }

    /**
     * Test whether the Children are being allocated correctly to its corresponding Node
     */
    @Test
    public void testChildrenNodes() {
        for (int i = 0; i < 3; i++) {
            _testNode.addChildNodes(new Node(5 - i, i, "testChildren" + i), 5 + i);
        }
        List<Node> childrenNodes = _testNode.getChildren();
        if (childrenNodes.size() != 3) {
            fail("Incorrect Children in Node");
        }
    }
}
