package com.medvid.andriy.housemanager.application;

import android.app.Application;
import android.util.Log;

import com.medvid.andriy.housemanager.client.ServerConnection;
import com.medvid.andriy.housemanager.utils.CookiesManager;
import com.medvid.andriy.housemanager.utils.DialogUtils;

public class HouseManagerApplication extends Application {

    public static ServerConnection mServerConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        mServerConnection = new ServerConnection();
        CookiesManager.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mServerConnection.closeConnection();
    }
}
