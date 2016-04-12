package GraphColor;

import java.util.*;
import java.io.*;

/**
 * Created by Ryan DePrekel on 4/10/16.
 * Graph Class to add edges and color the graph.
 * Some of the methods are from Algorithms Fourth Edition page 526
 *
 * Greedy Coloring:
 * Color first vertex with the first color [0]:
 * For the remaining V-1 vertices
 *      Consider the currently picked vertex and color it with the lowest
 *      numbered color that has not been used on any previously colored vertices
 *      adjacent to it. If all previously colored vertices appears on vertices
 *      adjacent to V, assign a new color to it.
 */
public class MyGraph {

    private final int V; // number of  vertices
    private int E;       // number of edges
    private LinkedList<Integer>[] adj; //adjacency list

    public MyGraph(int V){
        this.V = V;
        this.E = 0;
        adj =  new LinkedList[V];
        for(int i=0; i<V; i++){
            adj[i] = new LinkedList<Integer>();
        }
    }

    /*
        For graph creation via a file
        In format of:
        V E
        V W edgeWeight
        .
        .

     */
    public MyGraph(Scanner reader){
        this(reader.nextInt());
        int edge = reader.nextInt();
        while(reader.hasNextInt()){
            int v = reader.nextInt() - 1; //Indices in the file go from 1-V would cause an ArrayOutOfBoundsException
            int w = reader.nextInt() - 1;
            addEdge(v,w);
            reader.nextInt(); // Read the edge weight, but do nothing with it.
        }

    }

    /*
        Adds an edge to the graph from vertex v to vertex w
     */
    public void addEdge(int v, int w){
        adj[v].add(w); //Add w to v's list
        adj[w].add(v); //Add v to w's list
        E++;           //Increment edge count
    }

    /*
        Prints the adjacency list of the graph
     */
    @Override public String toString(){
        String s = V + " vertices, " + E + " edges\n";
        for(int v = 0; v < V; v++){
            s += v + ": ";
            for(int w : this.adj(v)){
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public int getV(){
        return V;
    }

    public int getE(){
        return E;
    }

    //Assigns "colors" starting from 0 to all vertices and
    //prints the assignment of "colors"

    public void greedyColor(){
        int color[] = new int[V];
        int i, j, k, numColors=0; //iterators
        boolean used[] = new boolean[V];

        //Assign the first color to first vertex
        color[0] = 0;

        //Initialize remaining V-1 vertices as unassigned
        for(i = 1; i < V; i++ ){
            color[i] = -1;
        }

        // A temporary array to store the used colors. True
        // value of the used[j] would mean that the color cr is
        // assigned to one of its adjacent vertices
        for(j = 0; j < V; j++){
            used[j] = false;
        }

        // Assign colors to remaining V-1 vertices
        for(k = 1; k < V; k++){
            //Process all adjacent vertices and flag their colors
            // as unused
            Iterator<Integer> it = adj[k].iterator();
            while(it.hasNext()){
                i = it.next();
                if(color[i] != -1){
                    used[color[i]] = true;
                }
            }
            //Find the first available color
            for(j = 0; j < V; j++){
                if(!used[j]) {
                    break;
                }
            }

            color[k] = j; //Assign the found color

            // Reset the values back to false for the next iteration
            it = adj[k].iterator();
            while(it.hasNext()){
                i = it.next();
                if(color[i] != -1)
                    used[color[i]] = false;
            }
        }
        for(i = 0; i < V; i++){
            if(color[i] > numColors){
                numColors = color[i];
            }
            System.out.println("Vertex " + i + " ---> Color " + color[i]);
        }
        System.out.println("This was done in " + (numColors+1) + " colors.");
    }
}
