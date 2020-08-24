package main;

import algorithm.Model;
import algorithm.Node;
import algorithm.Processor;
import algorithm.algorithm;
import algorithm.BadAlgorithm;
import algorithm.FinalAlgorithm;
import read_inputs.IncorrectInputException;
import read_inputs.TerminalReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TerminalReader terminalReader = new TerminalReader(args);
        try {
            terminalReader.validateInputs();
            Model model = terminalReader.readInput();
            List<Processor> processorList = terminalReader.createProcessors();
            List<Node> nodesList = model.getNodes();
//            algorithm algorithm = new BadAlgorithm(processorList,nodesList);
            algorithm algorithm = new FinalAlgorithm(processorList,nodesList);
            List<Processor> scheduledProcessors = algorithm.execute();
            terminalReader.writeOutput(scheduledProcessors);

        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

    }
}
