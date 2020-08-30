package gui;

import algorithm.Node;
import algorithm.Processor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProcGraphController{

    private List<Processor> _bestSchedule;
    private int _totalPerms;
    private int _killedPerms;

    @FXML
    private Label _numUpdates;
    @FXML
    private HBox _processorContainer;

    @FXML
    private VBox _container;


    public ProcGraphController(){
        _totalPerms = 0;
        _killedPerms = 0;
        _bestSchedule = null;
    }

    public void update (List<Processor> newBest){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateDisplay(newBest);

                }
            });


    }
    private class mistake{
        String NAME;
        int ENDTIME;
        int WEIGHT;
        public int start(){
            return ENDTIME-WEIGHT;
        }
    }

    private void incriment(){
        _numUpdates.
    }
    @FXML
    private void updateDisplay(List<Processor> bestSchedule) {
        incriment();
        //_processorContainer.pro;

        int size =1;
        for(Processor schedule : bestSchedule){
            if(schedule.getTime()>size){
                size =schedule.getTime();
            }
        }
        int scale = 700/size;
        initScale(size,scale);
        int q =0;
        for(int i=0; i<bestSchedule.size(); i++){
            List<mistake> jesus = new ArrayList<mistake>();
            Processor schedule = bestSchedule.get(i);
            for(Node task : schedule.getTasks()){
                mistake ohno = new mistake();
                ohno.NAME = task.toString();
                ohno.ENDTIME = schedule.getEnd(task);
                ohno.WEIGHT = task.get_weight();
                jesus.add(ohno);
                System.out.println(q);
                q++;
            }
            int a=0;
            addProcToDisplay(i+1,scale,size,jesus);
            System.out.println("----------");
        }
    }


    private void addScale(int max){

        //_processorContainer.getChildren().add(0,scale);
    }

    private void initScale(int max, int scale){
        VBox container = new VBox();
        GridPane inner = new GridPane();
        javafx.scene.Node[] nodes = new javafx.scene.Node[max];
        container.getChildren().addAll(new Label ("Time"), inner);
        for(int i =0; i<max; i++){
            //Rectangle r = new Rectangle(50,scale);
            Label r = new Label(Integer.toString(i));
            r.setMinWidth(40);
            r.setMaxWidth(40);
            r.centerShapeProperty();
//            r.setBorder();
//            r.setStroke(Color.BLACK);
//            r.setFill(Color.TRANSPARENT);
            r.setMaxHeight(scale);
            r.setMinHeight(scale);
            Font sadness = Font.font(6);
            r.setFont(sadness);
            r.setStyle("-fx-border-color: black;");
//            r.setStyle("-fx-text-alignment: center");
//            r.setBorder();


            nodes[i] = r;
        }
        inner.addColumn(0,nodes);
        _processorContainer.getChildren().add(container);
    }

    private void addProcToDisplay(int id, int scale, int max, List<mistake> questionablecodingpractice){
        VBox proc = new VBox();
        GridPane proc2 = new GridPane();

        proc.getChildren().addAll(new Label (Integer.toString(id)),proc2);


        proc.setAlignment(Pos.TOP_CENTER);

        _processorContainer.getChildren().add(proc);
        int l = questionablecodingpractice.size();
        javafx.scene.Node[] nodes = new javafx.scene.Node[max];

        int j=0;
        //creates a grid square for each point of weight and colours it accordingly
        Color c = Color.color (Math.random(), Math.random(), Math.random());
        for(int i =0; i<max; i++){
            Color blank = Color.TRANSPARENT;
            Rectangle r = new Rectangle(50,scale);
            nodes[i] = r;
            r.setFill(blank);
            if(j<l) {


                if (i >=  questionablecodingpractice.get(j).start()) {
                    if (i > questionablecodingpractice.get(j).ENDTIME) {
                        i--;
                        j++;
                        c = Color.color(Math.random(), Math.random(), Math.random());
                    } else {
                        r.setFill(c);

                    }
                }
            }
        }
        proc2.addColumn(0,nodes);
    }
}
