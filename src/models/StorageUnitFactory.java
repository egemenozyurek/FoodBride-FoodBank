package models;

/**
 *
 * @author egeme
 */
public class StorageUnitFactory {

    /**
     * Only static methods: no object of the factory is ever needed.
     */
    public StorageUnitFactory() {
    }

    public static StorageUnit createUnit(String storageType, String unitId, String location) {
        switch (storageType) {
            case "Chilled":
                return new Fridge(unitId, location);
            case "Frozen":
                return new Freezer(unitId, location);
            case "Ambient":
                return new DryStorage(unitId, location);
            default:
                throw new IllegalArgumentException("Unknown storage type: " + storageType);
        }
    }
}
