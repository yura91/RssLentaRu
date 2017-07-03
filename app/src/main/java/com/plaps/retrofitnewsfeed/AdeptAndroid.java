package com.plaps.retrofitnewsfeed;

/**
 * Created by User on 12.05.2017.
 */

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class AdeptAndroid extends Application {

    private static AdeptAndroid instance;

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;

    }

    public static AdeptAndroid getInstance ()
    {
        return instance;
    }

    public static boolean hasNetwork ()
    {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
