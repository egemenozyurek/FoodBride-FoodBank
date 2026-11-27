
package models;

/**
 * Frozen storage (-18 C). 
 * Only accepts "Frozen items.
 * @author egeme
 */
public class Freezer extends StorageUnit{
    
    private static final long serialVersionUID = 1L;
    
    private String defrostDate;

    public Freezer(String unitId, String location, int capacity, double temperature) {
        super(unitId, location, capacity, temperature);
        this.defrostDate = "not set.";
    }
    
    //Overloaded: default capacity 80 and -18 C.
    public Freezer(String unitId, String location){
        this(unitId, location, 80, -18.0);
    }

    public String getDefrostDate() {
        return defrostDate;
    }

    public void setDefrostDate(String defrostDate) {
        this.defrostDate = defrostDate;
    }

    @Override
    public boolean canStore(StockItem item) {
        return item.getStorageType().equals("Frozen")
                && getFreeSpace() >= item.getQuantity();
    }
    
    public String displayDetails(){
        return super.displayDetails() + " | defrost " + defrostDate;
    }
    
}
