package models;

import interfaces.TemperatureControl;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Common parent for every place food can be stored (Fridge, Freezer,
 * DryStorage). Holds its own list of stock items. canStore() is left abstract
 * so that each subclass decides which items it accepts (polymorphism / Template
 * Method).
 *
 * @author egeme
 */
public abstract class StorageUnit implements Serializable, TemperatureControl {

    protected String unitId, location;
    protected int capacity;
    protected double temperature;
    protected ArrayList<StockItem> stockItems;

    public StorageUnit() {
        this("", "", 0, 0.0);
    }

    public StorageUnit(String unitId, String location, int capacity, double temperature) {
        this.unitId = unitId;
        this.location = location;
        this.capacity = capacity;
        this.temperature = temperature;
        this.stockItems = new ArrayList<>();
    }

    /**
     * Returns the real list, not a copy, so InventoryManager can remove items
     * with an iterator.
     *
     * @return
     */
    public ArrayList<StockItem> getStockItems() {
        return stockItems;
    }

    public String getUnitId() {
        return unitId;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    /**
     * Each storage type writes its own rule
     *
     * @param item
     * @return
     */
    @Override
    public abstract boolean canStore(StockItem item);

    /**
     * Adds the item item only if canStore() allows it
     *
     * @param item
     * @return true if the item was stored
     */
    public boolean addStock(StockItem item) {
        if (canStore(item)) {
            stockItems.add(item);
            return true;
        }
        return false;
    }

    /**
     *
     * @return capacity minus the total quantity already stored
     */
    @Override
    public int getFreeSpace() {
        int used = 0;
        for (StockItem s : stockItems) {
            used += s.getQuantity();
        }
        return capacity - used;
    }

    @Override
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String displayDetails() {
        int used = capacity - getFreeSpace();
        return getClass().getSimpleName().toUpperCase() + " " + unitId + " | " + location
                + " | " + temperature + " C | " + used + "/" + capacity;
    }

}
