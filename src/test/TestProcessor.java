package test;
import algorithm.Node;
import algorithm.Processor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

public class TestProcessor {
    private Processor _processor;

    @Before
    public void setUp() {
        _processor = new Processor(1);
    }

    /**
     * Test whether a task/node is being correctly scheduled to a processor
     */
    @Test
    public void testAddingNodeToProcessor() {
        _processor.scheduleTask(new Node(5, 3, "Node1"), 3);
        _processor.scheduleTask(new Node(2, 1, "Node1"), _processor.getTime());
        int processorEndTime = _processor.getTime();
        if (processorEndTime != 10) {
            fail("Incorrect scheduling of task/node");
        }
    }

    /**
     * Test if the correct end time of a particular node is being provided
     */
    @Test
    public void testEndTimeOfNode() {
        Node node1 = new Node(5, 3, "Node1");
        Node node2 = new Node(2, 1, "Node2");
        _processor.scheduleTask(node1, 0);
        _processor.scheduleTask(node2, _processor.getTime());
        int nodeEndTime = _processor.getEnd(node1);
        if (nodeEndTime != 5) {
            fail("Incorrect end time of task/node");
        }
    }

    /**
     * Test whether creating a deep copy of a processor is working correctly
     * @throws CloneNotSupportedException
     */
    @Test
    public void testProcessorCloning() throws CloneNotSupportedException {
        Node node1 = new Node(5, 3, "Node1");
        Node node2 = new Node(2, 1, "Node2");
        _processor.scheduleTask(node1, 0);
        _processor.scheduleTask(node2, _processor.getTime());
        Processor p2 = _processor.clone();
        if (!(_processor.toString().equals(p2.toString()))) {
            fail("Incorrect Cloning of Processor");
        }
    }

    /**
     * Test whether the writing a node to a string functionality is working correctly
     */
    @Test
    public void testWritingNodeToString() {
        Node node1 = new Node(5, 3, "ABC");
        _processor.scheduleTask(node1, 22);
        String expectedTaskString = _processor.writeString(node1);
        String actualTaskString = "ABC\t[Weight=5, Start=22, Processor=1]";
        if (!(actualTaskString.equals(expectedTaskString))) {
            fail("Incorrect Implementation of writing node to string");
        }
    }

}
