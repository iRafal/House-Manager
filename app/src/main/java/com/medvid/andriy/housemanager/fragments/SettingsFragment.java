package com.medvid.andriy.housemanager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.activity.EditProfileActivity;
import com.medvid.andriy.housemanager.utils.DialogUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Андрій on 5/3/2015.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener, ScreenShotable {

    public static final String SETTINGS_SCREEN = "Settings Screen";

    private ActionBarActivity mActionBarActivity = null;
    private ActionBar mActionBar = null;
    private DialogUtils mDialogUtils = null;
    private Bitmap bitmap;

    @InjectView(R.id.btn_sign_out)
    TextView tv_sign_out;

    @InjectView(R.id.fl_animation_container)
    FrameLayout fl_animation_container;

    @InjectView(R.id.tv_user_name_settings_item)
        TextView tv_user_name_settings_item;

    @InjectView(R.id.tv_user_password_settings_item)
        TextView tv_user_password_settings_item;

    public static SettingsFragment instantiate() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.settings_screen_layout, container, false);
        ButterKnife.inject(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar = mActionBarActivity.getSupportActionBar();
        mActionBar.setTitle(R.string.settings);
        mActionBar.invalidateOptionsMenu();
        initViews();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActionBarActivity = (ActionBarActivity) activity;
        mDialogUtils = new DialogUtils(mActionBarActivity);
    }

    private void initViews() {
        tv_sign_out.setOnClickListener(this);

        tv_user_name_settings_item.setOnClickListener(this);
        tv_user_password_settings_item.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.tv_user_name_settings_item:
                startEditUserNameScreen();
                break;
            case R.id.tv_user_password_settings_item:
                startEditUserPasswordScreen();
                break;
            case R.id.btn_sign_out:
                mDialogUtils.showSignOutDialog();
                break;
        }
    }

    private void startEditUserPasswordScreen() {
        Intent intent = new Intent(mActionBarActivity, EditProfileActivity.class);
        intent.putExtra(EditProfileActivity.EXTRA_PROFILE_EDITING_TYPE,
                EditProfileActivity.EDIT_USER_PASSWORD);
        startActivity(intent);
    }

    private void startEditUserNameScreen() {
        Intent intent = new Intent(mActionBarActivity, EditProfileActivity.class);
        intent.putExtra(EditProfileActivity.EXTRA_PROFILE_EDITING_TYPE,
                EditProfileActivity.EDIT_USER_NAME);
        startActivity(intent);
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(fl_animation_container.getWidth(),
                        fl_animation_container.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                fl_animation_container.draw(canvas);
                SettingsFragment.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
