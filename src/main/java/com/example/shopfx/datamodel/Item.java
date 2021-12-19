package com.example.shopfx.datamodel;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {

    private String itemName;
    private ItemType itemType;
    private String itemCode;
    private BigDecimal price;
    private int quantity;

    public Item(String itemName, ItemType itemType, String itemCode, BigDecimal price, int quantity) {
        this.itemName = itemName;
        this.itemType = itemType;
        this.itemCode = itemCode;
        this.price = price;
        this.quantity = quantity;
    }

    public Item(Item item) {
        this.itemName = item.getItemName();
        this.itemType = item.getItemType();
        this.itemCode = item.getItemCode();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemName, item.itemName) && itemType == item.itemType && Objects.equals(itemCode, item.itemCode) && Objects.equals(price, item.price);
    }

    @Override public int hashCode() {
        return Objects.hash(itemName, itemType, itemCode, price);
    }

    @Override public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemType=" + itemType +
                ", itemCode='" + itemCode + '\'' +
                ", price=" + price +
                '}';
    }

}
