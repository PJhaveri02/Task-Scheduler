package gui;

import algorithm.Model;
import algorithm.Node;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertex;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;

import java.util.Collection;
import java.util.List;

public class DumbPlacementStrategy implements SmartPlacementStrategy{


    private Model m;
    public DumbPlacementStrategy(Model m){
//        m.confirm();
    }

    @Override
    public <V, E> void place(double width, double height, Graph<V, E> graph, Collection<? extends SmartGraphVertex<V>> verts) {

        double yIncriment = height/m.getLevels().size();
        int i =0;
        for (List<Node> level : m.getLevels()){

            int j=0;
            double xIncriment = width/level.size();
            for(Node n : level){
                SmartGraphVertex vert = verts.stream().filter(node -> n.toString().equals(node.toString()).findAny().orElse(null));
                vert.setPosition(xIncriment*j, yIncriment*i);
                j++;
            }
            i++;
        }
    }
}
