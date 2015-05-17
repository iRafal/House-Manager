package com.medvid.andriy.housemanager.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.activity.DeviceActivity;
import com.medvid.andriy.housemanager.dataset.Device;
import com.medvid.andriy.housemanager.dataset.DevicesRoom;
import com.medvid.andriy.housemanager.views.DrawableTouchEditText;
import com.medvid.andriy.housemanager.views.animated_expandble_list_view.adapters.ExpandableListAdapter;
import com.medvid.andriy.housemanager.views.animated_expandble_list_view.widgets.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Андрій on 5/3/2015.
 */
public class DevicesListFragment extends Fragment implements ScreenShotable {

    public static final String DEVICES_SCREEN = "Devices Screen";

    private ActionBar mActionBar = null;
    private ActionBarActivity mActionBarActivity = null;

    @InjectView(R.id.fl_animation_container)
        FrameLayout fl_animation_container;

    @InjectView(R.id.et_search_header)
        DrawableTouchEditText et_search_header;

    @InjectView(R.id.expandable_list_view)
        AnimatedExpandableListView expandable_list_view;

    @InjectView(R.id.tv_list_is_empty)
        TextView tv_list_is_empty;

    @InjectView(R.id.tv_no_matches_found)
        TextView tv_no_matches_found;

    private ExpandableListAdapter mExpandableListAdapter;
    private Bitmap bitmap;
    private ProgressDialog mProgressDialog = null;

    public static DevicesListFragment instantiate()    {
        return new DevicesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.devices_screen_layout, container, false);

        ButterKnife.inject(this, fragmentView);

        return fragmentView;
    }

    private void showProgressDialog()    {
        String dialogTitle = mActionBarActivity.getString(R.string.sync_dialog_title);
        String dialogMessage = mActionBarActivity.getString(R.string.sync_dialog_message);

        mProgressDialog = ProgressDialog.show(mActionBarActivity,
                dialogTitle,dialogMessage, true, true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        }, 4 * 1_000);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        int syncMenuItemPosition = 0;
        MenuItem syncMenuItem = menu.getItem(syncMenuItemPosition);
        syncMenuItem.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_sync:
                showProgressDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActionBar = mActionBarActivity.getSupportActionBar();
        mActionBar.setTitle(R.string.devices);

        initViews();
    }

    private void initViews()    {
        initSearchHeader();
        initExpandableList();
    }

    private void initExpandableList() {
        List<DevicesRoom> devicesRoomList = generateDevicesList();

        mExpandableListAdapter = new ExpandableListAdapter(mActionBarActivity, devicesRoomList, expandable_list_view);
        //expandable_list_view.setGroupIndicator(getDrawableFromResource(R.drawable.ic_find_next_holo_light));
        expandable_list_view.setAdapter(mExpandableListAdapter);
        expandable_list_view.setEmptyView(tv_list_is_empty);

        expandable_list_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                DevicesRoom devicesRoom = mExpandableListAdapter.getData().get(groupPosition);
                Device device = devicesRoom.getDevicesList().get(childPosition);

                startDeviceActivity(device, devicesRoom);

                return false;
            }
        });
        expandable_list_view.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {

                } else {

                }
                return true;
            }
        });

/*
        expandable_list_view.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (expandable_list_view.isGroupExpanded(groupPosition)) {
                    expandable_list_view.setGroupIndicator(
                            getDrawableFromResource(R.drawable.ic_find_next_holo_light));
                }
            }
        });

        expandable_list_view.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                if (!expandable_list_view.isGroupExpanded(groupPosition)) {
                    expandable_list_view.setGroupIndicator(
                            getDrawableFromResource(R.drawable.ic_find_previous_holo_light));
                }
            }
        });*/

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        expandable_list_view.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (expandable_list_view.isGroupExpanded(groupPosition)) {
                    expandable_list_view.collapseGroupWithAnimation(groupPosition);
                } else {
                    expandable_list_view.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });
    }


    private void initSearchHeader()    {
        et_search_header.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                boolean isSuccessSearch = mExpandableListAdapter.dataFilter(charSequence.toString());
                if (!isSuccessSearch) {
                    if (expandable_list_view.getVisibility() == View.VISIBLE) {
                        expandable_list_view.setVisibility(View.GONE);
                    }
                    if (tv_list_is_empty.getVisibility() == View.VISIBLE) {
                        tv_list_is_empty.setVisibility(View.GONE);
                    }
                } else {
                    if (expandable_list_view.getVisibility() == View.GONE) {
                        expandable_list_view.setVisibility(View.VISIBLE);
                    }
                    if (tv_list_is_empty.getVisibility() == View.GONE) {
                        tv_list_is_empty.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private Drawable getDrawableFromResource(int resId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mActionBarActivity.getDrawable(resId);
        }
        return getResources().getDrawable(resId);
    }

    private List<DevicesRoom> generateDevicesList()   {
        List<DevicesRoom> devicesRoomList = new ArrayList<DevicesRoom>();

        DevicesRoom devicesRoom = null;
        List<Device> devicesList = new ArrayList<>();
        Device device = null;
        String deviceType = null;
        boolean isDeviceSwitchedOn = false;
        boolean isDeviceBusy = false;

        for (int i = 0; i < 10; ++i) {

            for (int j = 0; j < 6; ++j) {

                if (j % 2 == 0) {
                    deviceType = Device.MEASURE_DEVICE;
                } else {
                    deviceType = Device.SIMPLE_DEVICE;
                }

                if (j % 2 != 0) {
                    isDeviceBusy = isDeviceSwitchedOn = false;
                } else {
                    isDeviceBusy = isDeviceSwitchedOn = true;
                }

                device = new Device("Device " + j, deviceType, "Model " + j, isDeviceSwitchedOn, isDeviceBusy);
                devicesList.add(device);
                device = null;
            }

            devicesRoom = new DevicesRoom(i, "Room " + i, devicesList);
            devicesRoomList.add(devicesRoom);

            devicesRoom = null;
            devicesList.clear();
        }

        return devicesRoomList;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActionBarActivity = (ActionBarActivity)activity;
    }

    private void startDeviceActivity(Device device, DevicesRoom devicesRoom)  {
        Intent deviceActivityIntent = new Intent(mActionBarActivity, DeviceActivity.class);

        deviceActivityIntent.putExtra(DeviceActivity.EXTRA_DEVICE_NAME, device.getDeviceName());
        deviceActivityIntent.putExtra(DeviceActivity.EXTRA_DEVICE_MODEL, device.getDeviceModel());
        deviceActivityIntent.putExtra(DeviceActivity.EXTRA_DEVICE_TYPE, device.getDeviceType());
        deviceActivityIntent.putExtra(DeviceActivity.EXTRA_IS_DEVICE_SWITCHED_ON, device.isDeviceSwitchedOn());
        deviceActivityIntent.putExtra(DeviceActivity.EXTRA_IS_DEVICE_BUSY, device.isDeviceBusy());

        deviceActivityIntent.putExtra(DeviceActivity.EXTRA_ROOM_ID, devicesRoom.getRoomId());
        deviceActivityIntent.putExtra(DeviceActivity.EXTRA_ROOM_NAME, devicesRoom.getRoomName());

        startActivity(deviceActivityIntent);
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
                DevicesListFragment.this.bitmap = bitmap;
            }
        };

        thread.start();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}
