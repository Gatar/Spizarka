package com.gatar.Spizarka.ItemFiller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.ManagerDAOImpl;
import com.gatar.Spizarka.Database.Objects.Barcode;
import com.gatar.Spizarka.Database.Objects.Item;
import com.gatar.Spizarka.Depot.DepotOptions;
import com.gatar.Spizarka.Application.MyApp;

import javax.inject.Inject;

/**
 * Model layer for ItemFiller.
 */
public class ItemFillerModel implements ItemFillerMVP.ModelOperations{

    private ItemFillerMVP.RequiredPresenterOperations mPresenter;

    @Inject SharedPreferences preferences;
    @Inject SharedPreferences.Editor preferencesEditor;
    @Inject ManagerDAOImpl managerDAOImpl;
    @Inject Context appContext;

    private final static String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";
    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";
    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";

    public ItemFillerModel(ItemFillerMVP.RequiredPresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
        MyApp.getAppComponent().inject(this);
        synchronizeDatabases();
    }

    @Override
    public void addNewItem(Item item) {
        managerDAOImpl.addNewItem(item);
        Long itemId = managerDAOImpl.getItemIdByTitle(item.getTitle());
        Barcode bar = new Barcode(getBarcodePreferences(),itemId);
        managerDAOImpl.addNewBarcode(bar);
        mPresenter.reportFromModel(appContext.getString(R.string.communicateAddedItemAndBarcode));
    }

    @Override
    public void updateItem(Item item) {
        item.setId(managerDAOImpl.getItemIdByTitle(item.getTitle()));
        managerDAOImpl.updateItem(item);
        mPresenter.reportFromModel(appContext.getString(R.string.communicateItemUpdated));
    }

    @Override
    public void getItemFillerPreferences() {
        ItemFillerOptions options = ItemFillerOptions.valueOf(preferences.getString(CHANGE_ACTIVITY_OPTION,null));
        mPresenter.setItemFillerOptions(options);
    }

    @Override
    public void getItemByBarcode(){
        String barcode = preferences.getString(EXTRA_BARCODE,null);
        Item item = managerDAOImpl.getSingleItem(managerDAOImpl.getItemIdByBarcode(barcode));
        mPresenter.setItem(item);
    }

    @Override
    public void setDepotPreferences(DepotOptions depotOptions){
        preferencesEditor.putString(DEPOT_ACTIVITY_OPTION,depotOptions.toString());
        preferencesEditor.commit();
    }

    @Override
    public void synchronizeDatabases() {
        managerDAOImpl.synchronizeDatabases();
    }

    private String getBarcodePreferences(){
        return  preferences.getString(EXTRA_BARCODE,null);
    }
}
