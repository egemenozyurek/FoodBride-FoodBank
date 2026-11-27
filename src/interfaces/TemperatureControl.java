
package interfaces;

import models.StockItem;

/**
 * Contract for any storage that keeps food at a set temperature
 * and has limited space. Implemented by StorageUnit
 * 
 * @author egeme
 */
public interface TemperatureControl {
    
    boolean canStore(StockItem item);
    
    int getFreeSpace();
    
    void setTemperature(double temperature);
    
    double getTemperature();
}
