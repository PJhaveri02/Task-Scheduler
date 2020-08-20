package gui;

import algorithm.Model;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GraphCreator {

    private Model _schedule;
    //pass in model
    public GraphCreator(Model graph) {
        _schedule = graph;
    }

    public void showGraph(){
        Graph<String, String> g = new GraphEdgeList<>();
//... see example below

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

}
