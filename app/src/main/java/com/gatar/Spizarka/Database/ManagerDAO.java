package com.gatar.Spizarka.Database;

import android.content.Context;

import java.util.ArrayList;

/**
 * Manager for both internal and cloud databases working together.
 */

public class ManagerDAO implements MethodsDAO {

    public final String INTERNAL_DATABASE_NAME = "homePantry.db";

    MethodsDAO androidDatabaseDAO;
    MethodsDAO remoteDatabaseDAO;

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
    public String getTitle(String barcode) {
        return androidDatabaseDAO.getTitle(barcode);
    }

    @Override
    public void addNewBarcode(String barcode, String title) {
        androidDatabaseDAO.addNewBarcode(barcode,title);
        remoteDatabaseDAO.addNewBarcode(barcode,title);
    }

    @Override
    public void deleteBarcode(String barcode) {
        androidDatabaseDAO.deleteBarcode(barcode);
        remoteDatabaseDAO.deleteBarcode(barcode);
    }

    @Override
    public boolean isContainItem(String title) {
        return androidDatabaseDAO.isContainItem(title);
    }

    @Override
    public void addNewItem(Item item) {
        androidDatabaseDAO.addNewItem(item);
        remoteDatabaseDAO.addNewItem(item);
    }

    @Override
    public void updateItem(Item item) {
        androidDatabaseDAO.updateItem(item);
        remoteDatabaseDAO.updateItem(item);
    }

    @Override
    public Item getSingleItem(String title) {
        return androidDatabaseDAO.getSingleItem(title);
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
    public void deleteItem(String title) {
        androidDatabaseDAO.deleteItem(title);
        remoteDatabaseDAO.deleteItem(title);
    }

    @Override
    public void deleteDatabase() {
        androidDatabaseDAO.deleteDatabase();
    }

    private void updateAndroidDatabase(){
        /*TODO Do napisania funkcja, która pozwoli na ściągnięcie bazy danych z neta i zastąpienie nią lokalnej bazy
        * pomysł: dodanie zmian do bazy danych powoduje zmianę wersji bazy danych
        * jeśli dany user posiada nieaktualną wersję, to ściąga całą bazę od zera
         * user, który dokonał zmian zwiększa również lokalny nr bazy, tak aby sam nie próbował ściągać
         * posiadanie nieaktualnej bazy w telefonie powoduje brak możliwości EDYCJI miejscowej bazy!!!!
         * TRYB OFFLINE POSIADA JEDYNIE MOŻLIWOŚĆ ODCZYTU BAZY (tej wewn. również)
         * TRYB OFFLINE POSIADA MOŻLIWOŚĆ EDYCJI OFFLINE TYLKO JEŚLI JEDEN TELEFON NARAZ JEST PODPIĘTY DO KONTA
         */
    }

}
