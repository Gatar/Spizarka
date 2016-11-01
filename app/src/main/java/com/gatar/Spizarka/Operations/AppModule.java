package com.gatar.Spizarka.Operations;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gatar.Spizarka.Database.ManagerDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gatar on 2016-11-01.
 */
@Module
public class AppModule {
    private MyApp myApp;
    private final ManagerDAO managerDAO;

    public AppModule(MyApp myApp){
        this.myApp = myApp;
        managerDAO = new ManagerDAO(myApp);
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(myApp);
    }

    @Provides
    public SharedPreferences.Editor provideSharedPreferencesEditor(){
        return PreferenceManager.getDefaultSharedPreferences(myApp).edit();
    }

    @Provides
    @Singleton
    public ManagerDAO provideManagerDAO(){
        return managerDAO;
    }

    @Provides
    public Context provideAppContext(){
        return myApp;
    }
}
