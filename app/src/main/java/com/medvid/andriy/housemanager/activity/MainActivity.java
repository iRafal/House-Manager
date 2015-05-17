package com.medvid.andriy.housemanager.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.codetail.animation.ViewAnimationUtils;

import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.fragments.AboutFragment;
import com.medvid.andriy.housemanager.fragments.DevicesListFragment;
import com.medvid.andriy.housemanager.fragments.SettingsFragment;
import com.medvid.andriy.housemanager.fragments.VoiceControlFragment;
import com.medvid.andriy.housemanager.utils.DialogBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.codetail.animation.SupportAnimator;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MainActivity extends BaseActivity implements ViewAnimator.ViewAnimatorListener, ScreenShotable {

    private static final String CLOSE = "Close";
    private static final int ON_BACK_PRESS_TIME_DELAY = 2000;
    private String mCurrentFragmentTag = DevicesListFragment.DEVICES_SCREEN;
    private boolean mDoubleBackToExitPressedOnce = false;

    private ViewAnimator mViewAnimator;
    private List<SlideMenuItem> list = new ArrayList<>();

    @InjectView(R.id.left_drawer)
    LinearLayout ll_left_drawer = null;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen_layout);

        DialogBuilder.setContext(this);

        startFragmentByTag();

        ButterKnife.inject(this);
        initDrawer();
    }

    private Toolbar initActionBar() {
        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return toolbar;
    }

    private Fragment getFragmentByTag(String tag) {
        Fragment fragment = null;

        switch (tag) {
            case DevicesListFragment.DEVICES_SCREEN:
                fragment = DevicesListFragment.instantiate();
                break;
            case VoiceControlFragment.VOICE_CONTROL_SCREEN:
                fragment = VoiceControlFragment.instantiate();
                break;
            case SettingsFragment.SETTINGS_SCREEN:
                fragment = SettingsFragment.instantiate();
                break;
            case AboutFragment.ABOUT_SCREEN:
                fragment = AboutFragment.instantiate();
                break;
        }

        return fragment;
    }

    private void startFragmentByTag() {

        Fragment fragment = null;

        fragment = getFragmentByTag(mCurrentFragmentTag);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.activity_content_frame, fragment).commit();
    }

    private void initDrawer() {

        Toolbar toolbar = initActionBar();

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.setDrawerListener(intDrawerToggle(toolbar));

        ll_left_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });

        createMenuList();
        mViewAnimator = new ViewAnimator<>(this, list, this, mDrawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(CLOSE, R.drawable.ic_dialog_close_normal_holo);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(DevicesListFragment.DEVICES_SCREEN, R.drawable.abc_btn_radio_material);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(VoiceControlFragment.VOICE_CONTROL_SCREEN, R.drawable.ic_microphone);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(SettingsFragment.SETTINGS_SCREEN, R.drawable.ic_settings);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(AboutFragment.ABOUT_SCREEN, R.drawable.stat_sys_certificate_info);
        list.add(menuItem4);
    }

    private DrawerLayout.DrawerListener intDrawerToggle(Toolbar toolbar) {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ll_left_drawer.removeAllViews();
                ll_left_drawer.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && ll_left_drawer.getChildCount() == 0)
                    mViewAnimator.showMenuContent();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);

        return mDrawerToggle;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return false;
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        View contentView = ButterKnife.findById(this, R.id.activity_content_frame);

        int finalRadius = Math.max(contentView.getWidth(), contentView.getHeight());

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(contentView, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        View content_overlay = ButterKnife.findById(this, R.id.content_overlay);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            content_overlay.setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        } else {
            content_overlay.setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        }

        animator.start();

        Fragment content = (Fragment) screenShotable;
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_content_frame, content).commit();
        return screenShotable;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDoubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        mDoubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.on_back_tap_message), Toast.LENGTH_SHORT).show();

        runDelay(ON_BACK_PRESS_TIME_DELAY);
    }

    private void runDelay(int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDoubleBackToExitPressedOnce = false;
            }
        }, delay);
    }

    @Override
    public ScreenShotable onSwitch(Resourceble resourceble, ScreenShotable screenShotable, int i) {

        ScreenShotable fragment = null;

        switch (resourceble.getName()) {
            case CLOSE:
                return screenShotable;
            default:
                fragment = (ScreenShotable) getFragmentByTag(resourceble.getName());
        }

        return replaceFragment(fragment, i);
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        ll_left_drawer.addView(view);
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}