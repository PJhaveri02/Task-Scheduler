package read_inputs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TerminalReader {

    private String[] args;

    private String inputDotFile;
    private String outputDotFile;
    private int numberOfProcessors = 1;
    private boolean visualiseSearch = false;
    private int numberOfCores = 0;
    private File input;

    public TerminalReader(String[] args) {
        this.args = args;
    }

    public void validateInputs() throws IncorrectInputException {

        // Check number of Inputs
        if (args.length < 2) {
            throw new IncorrectInputException("Not enough inputs! Please provide the input file name an number of processors.");
        } else if (args.length > 7) {
            throw new IncorrectInputException("Too many arguments! Please provide less than 7 arguments.");
        }

        // Validate Input .dot file
        File inputFile = new File(args[0]);

        // If file does not exist OR the input file does not end in .dot then throw exception
        if (inputFile.exists() && args[0].endsWith(".dot")) {
            inputDotFile = args[0];
            outputDotFile = args[0].replace(".dot", "-output.dot");
        } else {
            throw new IncorrectInputException("Invalid INPUT .dot file");
        }

        input = inputFile; // Set field to validated input file


        // Validate Number of Processors
        try {
            numberOfProcessors = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new IncorrectInputException("Number of processors provided (" + args[1] + ") is not a valid integer");
        }

        // Validate Optional parameters
        for (int i = 2; i < args.length; i++) {
            switch (args[i]) {

                case "-p":
                    try {
                        numberOfCores = Integer.parseInt(args[i + 1]);
                        i++;
                    } catch (Exception e) {
                        throw new IncorrectInputException("-p must be followed by an integer.");
                    }
                    break;

                case "-o":
                    try {
                        outputDotFile = args[i + 1] + ".dot";
                        i++;
                    } catch (Exception e) {
                        throw new IncorrectInputException("-o must be followed by an output file name");
                    }
                    break;

                case "-v":
                    visualiseSearch = true;
                    break;
                default:
                    throw new IncorrectInputException("Invalid optional argument " + args[i]);
            }
        }
    }

    //read the .dot file and print it
    public void readInput() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(input));
            String st;

            while((st = in.readLine()) != null) {
                System.out.println(st);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public String getOutputDotFile() {
        return outputDotFile;
    }

    public int getNumberOfProcessors() {
        return numberOfProcessors;
    }

    public boolean isVisualiseSearch() {
        return visualiseSearch;
    }

    public int getNumberOfCores() {
        return numberOfCores;
    }
}
