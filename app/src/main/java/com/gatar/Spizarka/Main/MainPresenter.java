package com.gatar.Spizarka.Main;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
import com.gatar.Spizarka.Depot.DepotOptions;

import java.lang.ref.WeakReference;

/**
 * Presenter layer for Main Menu activity.
 */
public class MainPresenter implements MainMVP.PresenterOperations, MainMVP.RequiredPresenterOperations {

    private WeakReference<MainMVP.RequiredViewOperations> mView;
    private MainMVP.ModelOperations mModel;

    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";
    private final String DEPOT_ACTIVITY_OPTION = "com.example.spizarka.depotActivityOption";


    public MainPresenter(MainMVP.RequiredViewOperations mView) {
        this.mView = new WeakReference<MainMVP.RequiredViewOperations>(mView);
        mModel = new MainModel(this);
    }

    @Override
    public void toAdd(){
        mModel.setMainViewPreferences(CHANGE_ACTIVITY_OPTION, ItemFillerOptions.AddProduct);
        getView().toBarcodeScannerView();
    }


    @Override
    public void toDecreaseQuantity(){
        mModel.setMainViewPreferences(CHANGE_ACTIVITY_OPTION, ItemFillerOptions.DecreaseQuantity);
        getView().toBarcodeScannerView();
    }


    @Override
    public void toDepot(){
        mModel.setMainViewPreferences(DEPOT_ACTIVITY_OPTION, DepotOptions.DepotView);
        getView().toDepotView();
    }


    @Override
    public void toShoppingList(){
        mModel.setMainViewPreferences(DEPOT_ACTIVITY_OPTION,DepotOptions.ShoppingListView);
        getView().toDepotView();
    }

    @Override
    public void toDatabaseDeleteDialog(){
        getView().startDeleteDatabaseDialog();
    }

    @Override
    public void deleteInternalDatabase() {
        mModel.deleteInternalDatabase();
    }

    @Override
    public void reportFromModel(String report) {
        getView().showToast(report);
    }

    /**
     * Return the View reference.
     * Throw an exception if the View is unavailable.
     */
    private MainMVP.RequiredViewOperations getView() throws NullPointerException{
        if ( mView != null )
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }


}
