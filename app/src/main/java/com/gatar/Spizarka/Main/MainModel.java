package com.gatar.Spizarka.Main;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.ManagerDAOImpl;
import com.gatar.Spizarka.Application.MyApp;


import javax.inject.Inject;


/**
 * Model layer of MainActivity.
 */
public class MainModel implements MainMVP.ModelOperations{

    private MainMVP.RequiredPresenterOperations mPresenter;

    @Inject SharedPreferences.Editor preferencesEditor;
    @Inject ManagerDAOImpl managerDAOImpl;
    @Inject Context context;

    public MainModel(MainMVP.RequiredPresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
        MyApp.getAppComponent().inject(this);
    }

    @Override
    public void deleteInternalDatabase() {
        managerDAOImpl.deleteDatabase();
        mPresenter.reportFromModel(context.getString(R.string.database_erased));
    }

    @Override
    public void setMainViewPreferences(String preferenceName, Enum option) {
        preferencesEditor.putString(preferenceName,option.toString());
        preferencesEditor.commit();
    }

    @Override
    public boolean isConnectedWithInternet() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        
        if(!isConnected) showNoInternetConnectionToast();
        
        return isConnected;
    }
    
    private void  showNoInternetConnectionToast(){
        mPresenter.reportFromModel(context.getString(R.string.no_internet_connection));
    }
}
