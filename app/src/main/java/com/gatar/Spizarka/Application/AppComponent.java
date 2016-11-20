package com.gatar.Spizarka.Application;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.gatar.Spizarka.Account.AccountModel;
import com.gatar.Spizarka.BarcodeScanner.BarcodeScannerModel;
import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.ManagerDAO;
import com.gatar.Spizarka.Depot.DepotModel;
import com.gatar.Spizarka.ItemFiller.ItemFillerModel;
import com.gatar.Spizarka.Main.MainModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Gatar on 2016-10-30.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    SharedPreferences.Editor provideSharedPreferencesEditor();
    SharedPreferences provideSharedPreferences();
    ManagerDAO provideManagerDAO();
    Context provideAppContext();
    RequestQueue provideRequestQueue();

    void inject(DepotModel model);
    void inject(ItemFillerModel model);
    void inject(BarcodeScannerModel model);
    void inject(MainModel model);
    void inject(Categories categories);
    void inject(ManagerDAO managerDAO);
    void inject(AccountModel accountModel);
}
