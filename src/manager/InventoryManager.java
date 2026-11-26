/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

import exceptions.DuplicateStockIdException;
import exceptions.NoStorageSpaceException;
import exceptions.StockNotFoundException;
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
public class InventoryManager implements FileStorable {

    private ArrayList<StorageUnit> storageUnits;
    private final int lowStockThreshold;
    private static final String FILE_NAME = "foodbank.dat";

    public InventoryManager() {
        storageUnits = new ArrayList<>();
        lowStockThreshold = 10;

        loadFromFile();

        if (storageUnits.isEmpty()) {
            addStorageUnit(new Fridge("FR1", "Back Room"));
            addStorageUnit(new Freezer("FZ1", "Back room"));
            addStorageUnit(new DryStorage("DR1", "Main hall"));
        }
    }

    public final void addStorageUnit(StorageUnit unit) {
        storageUnits.add(unit);
    }

    public void addStock(StockItem item) throws DuplicateStockIdException, NoStorageSpaceException {
        if (stockIdExists(item.getStockId())) {
            throw new DuplicateStockIdException(item.getStockId());
        }

        for (StorageUnit unit : storageUnits) {
            if (unit.addStock(item)) {
                return;
            }
        }
        throw new NoStorageSpaceException(item);
    }

    public boolean stockIdExists(String stockId) {
        Iterator<StorageUnit> unitIt = storageUnits.iterator();
        while (unitIt.hasNext()) {
            Iterator<StockItem> itemIt = unitIt.next().getStockItems().iterator();
            while (itemIt.hasNext()) {
                if (itemIt.next().getStockId().equalsIgnoreCase(stockId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String displayAllStock() {
        StringBuilder sb = new StringBuilder();
        int total = 0;

        for (StorageUnit unit : storageUnits) {
            sb.append(unit.displayDetails()).append("\n");

            if (unit.getStockItems().isEmpty()) {
                sb.append("  (empty)\n");
            }
            Iterator<StockItem> itemIt = unit.getStockItems().iterator();
            while (itemIt.hasNext()) {
                sb.append("  ").append(itemIt.next().displayDetails()).append("\n");
                total++;
            }
            sb.append("\n");
        }
        sb.append(total).append(" item(s) in stock");
        return sb.toString();
    }

    public StockItem searchStock(String name) {
        String target = name.trim().toLowerCase();

        Iterator<StorageUnit> unitIt = storageUnits.iterator();
        while (unitIt.hasNext()) {
            for (StockItem item : unitIt.next().getStockItems()) {
                if (item.getName().toLowerCase().contains(target)) {
                    return item;
                }
            }
        }
        return null;
    }

    public void deleteStock(String stockId) throws StockNotFoundException {
        Iterator<StorageUnit> unitIt = storageUnits.iterator();
        while (unitIt.hasNext()) {
            Iterator<StockItem> itemIt = unitIt.next().getStockItems().iterator();
            while (itemIt.hasNext()) {
                if (itemIt.next().getStockId().equalsIgnoreCase(stockId)) {
                    itemIt.remove();
                    return;
                }
            }
        }
        throw new StockNotFoundException(stockId);
    }

    public ArrayList<StockItem> getLowStockItems() {
        ArrayList<StockItem> low = new ArrayList<>();

        Iterator<StorageUnit> unitIt = storageUnits.iterator();
        while (unitIt.hasNext()) {
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
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(storageUnits);
        } catch (IOException e) {
            System.out.println("Could not save " + FILE_NAME + ": " + e.getMessage());
        }
    }

    @Override
    public final void loadFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            storageUnits = (ArrayList<StorageUnit>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load " + FILE_NAME + ": " + e.getMessage());
            storageUnits = new ArrayList<>();
        }
    }
}
