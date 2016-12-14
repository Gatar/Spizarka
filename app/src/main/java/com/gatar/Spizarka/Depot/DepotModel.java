package com.gatar.Spizarka.Depot;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Application.InternetConnectionStatus;
import com.gatar.Spizarka.Database.Objects.Barcode;
import com.gatar.Spizarka.Database.Objects.Item;
import com.gatar.Spizarka.Database.ManagerDAOImpl;
import com.gatar.Spizarka.Application.MyApp;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Model layer for both Presenters in Depot.
 */
public class DepotModel implements DepotMVP.ModelOperations{

    DepotMVP.RequiredPresenterOperationsOverview mPresenterOverview;
    DepotMVP.RequiredPresenterOperationsDetail mPresenterDetail;

    @Inject
    SharedPreferences preferences;

    @Inject
    SharedPreferences.Editor preferencesEditor;

    @Inject
    ManagerDAOImpl managerDAOImpl;

    @Inject
    InternetConnectionStatus internetConnectionStatus;

    @Inject
    Context context;

    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";
    private final String ITEM_ID = "com.example.spizarka.ITEM_ID";
    private final static String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";

    public DepotModel(Object mPresenter) {
        if(mPresenter instanceof DepotMVP.RequiredPresenterOperationsOverview)
            mPresenterOverview = (DepotMVP.RequiredPresenterOperationsOverview) mPresenter;
        else mPresenterDetail = (DepotMVP.RequiredPresenterOperationsDetail) mPresenter;
        MyApp.getAppComponent().inject(this);
        managerDAOImpl.synchronizeDatabases();
    }


    @Override
    public <T> void setPreferencesValue(String preferenceName, T value) {
        if(value instanceof String) preferencesEditor.putString(preferenceName,(String) value);
        else if(value instanceof Long) preferencesEditor.putLong(preferenceName,(Long) value);
        preferencesEditor.commit();
    }

    @Override
    public void getAllItems() {
        ArrayList<Item> items = managerDAOImpl.getAllItems(false);
        mPresenterOverview.setItemList(items);
        mPresenterOverview.reportFromModel("All items list loaded.");
    }

    @Override
    public void getAllItemsOverZeroQuantity() {
        ArrayList<Item> items = managerDAOImpl.getAllItems(true);
        mPresenterOverview.setItemList(items);
        mPresenterOverview.reportFromModel("All on stock items list loaded.");
    }

    @Override
    public void getShoppingList() {
        ArrayList<Item> items = managerDAOImpl.getShoppingList();
        mPresenterOverview.setItemList(items);
        mPresenterOverview.reportFromModel("Shopping list loaded");
    }

    @Override
    public void getSingleItem(Long itemId) {
        Item item = managerDAOImpl.getSingleItem(itemId);
        mPresenterDetail.setItemOnView(item);
    }

    @Override
    public void setBarcodePreferenceByItemId(Long requestedItemId){
        String barcode = managerDAOImpl.getFirstBarcodeByItemId(requestedItemId);
        preferencesEditor.putString(EXTRA_BARCODE,barcode);
        preferencesEditor.commit();
    }

    @Override
    public void addNewBarcode(Item item, String barcode) {
        Barcode bar = new Barcode(barcode,item.getId());
        managerDAOImpl.addNewBarcode(bar);
        mPresenterOverview.reportFromModel("Barcode added to database");
    }

    @Override
    public void getItemId(){
        mPresenterDetail.setRequestItemId(preferences.getLong(ITEM_ID,-1));
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

    @Override
    public void synchronizeDatabases() {
        managerDAOImpl.synchronizeDatabases();
    }

    @Override
    public boolean isConnectedWithInternet(){
        if(!internetConnectionStatus.isConnectedWithInternet()){
            mPresenterDetail.reportFromModel(context.getString(R.string.no_internet_connection));
            return false;
        }
        return true;
    }
}


