package com.agile.ireality.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by sarath on 8/2/16.
 */
public class NetworkChecker {
    private Context mContext;

    public NetworkChecker(Context mContext) {
        this.mContext = mContext;
    }

    public void notifyNetworkStatus(){
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting());

        Intent broadcastIntent =  new Intent();
        broadcastIntent.setAction(IRealityNetworkManager.NETWORK);
        broadcastIntent.putExtra(IRealityNetworkManager.IS_NETWORK_CONNECTED, isConnected);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(broadcastIntent);
    }

}

