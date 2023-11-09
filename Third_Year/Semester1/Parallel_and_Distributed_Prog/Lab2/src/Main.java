import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        // reading vector size
        Scanner scanner = new Scanner(System.in);
        System.out.println("\tPlease input vector size: ");
        int vectorSize = scanner.nextInt();

        // initializing vectors
        List<Integer> firstVect = new ArrayList<>();
        List<Integer> secondVect = new ArrayList<>();
        Random random = new Random();

         for (int i = 1; i <= 100; i++)
         {
             firstVect.add(1);
             secondVect.add(i);
         }

        System.out.println("First vector: " + firstVect);
        System.out.println("Second vector: " + secondVect);

        // initialing data for threads
        int sharedDataSize = vectorSize / 2; // to assure that we can add data to the queue when there's available space, avoid memory exhaustion
        Queue<Integer> sharedData = new LinkedList<>();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        // initialing threads
        Producer producer = new Producer(sharedDataSize, sharedData, lock, condition, firstVect, secondVect);
        Consumer consumer = new Consumer(sharedData, lock, condition, vectorSize);

        // starting threads
        producer.start();
        consumer.start();

        try { // we try to stop the thread execution
            producer.join();
            consumer.join();
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        // checking the sums
        System.out.println("\n\tSum from the threads is: " + consumer.getSum());
        int sum = 0;
        for (int i = 0; i < vectorSize;i++)
        {
            sum += firstVect.get(i) * secondVect.get(i);
        }
        System.out.println("\tThe correct sum is: " + sum);
    }
}