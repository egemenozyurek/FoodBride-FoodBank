package exceptions;

/**
 * Parent of every exception in the Food Bank section. Checked (extends
 * Exception), so the compiler makes callers handle it. The GUI catches this one
 * type to handle all four (polymorphism).
 *
 * @author egeme
 */
public class FoodBankException extends Exception {

    public FoodBankException(String message) {
        super(message);
    }

}
