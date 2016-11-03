package com.gatar.Spizarka.Database;

import java.util.ArrayList;

import dagger.Component;

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
     * @param itemId id of item connected to barcode
     */
    void addNewBarcode (String barcode, Integer itemId);


    /**
     * Get item id connceted with barcode
     * @param barcode String with barcode
     * @return Integer with id number.
     */
    Integer getItemIdByBarcode(String barcode);


    //METHODS OF ITEMS TABLE

    /**
     * Get item id connceted with barcode
     * @param title String with barcode
     * @return Integer with id number.
     */
    Integer getItemIdByTitle(String title);

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
     * Get single Item from database by it's id.
     * @param itemId id of Item to get out.
     * @return Item for inputed title.
     */
    Item getSingleItem(Integer itemId);

    /**
     * Get single Item from database by it's title.
     * @param title id of Item to get out.
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
     * Get all items with quantity under minimum quantity.
     * @return list of all Item with quantity under minimum
     */
    ArrayList<Item> getShoppingList();

    /**
     * Delete all databases.
     */
    void deleteDatabase();


}
