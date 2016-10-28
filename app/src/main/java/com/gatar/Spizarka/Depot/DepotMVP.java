package com.gatar.Spizarka.Depot;

import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Depot.Operations.DepotSortTypes;

import java.util.ArrayList;

/**
 * Created by Gatar on 2016-10-26.
 */
public interface DepotMVP {

    interface RequiredViewOperations {
        interface Detail {
            /**
             * Change View for Actualize item data.
             */
            void toUpdateItemDataActivity();

            /**
             * Fill view with Item data
             */
            void setDataOnView(Item item);
        }

        interface Overview {
            /**
             * Show Toast meessage.
             */
            void showToast(String message);

            /**
             * load to view actual List Adapter
             * @param depotItems
             */
            void fillListByItems(ArrayList<Item> depotItems);

            /**
             * Set detail fragment.
             */
            void setDetailFragment();

            /**
             * Set detail to update Item quantity after add new Barcode.
             */
            void toChangeActivity();
        }
    }

    interface PresenterOperationsOverview {
        void setItemForDetailFragment(Item item);

        void sortListView(ArrayList<Item> depotItems, DepotSortTypes sortType);

        void limitByCategoryListView(ArrayList<Item> depotItems, Categories chosenCategory);

        void setListView();

    }

    interface PresenterOperationsDetail{
        void fillViewWithData();

        void updateItem();
    }

    interface ModelOperations{
        void setPreferencesValue(String preferenceName, String option);

        void getDepotOptions(String preferenceName);

        void getAllItems ();

        void getAllItemsOverZeroQuantity ();

        void getShoppingList ();

        void getPreferencesValue(String preferenceName);

        void addNewBarcode(Item item, String barcode);

        void getSingleItem(String itemTitle);
    }

    interface RequiredPresenterOperationsOverview {
        void reportFromModel(String report);

        void setItemList(ArrayList<Item> depotItem);

        void setDepotOption(DepotOptions option);

        void setPreferencesRequestValue(String requestValue);
    }

    interface RequiredPresenterOperationsDetail{
        void setPreferencesRequestValue(String requestValue);

        void setItemOnView(Item item);
    }

}
