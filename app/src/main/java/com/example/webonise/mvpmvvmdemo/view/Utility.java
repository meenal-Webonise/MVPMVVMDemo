package com.example.webonise.mvpmvvmdemo.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by webonise on 22/9/16.
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
