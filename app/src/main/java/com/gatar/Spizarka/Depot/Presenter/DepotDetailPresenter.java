package com.gatar.Spizarka.Depot.Presenter;

import com.gatar.Spizarka.ItemFiller.ItemFillerOptions;
import com.gatar.Spizarka.Database.Item;
import com.gatar.Spizarka.Depot.DepotMVP;
import com.gatar.Spizarka.Depot.DepotModel;

import java.lang.ref.WeakReference;

/**
 * Created by Gatar on 2016-10-26.
 */
public class DepotDetailPresenter implements DepotMVP.PresenterOperationsDetail, DepotMVP.RequiredPresenterOperationsDetail{

    WeakReference<DepotMVP.RequiredViewOperations.Detail> mView;

    DepotMVP.ModelOperations mModel;
    private Integer requestedItemId;

    private final String ITEM_ID = "com.example.spizarka.ITEM_ID";
    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";

    public DepotDetailPresenter(DepotMVP.RequiredViewOperations.Detail mView) {
        this.mView = new WeakReference<DepotMVP.RequiredViewOperations.Detail>(mView);
        this.mModel = new DepotModel(this);
    }

    @Override
    public void fillViewWithData() {
        mModel.getPreferencesValue(ITEM_ID);
        mModel.getSingleItem(requestedItemId);
    }

    @Override
    public void updateItem() {
        mModel.setPreferencesValue(CHANGE_ACTIVITY_OPTION, ItemFillerOptions.UpdateItem.toString());
        getView().toUpdateItemDataActivity();
    }

    @Override
    public void setRequestItemId(String requestValue) {
        this.requestedItemId = Integer.parseInt(requestValue);
    }

    @Override
    public void setItemOnView(Item item) {
        getView().setDataOnView(item);
    }

    private DepotMVP.RequiredViewOperations.Detail getView() throws NullPointerException{
        if ( mView != null )
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }
}
