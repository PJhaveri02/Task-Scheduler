package gui;

import algorithm.Node;
import algorithm.Processor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class ProcGraphController{
    private List<HBox> _prior;

    private List<Processor> _bestSchedule;
    private int _totalPerms;
    private int _killedPerms;

    @FXML
    private Label _updateCounter;
    private int _numUpdates;

    @FXML
    private VBox _container;
    private HBox _schedule;

    @FXML
    private ComboBox _switcher;

    private int _max;

    private final int SIZE =900;

    public ProcGraphController(){
        _totalPerms = 0;
        _killedPerms = 0;
        _bestSchedule = null;
        _prior = new ArrayList<HBox>();
    }

    public void update (List<Processor> newBest){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateDisplay(newBest);

                }
            });


    }

    public void setMax(int max) {
        _max = max;
    }


    private class mistake{
        String NAME;
        int ENDTIME;
        int WEIGHT;
        public int start(){
            return ENDTIME-WEIGHT;
        }

    }

    private void increment() {
        _numUpdates++;
        _updateCounter.setText("Total updates: " + _numUpdates );
    }

    @FXML
    private void mySanityIsFading(){

    }

    private void updateDisplay(List<Processor> bestSchedule) {
        if(_schedule != null){
            _container.getChildren().clear();
            _prior.add(_schedule);
            _switcher.getItems().add(Integer.toString(_prior.size()));
        }else{
            _switcher.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
                if(!_prior.contains(_schedule)){
                    System.out.println("why lord");
                    _prior.add(_schedule);
                    _switcher.getItems().add("final schedule");
                }
                if(((String)newValue).length()>2){
                    _schedule=_prior.get(_prior.size()-1);
                }else{
                    _schedule = _prior.get(Integer.parseInt((String) newValue)-1);
                }
                _container.getChildren().clear();
                _container.getChildren().add(_schedule);
            });
        }

        _schedule = new HBox();

        _container.getChildren().add(_schedule);
        increment();
        int scale = SIZE/_max;
        initScale2();
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
            addProcToDisplay(i+1,scale,jesus);
            System.out.println("----------");
        }
    }

    private void initScale2(){
        VBox container = new VBox();
        Label title = new Label("oh no");
        Canvas biggerMistake = new Canvas(50,SIZE);
        GraphicsContext gc = biggerMistake.getGraphicsContext2D();
        gc.setLineWidth(2);
        int scale = SIZE/_max;
        for(int i = 0 ; i<_max;i++){
            int y=i*scale;
            if(i%5==0){
                gc.strokeLine(30,y,50,y);
            }else{
                gc.strokeLine(40,y,50,y);
            }
        }
        container.getChildren().addAll(title,biggerMistake);
        _schedule.getChildren().add(container);
    }

    private void initScale(int scale){
        VBox container = new VBox();
        GridPane inner = new GridPane();
        javafx.scene.Node[] nodes = new javafx.scene.Node[_max];
        container.getChildren().addAll(new Label ("Time"), inner);
        for(int i =0; i<_max; i++){
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
        _schedule.getChildren().add(container);
    }

    private void addProcToDisplay(int id, int scale, List<mistake> questionablecodingpractice){
        VBox proc = new VBox();
        GridPane proc2 = new GridPane();

        proc.getChildren().addAll(new Label (Integer.toString(id)),proc2);


        proc.setAlignment(Pos.TOP_CENTER);

        _schedule.getChildren().add(proc);
        int l = questionablecodingpractice.size();
        javafx.scene.Node[] nodes = new javafx.scene.Node[_max];

        int j=0;
        //creates a grid square for each point of weight and colours it accordingly
        Color c = Color.color (Math.random(), Math.random(), Math.random());
        for(int i =0; i<_max; i++){
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
