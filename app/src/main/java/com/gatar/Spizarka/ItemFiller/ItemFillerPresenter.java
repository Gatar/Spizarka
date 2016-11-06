package com.gatar.Spizarka.ItemFiller;

import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Depot.DepotOptions;

import java.lang.ref.WeakReference;

/**
 * Presenter layer for ItemFiller.
 */
public class ItemFillerPresenter implements ItemFillerMVP.PresenterOperations, ItemFillerMVP.RequiredPresenterOperations{

    private WeakReference<ItemFillerMVP.RequiredViewOperations> mView;
    private ItemFillerMVP.ModelOperations mModel;

    private ItemFillerOptions options;
    private Item viewItem;

    public ItemFillerPresenter(ItemFillerMVP.RequiredViewOperations mView) {
        this.mView = new WeakReference<ItemFillerMVP.RequiredViewOperations>(mView);
        mModel = new ItemFillerModel(this);
    }

    @Override
    public void getCorrectView() {
        mModel.getItemFillerPreferences();

        switch (options){
            case AddProduct:
                getView().setButtonView(options);
                getView().setDataView(options, new Item());
                break;

            case AddBarcodeToProduct:
                mModel.setDepotPreferences(DepotOptions.AddBarcodeToExistingItemView);
                getView().toDepotActivity();
                break;


            case DecreaseQuantity:
            case IncreaseQuantity:
            case UpdateItem:
                mModel.getItemByBarcode();
                getView().setButtonView(options);
                getView().setDataView(options, viewItem);
                break;
        }
    }

    @Override
    public void saveItem(Item item, Boolean scanNext) {
        mModel.getItemFillerPreferences();

        switch (options){
            case AddProduct:
                mModel.addNewItem(item);
                goToNextActivity(scanNext);
                break;

            case IncreaseQuantity:
            case DecreaseQuantity:
                mModel.updateItem(item);
                goToNextActivity(scanNext);
                break;

            case UpdateItem:
                mModel.updateItem(item);
                getView().toDepotActivity();
                break;
        }
    }

    @Override
    public void setItem(Item item){
        this.viewItem = item;
    }

    @Override
    public void reportFromModel(String report) {
        getView().showToast(report);
    }

    @Override
    public void setItemFillerOptions(ItemFillerOptions options) {
        this.options = options;
    }

    private ItemFillerMVP.RequiredViewOperations getView() throws NullPointerException{
        if ( mView != null )
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    private void goToNextActivity(boolean scanNext){
        if(scanNext) getView().toBarcodeScanner();
        else getView().toMainMenu();
    }
}
