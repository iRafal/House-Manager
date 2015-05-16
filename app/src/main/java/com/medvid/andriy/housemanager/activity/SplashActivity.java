package com.medvid.andriy.housemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.medvid.andriy.housemanager.R;

/**
 * Created by Андрій on 5/2/2015.
 */
public class SplashActivity extends BaseActivity {

    public static final long SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, TutorialActivity.class/*EntryActivity.class*/);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
