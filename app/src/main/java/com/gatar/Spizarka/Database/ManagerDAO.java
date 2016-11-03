package com.gatar.Spizarka.Database;

import android.content.Context;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Manager for both internal and cloud databases working together.
 */

public class ManagerDAO implements MethodsDAO {

    public final String INTERNAL_DATABASE_NAME = "homePantry.db";

    MethodsDAO androidDatabaseDAO;
    MethodsDAO remoteDatabaseDAO;


    @Inject
    public ManagerDAO(Context context){
        androidDatabaseDAO = new AndroidDatabaseDAO(context, INTERNAL_DATABASE_NAME);
        remoteDatabaseDAO = new RemoteDatabaseDAO();
        updateAndroidDatabase();
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
    }


    @Override
    public void addNewItem(Item item) {
        androidDatabaseDAO.addNewItem(item);
    }

    @Override
    public void updateItem(Item item) {
        androidDatabaseDAO.updateItem(item);
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
    }

    private void updateAndroidDatabase(){

    }

}
