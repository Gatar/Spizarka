package com.gatar.Spizarka.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Manager for both internal and cloud databases working together.
 */

public class ManagerDAO implements MethodsDAO {

    public final String INTERNAL_DATABASE_NAME = "homePantry.db";
    public final String DATABASE_VERSION_PREFERENCES = "com.gatar.Spizarka.DB_VERSION";

    MethodsDAO androidDatabaseDAO;
    MethodsDAO remoteDatabaseDAO;

    SharedPreferences preferences;
    SharedPreferences.Editor preferencesEditor;


    @Inject
    public ManagerDAO(Context context){
        androidDatabaseDAO = new AndroidDatabaseDAO(context, INTERNAL_DATABASE_NAME);
        remoteDatabaseDAO = new RemoteDatabaseDAO();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferencesEditor = preferences.edit();
    }


    @Override
    public boolean isContainBarcode(String barcode) {
        return androidDatabaseDAO.isContainBarcode(barcode);
    }

    @Override
    public Integer getItemIdByBarcode(String barcode) {
        return androidDatabaseDAO.getItemIdByBarcode(barcode);
    }

    @Override
    public String getFirstBarcodeByItemId(Integer itemId) {
        return  androidDatabaseDAO.getFirstBarcodeByItemId(itemId);
    }

    @Override
    public Integer getItemIdByTitle(String title) {
        return androidDatabaseDAO.getItemIdByTitle(title);
    }

    @Override
    public Item getSingleItem(String title) {
        return androidDatabaseDAO.getSingleItem(title);
    }

    @Override
    public void addNewBarcode(String barcode, Integer itemId) {
        androidDatabaseDAO.addNewBarcode(barcode,itemId);
        increaseDatabaseVersion();
    }


    @Override
    public void addNewItem(Item item) {
        androidDatabaseDAO.addNewItem(item);
        increaseDatabaseVersion();
    }

    @Override
    public void updateItem(Item item) {
        androidDatabaseDAO.updateItem(item);
        increaseDatabaseVersion();
    }

    @Override
    public Item getSingleItem(Integer itemId) {
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


    private void increaseDatabaseVersion(){
        Integer actualVersion = preferences.getInt(DATABASE_VERSION_PREFERENCES,0);
        preferencesEditor.putInt(DATABASE_VERSION_PREFERENCES,++actualVersion);
        preferencesEditor.commit();
        Log.d("***DB Verion*** ",actualVersion.toString());
    }

    private void restartDatabaseVersion(){
        preferencesEditor.putInt(DATABASE_VERSION_PREFERENCES,0);
        preferencesEditor.commit();
        Log.d("**DB version *** ","0");
    }

}
