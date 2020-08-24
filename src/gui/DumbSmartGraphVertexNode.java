package gui;

import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.SmartGraphVertexNode;


public class DumbSmartGraphVertexNode extends SmartGraphVertexNode {
    private int _level;
    private String _name;
    public DumbSmartGraphVertexNode(Vertex v, double x, double y, double radius, boolean allowMove, int weight,String name) {
        super(v, x, y, radius, allowMove);
//        _level = level;
        _name = name;
    }


    public int getLevel(){
        return  _level;
    }

    public String getName(){
        return _name;
    }
}
