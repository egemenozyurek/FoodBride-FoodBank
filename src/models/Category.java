package models;

/**
 * The product categories of the food bank and the storage types each one may
 * use. This is the single place where the 'which food goes where' rule lives:
 * e.g. dry goods may only be stored Ambient, dairy only Chilled.
 *
 * An enum is used because the list of categories is fixed and known in advance
 *
 * @author egeme
 */
public enum Category {
    DAIRY("Dairy", "Chilled"),
    MEAT("Meat", "Chilled", "Frozen"),
    VEGETABLES("Vegetables", "Chilled", "Frozen", "Ambient"),
    BAKERY("Bakery", "Ambient", "Frozen"),
    TINNED("Tinned", "Ambient"),
    DRY_GOODS("Dry goods", "Ambient");

    private final String label; // text shown in the Category drop-down
    private final String[] allowedStorageTypes; // storage types this category may use

    Category(String label, String... allowedStorageTypes) {
        this.label = label;
        this.allowedStorageTypes = allowedStorageTypes;
    }

    public String getLabel() {
        return label;
    }

    /**
     *
     * @return the storage types this category may use (for the Storage type
     * drop-down)
     */
    public String[] getAllowedStorageTypes() {
        return allowedStorageTypes.clone(); // a copy, so nobody can change the rule from outside
    }

    /**
     *
     * @param storageType
     * @return true if this category may be stored with the given storage type
     */
    public boolean allows(String storageType) {
        for (String type : allowedStorageTypes) {
            if (type.equalsIgnoreCase(storageType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the category whose label matches the given text, e.g. "Dry goods".
     *
     * @param label
     * @return the category, or null if no category has this label
     */
    public static Category fromLabel(String label) {
        for (Category c : values()) {
            if (c.label.equalsIgnoreCase(label)) {
                return c;
            }
        }
        return null;
    }

}
