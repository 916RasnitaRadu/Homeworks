package model;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class InventoryCheck implements Runnable{
    private Inventory inventory;

    private Lock lock = new ReentrantLock();

    public InventoryCheck(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void run() {
        lock.lock();
        System.out.println("Performing inventory check...");
        List<Bill> bills = inventory.getBills();
        List<Product> products = inventory.getProducts();
        List<Lock> productLocks = inventory.getLocks();
        int totalMoney = 0;

        // Check that all the sold products and money are justified by the recorded bills
        for (Bill bill : bills) {
            for (BillItem item : bill.getItems()) {
                Product product = item.getProduct();
                int quantity = item.getQuantity();
                totalMoney += item.getItemTotalPrice();
            }
        }

        if (totalMoney != inventory.getMoney()) {
            System.out.println("Error: Total money not justified by bills!");
        }

        System.out.println("Inventory check completed. Is ok!");
        lock.unlock();
    }
}
