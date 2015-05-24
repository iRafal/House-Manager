package com.medvid.andriy.housemanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.medvid.andriy.housemanager.dataset.User;

public class CookiesManager {

    private static SharedPreferences sharedPreferences = null;

    private static final String COOKIES_MANAGER_TAG = "CookiesManager.TAG";

    private static final String PREFERENCES_NAME = "session_preferences";

    public static void init(Context context)  {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static boolean writeUserData(String userDataJson) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.putString(User.USER_DATA_JSON, userDataJson);
        boolean commitSuccess = sharedPreferencesEditor.commit();

        if (commitSuccess) {
            Log.d(COOKIES_MANAGER_TAG, "User data adding to cookies(loginUser) - success");
        } else {
            Log.d(COOKIES_MANAGER_TAG, "User data adding to cookies(loginUser) - failure");
        }
        return commitSuccess;
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

    public static boolean updateUserName(String userName) {
        if(userName == null || userName.isEmpty())  {
            return false;
        }
        Gson gson = new Gson();
        User user = gson.fromJson(getUserData(), User.class);
        user.setName(userName);
        return writeUserData(gson.toJson(user));
    }

    public static boolean updateUserPassword(String userPassword) {
        if(userPassword == null || userPassword.isEmpty())  {
            return false;
        }
        Gson gson = new Gson();
        User user = gson.fromJson(getUserData(), User.class);
        user.setPassword(userPassword);
        return writeUserData(gson.toJson(user));
    }
}
