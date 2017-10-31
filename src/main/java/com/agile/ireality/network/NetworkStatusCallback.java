package com.agile.ireality.network;

/**
 * Created by sarath on 8/2/16.
 */
public interface NetworkStatusCallback {
    void networkAvailable();
    void networkFailure();
}
