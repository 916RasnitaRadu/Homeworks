package model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Product {
    // we will lock the selling based on the Product
    private final int id ;
    private final int price;
    private int quantity;
    private Lock lock;

    public Product (int id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity= quantity;
        this.lock = new ReentrantLock();
    }

    public int getId() {
        return id;
    }

    public int getAvailableQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void sell(int sell_quantity) throws InterruptedException {
        if (lock.tryLock(10, TimeUnit.SECONDS)) {// to avoid thread starvation and deadlock
            if (sell_quantity <= quantity) {
                quantity -= sell_quantity;
            } else {
                throw new IllegalArgumentException("Not enough available quantity for product with ID " + id + " have " + Integer.toString(this.getAvailableQuantity()) + " want " + Integer.toString(sell_quantity));
            }

            lock.unlock();
        }
    }
}
