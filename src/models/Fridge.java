
package models;

/**
 * Chilled storage (1-5 C) for dairy, meat and prepared food.
 * Only accepts "Chilled" items
 * @author egeme
 */
public class Fridge extends StorageUnit{
    
    private static final long serialVersionUID = 1L;
    
    private int humidityLevel;
    // Overloaded: default capacity 100 and 4 C
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
    
    /**
     * Accepts only Chilled items, and only if there is enough free space
     * @param item
     * @return true if the item can go in this fridge
     */
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
