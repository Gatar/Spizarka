package com.example.gatar.Spizarka.Operations;

import android.app.Application;
import android.content.Context;

/**
 * Class providing Context access in every class in application by App.getAppContext().
 * Used only for provide Categories enum access to Strings with names from Resources.
 */
public class MyApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApp.context = getApplicationContext();
    }

    /**
     * Get application context.
     * @return return application Context.
     */
    public static Context getAppContext() {
        return MyApp.context;
    }
}

