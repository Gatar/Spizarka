package com.gatar.Spizarka.Database;

import com.gatar.Spizarka.Database.Objects.BarcodeDTO;
import com.gatar.Spizarka.Database.Objects.Item;

/**
 * Interface for WebAPI methods.
 */
public interface RemoteDatabaseDAO {

    /**
     * Tag for get database version from preferences
     */
    String DATABASE_VERSION_PREFERENCES = "com.gatar.Spizarka.DB_VERSION";
    /**
     * Tag for get Username from preferences
     */
    String USERNAME_PREFERENCES = "com.gatar.Spizarka.USERNAME";
    /**
     * Tag for get Password from preferences
     */
    String PASSWORD_PREFERENCES = "com.gatar.Spizarka.P_A_SS_WOR_D";


    // Paths for WebAPI
    String DOMAIN_PATH = "http://spizarkaservlet.eu-west-1.elasticbeanstalk.com/";
    String PUT_DB_VERSION_PATH = "/putDataVersion";
    String SAVE_ITEM = "/saveItem";
    String SAVE_BARCODE = "/saveBarcode";
    String GET_DB_VERSION_PATH = "/getDataVersion";
    String GET_ALL_ITEMS = "/getAllItems";
    String GET_ALL_BARCODES = "/getAllBarcodes";

    /**
     * Download all items from cloud database.
     */
    void getAllItems();

    /**
     * Download all barcodes from cloud database.
     */
    void getAllBarcodes();

    /**
     * Save single Item to remote database (new one or updated).
     * @param item object with values to transfer
     */
    void saveItem(Item item);

    /**
     * Save single Item barcode to remote database.
     * @param barcode object with values to transfer
     */
    void saveBarcode(BarcodeDTO barcode);

    /**
     * Put new database version to remote database.
     */
    void putDatabaseVersion();

    /**
     * Get verion of database from cloud and handle it.
     */
    void getDatabaseVersion();

    /**
     * Set synchronizer object reference
     * @param databaseSynchronizer reference to {@link DatabaseSynchronizerRemoteOperations}
     */
    void setDatabaseSynchronizer(DatabaseSynchronizerRemoteOperations databaseSynchronizer);

}
