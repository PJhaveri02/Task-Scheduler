package gui;

import algorithm.Model;
import algorithm.algorithm;
import algorithm.BadAlgorithm;
import algorithm.Processor;
import algorithm.Node;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import read_inputs.IncorrectInputException;
import read_inputs.TerminalReader;

import java.net.URL;
import java.util.*;

public class Main extends Application implements Initializable {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        TerminalReader terminalReader = new TerminalReader(args);
        try {
            terminalReader.validateInputs();
            Model model = terminalReader.readInput();
            // need to change method of generating processor
            List<Processor> processorList = Arrays.asList(new Processor(1), new Processor(2));
            List<Node> nodesList = model.getNodes();
            algorithm algorithm = new BadAlgorithm(processorList,nodesList);
            List<Processor> scheduledProcessors = algorithm.execute();
            terminalReader.writeOutput(scheduledProcessors);

        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

     launch(args);
    }

    /**
     * Initial setup of application after FXML objects have been injected
     * @param url
     * @param resourceBundle
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
