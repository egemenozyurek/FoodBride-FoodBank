/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author egeme
 */
public class StockNotFoundException extends FoodBankException{

    public StockNotFoundException(String stockId) {
        super("No stock item with ID " + stockId + ".");
    }
    
    
}
