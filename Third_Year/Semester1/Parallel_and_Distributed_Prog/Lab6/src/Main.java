import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static final int THREAD_COUNT = 5;

    private static List<List<Integer>> loadGraph(String path) throws FileNotFoundException {
        List<List<Integer>> graph = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(path))) {
            int size = Integer.parseInt(scanner.nextLine());
            for(int i = 0; i < size; i++){
                graph.add(new ArrayList<>());
            }
            while (scanner.hasNextLine()){
                String[] splitEdge = scanner.nextLine().split(" ");
                graph.get(Integer.parseInt(splitEdge[0])).add(Integer.parseInt(splitEdge[1]));
            }
        }

        return graph;
    }

    public static void main(String[] args) throws Exception {


        List<List<Integer>> graph = loadGraph("g1.txt");

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        AtomicBoolean foundHamiltonCycle = new AtomicBoolean(false);
        List<Integer> result = new ArrayList<>();

        System.out.println(graph);

        for (int i = 0; i < graph.size(); i++) {
            // searching for a Hamiltonian Cycle from every node
            executorService.submit(new HamiltonianCycleSearchTask(graph, i, foundHamiltonCycle, result));
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        if (foundHamiltonCycle.get()) {
            System.out.println(result);
        }
        else {
            System.out.println("No hamiltonian cycles :(");
        }
    }
}