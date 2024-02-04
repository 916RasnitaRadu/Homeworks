package algorithms;

import model.Polynomial;
import model.Task;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelClassic {

    private static final int THREADS = 4;

    public static Polynomial multiply(Polynomial p1, Polynomial p2) throws InterruptedException {
        int sizeOfResult = p1.getDegree() + p2.getDegree() + 1;

        List<Integer> coeffs = IntStream.range(0, sizeOfResult).mapToObj(i -> 0).collect(Collectors.toList());
        Polynomial result = new Polynomial(coeffs);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS);

        // The result polynomial is divided into segments, and each segment is assigned to a thread for computation.
        int step = sizeOfResult / THREADS;

        if (step == 0) {
            step = 1;
        }

        int end;
        for (int i = 0; i < sizeOfResult; i += step) {
            end = i + step;
            Task task = new Task(i, end, p1, p2, result); // here we create a Task of size step for each computation
            executor.execute(task);
        }

        executor.shutdown();
        executor.awaitTermination(50, TimeUnit.SECONDS);

        return result;
    }
}
