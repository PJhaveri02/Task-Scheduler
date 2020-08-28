package gui;

import algorithm.Processor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.List;

public class ProcGraphController{

    private List<Processor> _bestSchedule;
    private int _totalPerms;
    private int _killedPerms;
    @FXML
    private HBox _processorContainer;



    public ProcGraphController(){
        _totalPerms = 0;
        _killedPerms = 0;
        _bestSchedule = null;
    }

    public void update (List<Processor> newBest){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateDisplay(_bestSchedule);
                }
            });


    }

    @FXML
    public void updateDisplay(List<Processor> bestSchedule) {
        //_processorContainer.pro;
        Button b = new Button("please for the love of god");
        Label x = new Label("nothing to see here");
        _processorContainer.getChildren().addAll(x,b );
    }
}
