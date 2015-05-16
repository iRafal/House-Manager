package com.medvid.andriy.housemanager.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medvid.andriy.housemanager.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Андрій on 5/3/2015.
 */
public class AboutFragment extends Fragment implements ScreenShotable {

    public static final String ABOUT_SCREEN = "About Screen";

    public static AboutFragment instantiate()    {
        return new AboutFragment();
    }

    private ActionBarActivity mActionBarActivity = null;
    private ActionBar mActionBar = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.about_screen_layout, container, false);
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar = mActionBarActivity.getSupportActionBar();
        mActionBar.setTitle(R.string.about);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActionBarActivity = (ActionBarActivity)activity;
    }



    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
