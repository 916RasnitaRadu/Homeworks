import algorithms.Algorithm;
import mpi.MPI;
import polynomial.Polynomial;

import java.io.File;

public class Main {

    private static final int POLYNOMIAL_SIZE = 5;

    private static final String METHOD = "karatsuba";

    private static void master(Polynomial p1, Polynomial p2, int procs) {
        long startTime = System.currentTimeMillis();
        int length = p1.getSize() / (procs - 1);
        int start = 0;
        int end = 0;

        for (int i = 1; i < procs; i++) {
            start = end;
            end += length;
            if (i == procs - 1) {
                end = p1.getSize();
            }
            MPI.COMM_WORLD.Send(new Object[]{p1}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new Object[]{p2}, 0, 1, MPI.OBJECT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{start}, 0, 1, MPI.INT, i, 0);
            MPI.COMM_WORLD.Send(new int[]{end}, 0, 1, MPI.INT, i, 0);
        }

        Object[] results = new Object[procs - 1];
        for (int i = 1; i < procs; i++) {
            MPI.COMM_WORLD.Recv(results, i - 1, 1, MPI.OBJECT, i, 0);
        }

        Polynomial result = Algorithm.buildResult(results);
        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime);
        System.out.println(METHOD + ":\n");
        System.out.println("Execution time: " + duration);
        System.out.println("Result: " + result + "\n");
    }


    public static void main(String[] args) {
        MPI.Init(args);

        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (me == 0) {
            System.out.println("Master process: \n");
            Polynomial p1 = new Polynomial(POLYNOMIAL_SIZE);
            p1.generateCoefficients();
            Polynomial p2 = new Polynomial(POLYNOMIAL_SIZE);
            p2.generateCoefficients();
            master(p1, p2, size);
        }
        else {
            if (METHOD.equalsIgnoreCase("regular")) {
                regularWorker(me);
            }
            else {
                karatsubaWorker(me);
            }
        }
        MPI.Finalize();
    }

    private static void karatsubaWorker(int me) {
        Object[] p1 = new Object[2];
        Object[] p2 = new Object[2];
        int[] start = new int[1];
        int[] end = new int[1];

        MPI.COMM_WORLD.Recv(p1, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(p2, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(start, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(end, 0, 1, MPI.INT, 0, 0);

        Polynomial poly1 = (Polynomial) p1[0];
        Polynomial poly2 = (Polynomial) p2[0];

        for (int i = 0; i < start[0]; i++) {
            poly1.getCoefficients().set(i, 0);
        }
        for (int j = end[0]; j < poly1.getCoefficients().size(); j++) {
            poly1.getCoefficients().set(j, 0);
        }

        Polynomial result = Algorithm.karatsubaSequential(poly1, poly2);
        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);
    }

    private static void regularWorker(int me) {
        Object[] p1 = new Object[2];
        Object[] p2 = new Object[2];
        int[] start = new int[1];
        int[] end = new int[1];

        MPI.COMM_WORLD.Recv(p1, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(p2, 0, 1, MPI.OBJECT, 0, 0);
        MPI.COMM_WORLD.Recv(start, 0, 1, MPI.INT, 0, 0);
        MPI.COMM_WORLD.Recv(end, 0, 1, MPI.INT, 0, 0);

        Polynomial poly1 = (Polynomial) p1[0];
        Polynomial poly2 = (Polynomial) p2[0];

        Polynomial result = Algorithm.multiplySimple(poly1, poly2, start[0], end[0]);
        MPI.COMM_WORLD.Send(new Object[]{result}, 0, 1, MPI.OBJECT, 0, 0);
    }
}