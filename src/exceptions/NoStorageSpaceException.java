
package exceptions;

import models.StockItem;

/**
 * Thrown when no storage unit accepts an item
 * @author egeme
 */
public class NoStorageSpaceException extends FoodBankException{

    public NoStorageSpaceException(StockItem item) {
        super("No suitable storage space for " + item.getQuantity() + " "
                + item.getStorageType() + " item(s)");
    }
    
    
}
