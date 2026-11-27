
package exceptions;

/**
 * Thrown when stock data is missing or in the wrong format
 * @author egeme
 */
public class InvalidStockDataException extends FoodBankException{

    public InvalidStockDataException(String message) {
        super(message);
    }
    
    
}
