package com.gatar.Spizarka.Database;

import com.gatar.Spizarka.Database.Objects.Barcode;
import com.gatar.Spizarka.Database.Objects.Item;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Interface for DataAccessObject
 */
public interface AndroidDatabaseDAO {

      String TABLE_NAME_ITEMS = "ITEMS";
      String COLUMN_NAME_ID_KEY = "id";
      String COLUMN_NAME_TITLE = "title";
      String COLUMN_NAME_CATEGORY = "category";
      String COLUMN_NAME_QUANTITY = "quantity";
      String COLUMN_NAME_MINIMUM_QUANTITY = "minimum";
      String COLUMN_NAME_DESCRIPTION = "description";

      String TABLE_NAME_BARCODES = "BARCODES";
      String COLUMN_NAME_BARCODE_KEY = "barcode";
      String COLUMN_NAME_ITEM_ID = "item_id";

      String SQL_CREATE_TABLE_ITEMS=
            "CREATE TABLE " + TABLE_NAME_ITEMS + " (" +
                    COLUMN_NAME_ID_KEY + "              INTEGER     PRIMARY KEY     AUTOINCREMENT, " +
                    COLUMN_NAME_TITLE + "               TEXT        NOT NULL        UNIQUE, " +
                    COLUMN_NAME_CATEGORY + "            TEXT        NOT NULL, " +
                    COLUMN_NAME_QUANTITY + "            INTEGER     NOT NULL, " +
                    COLUMN_NAME_MINIMUM_QUANTITY + "    INTEGER     NOT NULL, " +
                    COLUMN_NAME_DESCRIPTION + "         TEXT );";

      String SQL_CREATE_TABLE_BARCODES=
            "CREATE TABLE " + TABLE_NAME_BARCODES + "(" +
                    COLUMN_NAME_BARCODE_KEY +"          TEXT    PRIMARY KEY     NOT NULL, " +
                    COLUMN_NAME_ITEM_ID + "         INTEGER    NOT NULL );";



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
     * Add new barcodes list connected with title to database.
     * @param barcodes {@link Barcode} object list with barcode value and item's id
     */
    void addNewBarcode (LinkedList<Barcode> barcodes);


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

    /**
     * Get all barcodes for item as list.
     * @param itemId id of item
     * @return list of barcodes
     */
    ArrayList<String> getAllBarcodesAsStrings(Long itemId);


    //METHODS OF ITEMS TABLE

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
     * Add list of new items to database.
     * @param items Item to add.
     */
    void addNewItem(LinkedList<Item> items);

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


}
