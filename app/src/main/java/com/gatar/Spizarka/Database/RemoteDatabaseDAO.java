package com.gatar.Spizarka.Database;

import java.util.ArrayList;

/**
 * Manager for esrvice cloud database by REST.
 */
class RemoteDatabaseDAO implements MethodsDAO{
    @Override
    public boolean isContainBarcode(String barcode) {
        return false;
    }

    @Override
    public void addNewBarcode(String barcode, String title) {

    }

    @Override
    public void deleteBarcode(String barcode) {

    }

    @Override
    public String getTitle(String barcode) {
        return null;
    }

    @Override
    public boolean isContainItem(String title) {
        return false;
    }

    @Override
    public void addNewItem(Item item) {

    }

    @Override
    public void updateItem(Item item) {

    }

    @Override
    public Item getSingleItem(String title) {
        return null;
    }

    @Override
    public ArrayList<Item> getItemsByCategory(String category) {
        return null;
    }

    @Override
    public ArrayList<Item> getAllItems(boolean overZeroQuantity) {
        return null;
    }

    @Override
    public ArrayList<Item> getShoppingList() {
        return null;
    }

    @Override
    public void deleteDatabase() {

    }

    @Override
    public void deleteItem(String title) {

    }
}
