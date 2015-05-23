package com.medvid.andriy.housemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;
import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.dataset.User;
import com.medvid.andriy.housemanager.utils.CookiesManager;
/**
 * Created by Андрій on 5/2/2015.
 */
public class SplashActivity extends BaseActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 2_000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chooseNextScreen();
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void chooseNextScreen() {
        Intent intent = null;

        User user = null;
        String userDataJson = CookiesManager.getUserData();
        Gson gson = new Gson();
        user = gson.fromJson(userDataJson, User.class);

        if (user == null) {
            intent = new Intent(SplashActivity.this, TutorialActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
        }
        startActivity(intent);
    }
}
