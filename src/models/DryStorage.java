
package models;

/**
 * Room-temperature shelves for tins, pasta, rice.
 * Only accepts "Ambient" items.
 * Its temperature is always 20 C, so it is not a constructor parameter.
 * @author egeme
 */
public class DryStorage extends StorageUnit{
    
    private static final long serialVersionUID = 1L;
    
    private int shelfCount;

    public DryStorage(String unitId, String location, int capacity, int shelfCount) {
        super(unitId, location, capacity, 20.0);
        this.shelfCount = shelfCount;
    }
    
    // Overloaded: default capacity 300 and 6 shelves
    public DryStorage(String unitId, String location) {
        this(unitId, location, 300, 6);
    }

    public int getShelfCount() {
        return shelfCount;
    }

    public void setShelfCount(int shelfCount) {
        this.shelfCount = shelfCount;
    }
    
    @Override
    public boolean canStore(StockItem item) {
        return item.getStorageType().equals("Ambient")
                && getFreeSpace() >= item.getQuantity();
    }
    
    @Override
    public String displayDetails(){
        return super.displayDetails() + " | " + shelfCount + " shelves";
    }
    
}
