package com.example.webonise.mvpmvvmdemo.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utils class define for common operations to be used in app.
 */
public class Utility {

    private static Utility mInstance = null;
    private boolean connected;
    private Context mContext;

    public static Utility getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new Utility(mContext);
        }
        return mInstance;
    }

    private Utility(Context mContext) {
        this.mContext = mContext;
    }

    /**
     *  method to check internet connectivity
     * @return true or false
     */
    public boolean isOnline() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {
           e.printStackTrace();
        }
        return connected;
    }

    /**
     * read model data from file saved in assets if offline
     * @param inFile file name from which data to be loaded
     * @return data type of the value to be returned as read from file
     */
    public String loadData(String inFile) {
        String tContents = "";
        try {
            InputStream stream = mContext.getAssets().open(inFile);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tContents;

    }

}
