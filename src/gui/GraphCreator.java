package gui;

import algorithm.Model;
import algorithm.Node;
import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GraphCreator implements Runnable {

    private Model _schedule;
    //pass in model
    public GraphCreator(Model graph) {
        _schedule = graph;
    }

    @Override
    public void run(){
        Digraph<String, String> g = new DigraphEdgeList<>();

            for (Node n :_schedule.getNodes()){
                g.insertVertex(n.toString());
            }
            for (Node n :_schedule.getNodes()){
                for(Node j: n.getDependencies()){
                    g.insertEdge(j.toString(),n.toString(),j.toString()+"->"+n.toString()+"("+Integer.toString(j.get_weight())+")");
                }
            }


            SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
//            SmartPlacementStrategy strategy = new SmartRandomPlacementStrategy();
            //create own own strategy
//        SmartPlacementStrategy strategy = new SmartPlacementStrategy();
            SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
            Scene scene = new Scene(graphView, 1024, 768);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Initial Graph");
            stage.setScene(scene);
            stage.show();

            graphView.init();
//            graphView.setAutomaticLayout(true);


    }
}
