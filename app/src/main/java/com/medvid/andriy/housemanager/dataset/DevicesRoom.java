package com.medvid.andriy.housemanager.dataset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрій on 5/11/2015.
 */
public class DevicesRoom {

    private int mRoomId = 0;
    private String mRoomName = null;
    private List<Device> mDevicesList = null;

    public DevicesRoom(int roomId, String roomName, List<Device> devicesList) {
        this.mRoomId = roomId;
        this.mRoomName = roomName;
        this.mDevicesList = new ArrayList<>(devicesList);
    }

    public DevicesRoom() {
    }

    @Override
    public String toString() {
        return "DevicesRoom{" +
                "mRoomId=" + mRoomId +
                ", mRoomName='" + mRoomName + '\'' +
                ", mDevicesList=" + mDevicesList.toString() +
                '}';
    }

    public int getRoomId() {
        return mRoomId;
    }

    public void setRoomId(int roomId) {
        this.mRoomId = roomId;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public void setRoomName(String roomName) {
        this.mRoomName = roomName;
    }

    public List<Device> getDevicesList() {
        return mDevicesList;
    }

    public void setDevicesList(List<Device> devicesList) {
        this.mDevicesList = devicesList;
    }
}
