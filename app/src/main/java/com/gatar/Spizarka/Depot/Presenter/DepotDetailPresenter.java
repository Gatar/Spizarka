package com.gatar.Spizarka.Depot.Presenter;

import com.gatar.Spizarka.Activities.ChangeOptions;
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
    private String requestedPreferenceValue;

    private final String EXTRA_TITLE = "com.example.spizarka.TITLE";
    private final String CHANGE_ACTIVITY_OPTION = "com.example.spizarka.changeActivityOption";

    public DepotDetailPresenter(DepotMVP.RequiredViewOperations.Detail mView) {
        this.mView = new WeakReference<DepotMVP.RequiredViewOperations.Detail>(mView);
        this.mModel = new DepotModel(this);
    }

    @Override
    public void fillViewWithData() {
        mModel.getPreferencesValue(EXTRA_TITLE);
        mModel.getSingleItem(requestedPreferenceValue);
    }

    @Override
    public void updateItem() {
        mModel.setPreferencesValue(CHANGE_ACTIVITY_OPTION, ChangeOptions.UpdateItem.toString());
        getView().toUpdateItemDataActivity();
    }

    @Override
    public void setPreferencesRequestValue(String requestValue) {
        this.requestedPreferenceValue = requestValue;
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
