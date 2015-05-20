package com.medvid.andriy.housemanager.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.rey.material.widget.Switch;

import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.dataset.Device;
import com.medvid.andriy.housemanager.dataset.DevicesRoom;
import com.medvid.andriy.housemanager.utils.DialogUtils;
import com.medvid.andriy.housemanager.utils.SlidrHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/3/2015.
 */
public class DeviceActivity extends BaseActivity implements View.OnClickListener, Switch.OnCheckedChangeListener {

    public static final String EXTRA_DEVICE_NAME = "com.medvid.andriy.housemanager" +
            ".activity.EXTRA_DEVICE_NAME";
    public static final String EXTRA_DEVICE_MODEL = "com.medvid.andriy.housemanager" +
            ".activity.EXTRA_DEVICE_MODEL";
    public static final String EXTRA_DEVICE_TYPE = "com.medvid.andriy.housemanager" +
            ".activity.DEVICE_TYPE";
    public static final String EXTRA_IS_DEVICE_SWITCHED_ON = "com.medvid.andriy.housemanager" +
            ".activity" +
            ".IS_DEVICE_SWITCHED_ON";

    public static final String EXTRA_IS_DEVICE_BUSY = "com.medvid.andriy.housemanager" +
            ".activity" +
            ".IS_DEVICE_BUSY";

    public static final String EXTRA_ROOM_ID = "com.medvid.andriy.housemanager" +
            ".activity" +
            ".ROOM_ID";

    public static final String EXTRA_ROOM_NAME = "com.medvid.andriy.housemanager" +
            ".activity" +
            ".ROOM_NAME";

    private Device mDevice = null;
    private DevicesRoom mDevicesRoom = null;
    private ProgressDialog mProgressDialog = null;
    private boolean mIsScreenInit = true;
    private DialogUtils mDialogUtils = null;

    @InjectView(R.id.tv_device_name)
    TextView tv_device_name;

    @InjectView(R.id.tv_device_model)
    TextView tv_device_model;

    @InjectView(R.id.tv_device_type)
    TextView tv_device_type;

    @InjectView(R.id.switch_status)
    Switch switch_status;

    @InjectView(R.id.tv_device_status)
    TextView tv_device_status;

    @InjectView(R.id.tv_device_room)
    TextView tv_device_room;

    @InjectView(R.id.tv_device_is_busy)
    TextView tv_device_is_busy;

    @InjectView(R.id.btn_devices_measurement_statistic)
    Button btn_devices_measurement_statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_screen_layout);

        ButterKnife.inject(this);

        mDialogUtils = new DialogUtils(this);

        initSlidr();

        getDataFromBundle();

        initView();
        mIsScreenInit = false;
    }

    private void getDataFromBundle() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            mDevice = new Device();

            mDevice.setDeviceName(extras.getString(EXTRA_DEVICE_NAME));
            mDevice.setDeviceModel(extras.getString(EXTRA_DEVICE_MODEL));
            mDevice.setDeviceType(extras.getString(EXTRA_DEVICE_TYPE));
            mDevice.setIsDeviceSwitchedOn(extras.getBoolean(EXTRA_IS_DEVICE_SWITCHED_ON));
            mDevice.setIsDeviceBusy(extras.getBoolean(EXTRA_IS_DEVICE_BUSY));

            mDevicesRoom = new DevicesRoom();
            mDevicesRoom.setRoomId(extras.getInt(EXTRA_ROOM_ID));
            mDevicesRoom.setRoomName(extras.getString(EXTRA_ROOM_NAME));
        } else {
            mDevice = new Device("", "", "", false, false);
        }
    }

    private void initView() {
        btn_devices_measurement_statistic.setOnClickListener(this);

        if (mDevice.getDeviceType() == Device.SIMPLE_DEVICE) {
            btn_devices_measurement_statistic.setVisibility(View.GONE);
        }

        initSwitcherView();

        if (!mDevice.getDeviceType().equals(Device.MEASURE_DEVICE)) {
            btn_devices_measurement_statistic.setVisibility(View.GONE);
        }

        tv_device_name.setText(mDevice.getDeviceName());
        tv_device_model.setText(mDevice.getDeviceModel());
        tv_device_type.setText(mDevice.getDeviceType());
        tv_device_room.setText(mDevicesRoom.getRoomName());
        tv_device_is_busy.setText(mDevice.isDeviceBusy()
                ? getString(R.string.yes) : getString(R.string.no));

    }

    private void initSwitcherView() {
        switch_status.setOnCheckedChangeListener(this);
        switch_status.setChecked(mDevice.isDeviceBusy());
        switch_status.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!isUserRoot()) {
                        showDisableChangeDeviceStatusDialog();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void initSlidr() {
        SlidrHelper.initSlidr(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_devices_measurement_statistic:
                startMeasurementStatisticActivity();
                break;
        }
    }

    private void startMeasurementStatisticActivity() {
        Intent measurementStatisticActivityIntent = new Intent(this, MeasurementStatisticActivity.class);
        startActivity(measurementStatisticActivityIntent);
    }

    @Override
    public void onCheckedChanged(Switch aSwitch, boolean isChecked) {

        if (!mIsScreenInit) {
            showProgressDialog();
        }

        if (isChecked) {
            tv_device_status.setText(getString(R.string.on));
        } else {
            tv_device_status.setText(getString(R.string.off));
        }
    }

    private boolean isUserRoot() {
        boolean isCurrentUserRoot = true;

        return isCurrentUserRoot;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    private void showDisableChangeDeviceStatusDialog() {
        String message = getString(R.string.device_is_busy_by_another_user);
        String title = getString(R.string.toggle_device_status);
        mDialogUtils.showOkDialog(title, message);
    }

    private void showProgressDialog() {
        String dialogMessage = getString(R.string.switching_off_device);

        mProgressDialog = mDialogUtils.getProgressDialog(null, dialogMessage);
        mProgressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
            }
        }, 4 * 1_000);
    }
}
