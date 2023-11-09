import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.List;

public class Producer extends Thread {
    private final int qSize;
    private final Queue<Integer> q;
    private final Lock lock;
    private final Condition condition;
    private final List<Integer> firstVector;
    private final List<Integer> secondVector;

    public Producer(int size, Queue<Integer> q, Lock lock, Condition condition,
                    List<Integer> firstVector, List<Integer> secondVector) {
        this.qSize = size;
        this.q = q;
        this.lock = lock;
        this.condition = condition;
        this.firstVector = firstVector;
        this.secondVector = secondVector;
    }

    @Override
    public void run() {
        for (int i = 0; i < firstVector.size(); i++) {
            putProduct(firstVector.get(i) * secondVector.get(i));
        }
        System.out.println("Producer sent product");
    }

    private void putProduct(int product) {
        this.lock.lock();
        try {
            while (q.size() >= this.qSize) {
                System.out.println("The queue is full. Producer is waiting...");
                // unlock mutex and wait for signal
                this.condition.await();
            }
            System.out.println("Producer adds to queue the product " + product);
            this.q.add(product);
            // signal the sleeping consumer thread
            this.condition.signalAll();
        }
        catch (InterruptedException i) {
            System.out.println("Producer Thread: " + i.getMessage());
        }
        finally {
            lock.unlock();
        }
    }
}
