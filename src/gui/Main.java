package gui;

import algorithm.Model;
import algorithm.algorithm;
import algorithm.BadAlgorithm;
import algorithm.FinalAlgorithm;
import algorithm.Processor;
import algorithm.Node;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import read_inputs.IncorrectInputException;
import read_inputs.TerminalReader;
import Thread.AlgorithmService;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends Application implements Initializable {

    private static Model model;
    private static Service _algorithmService;
    @Override
    public void start(Stage primaryStage) throws Exception{

        GraphCreator graph = new GraphCreator(model);
        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
        _algorithmService.start();
      //  graph.start(primaryStage);
    }

    public static void main(String[] args) {
        System.out.println("gui main");
        TerminalReader terminalReader = new TerminalReader(args);
        try {
            terminalReader.validateInputs();
            model = terminalReader.readInput();
            model.addLevels();
            List<Node> nodesList = model.getNodes();
            algorithm algorithm = new FinalAlgorithm(terminalReader.getProcNum(),nodesList);
            setUpAlgorithmServices(algorithm, terminalReader);
//            List<Processor> processorList = terminalReader.createProcessors();
            launch(args);
//            algorithm algorithm = new BadAlgorithm(terminalReader.getProcNum(),nodesList);

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



    private static void setUpAlgorithmServices(algorithm algorithm, TerminalReader tr) {
        // Get number of cores on this machine
        int coreCount = Runtime.getRuntime().availableProcessors();
        int cores = tr.getNumberOfCores();

        // Create algorithm service
        _algorithmService = new AlgorithmService(algorithm);

        // Create an executor service for managing threads
        ExecutorService executorService;
        if (cores > coreCount) {
           executorService = Executors.newFixedThreadPool(coreCount);
        } else {
            executorService = Executors.newFixedThreadPool(cores);
        }

        // Set algorithm service to use the executor
        _algorithmService.setExecutor(executorService);
    }



}
