package com.gatar.Spizarka.Application;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Class for check internet connection status.
 */
public class InternetConnectionStatus {

    private Context context;

    public InternetConnectionStatus(Context context) {
        this.context = context;
    }

    /**
     * Check internet connection.
     * @return true - connection works, false - no internet connection
     */
    public boolean isConnectedWithInternet() {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }

}
