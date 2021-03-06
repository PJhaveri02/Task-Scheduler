package test;

import algorithm.Model;
import algorithm.Node;
import algorithm.Processor;
import org.junit.Before;
import org.junit.Test;
import read_inputs.IncorrectInputException;
import read_inputs.TerminalReader;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestTerminalInputReader {

    public static final String[] INPUT = {"./dot/graph.dot", "2", "-v", "-o", "OutputTest", "-p", "10"};
    Model _model;

    @Test
    public void testReadInput() {
        try {
            TerminalReader tr = new TerminalReader(INPUT);
            tr.validateInputs();
        } catch (Exception e) {
            fail("Cannot read from file");
        }
    }

    /**
     * Tests if the number of processors value corresponds to the user's input
     */
    @Test
    public void testNumProcessors() {
        try {
            TerminalReader tr = new TerminalReader(INPUT);
            tr.validateInputs();

            int num_processors = tr.getNumberOfProcessors();
            List<Processor> processorList = tr.createProcessors();

            assertEquals(INPUT[1], Integer.toString(num_processors));
        } catch (Exception e) {
            fail("Cannot read from file");
        }

    }

    /**
     * Tests if the number of processes created corresponds to the user's input
     */
    @Test
    public void testCreateProcessors() {
        try {
            TerminalReader tr = new TerminalReader(INPUT);
            tr.validateInputs();

            List<Processor> processorList = tr.createProcessors();
            assertEquals(INPUT[1], Integer.toString(processorList.size()));

        } catch (Exception e) {
            fail("Cannot read from file");
        }
    }

    /**
     * Test if the visualisation mode is saved correctly
     * @throws IncorrectInputException
     */
    @Test
    public void testIsVisualisation() throws IncorrectInputException {
        TerminalReader tr = new TerminalReader(INPUT);
        tr.validateInputs();
        assertEquals(true, tr.isVisualiseSearch());
    }

    /**
     * Test if the output dot file is named correctly
     * @throws IncorrectInputException
     */
    @Test
    public void testOutPutDotFileName() throws IncorrectInputException {
        TerminalReader tr = new TerminalReader(INPUT);
        tr.validateInputs();
        assertEquals("OutputTest.dot", tr.getOutputDotFile());
    }

    /**
     * Test if the number of cores for the algorithm is correct
     * @throws IncorrectInputException
     */
    @Test
    public void testNumberOfCores() throws IncorrectInputException {
        TerminalReader tr = new TerminalReader(INPUT);
        tr.validateInputs();
        assertEquals(10, tr.getNumberOfCores());
    }

}