package com.medvid.andriy.housemanager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.activity.VoiceControlInfoActivity;
import com.melnykov.fab.FloatingActionButton;
import com.skyfishjy.library.RippleBackground;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Андрій on 5/4/2015.
 */
public class VoiceControlFragment extends Fragment implements ScreenShotable, View.OnClickListener {

    private ActionBarActivity mActionBarActivity = null;
    private ActionBar mActionBar = null;
    private View mFragmentView = null;

    @InjectView(R.id.floating_button)
        FloatingActionButton floating_button;

    public static final String VOICE_CONTROL_SCREEN = "Voice Control";

    public static VoiceControlFragment instantiate()    {
        return new VoiceControlFragment();
    }

    @InjectView(R.id.rb_ripple_microphone_background)
        RippleBackground mRippleBackground;
    @InjectView(R.id.iv_center_image)
        ImageView iv_center_image;
    @InjectView(R.id.tv_command_title)
        TextView tv_command_title;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.voice_control_screen_layout, container, false);
        ButterKnife.inject(this, mFragmentView);
        return mFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar = mActionBarActivity.getSupportActionBar();
        mActionBar.setTitle(R.string.voice_control);
        mActionBar.invalidateOptionsMenu();

        initViews();
    }

    private void initViews()    {
        iv_center_image.setOnClickListener(this);
        floating_button.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.iv_center_image:
               onRippleAction();
                break;
            case R.id.floating_button:
                startVoiceControlInfoScreen();
                break;
        }
    }

    private void startVoiceControlInfoScreen() {
        Intent voiceControlInfoActivityIntent = new Intent(mActionBarActivity, VoiceControlInfoActivity.class);
        mActionBarActivity.startActivity(voiceControlInfoActivityIntent);
    }

    private void toggleFloatingActionButton()   {
        if(floating_button.isVisible())   {
            floating_button.hide(true);
        }   else    {
            floating_button.show(true);
        }
    }

    private void onRippleAction()   {
        if(!mRippleBackground.isRippleAnimationRunning()) {
            mRippleBackground.startRippleAnimation();
            tv_command_title.setVisibility(View.VISIBLE);
        }   else    {
            mRippleBackground.stopRippleAnimation();
            tv_command_title.setVisibility(View.INVISIBLE);
        }

        toggleFloatingActionButton();
    }
}