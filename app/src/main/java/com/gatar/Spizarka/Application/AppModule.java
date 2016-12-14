package com.gatar.Spizarka.Application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gatar.Spizarka.Database.ManagerDAOImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module providing field injections for Dagger2 use.
 */
@Module
public class AppModule {
    private MyApp myApp;
    private final ManagerDAOImpl managerDAOImpl;
    private final RequestQueue mRequestQueue;
    private final InternetConnectionStatus internetConnectionStatus;

    public AppModule(MyApp myApp){
        this.myApp = myApp;
        managerDAOImpl = new ManagerDAOImpl(myApp);
        mRequestQueue = Volley.newRequestQueue(myApp);
        internetConnectionStatus = new InternetConnectionStatus(myApp);
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
    public ManagerDAOImpl provideManagerDAO(){
        return managerDAOImpl;
    }

    @Provides
    public Context provideAppContext(){
        return myApp;
    }

    @Provides
    @Singleton
    public RequestQueue provideRequestQueue(){
        return mRequestQueue;
    }

    @Provides
    @Singleton
    public InternetConnectionStatus provideInternetConnectionStatus(){
        return internetConnectionStatus;
    }
}
