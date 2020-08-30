package gui;

import algorithm.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import read_inputs.IncorrectInputException;
import read_inputs.TerminalReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import java.util.concurrent.CountDownLatch;


public class Main extends Application{
    private static ProcGraphController _visController;
    private static Model model;

    public static void main(String[] args) {

        System.out.println("gui main");
        TerminalReader terminalReader = new TerminalReader(args);
        try {
            terminalReader.validateInputs();
            model = terminalReader.readInput();
            List<Node> nodesList = model.getNodes();

            algorithm alg;
            if (terminalReader.getNumberOfCores() == 0) {
                alg = new FinalAlgorithm(terminalReader.getProcNum(),nodesList);
            } else {
                alg = new ParallelFinalAlgorithm(terminalReader.getProcNum(), nodesList, terminalReader.getNumberOfCores());
            }
            if (terminalReader.getVisualizationResult()) {
                GraphCreator graph = new GraphCreator(model);
                final CountDownLatch latch = new CountDownLatch(1);
                doVis(latch);
                latch.await();
                Platform.runLater(graph);
                alg.addListener(_visController);
            }

            List<Processor> scheduledProcessors = alg.execute();
            terminalReader.writeOutput(scheduledProcessors);

        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doVis(CountDownLatch latch) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader();
                // Path to the FXML File
                String fxmlDocPath = "src/gui/ProcGraph.fxml";
                FileInputStream fxmlStream = null;
                try {
                    fxmlStream = new FileInputStream(fxmlDocPath);
                    // Create the Pane and all Details
                    AnchorPane root = null;
                    root = (AnchorPane) loader.load(fxmlStream);
                    _visController = loader.getController();
                    latch.countDown();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage(StageStyle.DECORATED);
                    stage.setScene(scene);
                    stage.show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
