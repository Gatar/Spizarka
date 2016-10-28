package com.gatar.Spizarka.ItemFiller;

import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Depot.DepotOptions;

/**
 * Created by Gatar on 2016-10-27.
 */
public interface ItemFillerMVP {

    interface RequiredViewOperations{
        void setDataView(ItemFillerOptions options, Item item);

        void setButtonView(ItemFillerOptions options);

        void toDepotActivity();

        void toMainMenu();

        void toBarcodeScanner();

        /**
         * Show Toast meessage.
         */
        void showToast(String message);
    }

    interface PresenterOperations{
        /**
         * Set correct view based on SharedPreferences settings.
         */
        void getCorrectView();

        void saveItem(Item item, Boolean scanNext);
    }

    interface RequiredPresenterOperations{
        void setItemFillerOptions(ItemFillerOptions options);

        void setItem(Item item);

        void reportFromModel(String report);
    }

    interface ModelOperations{
        void addNewItem(Item item);

        void updateItem(Item item);

        void getItemByBarcode();

        void getItemFillerPreferences();

        void setDepotPreferences(DepotOptions depotOptions);
    }
}
