package gui;

import algorithm.Processor;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.List;

public class ProcGraphController{

    private List<Processor> _bestSchedule;
    private int _totalPerms;
    private int _killedPerms;
    private HBox _processorContainer;


    public ProcGraphController(){
        _totalPerms = 0;
        _killedPerms = 0;
        _bestSchedule = null;
        _processorContainer.getChildren().add(new Label("nothing to see here"));
    }

    public void update (List<Processor> newBest){
        if(newBest!=_bestSchedule){
            _bestSchedule = newBest;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateDisplay(_bestSchedule);
                }
            });

        }

    }

    public void updateDisplay(List<Processor> bestSchedule) {
        _processorContainer.getChildren().add(new Label("nothing to see here"));
    }
}
