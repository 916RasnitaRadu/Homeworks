import mpi.MPI;

public class Main {
    public static void main(String[] args) {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        //example
//        Graph graph2 = new Graph(5);
//        graph2.addEdge(0,1);
//        graph2.addEdge(1,2);
//        graph2.addEdge(2,3);
//        graph2.addEdge(3,4);
//        graph2.addEdge(4,0);
//        graph2.addEdge(2,0);
//        graph2.addEdge(0,4);
//        graph2.addEdge(4,3);
//        graph2.addEdge(3,1);
//
//        Colors colors2 = new Colors(3);
//        colors2.addColor(0, "red");
//        colors2.addColor(1, "green");
//        colors2.addColor(2, "blue");

   //     Graph graph2 = Graph.generateRandomGraph(10);
    //    System.out.println(graph2);
        Colors colors2 = new Colors(3);
        colors2.addColor(0, "red");
        colors2.addColor(1, "green");
        colors2.addColor(2, "blue");
//        colors2.addColor(3, "yellow");
//        colors2.addColor(4, "pink");

        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1,2);
        g.addEdge(2,3);
        //g.addEdge(3,0);
        g.addEdge(0,2);
        g.addEdge(1,3);

        if (rank==0){
            System.out.println("Main process");

            try{
                long start = System.nanoTime();

                System.out.println(GraphColoring.graphColoringMain(size,g,colors2));
                long stop = System.nanoTime();

                long time = stop - start;
                System.out.println("Time: " + time / 1000000 + " ms");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Process number: "+ rank);

            int colorsNumber = colors2.getColorsNumber();

            GraphColoring.graphColoringChild(rank, size, g, colorsNumber);
        }
        MPI.Finalize();

    }
}

