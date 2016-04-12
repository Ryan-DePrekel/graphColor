package GraphColor;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.VertexFactory;
import org.jgrapht.alg.NeighborIndex;
import org.jgrapht.generate.RandomGraphGenerator;
import org.jgrapht.generate.ScaleFreeGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Ryan DePrekel on 4/10/16.
 */
public class Graph {
    private UndirectedGraph<Integer,DefaultEdge> g;
    private Integer[] colors;
    private int V, E;

    public Graph(int V){
        this.V = V;
        E = V/2;
        ScaleFreeGraphGenerator<Integer,DefaultEdge> graphGen = new ScaleFreeGraphGenerator<Integer, DefaultEdge>(V,E);
        g = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
        graphGen.generateGraph(g, new IntVertexFactory(), null);
        colors = new Integer[V];
        for(int i = 0; i < V; i++)
            colors[i] = -1;
    }

    public void coloring(){
        NeighborIndex nw = new NeighborIndex(g);
        Object[] neighbors;
        Boolean[] isColored = new Boolean[V];
        int largestColor,i,j;

        for(i = 0; i < V; i++){
            isColored[i] = false;
        }

        // Color first node of Graph G color 0
        colors[0] = 0;
        largestColor = 0;
        isColored[0] = true;

        // For each node 1 to V-1 compare the neighbors and assign it lowest value color
        for(i = 1; i < V; i++){
            neighbors = nw.neighborListOf(i).toArray();

            for(j = 0; j < neighbors.length-1; j++){
                System.out.println(neighbors[j]);
                if(!isColored[(Integer)neighbors[j]]){ //Node is not colored
                    break;
                }
            }
            colors[i] = j;
            isColored[i] = true;
        }

        printColors();
    }


    public void printColors(){
        for(int i = 0; i < V; i++){
            System.out.println("Vertex " + (i+1) + " is color " + colors[i]);
        }
    }

    public String toString(){
        return g.toString();
    }

    private static class IntVertexFactory implements VertexFactory<Integer> {

        private int nextVertex = 1;


        public Integer createVertex() {
            return nextVertex++;
        }
    }
}
