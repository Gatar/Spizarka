package com.gatar.Spizarka.Depot.Presenter;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Depot.DepotMVP;
import com.gatar.Spizarka.Depot.DepotModel;
import com.gatar.Spizarka.Depot.DepotOptions;
import com.gatar.Spizarka.Depot.Operations.DepotCategoryLimiter;
import com.gatar.Spizarka.Depot.Operations.DepotSort;
import com.gatar.Spizarka.Depot.Operations.DepotSortTypes;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Gatar on 2016-10-26.
 */
public class DepotOverviewPresenter implements DepotMVP.PresenterOperationsOverview, DepotMVP.RequiredPresenterOperationsOverview {

    private WeakReference<DepotMVP.RequiredViewOperations.Overview> mView;

    private DepotMVP.ModelOperations mModel;

    private DepotOptions options;
    private DepotSort depotSort;
    private DepotCategoryLimiter depotCategoryLimiter;
    private String requestedItemId;


    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";
    private final String ITEM_ID = "com.example.spizarka.ITEM_ID";
    private final static String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";
    private final static String EXTRA_BARCODE = "com.example.gatar.spizarkainterfejs.BARCODE";

    public DepotOverviewPresenter(DepotMVP.RequiredViewOperations.Overview mView) {
        this.mView = new WeakReference<DepotMVP.RequiredViewOperations.Overview>(mView);
        this.mModel = new DepotModel(this);
        this.depotSort = new DepotSort();
    }


    @Override
    public void sortListView(ArrayList<Item> depotItems, DepotSortTypes sortType) {
        depotSort.setDepotItems(depotItems);
        depotItems = depotSort.sort(sortType);

        getView().fillListByItems(depotItems, options);
    }

    @Override
    public void limitByCategoryListView(ArrayList<Item> depotItems, Categories chosenCategory) {
        if(depotCategoryLimiter == null) depotCategoryLimiter = new DepotCategoryLimiter(depotItems);
        depotCategoryLimiter.getAllItems();
        depotItems = depotCategoryLimiter.limitByCategory(chosenCategory);

        getView().fillListByItems(depotItems, options);
    }

    @Override
    public void setListView() {
        mModel.getDepotOptions(DEPOT_ACTIVITY_OPTION);
        switch(options){
            case DepotView:
                mModel.getAllItemsOverZeroQuantity();
                break;

            case ShoppingListView:
                mModel.getShoppingList();
                break;

            case AddBarcodeToExistingItemView:
                mModel.getAllItems();
                break;
        }
    }

    @Override
    public void setItemForDetailFragment(Item item) {
        mModel.getDepotOptions(DEPOT_ACTIVITY_OPTION);
        mModel.setPreferencesValue(ITEM_ID,item.getId().toString());

        switch (options){
            case AddBarcodeToExistingItemView:
                mModel.setPreferencesValue(CHANGE_ACTIVITY_OPTION, ItemFillerOptions.IncreaseQuantity.toString());
                mModel.getPreferencesValue(EXTRA_BARCODE);
                mModel.addNewBarcode(item, requestedItemId);
                getView().toChangeActivity();
                break;
            default:
                getView().setDetailFragment();
                break;
        }
    }

    @Override
    public void reportFromModel(String report) {
        getView().showToast(report);
    }

    @Override
    public void setItemList(ArrayList<Item> depotItem) {
        getView().fillListByItems(depotItem, options);
    }

    @Override
    public void setDepotOption(DepotOptions options) {
        this.options = options;
    }

    @Override
    public void setRequestItemId(String requestValue) {
        this.requestedItemId = requestValue;
    }

    /**
     * Return the View reference.
     * Throw an exception if the View is unavailable.
     */
    private DepotMVP.RequiredViewOperations.Overview getView() throws NullPointerException{
        if ( mView != null )
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }
}
