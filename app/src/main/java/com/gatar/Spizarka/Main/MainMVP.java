package com.gatar.Spizarka.Main;

import com.gatar.Spizarka.BarcodeScanner.View.BarcodeScannerActivity;
import com.gatar.Spizarka.Depot.View.DepotActivity;
import com.gatar.Spizarka.Main.View.MainDialogDatabaseDelete;

/**
 * MVP pattern interface for Main activity.
 */
public interface MainMVP {

    //---------Operations in View used by Presenter-------------------
    interface RequiredViewOperations {

        /**
         * Set DatabaseDeleteDialog.
         */
        void startDeleteDatabaseDialog();

        /**
         * Set BarcodeScannerActivity to scan barcode of add/remove product.
         * Type of the view is defined in SharedPreferences: CHANGE_ACTIVITY_OPTION and set up after button click.
         * {@link BarcodeScannerActivity}
         */
        void toBarcodeScannerView();

        /**
         * Set DepotActivity with view what are on stock/shopping list view.
         * Type of the view is defined in SharedPreferences: DEPOT_ACTIVITY_OPTION and set up after button click.
         * {@link DepotActivity}
         */
        void toDepotView();

        /**
         * Set LoginActivity to manage account in phone.
         */
        void toLoginActivity();

        /**
         * Show Toast meessage.
         */
        void showToast(String message);
    }

    //---------Operations in Presenter used by View-------------------
    interface PresenterOperations {

        /**
         * TEMPORARY: Set dialog box about database delete, finally -> settings.
         * {@link MainDialogDatabaseDelete}
         */
        void toDatabaseDeleteDialog();

        /**
         * Set BarcodeScannerActivity to scan barcode of add product.
         * {@link BarcodeScannerActivity}
         */
        void toAdd();

        /**
         * Set BarcodeScannerActivity to scan barcode of product, which quantity will be decreased.
         * {@link BarcodeScannerActivity}
         * If removed quantity are lower than on stock quantity it will be set at 0.
         */
        void toDecreaseQuantity();

        /**
         * Set DepotActivity with view what are on stock.
         * {@link DepotActivity}
         */
        void toDepot();

        /**
         * Set DepotActivity with view what products are under minimum level of quantity.
         * {@link DepotActivity}
         */
        void toShoppingList();

        /**
         * Tell MainModel to delete internal Android database.
         */
        void deleteInternalDatabase();

        /**
         * Set LoginActivity on view to manage Accounts.
         */
        void toLoginActivity();
    }

    //---------Operations in Presenter used by Model-------------------
    interface RequiredPresenterOperations{

        /**
         * Passing massage to make Toast in View.
         * @param report message body
         */
        void reportFromModel(String report);
    }

    //---------Operations in Model used by Presenter-------------------
    interface ModelOperations{

        /**
         * Delete internal android database ane restart to 0 database version in preferences.
         */
        void deleteInternalDatabase();

        /**
         * Set in preferences chosen by button click value of next activity - {@link com.gatar.Spizarka.Depot.DepotOptions} or {@link com.gatar.Spizarka.ItemFiller.ItemFillerOptions}
         * @param preferenceName key-value for preference
         * @param option value of Enum for set
         */
        void setMainViewPreferences(String preferenceName, Enum option);

        /**
         * Check Internet connection
         * @return true - connection are established, false - no connection
         */
        boolean isConnectedWithInternet();
    }

}
