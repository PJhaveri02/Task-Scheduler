package gui;

import algorithm.Processor;
import algorithm.algorithm;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class ProcGraphController implements Runnable{

    private List<Processor> bestSchedule;
    private int totalPerms;
    private int killedPerms;
    private algorithm executingAlg;

    @Override
    public void run() {
        Group root = new Group();
        Scene scene = new Scene(root,600, 300);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("fuk noes");
        stage.setScene(scene);
        stage.show();
    }
}
