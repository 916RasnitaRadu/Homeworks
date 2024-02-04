package runner;

import model.Matrix;
import model.exception.MatrixException;
import utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {
    public static void run(Matrix first, Matrix second, Matrix result, int nrThreads, String type, double start) throws MatrixException {
        ExecutorService service = Executors.newFixedThreadPool(nrThreads);

        switch (type) {
            case "row":
                for (int i = 0 ; i < nrThreads; i++)
                {
                    service.submit(Utils.initRowThread(i, first,second,result,nrThreads));
                }
                break;
            case "column":
                for (int i = 0 ; i < nrThreads; i++)
                {
                    service.submit(Utils.initColThread(i, first,second,result,nrThreads));
                }
                break;
            case "kth":
                for (int i = 0 ; i < nrThreads; i++)
                {
                    service.submit(Utils.initKThread(i, first,second,result,nrThreads));
                }
                break;
            default:
                throw new MatrixException("Invalid type");
        }

        service.shutdown();
        try {
            if (!service.awaitTermination(300, TimeUnit.SECONDS)) {
                service.shutdown();
            }
            double end = System.nanoTime() / 1_000_000.0;

            System.out.println("Time elapsed: " + (end-start)/1000 + " seconds");
       //     System.out.println("result:\n" + result.toString());
        }
        catch (Exception e) {
            service.shutdown();
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
