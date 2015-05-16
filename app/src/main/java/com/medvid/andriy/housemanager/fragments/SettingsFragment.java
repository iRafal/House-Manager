package com.medvid.andriy.housemanager.fragments;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.utils.DialogBuilder;
import com.zzt.inbox.interfaces.OnDragStateChangeListener;
import com.zzt.inbox.widget.InboxBackgroundScrollView;
import com.zzt.inbox.widget.InboxLayoutBase;
import com.zzt.inbox.widget.InboxLayoutScrollView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Андрій on 5/3/2015.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener, ScreenShotable {

    public static final String SETTINGS_SCREEN = "Settings Screen";
    private static final int SWIPE_CLOSING_DISTANCE = 50;

    private ActionBarActivity mActionBarActivity = null;
    private ActionBar mActionBar = null;

    private Bitmap bitmap;

    //Titles
    @InjectView(R.id.tv_user_name_settings_title)
    TextView tv_user_name_settings_title;
    @InjectView(R.id.tv_password_settings_title)
    TextView tv_password_settings_title;

    //LL sections
    @InjectView(R.id.ll_user_name_settings)
    LinearLayout ll_user_name_settings;
    @InjectView(R.id.ll_password_settings)
    LinearLayout ll_password_settings;

    @InjectView(R.id.tv_change_user_name)
    TextView tv_change_user_name;
    @InjectView(R.id.et_new_user_name)
    EditText et_new_user_name;

    @InjectView(R.id.et_enter_new_password)
    EditText et_enter_new_password;

    @InjectView(R.id.et_confirm_new_password)
    EditText et_confirm_new_password;
    @InjectView(R.id.tv_change_user_password)
    TextView tv_change_user_password;

    @InjectView(R.id.btn_sign_out)
    TextView tv_sign_out;

    @InjectView(R.id.fl_animation_container)
    FrameLayout fl_animation_container;

    @InjectView(R.id.inboxBackgroundScrollView)
        InboxBackgroundScrollView inboxBackgroundScrollView;

    @InjectView(R.id.tv_user_name_settings_item)
        TextView tv_user_name_settings_item;

    @InjectView(R.id.tv_user_password_settings_item)
        TextView tv_user_password_settings_item;

    @InjectView(R.id.inboxlayout_user_name)
    InboxLayoutScrollView inboxlayout_user_name;

    @InjectView(R.id.inboxlayout_user_password)
    InboxLayoutScrollView inboxlayout_user_password;

    private View containerView = null;

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
        this.containerView = view.findViewById(R.id.container);
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
        DialogBuilder.setContext(mActionBarActivity);
    }

    private void initViews() {

        //Titles
        tv_user_name_settings_title.setOnClickListener(this);
        tv_password_settings_title.setOnClickListener(this);

        tv_change_user_name.setOnClickListener(this);
        tv_change_user_password.setOnClickListener(this);
        tv_sign_out.setOnClickListener(this);

        tv_user_name_settings_item.setOnClickListener(this);
        tv_user_password_settings_item.setOnClickListener(this);

        inboxlayout_user_name.setBackgroundScrollView(inboxBackgroundScrollView);
        inboxlayout_user_name.setCloseDistance(SWIPE_CLOSING_DISTANCE);
        inboxlayout_user_name.setOnDragStateChangeListener(new OnDragStateChangeListener() {
            @Override
            public void dragStateChange(InboxLayoutBase.DragState state) {
                switch (state) {
                    case CANCLOSE:
                        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff5e5e5e));
                        //getSupportActionBar().setTitle("Back");
                        break;
                    case CANNOTCLOSE:
                        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xdd000000));
                        //getSupportActionBar().setTitle("InboxLayout");
                        break;
                }
            }
        });

        inboxlayout_user_password.setBackgroundScrollView(inboxBackgroundScrollView);
        inboxlayout_user_password.setCloseDistance(SWIPE_CLOSING_DISTANCE);
        inboxlayout_user_password.setOnDragStateChangeListener(new OnDragStateChangeListener() {
            @Override
            public void dragStateChange(InboxLayoutBase.DragState state) {
                switch (state) {
                    case CANCLOSE:
                        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff5e5e5e));
                        //getSupportActionBar().setTitle("Back");
                        break;
                    case CANNOTCLOSE:
                        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xdd000000));
                        //getSupportActionBar().setTitle("InboxLayout");
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            //Titles
            case R.id.tv_user_name_settings_title:
                changeVisibility(ll_user_name_settings);
                break;
            case R.id.tv_password_settings_title:
                changeVisibility(ll_password_settings);
                break;
            case R.id.tv_change_user_name:
                break;
            case R.id.tv_change_user_password:
                break;
            case R.id.btn_sign_out:
                DialogBuilder.showSignOutDialog();
                break;
            case R.id.tv_user_name_settings_item:
                inboxlayout_user_name.openWithAnim(tv_user_name_settings_item);
                break;
            case R.id.tv_user_password_settings_item:
                inboxlayout_user_password.openWithAnim(tv_user_password_settings_item);
                break;
        }
    }

    private void changeVisibility(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
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
