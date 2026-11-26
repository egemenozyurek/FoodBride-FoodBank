/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import exceptions.InvalidStockDataException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * One line of stock in the food bank, e.g. 4 x cans of beans
 *
 * @author egeme
 */
public class StockItem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String stockId, name, category, expiryDate, storageType;
    private int quantity;

    public StockItem(String stockId, String name, String category, String expiryDate,
            String storageType, int quantity) throws InvalidStockDataException {

        if (stockId == null || stockId.isEmpty()) {
            throw new InvalidStockDataException("Please enter a Stock ID");
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidStockDataException("Please enter a product name.");
        }
        
        if (quantity <= 0) {
            throw new InvalidStockDataException("Quantity must be greater than 0.");
        }
        
        try{
            LocalDate.parse(expiryDate);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new InvalidStockDataException(
            "Expiry date must be in the format yyyy-MM-dd");
        }

        this.stockId = stockId;
        this.name = name;
        this.category = category;
        this.expiryDate = expiryDate;
        this.storageType = storageType;
        this.quantity = quantity;
    }
    
    public static int parseQuantity(String text) throws InvalidStockDataException {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new InvalidStockDataException("Quantity must be a whole number");
        }
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

    public String displayDetails() {
        return String.format("%-5s %-15s %-10s %4d  %s  %s",
                stockId, name, category, quantity, expiryDate, storageType);
    }
}
