/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

/**
 *
 * @author egeme
 */
public class DuplicateStockIdException extends FoodBankException{

    public DuplicateStockIdException(String stockId) {
        super("Stock ID " + stockId + " already exists. Please user a different ID.");
    }
    
    
}
