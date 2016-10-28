package com.gatar.Spizarka.Depot;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.Operations.GlobalContextProvider;

import java.util.ArrayList;

/**
 * Created by Gatar on 2016-10-26.
 */
public class DepotModel implements DepotMVP.ModelOperations{

    DepotMVP.RequiredPresenterOperationsOverview mPresenterOverview;
    DepotMVP.RequiredPresenterOperationsDetail mPresenterDetail;

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private ManagerDAO managerDAO;

    public DepotModel(Object mPresenter) {
        if(mPresenter instanceof DepotMVP.RequiredPresenterOperationsOverview)
            mPresenterOverview = (DepotMVP.RequiredPresenterOperationsOverview) mPresenter;
        else mPresenterDetail = (DepotMVP.RequiredPresenterOperationsDetail) mPresenter;

        managerDAO = new ManagerDAO(GlobalContextProvider.getAppContext());
        setPreferences();
    }

    @Override
    public void setPreferencesValue(String preferenceName, String option) {
        preferencesEditor.putString(preferenceName,option);
        preferencesEditor.commit();
    }

    @Override
    public void getPreferencesValue(String preferenceName){
        if(mPresenterOverview != null) mPresenterOverview.setPreferencesRequestValue(preferences.getString(preferenceName,null));
            else mPresenterDetail.setPreferencesRequestValue(preferences.getString(preferenceName,null));
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
    public void getSingleItem(String itemTitle) {
        Item item = managerDAO.getSingleItem(itemTitle);
        mPresenterDetail.setItemOnView(item);
    }

    @Override
    public void addNewBarcode(Item item, String barcode) {
        managerDAO.addNewBarcode(barcode,item.getTitle());
        mPresenterOverview.reportFromModel("Barcode added to database");
    }

    @Override
    public void getDepotOptions(String preferenceName) {
        DepotOptions options = DepotOptions.valueOf(preferences.getString(preferenceName, DepotOptions.DepotView.name()));
        mPresenterOverview.setDepotOption(options);
    }

    private void setPreferences(){
        preferences = GlobalContextProvider.getAppContext().getSharedPreferences(GlobalContextProvider.getAppContext().getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }
}
