package com.gatar.Spizarka.Database;

import java.util.ArrayList;

/**
 * Interface for DataAccessObject
 */
public interface MethodsDAO {


    //METHODS OF BARCODES TABLE
    /**
     * Check of barcode presence in database.
     * @param barcode String with barcode value
     * @return true - barcode exist in database, false - barcode doesn't exist in database
     */
    boolean isContainBarcode(String barcode);

    /**
     * Add new barcode connected with title to database.
     * @param barcode String with barcode value
     * @param title String with title connected to barcode
     */
    void addNewBarcode (String barcode, String title);

    /**
     * Deleting barcode value from database.
     * @param barcode String with barcode value
     */
    void deleteBarcode(String barcode);

    /**
     * Get title connceted with barcode
     * @param barcode String with barcode
     * @return String with title.
     */
    String getTitle(String barcode);


    //METHODS OF ITEMS TABLE

    /**
     * Check of item with specified name presence in database.
     * @param title title of searched item.
     * @return true - item exist in databse, false - item doesn't exist in database
     */
    boolean isContainItem(String title);

    /**
     * Add new item to database.
     * @param item Item to add.
     */
    void addNewItem(Item item);

    /**
     * Update item which exists in database.
     * @param item Item do update.
     */
    void updateItem(Item item);

    /**
     * Get single Item from database by it's title.
     * @param title title of Item to get out.
     * @return Item for inputed title.
     */
    Item getSingleItem(String title);

    /**
     * Get all items limited by value of {@link Categories}
     * @param category limiting value of Categories
     * @return list of Item limited by category
     */
    ArrayList<Item> getItemsByCategory(String category);

    /**
     * Get all items in database.
     * @param overZeroQuantity true - return only items with quantity > 0, false - return all items with ANY quantity value
     * @return list of all Item
     */
    ArrayList<Item> getAllItems(boolean overZeroQuantity);

    /**
     * Get all items with quantity under minimum quantity
     * @return list of all Item with quantity under minimum
     */
    ArrayList<Item> getShoppingList();

    /**
     * Delete all databases.
     */
    void deleteDatabase();

    /**
     * Delete one item with specified title.
     * @param title String with title of item to delete.
     */
    void deleteItem(String title);

}
