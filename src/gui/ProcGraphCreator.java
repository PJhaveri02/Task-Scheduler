package gui;

import algorithm.Processor;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ProcGraphCreator implements Runnable{

    private List<Processor> _bestSchedule;
    private int _totalPerms;
    private int _killedPerms;
    private ProcGraphController controller;

    public ProcGraphCreator(){
        _totalPerms = 0;
        _killedPerms = 0;
        _bestSchedule = null;
    }

    public void update (List<Processor> newBest){
        if(newBest!=_bestSchedule){
            _bestSchedule = newBest;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    controller.updateDisplay(_bestSchedule);
                }
            });

        }

    }



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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




        // Create the Scene

    }
}
