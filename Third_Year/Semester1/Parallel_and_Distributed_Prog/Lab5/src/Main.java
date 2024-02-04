import algorithms.ParallelClassic;
import algorithms.ParallelKaratsuba;
import algorithms.SequentialClassic;
import algorithms.SequentialKaratsuba;
import model.Polynomial;
import model.enums.Algorithm;
import model.enums.Method;

import java.util.concurrent.ExecutionException;

public class Main {

    private static final Method METHOD = Method.PARALLEL;

    private static final Algorithm ALGORITHM = Algorithm.KARATSUBA;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Polynomial p1 = new Polynomial(100);
        System.out.println("Polynomial p1 = " + p1);

        Polynomial p2 = new Polynomial(100);
        System.out.println("Polynomial p1 = " + p2);

        long start = System.nanoTime();

        run(p1, p2);

        long stop = System.nanoTime();
        double time = ((double) stop - start) / 1_000_000_000.0;

        System.out.println("Elapsed time: " + time + " seconds");
    }

    private static void run(Polynomial p1, Polynomial p2) throws InterruptedException, ExecutionException {
        Polynomial result = null;

        if (METHOD.equals(Method.SEQUENTIAL)) {
            if (ALGORITHM.equals(Algorithm.CLASSIC)) {
                result = SequentialClassic.multiply(p1, p2);
            }
            else {
                result = SequentialKaratsuba.multiply(p1, p2);
            }
        }
        else {
            if (ALGORITHM.equals(Algorithm.CLASSIC)) {
                result = ParallelClassic.multiply(p1, p2);
            }
            else {
                result = ParallelKaratsuba.multiply(p1, p2, 1);
            }
        }

        System.out.println("p1 * p2 = " + result.toString());
    }
}