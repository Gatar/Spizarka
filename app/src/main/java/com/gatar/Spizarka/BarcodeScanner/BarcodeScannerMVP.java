package com.gatar.Spizarka.BarcodeScanner;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;

/**
 * Created by Gatar on 2016-10-27.
 */
public interface BarcodeScannerMVP {
    interface RequiredViewOperations{
        void showNewBarcodeDialogBox();

        void toItemFillerAcitivity();
    }

    interface PresenterOperations{
        void handleScannedBarcode(String barcode);

        void handleNewBarcode(ItemFillerOptions options);
    }

    interface RequiredPresenterOperations{
        void askHowHandleNewBarcode();

        void handleExistingBarcode();
    }

    interface ModelOperations{
        void isBarcodeExistInDatabase(String barcode);
    }
}
