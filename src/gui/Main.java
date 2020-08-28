package gui;

import algorithm.Model;
import algorithm.algorithm;
import algorithm.ParallelFinalAlgorithm;
import algorithm.Processor;
import algorithm.Node;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import read_inputs.IncorrectInputException;
import read_inputs.TerminalReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Main extends Application{
    private static ProcGraphController _visController;
    private static Model model;
    private static algorithm alg;

    public static algorithm getAlgorithm(){
        return alg;
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
            GraphCreator graph = new GraphCreator(model);
            doVis();
           // Platform.runLater(graph);
//            launch(args);
//            algorithm alg = new BadAlgorithm(terminalReader.getProcNum(),nodesList);
//            algorithm alg = new FinalAlgorithm(terminalReader.getProcNum(),nodesList);
            ParallelFinalAlgorithm alg = new ParallelFinalAlgorithm(terminalReader.getProcNum(),nodesList, terminalReader.getNumberOfCores());
            List<Processor> scheduledProcessors = alg.execute();
            terminalReader.writeOutput(scheduledProcessors);
            _visController.update(null);

        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doVis() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Create the FXMLLoader
                FXMLLoader loader = new FXMLLoader();
                // Path to the FXML File
                String fxmlDocPath = "src/gui/ProcGraph.fxml";
                FileInputStream fxmlStream = null;
                try {
                    fxmlStream = new FileInputStream(fxmlDocPath);
                    // Create the Pane and all Details
                    AnchorPane root = null;
                    root = (AnchorPane) loader.load(fxmlStream);
                    Scene scene = new Scene(root);
                    Stage stage = new Stage(StageStyle.DECORATED);
                    stage.setTitle("fuk noes");
                    stage.setScene(scene);
                    stage.show();
                    System.out.println("start");
                    System.out.println("finish");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
