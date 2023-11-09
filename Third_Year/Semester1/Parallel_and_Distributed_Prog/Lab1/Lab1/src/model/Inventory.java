package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Inventory {
    private int money;
    private final List<Bill> bills;
    private final List<Product> products;
    private final Lock lock;

    List<Lock> locks;

    public Inventory(List<Product> products) {
        this.money=0;
        this.products = products;
        this.bills=new ArrayList<>();
        this.lock= new ReentrantLock();
        this.locks = new ArrayList<>();

        for (int i = 1 ; i <= products.size(); i++)
        {
            this.locks.add(new ReentrantLock());
        }
    }

    public void addBill( Bill bill ,int  new_money ) throws InterruptedException {
        lock.lock();
        this.bills.add(bill);
        this.money += new_money;
        lock.unlock();
    }

    public int getMoney() {
        return money;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Lock> getLocks() {
        return locks;
    }
}
