package com.medvid.andriy.housemanager.activity;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

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

    protected void showToast(String toastMessage, int toastLength) {
        Toast.makeText(this, toastMessage, toastLength).show();
    }

    protected void showToast(int messageResId, int toastLength) {
        String message = getString(messageResId);
        showToast(message, toastLength);
    }
}
