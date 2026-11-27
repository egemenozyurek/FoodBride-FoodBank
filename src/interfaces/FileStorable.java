
package interfaces;

/**
 * Contract for every class that saves its data to a file.
 * Implemented by Inventorymanager. In the group project the main menu keeps 
 * all managers in a FileStorable[] array and saves them in one loop.
 * 
 * @author egeme
 */
public interface FileStorable {
    void saveToFile(); //write the data to the file.
    
    void loadFromFile(); //read the data back from the file.
}
