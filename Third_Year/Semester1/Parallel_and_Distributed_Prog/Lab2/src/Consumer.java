import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer extends Thread {
    private final Queue<Integer> queue;
    private final Lock lock;
    private final Condition condition;
    private final int iters;
    private int sum;

    public Consumer(Queue<Integer> queue, Lock mutex, Condition condition,
                                        int iterations) {
        this.queue = queue;
        this.lock = mutex;
        this.condition = condition;
        this.iters = iterations;
        this.sum = 0;
    }

    public int getSum() {
        return sum;
    }

    // function that computes sum of the multiplied pair of elements
    @Override
    public void run() {
        for (int i = 0; i < iters; i++) {
            this.sum += getProduct();
        }
    }

    public int getProduct() {
        this.lock.lock();
        try {
            while (this.queue.isEmpty()) {
                System.out.println("The queue is empty. Consumer is waiting...");
                // unlock mutex and wait for signal
                this.condition.await();
            }
            int pairProduct = this.queue.remove();
            System.out.println("Consumer gets from queue the product " + pairProduct);
            // signal the sleeping producer thread
            this.condition.signalAll();
            return pairProduct;
        }
        catch (InterruptedException e) {
            System.out.println("Consumer Thread: " + e.getMessage());
            return 0;
        }
        finally {
            this.lock.unlock();
        }

    }

}
