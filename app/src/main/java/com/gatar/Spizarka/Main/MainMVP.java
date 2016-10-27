package com.gatar.Spizarka.Main;

import com.gatar.Spizarka.BarcodeScanner.View.BarcodeScannerActivity;
import com.gatar.Spizarka.Depot.View.DepotActivity;
import com.gatar.Spizarka.Main.View.MainDialogDatabaseDelete;

/**
 * MVP Interfaces for Main Menu.
 */
public interface MainMVP {

    /**
     * Presenter -> View operations
     */
    interface RequiredViewOperations {

        /**
         * Set DatabaseDeleteDialog.
         */
        void toDatabaseDeleteView();

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
         * Show Toast meessage.
         */
        void showToast(String message);
    }

    /**
     * View -> Presenter operations
     */
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
    }

    /**
     * Presenter -> Model operations
     */
    interface ModelOperations{
        void deleteInternalDatabase();

        void setMainViewPreferences(String preferenceName, Enum option);
    }

    /**
     * Model -> Presenter operations
     */
    interface RequiredPresenterOperations{
        void reportFromModel(String report);
    }
}
