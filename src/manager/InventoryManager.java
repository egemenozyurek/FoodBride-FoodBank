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
 * Holds all storage units and provides Add / Display / Search / Delete, the
 * low-stock list and file storage. The GUI only talks tot this class, never to
 * the lists directly (seperation of concerns). problems are reported with
 * custom exceptions.
 *
 * @author egeme
 */
public class InventoryManager implements FileStorable {

    private ArrayList<StorageUnit> storageUnits;
    private final int lowStockThreshold;
    private static final String FILE_NAME = "foodbank.dat";

    /**
     * Loads the saved stock; on the first run creates one unit of each type
     */
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

    /**
     * Puts the item in the first unit whose canStore() accepts it.
     *
     * @param item
     * @throws DuplicateStockIdException
     * @throws NoStorageSpaceException
     */
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

    /**
     *
     * @param stockId
     * @return true if any unit already holds an item with this ID
     */
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

    /**
     * Every unit with the items inside it
     *
     * @return the text for the results area
     */
    public String displayAllStock() {
        StringBuilder sb = new StringBuilder();
        int total = 0;

        for (StorageUnit unit : storageUnits) {
            sb.append(unit.displayDetails()).append("\n");

            if (unit.getStockItems().isEmpty()) {
                sb.append("  (empty)\n");
            }
            Iterator<StockItem> itemIt = unit.getStockItems().iterator(); // inner loop: the items of one unit
            while (itemIt.hasNext()) {
                sb.append("  ").append(itemIt.next().displayDetails()).append("\n");
                total++;
            }
            sb.append("\n");
        }
        sb.append(total).append(" item(s) in stock");
        return sb.toString();
    }

    /**
     * First item whose name contains the text. Returns null instead of
     * throwing, because "not found" is a normal search result
     *
     * @param name
     * @return
     */
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

    /**
     * uses Iterator.remove() for safe removal.
     *
     * @param stockId
     * @throws StockNotFoundException
     */
    public void deleteStock(String stockId) throws StockNotFoundException {
        Iterator<StorageUnit> unitIt = storageUnits.iterator();
        while (unitIt.hasNext()) {
            Iterator<StockItem> itemIt = unitIt.next().getStockItems().iterator();
            while (itemIt.hasNext()) {
                if (itemIt.next().getStockId().equalsIgnoreCase(stockId)) {
                    itemIt.remove(); // safe removal while iterating
                    return;
                }
            }
        }
        throw new StockNotFoundException(stockId);
    }

    /**
     *
     * @return a new list of items below the threshold
     */
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

    /**
     * Writes the whole list (units and their items) with ObjectsOutputStream
     */
    @Override
    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {  // try-with-resources closes the file automatically
            out.writeObject(storageUnits);
        } catch (IOException e) {
            System.out.println("Could not save " + FILE_NAME + ": " + e.getMessage());
        }
    }

    /**
     * Reads the list back; does nothing if the file does not exist yet.
     */
    @Override
    public final void loadFromFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            storageUnits = (ArrayList<StorageUnit>) in.readObject();  // readObject() return Object, so cast it
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load " + FILE_NAME + ": " + e.getMessage());
            storageUnits = new ArrayList<>();
        }
    }
}
