package com.medvid.andriy.housemanager.views.animated_expandble_list_view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.dataset.Device;
import com.medvid.andriy.housemanager.dataset.DevicesRoom;
import com.medvid.andriy.housemanager.views.animated_expandble_list_view.widgets.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/10/2015.
 */
public class ExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater mLayoutInflater;

    private List<DevicesRoom> mDevicesRoomsList = null;
    private Context mContext = null;

    public ExpandableListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public ExpandableListAdapter(Context context, List<DevicesRoom> devicesRoomList)    {
        mLayoutInflater = LayoutInflater.from(context);
        this.mDevicesRoomsList = new ArrayList<>(devicesRoomList);
        mContext = context;
    }

    public void setData(List<DevicesRoom> devicesRoomList) {
        this.mDevicesRoomsList = devicesRoomList;
    }

    public List<DevicesRoom> getData()  {
        return this.mDevicesRoomsList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDevicesRoomsList.get(groupPosition).getDevicesList().get(childPosition);//items.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        Device deviceItem = (Device) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.expandable_list_item, parent, false);

            holder = new ChildHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        iniChildItem(holder, deviceItem);

        return convertView;
    }

    private void iniChildItem(ChildHolder childHolder, Device deviceItem)    {
        childHolder.tv_device_name.setText(deviceItem.getDeviceName());

        String deviceStatus = null;
        int statusIcResId = 0;

        if(deviceItem.isDeviceSwitchedOn()) {
            deviceStatus = mContext.getString(R.string.switched_on);
            statusIcResId = R.drawable.ic_microphone;
        }   else    {
            deviceStatus = mContext.getString(R.string.switched_off);
            statusIcResId = R.drawable.abc_btn_radio_to_on_mtrl_015;
        }

        childHolder.tv_device_status.setText(deviceStatus);
        childHolder.tv_device_status.setCompoundDrawablesWithIntrinsicBounds(statusIcResId, 0, 0, 0);
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return mDevicesRoomsList.get(groupPosition).getDevicesList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDevicesRoomsList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mDevicesRoomsList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        DevicesRoom devicesRoom = (DevicesRoom) getGroup(groupPosition);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.expandable_group_item, parent, false);

            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        initGroupItem(holder, devicesRoom);

        return convertView;
    }

    private void initGroupItem(GroupHolder groupHolder, DevicesRoom devicesRoom)   {
        groupHolder.tv_group_text_title.setText(devicesRoom.getRoomName());
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    class ChildHolder {
        @InjectView(R.id.tv_device_name)
            TextView tv_device_name;

        @InjectView(R.id.tv_device_status)
            TextView tv_device_status;

        public ChildHolder(View view)    {
            ButterKnife.inject(this, view);
        }
    }

    class GroupHolder {
        @InjectView(R.id.tv_group_text_title)
            TextView tv_group_text_title;

        public GroupHolder(View view)    {
            ButterKnife.inject(this, view);
        }
    }
}
