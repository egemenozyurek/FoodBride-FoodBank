/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import models.StockItem;

/**
 * Contract for any storage that keeps food at a set temperature
 * and has limited space.
 * 
 * @author egeme
 */
public interface TemperatureControl {
    
    boolean canStore(StockItem item);
    
    int getFreeSpace();
    
    void setTemperature(double temperature);
    
    double getTemperature();
}
