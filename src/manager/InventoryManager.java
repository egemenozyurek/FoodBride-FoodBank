/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import interfaces.FileStorable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
public class InventoryManager implements FileStorable{
    private ArrayList<StorageUnit> storageUnits;
    private int lowStockThreshold;
    private static final String fileName = "foodbank.dat";
    
    public InventoryManager() {
        storageUnits = new ArrayList<>();
        lowStockThreshold = 10;
        
        loadFromFile();
        
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

    @Override
    public void saveToFile() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))){
            out.writeObject(storageUnits);
        } catch(IOException e) {
            System.out.println("Could not save " + fileName + ": " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile() {
        File file = new File(fileName);
        
        if (!file.exists()) {
            return;
        }
        
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))){
            storageUnits = (ArrayList<StorageUnit>) in.readObject();
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Could not load " + fileName + ": " + e.getMessage());
            storageUnits = new ArrayList<>();
        }
    }
}
