package com.gatar.Spizarka.Depot;

import android.content.SharedPreferences;

import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.Application.MyApp;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Model layer for both Presenters in Depot.
 */
public class DepotModel implements DepotMVP.ModelOperations{

    DepotMVP.RequiredPresenterOperationsOverview mPresenterOverview;
    DepotMVP.RequiredPresenterOperationsDetail mPresenterDetail;

    @Inject SharedPreferences preferences;
    @Inject SharedPreferences.Editor preferencesEditor;
    @Inject ManagerDAO managerDAO;

    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";
    private final String ITEM_ID = "com.example.spizarka.ITEM_ID";
    private final static String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";

    public DepotModel(Object mPresenter) {
        if(mPresenter instanceof DepotMVP.RequiredPresenterOperationsOverview)
            mPresenterOverview = (DepotMVP.RequiredPresenterOperationsOverview) mPresenter;
        else mPresenterDetail = (DepotMVP.RequiredPresenterOperationsDetail) mPresenter;
        MyApp.getAppComponent().inject(this);
    }


    @Override
    public <T> void setPreferencesValue(String preferenceName, T value) {
        if(value instanceof String) preferencesEditor.putString(preferenceName,(String) value);
        else if(value instanceof Integer) preferencesEditor.putInt(preferenceName,(Integer) value);
        preferencesEditor.commit();
    }

    @Override
    public void getAllItems() {
        ArrayList<Item> items = managerDAO.getAllItems(false);
        mPresenterOverview.setItemList(items);
        mPresenterOverview.reportFromModel("All items list loaded.");
    }

    @Override
    public void getAllItemsOverZeroQuantity() {
        ArrayList<Item> items = managerDAO.getAllItems(true);
        mPresenterOverview.setItemList(items);
        mPresenterOverview.reportFromModel("All on stock items list loaded.");
    }

    @Override
    public void getShoppingList() {
        ArrayList<Item> items = managerDAO.getShoppingList();
        mPresenterOverview.setItemList(items);
        mPresenterOverview.reportFromModel("Shopping list loaded");
    }

    @Override
    public void getSingleItem(Integer itemId) {
        Item item = managerDAO.getSingleItem(itemId);
        mPresenterDetail.setItemOnView(item);
    }

    @Override
    public void setBarcodePreferenceByItemId(Integer requestedItemId){
        String barcode = managerDAO.getFirstBarcodeByItemId(requestedItemId);
        preferencesEditor.putString(EXTRA_BARCODE,barcode);
        preferencesEditor.commit();
    }

    @Override
    public void addNewBarcode(Item item, String barcode) {
        managerDAO.addNewBarcode(barcode,item.getId());
        mPresenterOverview.reportFromModel("Barcode added to database");
    }

    @Override
    public void getItemId(){
        mPresenterDetail.setRequestItemId(preferences.getInt(ITEM_ID,-1));
    }

    @Override
    public void getBarcode(){
        mPresenterOverview.setRequestBarcode(preferences.getString(EXTRA_BARCODE,null));
    }

    @Override
    public void getDepotOptions() {
        DepotOptions options = DepotOptions.valueOf(preferences.getString(DEPOT_ACTIVITY_OPTION, DepotOptions.DepotView.name()));
        mPresenterOverview.setDepotOption(options);
    }
}


