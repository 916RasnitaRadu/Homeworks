package model;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private final List<BillItem> items;
    private int totalPrice;

    public Bill() {
        this.items =new ArrayList<>();
        this.totalPrice= 0;
    }

    public void addItem(Product product, int quantity) {
        BillItem billItem = new BillItem(product, quantity);
        items.add(billItem);
        totalPrice += billItem.getItemTotalPrice();

    }

    public List<BillItem> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
