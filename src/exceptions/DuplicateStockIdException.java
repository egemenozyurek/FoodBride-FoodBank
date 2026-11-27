
package exceptions;

/**
 * Thrown when stock data is missing or in the wrong format
 * @author egeme
 */
public class DuplicateStockIdException extends FoodBankException{

    public DuplicateStockIdException(String stockId) {
        super("Stock ID " + stockId + " already exists. Please user a different ID.");
    }
    
    
}
