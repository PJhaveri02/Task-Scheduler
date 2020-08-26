package gui;

import algorithm.Model;
import algorithm.algorithm;
import algorithm.BadAlgorithm;
import algorithm.FinalAlgorithm;
import algorithm.Processor;
import algorithm.Node;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import read_inputs.IncorrectInputException;
import read_inputs.TerminalReader;

import java.net.URL;
import java.util.*;

public class Main extends Application implements Initializable {

    private static Model model;

    @Override
    public void start(Stage primaryStage) throws Exception{

        GraphCreator graph = new GraphCreator(model);
        graph.start(primaryStage);
    }

    public static void main(String[] args) {
        System.out.println("gui main");
        TerminalReader terminalReader = new TerminalReader(args);
        try {
            terminalReader.validateInputs();
            model = terminalReader.readInput();
            model.addLevels();
//            List<Processor> processorList = terminalReader.createProcessors();
            List<Node> nodesList = model.getNodes();
            //launch(args);
//            algorithm algorithm = new BadAlgorithm(terminalReader.getProcNum(),nodesList);
            algorithm algorithm = new FinalAlgorithm(terminalReader.getProcNum(),nodesList);
            List<Processor> scheduledProcessors = algorithm.execute();
            terminalReader.writeOutput(scheduledProcessors);

        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        launch(args);
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
