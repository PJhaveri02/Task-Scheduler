package gui;

import algorithm.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(this.getClass().getResource("/gui/ProcGraph.fxml"));
                    AnchorPane root = loader.load();

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
