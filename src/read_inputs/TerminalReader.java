package read_inputs;

import algorithm.Model;
import algorithm.Processor;
import algorithm.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TerminalReader {

    private String[] args;

    private String inputDotFile;
    private String outputDotFile;
    private int numberOfProcessors = 1;
    private boolean visualiseSearch = false;
    private int numberOfCores = 1;
    private File input;

    public TerminalReader(String[] args) {
        this.args = args;
    }

    /**
     * This function validates that the parameters provided by the user in terminal are valid.
     * If not, an IncorrectInputException is thrown
     * @throws IncorrectInputException
     */
    public void validateInputs() throws IncorrectInputException {

        // Check number of Inputs
        if (args.length < 2) {
            throw new IncorrectInputException("Not enough inputs! Please provide the input file name and number of processors.");
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

            // If the number of processors is negative then throw an exception
            if (numberOfProcessors < 1) {
                throw new IncorrectInputException("Number of processors provided (" + args[1] + ") is not a valid positive integer");
            }

        } catch (Exception e) {
            throw new IncorrectInputException("Number of processors provided (" + args[1] + ") is not a valid positive integer");
        }

        // Validate Optional parameters
        for (int i = 2; i < args.length; i++) {
            switch (args[i]) {

                case "-p":
                    try {
                        numberOfCores = Integer.parseInt(args[i + 1]);

                        // If the number of cores is negative then throw an exception
                        if (numberOfCores < 1) {
                            throw new IncorrectInputException("Number of cores provided (" + args[i + 1] + ") is not a valid positive integer");
                        }

                        i++;
                    } catch (NumberFormatException e) {
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

    //creates a list of processors according to the number of the number of processors specified by the user
    public List<Processor> createProcessors() {
        List<Processor> processorList = new ArrayList<>();
            for (int i = 1; i <= numberOfProcessors; i++){
                processorList.add(new Processor(i));
            }
        return processorList;
    }

    //read the .dot file and print it
    public Model readInput() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(input));
            String st = in.readLine();
            //gives the model the name
            Model model = new Model(st.split("\"")[1]);
            while((st = in.readLine()) != null) {
                //adds each line to the model as either a node or a dependency
                if(!(st.contains("}")|st.contains("{"))){
                    if(st.contains("->")){
                        model.addDependency(st);
                    }else{
                        model.addNode(st);
                    }
                }
            }
//            model.print();
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //writes an output file
    //for now, just writes a dot file with a single node
    public void writeOutput(List<Processor> sortedProcessors) {
        File output = new File(outputDotFile);
        try {
            if (output.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(output));
            bw.write("digraph \"outputGraph\" {");
            for (Processor stuff : sortedProcessors){
                for (Node tasks : stuff.getTasks()){
                    bw.write("\n\t\t"+tasks.toString());
                    for (String dependent: tasks.dependenciesToString()){
                        bw.write("\n\t\t"+dependent);
                    }
//                    bw.write("\n\t\t"+tasks.)
                }

            }

//            bw.write("\n\ta\t[weight = 4]");
            bw.write("\n}");
            bw.close();

        } catch (IOException e) {
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
