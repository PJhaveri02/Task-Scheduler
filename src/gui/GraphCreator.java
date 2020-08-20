package gui;

import algorithm.Model;
import algorithm.Node;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class GraphCreator extends Application implements Initializable {

    private Model _schedule;
    //pass in model
    public GraphCreator(Model graph) {
        _schedule = graph;
    }

    public void addEdge(){
        //add edge to graph
//        updateGraph
    }

    @Override
    public void start(Stage random) throws Exception {
        Digraph<String, String> g = new DigraphEdgeList<>();

            for (Node n :_schedule.getNodes()){
                g.insertVertex(n.getName());
                for(Node j: n.getDependencies()){
                    g.insertEdge(j.getName(),n.getName(),j.getName()+"->"+n.getName()+"("+Integer.toString(j.get_weight())+")");
                }
            }

            SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
            //create own own strategy
//        SmartPlacementStrategy strategy = new SmartPlacementStrategy();
            SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
            Scene scene = new Scene(graphView, 1024, 768);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("JavaFXGraph Visualization");
            stage.setScene(scene);
            stage.show();

            graphView.init();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
