package com.medvid.andriy.housemanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.medvid.andriy.housemanager.dataset.User;

public class CookiesManager {

    private static SharedPreferences sharedPreferences = null;

    private static final String COOKIES_MANAGER_TAG = "CookiesManager.TAG";

    private static final String PREFERENCES_NAME = "session_preferences";

    public static void init(Context context)  {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void writeUserData(String userDataJson) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.putString(User.USER_DATA_JSON, userDataJson);
        boolean commitSuccess = sharedPreferencesEditor.commit();

        if (commitSuccess) {
            Log.d(COOKIES_MANAGER_TAG, "User data adding to cookies(loginUser) - success");
        } else {
            Log.d(COOKIES_MANAGER_TAG, "User data adding to cookies(loginUser) - failure");
        }
    }

    public static boolean removeUserData() {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.remove(User.USER_DATA_JSON);
        return sharedPreferencesEditor.commit();
    }

    public static String getUserData() {
        String userDataJson = null;

        if (sharedPreferences.contains(User.USER_DATA_JSON)) {
            userDataJson = sharedPreferences.getString(User.USER_DATA_JSON, "");
        }

        return userDataJson;
    }
}
