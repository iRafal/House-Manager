package com.medvid.andriy.housemanager.dataset;

/**
 * Created by Андрій on 5/3/2015.
 */
public class Device {

    public static final String MEASURE_DEVICE = "device.measure_device";
    public static final String SIMPLE_DEVICE = "device.simple_device";

    private String mDeviceName = null;
    private String mDeviceModel = null;
    private String mDeviceType = null;
    private boolean mIsDeviceSwitchedOn = false;
    private boolean mIsDeviceBusy = false;

    public Device(String deviceName, String deviceType, String deviceModel,
                  boolean isDeviceSwitchedOn, boolean isDeviceBusy) {
        this.mDeviceName = deviceName;
        this.mDeviceType = deviceType;
        this.mDeviceModel = deviceModel;
        this.mIsDeviceSwitchedOn = isDeviceSwitchedOn;
        this.mIsDeviceBusy = isDeviceBusy;
    }

    public Device(Device device) {
        this.mDeviceName = device.getDeviceName();
        this.mDeviceType = device.getDeviceType();
        this.mDeviceModel = device.getDeviceModel();
        this.mIsDeviceSwitchedOn = device.isDeviceSwitchedOn();
        this.mIsDeviceBusy = device.isDeviceBusy();
    }

    public Device() {
    }

    @Override
    public String toString() {
        return "Device{" +
                "mDeviceName='" + mDeviceName + '\'' +
                ", mDeviceModel='" + mDeviceModel + '\'' +
                ", mDeviceType='" + mDeviceType + '\'' +
                ", mIsDeviceSwitchedOn=" + mIsDeviceSwitchedOn +
                ", mIsDeviceBusy=" + mIsDeviceBusy +
                '}';
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String mDeviceName) {
        this.mDeviceName = mDeviceName;
    }
    public String getDeviceType() {
        return mDeviceType;
    }

    public void setDeviceType(String mDeviceType) {
        this.mDeviceType = mDeviceType;
    }

    public boolean isDeviceSwitchedOn() {
        return mIsDeviceSwitchedOn;
    }

    public void setIsDeviceSwitchedOn(boolean isDeviceSwitchedOn) {
        this.mIsDeviceSwitchedOn = isDeviceSwitchedOn;
    }

    public String getDeviceModel() {
        return mDeviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.mDeviceModel = deviceModel;
    }

    public boolean isDeviceBusy() {
        return mIsDeviceBusy;
    }

    public void setIsDeviceBusy(boolean isDeviceBusy) {
        this.mIsDeviceBusy = isDeviceBusy;
    }
}
