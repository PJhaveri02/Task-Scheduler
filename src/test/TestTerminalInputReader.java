package test;

import algorithm.Model;
import algorithm.Node;
import org.junit.Before;
import org.junit.Test;
import read_inputs.TerminalReader;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestTerminalInputReader {

    public static final String[] INPUT = {"./dot/graph.dot", "2"};
    Model _model;

    @Test
    public void testReadInput(){
        try {
            TerminalReader tr = new TerminalReader(INPUT);
            tr.validateInputs();
        } catch (Exception e) {
            fail("Cannot read from file");
        }

    }

    @Test
    public void testModelAddNode() {

    }

}
