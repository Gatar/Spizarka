package com.gatar.Spizarka.Main;

import android.content.SharedPreferences;

import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.Application.MyApp;

import javax.inject.Inject;

/**
 * Model layer of MainActivity.
 */
public class MainModel implements MainMVP.ModelOperations{

    private MainMVP.RequiredPresenterOperations mPresenter;

    @Inject SharedPreferences.Editor preferencesEditor;
    @Inject ManagerDAO managerDAO;

    public MainModel(MainMVP.RequiredPresenterOperations mPresenter) {
        this.mPresenter = mPresenter;
        MyApp.getAppComponent().inject(this);
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

}
