/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import java.util.ArrayList;
import java.util.Iterator;
import models.DryStorage;
import models.Freezer;
import models.Fridge;
import models.StockItem;
import models.StorageUnit;

/**
 *
 * @author egeme
 */
public class InventoryManager {
    private ArrayList<StorageUnit> storageUnits;
    private int lowStockThreshold;
    
    public InventoryManager() {
        storageUnits = new ArrayList<>();
        lowStockThreshold = 10;
        
        addStorageUnit(new Fridge("FR1", "back Room"));
        addStorageUnit(new Freezer("FZ1", "Back room"));
        addStorageUnit(new DryStorage("DR1", "Main hall"));
    }
    
    public void addStorageUnit(StorageUnit unit) {
        storageUnits.add(unit);
    }
    
    public boolean addStock(StockItem item){
        for (StorageUnit unit : storageUnits) {
            if (unit.addStock(item)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean stockIdExists(String stockId){
        Iterator<StorageUnit> unitIt = storageUnits.iterator();
        while (unitIt.hasNext()){
            Iterator<StockItem> itemIt = unitIt.next().getStockItems().iterator();
            while(itemIt.hasNext()){
                if (itemIt.next().getStockId().equalsIgnoreCase(stockId)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public String displayAllStock(){
        StringBuilder sb = new StringBuilder();
        int total = 0;
        
        for (StorageUnit unit : storageUnits) {
            sb.append(unit.displayDetails()).append("\n");
            
            if (unit.getStockItems().isEmpty()) {
                sb.append("  (empty)\n");
            }
            Iterator<StockItem> itemIt = unit.getStockItems().iterator();
            while(itemIt.hasNext()){
                sb.append("  ").append(itemIt.next().displayDetails()).append("\n");
                total++;
            }
            sb.append("\n");
        }
        sb.append(total).append(" item(s) in stock");
        return sb.toString();
    }
    
    public StockItem searchStock(String name){
        String target = name.trim().toLowerCase();
        
        Iterator<StorageUnit> unitIt = storageUnits.iterator();
        while(unitIt.hasNext()){
            for (StockItem item : unitIt.next().getStockItems()) {
                if (item.getName().toLowerCase().contains(target)) {
                    return item;
                }
            }
        }
        return null;
    }
    
    public boolean deleteStock(String stockId){
        Iterator<StorageUnit> unitIt = storageUnits.iterator();
        while(unitIt.hasNext()) {
            Iterator<StockItem> itemIt = unitIt.next().getStockItems().iterator();
            while(itemIt.hasNext()){
                if (itemIt.next().getStockId().equalsIgnoreCase(stockId.trim())) {
                    itemIt.remove();
                    return true;
                }
            }
        }
        return false;
    }
    
    public ArrayList<StockItem> getLowStockItems(){
        ArrayList<StockItem> low = new ArrayList<>();
        
        Iterator<StorageUnit> unitIt = storageUnits.iterator();
        while(unitIt.hasNext()){
            for (StockItem item : unitIt.next().getStockItems()) {
                if (item.isLowStock(lowStockThreshold)) {
                    low.add(item);
                }
            }
        }
        return low;
    }

    public int getLowStockThreshold() {
        return lowStockThreshold;
    }   
}
