package com.app.vending.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Đại diện cho một đơn hàng hoàn chỉnh.
 */
public class Order {

    private static int counter = 1;

    private final int            id;
    private final List<OrderItem> items;
    private final LocalDateTime  createdAt;

    public Order(List<OrderItem> items) {
        this.id        = counter++;
        this.items     = List.copyOf(items);
        this.createdAt = LocalDateTime.now();
    }

    public int              getId()       { return id; }
    public List<OrderItem>  getItems()    { return items; }
    public LocalDateTime    getCreatedAt(){ return createdAt; }

    /** Tổng tiền toàn bộ đơn hàng */
    public long getTotal() {
        return items.stream().mapToLong(OrderItem::getSubtotal).sum();
    }

    public String getTotalFormatted() {
        return String.format("%,d VNĐ", getTotal()).replace(',', '.');
    }

    public String getCreatedAtFormatted() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "Đơn #" + id + " | " + getCreatedAtFormatted() + " | Tổng: " + getTotalFormatted();
    }
}
