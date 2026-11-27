package models;

import exceptions.InvalidStockDataException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * One line of stock in the FoodBank,
 * The constructor checks every value, so an invalid item can never be created.
 * Serializable so it can be saved to foodbank.dat.
 *
 * @author egeme
 */
public class StockItem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String stockId, name, category, expiryDate, storageType;
    private int quantity;
    
    
    /**
     * Creates a stock item after checking that every value is valid
     * 
     * @throws InvalidStockDataException if a a value is missing or wrong
     */
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
    
    /**
     * Turns the text typed in the Quantity text field into a number.
     * Static because it is called before the object exists.
     * @param text
     * @return
     * @throws InvalidStockDataException 
     */
    public static int parseQuantity(String text) throws InvalidStockDataException {
        try {
            return Integer.parseInt(text.trim());
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

    /**
     * 
     * @param threshold
     * @return true if the quantity is below the threshold
     */
    public boolean isLowStock(int threshold) {
        return quantity < threshold;
    }
    
    /**
     * 
     * @return the item as one line of text for the Text Area
     */
    public String displayDetails() {
        return String.format("%-5s %-15s %-10s %4d  %s  %s",
                stockId, name, category, quantity, expiryDate, storageType);
    }
}
