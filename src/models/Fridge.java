/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author egeme
 */
public class Fridge extends StorageUnit{
    
    private static final long serialVersionUID = 1L;
    
    private int humidityLevel;
    public Fridge(String unitId, String location, int capacity, double temperature){
        super(unitId, location, capacity, temperature);
        this.humidityLevel = 60;
    }
    
    public Fridge(String unitId, String location){
        this(unitId, location, 100, 4.0);
    }

    public int getHumidtyLevel() {
        return humidityLevel;
    }

    public void setHumidtyLevel(int humidtyLevel) {
        this.humidityLevel = humidtyLevel;
    }
    
    @Override
    public boolean canStore(StockItem item) {
        return item.getStorageType().equals("Chilled")
                && getFreeSpace() >= item.getQuantity();
    }
    
    @Override
    public String displayDetails() {
        return super.displayDetails() + " | humidity " + humidityLevel + "%";
    }
    
}
