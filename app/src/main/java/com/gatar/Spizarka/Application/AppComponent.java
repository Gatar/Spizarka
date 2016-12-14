package com.gatar.Spizarka.Application;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.gatar.Spizarka.Account.AccountModel;
import com.gatar.Spizarka.BarcodeScanner.BarcodeScannerModel;
import com.gatar.Spizarka.Database.Categories;
import com.gatar.Spizarka.Database.ManagerDAOImpl;
import com.gatar.Spizarka.Database.RemoteDatabaseDAOImpl;
import com.gatar.Spizarka.Depot.DepotModel;
import com.gatar.Spizarka.ItemFiller.ItemFillerModel;
import com.gatar.Spizarka.Main.MainModel;
import com.gatar.Spizarka.Main.View.MainActivity;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Interface with application components serviced by Dagger
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    SharedPreferences.Editor provideSharedPreferencesEditor();
    SharedPreferences provideSharedPreferences();
    ManagerDAOImpl provideManagerDAO();
    Context provideAppContext();
    RequestQueue provideRequestQueue();

    void inject(DepotModel model);
    void inject(ItemFillerModel model);
    void inject(BarcodeScannerModel model);
    void inject(MainModel model);
    void inject(Categories categories);
    void inject(AccountModel accountModel);
    void inject(MainActivity mainActivity);
    void inject(RemoteDatabaseDAOImpl remoteDatabase);
}
