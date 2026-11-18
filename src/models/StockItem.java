/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 * One line of stock in the food bank, e.g. 4 x cans of beans
 * 
 * @author egeme
 */
public class StockItem {
    
    private String stockId, name, category, expiryDate, storageType;
    private int quantity;

    public StockItem(String stockId, String name, String category, String expiryDate, String storageType, int quantity) {
        this.stockId = stockId;
        this.name = name;
        this.category = category;
        this.expiryDate = expiryDate;
        this.storageType = storageType;
        this.quantity = quantity;
    }

    public String getStockId() {
        return stockId;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getStorageType() {
        return storageType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    // true when the quantity has dropped below the given threshold.
    public boolean isLowStock(int threshold) {
        return quantity < threshold;
    }
    
    public String displayDetails(){
        return  String.format("%-5s %-15s %-10s %4d  %s  %s", 
                stockId, name, category, quantity, expiryDate, storageType);
    }
}
