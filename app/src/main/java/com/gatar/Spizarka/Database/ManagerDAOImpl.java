package com.gatar.Spizarka.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gatar.Spizarka.Database.Objects.Barcode;
import com.gatar.Spizarka.Database.Objects.BarcodeDTO;
import com.gatar.Spizarka.Database.Objects.EntityDTO;
import com.gatar.Spizarka.Database.Objects.Item;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Manager for both internal and cloud databases working together.
 */
public class ManagerDAOImpl implements ManagerDAO {

    private AndroidDatabaseDAO androidDatabaseDAO;
    private RemoteDatabaseDAO remoteDatabaseDAO;
    private DatabaseSynchronizer databaseSynchronizer;

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    @Inject
    public ManagerDAOImpl(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferencesEditor = preferences.edit();

        androidDatabaseDAO = new AndroidDatabaseDAOImpl(context, INTERNAL_DATABASE_NAME);
        remoteDatabaseDAO = new RemoteDatabaseDAOImpl(context, preferences);
        databaseSynchronizer = new DatabaseSynchronizerImpl(remoteDatabaseDAO,androidDatabaseDAO,preferences,preferencesEditor);
    }


    @Override
    public boolean isContainBarcode(String barcode) {
        return androidDatabaseDAO.isContainBarcode(barcode);
    }

    @Override
    public Long getItemIdByBarcode(String barcode) {
        return androidDatabaseDAO.getItemIdByBarcode(barcode);
    }

    @Override
    public String getFirstBarcodeByItemId(Long itemId) {
        return  androidDatabaseDAO.getFirstBarcodeByItemId(itemId);
    }

    @Override
    public Long getItemIdByTitle(String title) {
        return androidDatabaseDAO.getItemIdByTitle(title);
    }

    @Override
    public Item getSingleItem(String title) {
        return androidDatabaseDAO.getSingleItem(title);
    }

    @Override
    public void saveEntity(Item item) {
        EntityDTO entityDTO = item.toEntityDTO();
        entityDTO.setBarcodes(androidDatabaseDAO.getAllBarcodesAsStrings(item.getId()));
        entityDTO.setDatabaseVersion(getDatabaseVersionFromPreferences());
        remoteDatabaseDAO.saveEntity(entityDTO);
    }

    @Override
    public void addNewBarcode(Barcode barcode) {
        androidDatabaseDAO.addNewBarcode(barcode);
        increaseDatabaseVersion();
        Item item = androidDatabaseDAO.getSingleItem(barcode.getItemId());
        saveEntity(item);
    }

    @Override
    public void addNewItem(Item item) {
        androidDatabaseDAO.addNewItem(item);
        increaseDatabaseVersion();
        saveEntity(item);
    }

    @Override
    public void updateItem(Item item) {
        androidDatabaseDAO.updateItem(item);
        increaseDatabaseVersion();
        saveEntity(item);
    }

    @Override
    public Item getSingleItem(Long itemId) {
        return androidDatabaseDAO.getSingleItem(itemId);
    }

    @Override
    public ArrayList<Item> getItemsByCategory(String category) {
        return androidDatabaseDAO.getItemsByCategory(category);
    }

    @Override
    public ArrayList<Item> getAllItems(boolean overZeroQuantity) {
        return androidDatabaseDAO.getAllItems(overZeroQuantity);
    }

    @Override
    public ArrayList<Item> getShoppingList() {
        return androidDatabaseDAO.getShoppingList();
    }


    @Override
    public void deleteDatabase() {
        androidDatabaseDAO.deleteDatabase();
        restartDatabaseVersion();
    }

    @Override
    public void synchronizeDatabases() {
        databaseSynchronizer.synchronize();
    }



    private void increaseDatabaseVersion(){
        Long actualVersion = preferences.getLong(DATABASE_VERSION_PREFERENCES,0L);
        preferencesEditor.putLong(DATABASE_VERSION_PREFERENCES,++actualVersion);
        preferencesEditor.commit();
        Log.d("***DB Verion*** ",actualVersion.toString());
    }

    private void restartDatabaseVersion(){
        preferencesEditor.putLong(DATABASE_VERSION_PREFERENCES,0L);
        preferencesEditor.commit();
        Log.d("**DB version *** ","0");
    }

    private Long getDatabaseVersionFromPreferences(){
        return preferences.getLong(DATABASE_VERSION_PREFERENCES,-1);
    }

}
