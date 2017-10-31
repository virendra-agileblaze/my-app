package com.agile.ireality.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

import com.agile.ireality.log.AppLog;

/**
 * Created by sarath on 8/2/16.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkChangeReceiver";

    public NetworkChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();

        Intent broadcastIntent =  new Intent();
        broadcastIntent.setAction(IRealityNetworkManager.NETWORK);
        broadcastIntent.putExtra(IRealityNetworkManager.IS_NETWORK_CONNECTED, isConnected);
        LocalBroadcastManager.getInstance(context).sendBroadcast(broadcastIntent);
    }
}
