package com.gatar.Spizarka.Database;

import android.content.SharedPreferences;
import android.util.Log;

import com.gatar.Spizarka.Database.Objects.Barcode;
import com.gatar.Spizarka.Database.Objects.BarcodeDTO;
import com.gatar.Spizarka.Database.Objects.Item;
import com.gatar.Spizarka.Database.Objects.ItemDTO;

import java.util.LinkedList;

/**
 * Class providing synchronize operations between internal and cloud databases.
 */

public class DatabaseSynchronizerImpl implements DatabaseSynchronizer, DatabaseSynchronizerRemoteOperations {

    private RemoteDatabaseDAO remoteDatabaseDAO;
    private AndroidDatabaseDAO androidDatabaseDAO;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    public DatabaseSynchronizerImpl(RemoteDatabaseDAO remoteDatabaseDAO, AndroidDatabaseDAO androidDatabaseDAO, SharedPreferences preferences, SharedPreferences.Editor preferencesEditor) {
        this.remoteDatabaseDAO = remoteDatabaseDAO;
        this.androidDatabaseDAO = androidDatabaseDAO;
        this.preferences = preferences;
        this.preferencesEditor = preferencesEditor;
        remoteDatabaseDAO.setDatabaseSynchronizer(this);
    }

    public void synchronize(){
        remoteDatabaseDAO.getDatabaseVersion();
    }

    @Override
    public void checkDatabaseVersions(Long remoteDatabaseVersion) {
        if(remoteDatabaseVersion.equals(getInternalDatabaseVersion())){
            Log.d("DB Versions: ","Databases versions are equal");
            return;
        }else{
            Log.d("DB Versions: ","Databases versions are different");
            remoteDatabaseDAO.getAllItems();
            preferencesEditor.putLong(DATABASE_VERSION_PREFERENCES,remoteDatabaseVersion);
            preferencesEditor.commit();
        }
    }

    @Override
    public void saveDownloadedItems(LinkedList<ItemDTO> items) {
        if(items != null) {
            androidDatabaseDAO.deleteDatabase();
            androidDatabaseDAO.addNewItem(
                    convertToItemLinkedList(items));
            remoteDatabaseDAO.getAllBarcodes();
        }else{
            Log.d("DB Items: ", "Remote items list is empty");
        }
    }

    @Override
    public void saveDownloadedBarcodes(LinkedList<BarcodeDTO> barcodes) {
        androidDatabaseDAO.addNewBarcode(
                convertToBarcodeLinkedList(barcodes));
    }

    private Long getInternalDatabaseVersion(){
        return preferences.getLong(DATABASE_VERSION_PREFERENCES,-1L);
    }

    private LinkedList<Item> convertToItemLinkedList(LinkedList<ItemDTO> items){
        LinkedList<Item> itemsForSave = new LinkedList<>();
        for(ItemDTO item : items){
            itemsForSave.add(item.toItem());
            System.out.println(item.getTitle() + "----------------------");
        }
        return itemsForSave;
    }

    private LinkedList<Barcode> convertToBarcodeLinkedList(LinkedList<BarcodeDTO> barcodes){
        LinkedList<Barcode> barcodesForSave = new LinkedList<>();
        for(BarcodeDTO barcode : barcodes){
            barcodesForSave.add(barcode.toBarcode());
            System.out.println(barcode.getBarcodeValue() + "----------------------");
        }
        return barcodesForSave;
    }
}

/**
 * Interface with methods required only for {@link RemoteDatabaseDAO}
 */
interface DatabaseSynchronizerRemoteOperations {

    String DATABASE_VERSION_PREFERENCES = "com.gatar.Spizarka.DB_VERSION";

    /**
     * Check database versions and proceed download new database in case of difference.
     * @param remoteDatabaseVersion value of database version in cloud
     */
    void checkDatabaseVersions(Long remoteDatabaseVersion);

    /**
     * Save downloaded items to in-memory phone database
     * @param items list of items as {@link ItemDTO}
     */
    void saveDownloadedItems(LinkedList<ItemDTO> items);

    /**
     * Save downloaded items to in-memory phone database
     * @param barcodes list of barcodes as {@link BarcodeDTO}
     */
    void saveDownloadedBarcodes(LinkedList<BarcodeDTO> barcodes);
}
