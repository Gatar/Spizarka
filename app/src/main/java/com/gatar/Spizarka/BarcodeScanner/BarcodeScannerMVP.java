package com.gatar.Spizarka.BarcodeScanner;

import com.gatar.Spizarka.BarcodeScanner.View.DialogBoxNewBarcode;
import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;

/**
 * MVP pattern interface for BarcodeScanner.
 */
public interface BarcodeScannerMVP {

    //---------Operations in Views used by Presenter-------------------
    interface RequiredViewOperations{
        /**
         * Show dialogbox how handle unknown barcode:
         * - add to existing product
         * - create new product
         */
        void showNewBarcodeDialogBox();

        /**
         * Use intent to start ItemFiller activity.
         */
        void toItemFillerAcitivity();

        /**
         * Show Toast message.
         */
        void showToast(String message);
    }

    //---------Operations in Presenter used by Views-------------------
    interface PresenterOperations{

        /**
         * Handling every each scanned barcode. Method add barcode to SharedPreferences and start checking does barcode exist in database.
         * @param barcode receive as String from {@link com.gatar.Spizarka.BarcodeScanner.View.BarcodeScannerActivity}
         */
        void handleScannedBarcode(String barcode);

        /**
         * Handling new barcode after {@link DialogBoxNewBarcode} answer..
         * Set {@link ItemFillerOptions} type in preferences and start ItemFiller activity for input product data.
         * @param options received from DialogBox: AddBarcodeToProduct or AddProduct.
         */
        void handleNewBarcode(ItemFillerOptions options);
    }

    //---------Operations in Presenter used by Model-------------------
    interface RequiredPresenterOperations{

        /**
         * Starts {@link DialogBoxNewBarcode} for get info from user how handle new barcode.
         */
        void askHowHandleNewBarcode();

        /**
         * Starts ItemFiller activity.
         */
        void handleExistingBarcode();

        /**
         * Passing massage to make Toast in View.
         * @param report message body
         */
        void reportFromModel(String report);
    }

    //---------Operations in Model used by Presenter-------------------
    interface ModelOperations{

        /**
         * Check does barcode exist in database.
         * Starting Presenter method depend on answer:
         * - barcode exist:             check correctness of set{@link ItemFillerOptions} and start ItemFiller activity
         * - barcode doesn't exist:     start method asking by dialog box what to do with new barcode
         * @param barcode value to check
         */
        void isBarcodeExistInDatabase(String barcode);

        /**
         * Set {@link ItemFillerOptions} value in preferences.
         * @param options value to set
         */
        void setItemFillerPreferences(ItemFillerOptions options);

        /**
         * Set barcode value in preferences.
         * @param barcode value to set
         */
        void setBarcodePreferences(String barcode);
    }
}
