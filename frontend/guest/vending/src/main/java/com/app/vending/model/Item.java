package com.app.vending.model;

import javafx.beans.property.*;


public class Item {

    private final StringProperty  name;
    private final LongProperty    price;       // đơn vị: VNĐ
    private final StringProperty  imagePath;  // đường dẫn ảnh trong resources
    private final IntegerProperty quantity;   // số lượng người dùng chọn

    public Item(String name, long price, String imagePath) {
        this.name      = new SimpleStringProperty(name);
        this.price     = new SimpleLongProperty(price);
        this.imagePath = new SimpleStringProperty(imagePath);
        this.quantity  = new SimpleIntegerProperty(0);
    }

    // ── name ─────────────────────────────────────────────────────────────────
    public StringProperty nameProperty()    { return name; }
    public String getName()                 { return name.get(); }
    public void   setName(String v)         { name.set(v); }

    // ── price ─────────────────────────────────────────────────────────────────
    public LongProperty priceProperty()     { return price; }
    public long  getPrice()                 { return price.get(); }
    public void  setPrice(long v)           { price.set(v); }

    /** Trả về chuỗi hiển thị, VD: "15.000 VNĐ" */
    public String getPriceFormatted() {
        return String.format("%,d VNĐ", price.get()).replace(',', '.');
    }

    // ── imagePath ─────────────────────────────────────────────────────────────
    public StringProperty imagePathProperty() { return imagePath; }
    public String getImagePath()              { return imagePath.get(); }
    public void   setImagePath(String v)      { imagePath.set(v); }

    // ── quantity ─────────────────────────────────────────────────────────────
    public IntegerProperty quantityProperty() { return quantity; }
    public int  getQuantity()                 { return quantity.get(); }
    public void setQuantity(int v)            { quantity.set(Math.max(0, v)); }
    public void increment()                   { quantity.set(quantity.get() + 1); }
    public void decrement()                   { if (quantity.get() > 0) quantity.set(quantity.get() - 1); }

    // ── helpers ───────────────────────────────────────────────────────────────
    public long getSubtotal() { return price.get() * quantity.get(); }

    @Override
    public String toString() {
        return getName() + " x" + getQuantity() + " = " + getSubtotal() + " VNĐ";
    }
}
