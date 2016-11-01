package com.gatar.Spizarka.BarcodeScanner;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
import com.gatar.Spizarka.Operations.MyApp;

import javax.inject.Inject;

/**
 * Created by Gatar on 2016-10-27.
 */
public class BarcodeScannerModel implements BarcodeScannerMVP.ModelOperations{

    private BarcodeScannerMVP.RequiredPresenterOperations mPresenter;

    @Inject SharedPreferences preferences;
    @Inject SharedPreferences.Editor preferencesEditor;
    @Inject ManagerDAO managerDAO;

    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";
    private final static String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";

    public BarcodeScannerModel(BarcodeScannerMVP.RequiredPresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
        MyApp.getAppComponent().inject(this);
    }

    @Override
    public void isBarcodeExistInDatabase(String barcode) {
        if(managerDAO.isContainBarcode(barcode)) {
            checkItemFillerOptionCorrectness();
            mPresenter.handleExistingBarcode();
        }
            else mPresenter.askHowHandleNewBarcode();
    }

    @Override
    public void setItemFillerPreferences(ItemFillerOptions options) {
        preferencesEditor.putString(CHANGE_ACTIVITY_OPTION,options.toString());
        preferencesEditor.commit();
    }

    @Override
    public void setBarcodePreferences(String barcode) {
        preferencesEditor.putString(EXTRA_BARCODE,barcode);
        preferencesEditor.commit();
    }

    /**
     * In situation when we have option AddProduct, but we have item already existing in database, we must change our option to
     * IncreaseQuantity, because our product need only quantity change.
     */
    private void checkItemFillerOptionCorrectness(){
        ItemFillerOptions options = ItemFillerOptions.valueOf(preferences.getString(CHANGE_ACTIVITY_OPTION,null));
        if(options.equals(ItemFillerOptions.AddProduct)){
            setItemFillerPreferences(ItemFillerOptions.IncreaseQuantity);
        }
    }
}
