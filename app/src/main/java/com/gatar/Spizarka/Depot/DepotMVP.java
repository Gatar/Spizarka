package com.gatar.Spizarka.Depot;

import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Objects.Item;
import com.gatar.Spizarka.Depot.Operations.DepotSortTypes;

import java.util.ArrayList;

/**
 * MVP pattern interface for Depot.
 */
public interface DepotMVP {

    //---------Operations in Views used by Presenter-------------------
    interface RequiredViewOperations {

        //--------------Detail View------------------
        interface Detail {
            /**
             * Change View for Actualize item data.
             */
            void toUpdateItemDataActivity();

            /**
             * Fill view with Item data
             */
            void setDataOnView(Item item);

            /**
             * Show Toast message.
             */
            void showToast(String message);
        }

        //--------------Overview View------------------
        interface Overview {
            /**
             * Show Toast message.
             */
            void showToast(String message);

            /**
             * Fill depot item listAdapter with
             * @param depotItems list of items to set on view
             * @param depotOptions options for control quantity view, different for shopping list than for overall list
             */
            void fillListByItems(ArrayList<Item> depotItems, DepotOptions depotOptions);

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

    //---------Operations in Overview Presenter used by view-------------------
    interface PresenterOperationsOverview {

        /**
         * Set item chosen from Overview list to DetailFragment view.
         * In case when list was shown for choose item to add new barcode in this method barcode will be added.
         * @param item for presentation
         */
        void setItemForDetailFragment(Item item);

        /**
         * Sort received item list based on sort type. Sorting order are changed after each button change ascending-descending.
         * @param depotItems list of items
         * @param sortType enum based types of sort: by title and by category
         */
        void sortListView(ArrayList<Item> depotItems, DepotSortTypes sortType);

        /**
         * Limit recived item list by category type. There can be chosen only one category type at the moment.
         * @param depotItems list of items
         * @param chosenCategory limit category based on enum value
         */
        void limitByCategoryListView(ArrayList<Item> depotItems, Categories chosenCategory);

        /**
         * Defining type of list for view on base of {@link DepotOptions} value from shared preferences. Possibilities:
         * <ol>
         *     <li>View of items in pantry with quantity over zero</li>
         *     <li>View for all items for add new barcode</li>
         *     <li>Shopping list view, only items with quantity below minimum quantity</li>
         * </ol>
         */
        void setListView();

    }

    //---------Operations in Detail Presenter used by view-------------------
    interface PresenterOperationsDetail{

        /**
         * Get single item from database by its ID and set on view in Detail Fragment.
         */
        void fillViewWithData();

        /**
         * Change {@link com.gatar.Spizarka.ItemFiller.ItemFillerOptions} to UpdateItem and start ItemFiller activity.
         */
        void updateItem();
    }

    //---------Operations in Presenter for Overview used by Model-------------------
    interface RequiredPresenterOperationsOverview {

        /**
         * Send message to create Toast.
         * @param report message as String
         */
        void reportFromModel(String report);

        /**
         * Passing item list from database to set it on Overwiew view.
         * @param depotItem items list to set on view.
         */
        void setItemList(ArrayList<Item> depotItem);

        /**
         * Set in Presenter private DepotOption variable.
         * @param option value for set.
         */
        void setDepotOption(DepotOptions option);

        /**
         * Set in Presenter private Barcode variable.
         * @param requestBarcode value for set.
         */
        void setRequestBarcode(String requestBarcode);
    }

    //---------Operations in Presenter for Detail used by Model-------------------
    interface RequiredPresenterOperationsDetail{

        /**
         * Set in Presenter private ItemId variable.
         * @param requestedItemId value for set.
         */
        void setRequestItemId(Long requestedItemId);

        /**
         * Passing item from database to set in on Detail view.
         * @param item item for set on view.
         */
        void setItemOnView(Item item);

        /**
         * Send message to create Toast.
         * @param report message as String
         */
        void reportFromModel(String report);
    }

    //---------Operations in Model used by Presenter-------------------
    interface ModelOperations{

        /**
         * Set value in Shared Preferences with key and value. It works for types Long and String.
         * @param preferenceName key value
         * @param value value for save in preferences
         * @param <T> can be only String and Long type
         */
        <T> void setPreferencesValue(String preferenceName, T value);

        /**
         * Get all items, with any quantity from database.
         */
        void getAllItems ();

        /**
         * Get items with quantity over zero from database.
         */
        void getAllItemsOverZeroQuantity ();

        /**
         * Get items with quantity below minimum quantity from database.
         */
        void getShoppingList ();

        /**
         * Get ID value of single item saved in preferences.
         */
        void getItemId();

        /**
         * Get {@link DepotOptions} value saved in preferences.
         */
        void getDepotOptions();

        /**
         * Get barcode value saved in preferences.
         */
        void getBarcode();

        /**
         * Add new barcode to database
         * @param item item connected with barcode
         * @param barcode value of barcode
         */
        void addNewBarcode(Item item, String barcode);

        /**
         * Get single item from database by id
         * @param itemId value of item's id
         */
        void getSingleItem(Long itemId);

        /**
         * Set in preferences value of first barcode in database connected with actual item.
         * It's necessary for bind correct item in ItemFiller activity for it update.
         * @param requestedItemId item's id value
         */
        void setBarcodePreferenceByItemId(Long requestedItemId);

        /**
         * Synchronize cloud with internal database
         */
        void synchronizeDatabases();

        /**
         * Check internet connection and show toast message if not.
         * @return true - internet connection established, false - no internet connection
         */
        boolean isConnectedWithInternet();
    }



}
