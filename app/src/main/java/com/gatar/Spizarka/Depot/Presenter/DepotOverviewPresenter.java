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
 * Presenter for overview depot view.
 */
public class DepotOverviewPresenter implements DepotMVP.PresenterOperationsOverview, DepotMVP.RequiredPresenterOperationsOverview {

    private WeakReference<DepotMVP.RequiredViewOperations.Overview> mView;

    private DepotMVP.ModelOperations mModel;

    private DepotOptions options;
    private DepotSort depotSort;
    private DepotCategoryLimiter depotCategoryLimiter;
    private String requestedBarcode;


    private final String ITEM_ID = "com.example.spizarka.ITEM_ID";
    private final static String ITEM_FILLER_OPTION = "com.example.spizarka.changeActivityOption";

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
        mModel.getDepotOptions();
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
        mModel.getDepotOptions();
        mModel.setPreferencesValue(ITEM_ID,item.getId());

        switch (options){
            case AddBarcodeToExistingItemView:
                mModel.setPreferencesValue(ITEM_FILLER_OPTION, ItemFillerOptions.IncreaseQuantity.toString());
                mModel.getBarcode();
                mModel.addNewBarcode(item, requestedBarcode);
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
    public void setRequestBarcode(String requestBarcode) {
        this.requestedBarcode = requestBarcode;
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
