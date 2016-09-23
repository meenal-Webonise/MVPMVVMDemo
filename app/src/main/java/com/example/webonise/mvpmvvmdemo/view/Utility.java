package com.example.webonise.mvpmvvmdemo.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Utils class define for common operations to be used in app.
 */
public class Utility {

    private static Utility mInstance = null;
    private ConnectivityManager connectivityManager;
    private boolean connected;

    public static Utility getInstance() {

        if (mInstance == null) {
            mInstance = new Utility();
        }
        return mInstance;
    }

    /*  method to check internet connectivity */
    public boolean isOnline(Context mContext) {
        try {
            connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

}
