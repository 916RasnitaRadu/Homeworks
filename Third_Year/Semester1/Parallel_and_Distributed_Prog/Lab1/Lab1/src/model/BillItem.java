package model;

public class BillItem {
    // a product together with its quantity and the cost of product selling
    private Product product;
    private int quantity;

    public BillItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getItemTotalPrice() {
        return product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
