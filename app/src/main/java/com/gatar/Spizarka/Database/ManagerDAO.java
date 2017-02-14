package com.gatar.Spizarka.Database;

import com.gatar.Spizarka.Database.Objects.Barcode;
import com.gatar.Spizarka.Database.Objects.Item;

import java.util.ArrayList;

/**
 * Interface defining methods for database manager {@link ManagerDAOImpl}
 */
public interface ManagerDAO {

    String INTERNAL_DATABASE_NAME = "homePantry.db";
    String DATABASE_VERSION_PREFERENCES = "com.gatar.Spizarka.DB_VERSION";

    //METHODS OF BARCODES TABLE
    /**
     * Check of barcode presence in database.
     * @param barcode String with barcode value
     * @return true - barcode exist in database, false - barcode doesn't exist in database
     */
    boolean isContainBarcode(String barcode);

    /**
     * Add new single barcode connected with title to database.
     * @param barcode {@link Barcode} object with barcode value and item's id
     */
    void addNewBarcode (Barcode barcode);

    /**
     * Get item id connceted with barcode
     * @param barcode String with barcode
     * @return Long with id number.
     */
    Long getItemIdByBarcode(String barcode);

    /**
     * Get first barcode value saved for received item's id.
     * @param itemId value of item id
     * @return connected with id value of barcode
     */
    String getFirstBarcodeByItemId(Long itemId);


    //METHODS OF ITEMS TABLE

    /**
     * Save new / update item with their barcodes.
     * @param item Item to save
     */
    void saveEntity (Item item);

    /**
     * Get item id connceted with barcode
     * @param title String with barcode
     * @return Long with id number.
     */
    Long getItemIdByTitle(String title);

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
    Item getSingleItem(Long itemId);

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
     * Deleta Android database and clear cloud database.
     */
    void deleteDatabase();

    /**
     * Synchronize cloud database with internal database.
     * In case when cloud database version is higher than internal, erase internal db data
     * and download data from cloud.
     */
    void synchronizeDatabases();
}
