package com.gatar.Spizarka.Application;

import android.app.Application;
import android.content.Context;


/**
 * Class providing Context access in every class in application by App.getAppContext().
 * Used only for provide Categories enum access to Strings with names from Resources.
 */

public class MyApp extends Application {

    public AppComponent component;
    private static Context context;

    public AppModule getApplicationModule(){
        return new AppModule(this);
    }

    public static AppComponent getAppComponent(){
        MyApp myApp = (MyApp) context.getApplicationContext();
        return myApp.component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.context = this;
        component = DaggerAppComponent.builder().appModule(getApplicationModule()).build();
    }

    public static Context getAppContext() {
        return context;
    }
}

