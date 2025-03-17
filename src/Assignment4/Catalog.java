package Assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalog<T extends LibraryItem<?>> {
    private Map<String, T> items;
    private String catalogName;

    /**
     * Constructor for catalog
     * @param catalogName Name of the catalog
     */
    public Catalog(String catalogName) {
        this.catalogName = catalogName;
        this.items = new HashMap<>();
    }

    /**
     * Add an item to the catalog
     * @param item The library item to add
     * @return true if added successfully, false if item with same ID already exists
     */
    public boolean addItem(T item) {
        if (items.containsKey(item.getItemID())) {
            return false; // Item with this ID already exists
        }

        items.put(item.getItemID(), item);
        return true;
    }

    /**
     * Remove an item from the catalog by ID
     * @param itemID The ID of the item to remove
     * @return The removed item, or null if item not found
     * @throws ItemNotFoundException if item does not exist
     */
    public T removeItem(String itemID) throws ItemNotFoundException {
        if (!items.containsKey(itemID)) {
            throw new ItemNotFoundException("Item with ID " + itemID + " not found in catalog");
        }

        return items.remove(itemID);
    }

    /**
     * Get an item by its ID
     * @param itemID The ID of the item to retrieve
     * @return The item if found
     * @throws ItemNotFoundException if item does not exist
     */
    public T getItem(String itemID) throws ItemNotFoundException {
        if (!items.containsKey(itemID)) {
            throw new ItemNotFoundException("Item with ID " + itemID + " not found in catalog");
        }

        return items.get(itemID);
    }

    /**
     * Get all items in the catalog
     * @return List of all items
     */
    public List<T> getAllItems() {
        return new ArrayList<>(items.values());
    }

    /**
     * Get the number of items in the catalog
     * @return Number of items
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Get the catalog name
     * @return Catalog name
     */
    public String getCatalogName() {
        return catalogName;
    }
}