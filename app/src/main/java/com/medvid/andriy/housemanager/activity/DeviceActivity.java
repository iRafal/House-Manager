package com.medvid.andriy.housemanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.dataset.Device;
import com.medvid.andriy.housemanager.dataset.DevicesRoom;
import com.medvid.andriy.housemanager.utils.SlidrHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/3/2015.
 */
public class DeviceActivity extends BaseActivity implements View.OnClickListener{

    public static final String EXTRA_DEVICE_NAME = "com.medvid.andriy.housemanager" +
            ".activity.EXTRA_DEVICE_NAME";
    public static final String EXTRA_DEVICE_MODEL = "com.medvid.andriy.housemanager" +
            ".activity.EXTRA_DEVICE_MODEL";
    public static final String EXTRA_DEVICE_TYPE = "com.medvid.andriy.housemanager" +
            ".activity.DEVICE_TYPE";
    public static final String EXTRA_IS_DEVICE_SWITCHED_ON = "com.medvid.andriy.housemanager" +
            ".activity" +
            ".IS_DEVICE_SWITCHED_ON";

    public static final String EXTRA_ROOM_ID = "com.medvid.andriy.housemanager" +
            ".activity" +
            ".ROOM_ID";

    public static final String EXTRA_ROOM_NAME = "com.medvid.andriy.housemanager" +
            ".activity" +
            ".ROOM_NAME";

    private Device mDevice = null;
    private DevicesRoom mDevicesRoom = null;

    @InjectView(R.id.tv_device_name)
    TextView tv_device_name;

    @InjectView(R.id.tv_device_model)
    TextView tv_device_model;

    @InjectView(R.id.tv_device_type)
    TextView tv_device_type;

    @InjectView(R.id.tv_device_status)
    TextView tv_device_status;

    @InjectView(R.id.tv_device_room)
    TextView tv_device_room;

    @InjectView(R.id.btn_devices_measurement_statistic)
    Button btn_devices_measurement_statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_screen_layout);

        ButterKnife.inject(this);

        initSlidr();

        getDataFromBundle();

        initView();
    }

    private void getDataFromBundle() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            mDevice = new Device();

            mDevice.setDeviceName(extras.getString(EXTRA_DEVICE_NAME));
            mDevice.setDeviceModel(extras.getString(EXTRA_DEVICE_MODEL));
            mDevice.setDeviceType(extras.getString(EXTRA_DEVICE_TYPE));
            mDevice.setIsDeviceSwitchedOn(extras.getBoolean(EXTRA_IS_DEVICE_SWITCHED_ON));

            mDevicesRoom = new DevicesRoom();
            mDevicesRoom.setRoomId(extras.getInt(EXTRA_ROOM_ID));
            mDevicesRoom.setRoomName(extras.getString(EXTRA_ROOM_NAME));
        }   else {
            mDevice = new Device("", "", "", false);
        }
    }

    private void initView() {
        btn_devices_measurement_statistic.setOnClickListener(this);

        if(mDevice.getDeviceType()==Device.SIMPLE_DEVICE)   {
            btn_devices_measurement_statistic.setVisibility(View.GONE);
        }
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

    private void startMeasurementStatisticActivity()    {
        Intent measurementStatisticActivityIntent = new Intent(this, MeasurementStatisticActivity.class);
        startActivity(measurementStatisticActivityIntent);
    }
}
