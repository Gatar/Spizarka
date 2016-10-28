package com.gatar.Spizarka.BarcodeScanner;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;

/**
 * Created by Gatar on 2016-10-27.
 */
public class BarcodeScannerPresenter implements BarcodeScannerMVP.PresenterOperations, BarcodeScannerMVP.RequiredPresenterOperations {

    private BarcodeScannerMVP.RequiredViewOperations mView;
    private BarcodeScannerMVP.ModelOperations mModel;

    public BarcodeScannerPresenter(BarcodeScannerMVP.RequiredViewOperations mView) {
        this.mView = mView;
        mModel = new BarcodeScannerModel(this);
    }

    @Override
    public void handleScannedBarcode(String barcode) {
        mModel.setBarcodePreferences(barcode);
        mModel.isBarcodeExistInDatabase(barcode);
    }

    @Override
    public void handleNewBarcode(ItemFillerOptions options) {
        mModel.setItemFillerPreferences(options);
        mView.toItemFillerAcitivity();
    }

    @Override
    public void askHowHandleNewBarcode() {
        mView.showNewBarcodeDialogBox();
    }

    @Override
    public void handleExistingBarcode() {
        mView.toItemFillerAcitivity();
    }

    @Override
    public void reportFromModel(String report) {
        mView.showToast(report);
    }
}
