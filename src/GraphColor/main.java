package GraphColor;

import org.jgrapht.alg.ChromaticNumber;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Ryan DePrekel on 4/10/16.
 */
public class main {
    public static void main(String args[]) throws FileNotFoundException{
        int i = 0;
        int input;
        int numVertices = 50000;
        int numEdgesPerVertex = 5;
        long startTime, duration, total;
        Random rand = new Random();
        int numElements = numVertices * (1 + numEdgesPerVertex);
        Scanner buffer = new Scanner(System.in);
        Scanner reader;
        MyGraph g;
        System.out.print("Graph Coloring Algorithm\n1) Enter a file name for a graph\n2) Attempt to generate a random graph\n$");
        input = buffer.nextInt();

        switch(input){
            case 1:
                System.out.print("Please enter a file: ");
                buffer.nextLine();
                String fileName = buffer.nextLine();
                File file = new File(fileName);
                try {
                    reader = new Scanner(file);
                    startTime = System.currentTimeMillis();
                    g = new MyGraph(reader);

                    System.out.println("Coloring of your graph: ");
                    g.greedyColor();
                    System.out.println(g.getE());
                    reportPerformanceFor("Greedy Coloring", startTime);
                }catch(FileNotFoundException e){
                    e.getStackTrace();
                }
                break;


            //    Poorly attempt to generate a random graph, this graph may be unconnected
            //    and produce parallel edges

            case 2:
                g = new MyGraph(numVertices);
                while(i < numVertices) {
                    for (int j = 0; j < numEdgesPerVertex; j++) {
                        int w;
                        if ((w = rand.nextInt(numVertices)) != i)
                            g.addEdge(i, w);
                    }
                    i++;
                }
                System.out.print(g.toString());
                startTime = System.currentTimeMillis();
                g.greedyColor();
                reportPerformanceFor("Greedy Coloring", startTime);
                break;
        }

    }

    private static void reportPerformanceFor(String msg, long refTime)
    {
        double time = (System.currentTimeMillis() - refTime) / 1000.0;
        double mem = usedMemory()
                / (1024.0 * 1024.0);
        mem = Math.round(mem * 100) / 100.0;
        System.out.println(msg + " (" + time + " sec, " + mem + "MB)");
    }

    private static long usedMemory()
    {
        Runtime rt = Runtime.getRuntime();

        return rt.totalMemory() - rt.freeMemory();
    }
}
