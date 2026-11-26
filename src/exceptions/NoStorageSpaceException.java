/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

import models.StockItem;

/**
 *
 * @author egeme
 */
public class NoStorageSpaceException extends FoodBankException{

    public NoStorageSpaceException(StockItem item) {
        super("No suitable storage sapce for " + item.getQuantity() + " "
                + item.getStorageType() + " item(s)");
    }
    
    
}
