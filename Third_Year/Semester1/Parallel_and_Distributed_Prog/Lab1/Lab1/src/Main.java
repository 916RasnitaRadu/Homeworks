import model.Inventory;
import model.InventoryCheck;
import model.Product;
import model.Sale;

import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        int NR_PRODUCTS = 100;
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < NR_PRODUCTS; i++) {
            int price = random.nextInt(1,NR_PRODUCTS);
            int quantity =  random.nextInt(10, 400);

            productList.add(new Product(i+1, price, quantity));
        }

        // initialise inventory
        Inventory inventory = new Inventory(productList);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new InventoryCheck(inventory),1,20, TimeUnit.SECONDS);

        Sale[] sales = new Sale[10];
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            for (int i = 0; i < 10; i++) {
                sales[i] = new Sale(i + 1, inventory);
                executorService.execute(sales[i]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            executorService.shutdown();
            scheduler.awaitTermination(5, TimeUnit.MINUTES);
            scheduler.shutdown();
        }
    }
}