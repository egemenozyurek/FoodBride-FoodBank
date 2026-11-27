
package exceptions;

/**
 * Thrown when no item has the given ID.
 * @author egeme
 */
public class StockNotFoundException extends FoodBankException{

    public StockNotFoundException(String stockId) {
        super("No stock item with ID " + stockId + ".");
    }
    
    
}
