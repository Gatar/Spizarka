package com.gatar.Spizarka.ItemFiller;

import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.ItemFiller.View.Buttons.ButtonViewStrategy;
import com.gatar.Spizarka.ItemFiller.View.DataView.DataViewStrategy;

/**
 * Created by Gatar on 2016-10-27.
 */
public interface ItemFillerMVP {

    interface RequiredViewOperations{
        void setDataView(DataViewStrategy dataView,Item item);

        void setButtonView(ButtonViewStrategy buttonStrategy);

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

        void addNewItem(Item item, Boolean scanNext);

        void increaseQuantityItem(Item item, Boolean scanNext);

        void decreaseQuantityItem(Item item, Boolean scanNext);

        void updateItem(Item item);
    }

    interface RequiredPresenterOperations{
        void setCorrectView(DataViewStrategy dataView, ButtonViewStrategy buttonViewStrategy, Item item);

        void addNewItem(Item item);

        void updateItem(Item item);

        void reportFromModel(String report);
    }

    interface ModelOperations{
        void addNewItem(Item item);

        void updateItem(Item item);
    }
}
