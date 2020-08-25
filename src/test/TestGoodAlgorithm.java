package test;

import algorithm.Model;
import algorithm.Node;
import org.junit.Before;
import org.junit.Test;
import read_inputs.TerminalReader;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestGoodAlgorithm {

    public static final String[] INPUT = {"graph.dot", "2"};
    Model _model;

    @Test
    public void testReadInput(){
        TerminalReader tr = new TerminalReader(INPUT);
        _model = tr.readInput();
        assertEquals(2, tr.getNumberOfProcessors());
    }

}
