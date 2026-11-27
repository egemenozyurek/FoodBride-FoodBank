
package foodbank;

import gui.FoodBankGUI;
import java.awt.EventQueue;

/**
 * Starts the Food Bank application.
 * 
 * @author egeme
 */
public class FoodBankApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(()-> new FoodBankGUI().setVisible(true));
    }
    
}
