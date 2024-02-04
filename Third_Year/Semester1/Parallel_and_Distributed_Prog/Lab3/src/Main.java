import model.Matrix;
import model.exception.MatrixException;
import runner.NormalThreadRunner;
import runner.ThreadPoolRunner;

public class Main {
    private static final int n1 = 1000;
    private static final int m1 = 1000;
    private static final int n2 = 1000;
    private static final int m2 = 1000;

    private static final int NO_THREADS = 12;
    private static final String STRATEGY = "Pool";
    private static final String FUNCTION = "kth";

    public static void main(String[] args) {
        Matrix first = new Matrix(n1, m1);
        Matrix second = new Matrix(n2, m2);

        first.populate();
        second.populate();

//        System.out.println("The matrices are: ");
//        System.out.println(first);
//        System.out.println(second);

        if (first.cols == second.rows) {
            Matrix result = new Matrix(first.rows, second.cols);

            double start = System.nanoTime() / 1_000_000.0;

            if (STRATEGY.equals("Pool")) {
                try {
                    ThreadPoolRunner.run(first, second, result, NO_THREADS, FUNCTION, start);
                }
                catch (MatrixException e) {
                    System.err.println(e.getMessage());
                }
            }
            else if (STRATEGY.equals("Normal")) {
                try {
                    NormalThreadRunner.run(first,second,result,NO_THREADS, FUNCTION, start);
                }
                catch (MatrixException e) {
                    System.err.println(e.getMessage());
                }
            }
            else {
                System.err.println("Invalid approach.");
            }
//            double end = System.nanoTime() / 1_000_000.0;
//            System.out.println("Time elapsed: " + (end-start)/1000 + " seconds");

        }
        else {
            System.err.println("The matrices can't be multiplied");
        }
    }
}