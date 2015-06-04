package com.medvid.andriy.housemanager.activity;

import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by Андрій on 5/3/2015.
 */
public class BaseActivity extends ActionBarActivity {

    protected void showView(View view)    {
        if(view.getVisibility() != View.VISIBLE)    {
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void hideView(View view)    {
        if(view.getVisibility() != View.GONE)    {
            view.setVisibility(View.GONE);
        }
    }
}
