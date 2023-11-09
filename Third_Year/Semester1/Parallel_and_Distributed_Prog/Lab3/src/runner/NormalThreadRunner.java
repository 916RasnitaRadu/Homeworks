package runner;

import model.Matrix;
import model.exception.MatrixException;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class NormalThreadRunner {

    public static void run(Matrix first, Matrix second, Matrix result, int nrThreads, String type) throws MatrixException {
        List<Thread> threads = new ArrayList<>();

        switch (type) {
            case "row":
                for (int i = 0 ; i < nrThreads; i++)
                {
                    threads.add(Utils.initRowThread(i, first,second,result,nrThreads));
                }
                break;
            case "column":
                for (int i = 0 ; i < nrThreads; i++)
                {
                    threads.add(Utils.initColThread(i, first,second,result,nrThreads));
                }
                break;
            case "kth":
                for (int i = 0 ; i < nrThreads; i++)
                {
                    threads.add(Utils.initKThread(i, first,second,result,nrThreads));
                }
                break;
            default:
                throw new MatrixException("Invalid type");
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("result:\n" + result.toString());
    }
}
