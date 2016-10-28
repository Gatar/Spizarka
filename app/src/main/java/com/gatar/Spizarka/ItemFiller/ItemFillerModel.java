package com.gatar.Spizarka.ItemFiller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.Depot.DepotOptions;
import com.gatar.Spizarka.Operations.GlobalContextProvider;

/**
 * Created by Gatar on 2016-10-27.
 */
public class ItemFillerModel implements ItemFillerMVP.ModelOperations{

    private ItemFillerMVP.RequiredPresenterOperations mPresenter;

    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private ManagerDAO managerDAO;

    private final static String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";
    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";
    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";

    public ItemFillerModel(ItemFillerMVP.RequiredPresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
        managerDAO = new ManagerDAO(GlobalContextProvider.getAppContext());
        setPreferences();
    }

    @Override
    public void addNewItem(Item item) {
        managerDAO.addNewItem(item);
        managerDAO.addNewBarcode(getBarcodePreferences(),item.getTitle());
        mPresenter.reportFromModel(GlobalContextProvider.getAppContext().getString(R.string.communicateAddedItemAndBarcode));
    }

    @Override
    public void updateItem(Item item) {
        managerDAO.updateItem(item);
        mPresenter.reportFromModel(GlobalContextProvider.getAppContext().getString(R.string.communicateItemUpdated));
    }

    @Override
    public void getItemFillerPreferences() {
        ItemFillerOptions options = ItemFillerOptions.valueOf(preferences.getString(CHANGE_ACTIVITY_OPTION,null));
        mPresenter.setItemFillerOptions(options);
    }

    @Override
    public void getItemByBarcode(){
        String barcode = preferences.getString(EXTRA_BARCODE,null);
        Item item = managerDAO.getSingleItem(managerDAO.getTitle(barcode));
        mPresenter.setItem(item);
    }

    @Override
    public void setDepotPreferences(DepotOptions depotOptions){
        preferencesEditor.putString(DEPOT_ACTIVITY_OPTION,depotOptions.toString());
        preferencesEditor.commit();
    }

    private void setPreferences(){
        preferences = GlobalContextProvider.getAppContext().getSharedPreferences(GlobalContextProvider.getAppContext().getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }

    private String getBarcodePreferences(){
        return  preferences.getString(EXTRA_BARCODE,null);
    }
}
