package model;

import java.util.List;
import java.util.Random;

public class Sale implements Runnable{
    private final int id;
    private final Inventory inventory;
    private final Random random;

    public Sale(int id, Inventory inventory) {
        this.id = id;
        this.inventory = inventory;
        this.random = new Random();

    }

    public int stillQuantity() {
        List<Product> products = inventory.getProducts();
        int maxim = 0;
        for (Product product : products) {
            if (product.getAvailableQuantity() > maxim) {
                maxim = product.getAvailableQuantity();
            }

        }
        return maxim;
    }

    @Override
    public void run() {
        // while so that it continues to try and make sells
        int max_quantity = stillQuantity();
        while (max_quantity != 0) {
            System.out.println("Hello from thread " + Thread.currentThread().getId());
            // create a new bill
            Bill new_bill = new Bill();
            //randomly choose how many items to purchase
            int number_of_items_purchased = random.nextInt(1, 10);
            int number = 0;
            while (number != number_of_items_purchased) {
                max_quantity = stillQuantity();
                // randomly choose a product
                int productId = random.nextInt(1, inventory.getProducts().size());
                // randomly choose a quantity
                int quantity = random.nextInt(1, max_quantity);

                List<Product> products = inventory.getProducts();
                for (Product product : products) {
                    if (product.getId() == productId) {
                        try {
                            product.sell(quantity);
                            new_bill.addItem(product, quantity);
                        } catch (InterruptedException | IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }

                    }
                }
                number += 1;
            }


            try {
                inventory.addBill(new_bill, new_bill.getTotalPrice());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("New Bill Added  " + Thread.currentThread().getId());

        }
    }
}
