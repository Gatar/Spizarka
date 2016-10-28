package com.gatar.Spizarka.Main;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gatar.Spizarka.R;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.Operations.GlobalContextProvider;

/**
 * Model layer of MainActivity.
 */
public class MainModel implements MainMVP.ModelOperations{

    private MainMVP.RequiredPresenterOperations mPresenter;

    private SharedPreferences.Editor preferencesEditor;
    private ManagerDAO managerDAO;



    public MainModel(MainMVP.RequiredPresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
        setPreferences();
        managerDAO = new ManagerDAO(GlobalContextProvider.getAppContext());
    }

    @Override
    public void deleteInternalDatabase() {
        managerDAO.deleteDatabase();
        mPresenter.reportFromModel("Database deleted.");
    }

    @Override
    public void setMainViewPreferences(String preferenceName, Enum option) {
        preferencesEditor.putString(preferenceName,option.toString());
        preferencesEditor.commit();
    }

    private void setPreferences(){
        SharedPreferences preferences = GlobalContextProvider.getAppContext().getSharedPreferences(GlobalContextProvider.getAppContext().getResources().getString(R.string.preferencesKey), Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }
}
