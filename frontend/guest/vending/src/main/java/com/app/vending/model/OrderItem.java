package com.app.vending.model;

/**
 * Snapshot của một Item khi tạo đơn hàng.
 * Tách biệt với Item để tránh thay đổi sau khi đặt hàng.
 */
public class OrderItem {

    private final String name;
    private final long   price;
    private final int    quantity;

    public OrderItem(Item item) {
        this.name     = item.getName();
        this.price    = item.getPrice();
        this.quantity = item.getQuantity();
    }

    public String getName()     { return name; }
    public long   getPrice()    { return price; }
    public int    getQuantity() { return quantity; }
    public long   getSubtotal() { return price * quantity; }

    public String getPriceFormatted() {
        return String.format("%,d VNĐ", price).replace(',', '.');
    }

    public String getSubtotalFormatted() {
        return String.format("%,d VNĐ", getSubtotal()).replace(',', '.');
    }

    @Override
    public String toString() {
        return name + " x" + quantity + " = " + getSubtotalFormatted();
    }
}
