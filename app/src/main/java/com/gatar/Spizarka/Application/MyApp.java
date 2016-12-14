package com.gatar.Spizarka.Application;

import android.app.Application;

/**
 * Class providing service for Dagger2 dependency injection
 */
public class MyApp extends Application {

    public static AppComponent component;

    public AppModule getApplicationModule(){
        return new AppModule(this);
    }

    public static AppComponent getAppComponent(){
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().appModule(getApplicationModule()).build();
    }
}

