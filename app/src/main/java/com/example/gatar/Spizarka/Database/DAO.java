package com.example.gatar.Spizarka.Database;

import java.util.ArrayList;

/**
 * Created by Gatar on 2016-08-14.
 */
public interface DAO {
    public final String DATABASE_NAME="database.db";
    public final int DATABASE_VERSION = 1;

    public final String TABLE_NAME_ITEMS = "itemsTable";
    public final String COLUMN_NAME_TITLE = "title";
    public final String COLUMN_NAME_CATEGORY = "category";
    public final String COLUMN_NAME_QUANTITY = "quantity";
    public final String COLUMN_NAME_MINIMUM_QUANTITY = "minimum";
    public final String COLUMN_NAME_DESCRIPTION = "description";

    public final String TABLE_NAME_BARCODES = "barcodes";
    public final String COLUMN_NAME_BARCODE = "barcode";

    public final String SQL_CREATE_TABLE_ITEMS=
            "CREATE TABLE " + TABLE_NAME_ITEMS + " (" +
                    COLUMN_NAME_TITLE + " TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_NAME_CATEGORY + " TEXT NOT NULL, " +
                    COLUMN_NAME_QUANTITY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_MINIMUM_QUANTITY + " INTEGER NOT NULL, " +
                    COLUMN_NAME_DESCRIPTION + " TEXT );";

    public final String SQL_CREATE_TABLE_BARCODES=
            "CREATE TABLE " + TABLE_NAME_BARCODES + "(" +
                    COLUMN_NAME_BARCODE +" TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_NAME_TITLE + " TEXT NOT NULL );";

    //METHODS OF BARCODES TABLE
    /**
     * Check of barcode presence in database.
     * @param barcode String with barcode value
     * @return true - barcode exist in database, false - barcode doesn't exist in database
     */
    public boolean isContainBarcode(String barcode);

    /**
     * Add new barcode connected with title to database.
     * @param barcode String with barcode value
     * @param title String with title connected to barcode
     */
    public void addNewBarcode (String barcode, String title);

    /**
     * Deleting barcode value from database.
     * @param barcode String with barcode value
     */
    public void deleteBarcode (String barcode);

    /**
     * Get title connceted with barcode
     * @param barcode String with barcode
     * @return String with title.
     */
    public String getTitle(String barcode);


    //METHODS OF ITEMS TABLE

    /**
     * Check of item with specified name presence in database.
     * @param title title of searched item.
     * @return true - item exist in databse, false - item doesn't exist in database
     */
    public boolean isContainItem(String title);

    /**
     * Add new item to database.
     * @param item Item to add.
     */
    public void addNewItem(Item item);

    /**
     * Update item which exists in database.
     * @param item Item do update.
     */
    public void updateItem(Item item);

    /**
     * Get single Item from database by it's title.
     * @param title title of Item to get out.
     * @return Item for inputed title.
     */
    public Item getSingleItem(String title);

    /**
     * Get all items limited by value of {@link Categories}
     * @param category limiting value of Categories
     * @return list of Item limited by category
     */
    public ArrayList<Item> getItemsByCategory(String category);

    /**
     * Get all items in database.
     * @param overZeroQuantity true - return only items with quantity > 0, false - return all items with ANY quantity value
     * @return list of all Item
     */
    public ArrayList<Item> getAllItems(boolean overZeroQuantity);

    /**
     * Get all items with quantity under minimum quantity
     * @return list of all Item with quantity under minimum
     */
    public ArrayList<Item> getShoppingList();

    /**
     * Delete all databases.
     */
    public void deleteDatabase();

    /**
     * Delete one item with specified title.
     * @param title String with title of item to delete.
     */
    public void deleteItem(String title);

}
