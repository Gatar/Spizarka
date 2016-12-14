package com.gatar.Spizarka.ItemFiller;

import com.gatar.Spizarka.Database.Objects.Item;
import com.gatar.Spizarka.Depot.DepotOptions;

/**
 * MVP pattern interface for ItemFiller activity.
 */
public interface ItemFillerMVP {

    //---------Operations in View used by Presenter-------------------
    interface RequiredViewOperations{

        /**
         * Set correct for actual {@link ItemFillerOptions} DataView, filled with data of item from database (or empty item in case of add new item).
         * @param options {@link ItemFillerOptions} used by DataView factory for set correct view.
         * @param item with data for set on view.
         */
        void setDataView(ItemFillerOptions options, Item item);

        /**
         * Set correct button for actual {@link ItemFillerOptions}.
         * @param options {@link ItemFillerOptions} used by Button factory for set correct view.
         */
        void setButtonView(ItemFillerOptions options);

        /**
         * Start intent to go to Depot activity.
         */
        void toDepotActivity();


        /**
         * Start intent to go to Main activity.
         */
        void toMainMenu();


        /**
         * Start intent to go to BarcodeScanner activity.
         */
        void toBarcodeScanner();

        /**
         * Show Toast message.
         */
        void showToast(String message);
    }

    //---------Operations in Presenter used by View-------------------
    interface PresenterOperations{
        /**
         * Set correct view in fragments based on SharedPreferences settings.
         */
        void getCorrectView();

        /**
         * Create/update to database item with data from DataView fragment edit text fields.
         * @param item object to save in database
         * @param scanNext true - start BarcodeScanner activity after save, false - start Main activity after save
         */
        void saveItem(Item item, Boolean scanNext);
    }

    //---------Operations in Presenter used by Model-------------------
    interface RequiredPresenterOperations{

        /**
         * Set in Presenter reference for actual {@link ItemFillerOptions} from preferences.
         * @param options value for set.
         */
        void setItemFillerOptions(ItemFillerOptions options);

        /**
         * Set in Presenter reference for {@link Item} value from Model. Referenced Item will be use next for set on DataView.
         * @param item value for Item.
         */
        void setItem(Item item);

        /**
         Passing massage to make Toast in View.
         * @param report message body
         */
        void reportFromModel(String report);
    }

    //---------Operations in Model used by Presenter-------------------
    interface ModelOperations{

        /**
         * Add new {@link Item} to database.
         * @param item values for save
         */
        void addNewItem(Item item);

        /**
         * Update existing item in database.
         * @param item values for update.
         */
        void updateItem(Item item);

        /**
         * Set in Presenter reference for item from database extracted on base of barcode value.
         */
        void getItemByBarcode();

        /**
         * Set in Presenter reference for {@link ItemFillerOptions} from preferences.
         */
        void getItemFillerPreferences();

        /**
         * Save in Shared Preferences value for {@link ItemFillerOptions}
         * @param depotOptions value for save.
         */
        void setDepotPreferences(DepotOptions depotOptions);

        /**
         * Synchronize cloud with internal database
         */
        void synchronizeDatabases();
    }
}
