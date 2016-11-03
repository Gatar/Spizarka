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
    public void addNewBarcode(String barcode, Integer itemId) {

    }

    @Override
    public Integer getItemIdByBarcode(String barcode) {
        return null;
    }

    @Override
    public Integer getItemIdByTitle(String title) {
        return null;
    }

    @Override
    public Item getSingleItem(String title) {
        return null;
    }

    @Override
    public void addNewItem(Item item) {

    }

    @Override
    public void updateItem(Item item) {

    }

    @Override
    public Item getSingleItem(Integer itemId) {
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
}


