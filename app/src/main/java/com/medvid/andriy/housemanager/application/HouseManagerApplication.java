package com.medvid.andriy.housemanager.application;

import android.app.Application;

import com.medvid.andriy.housemanager.utils.CookiesManager;
import com.medvid.andriy.housemanager.utils.DialogUtils;

public class HouseManagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CookiesManager.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
