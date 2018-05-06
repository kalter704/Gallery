package com.yad.vasilii.gallery.data.network;

import android.content.*;
import android.net.*;

import javax.inject.*;

public class NetworkChecker {

    private final Context mContext;

    @Inject
    public NetworkChecker(Context context) {
        mContext = context;
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
