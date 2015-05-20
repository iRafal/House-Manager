package com.medvid.andriy.housemanager.views.animated_expandble_list_view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.dataset.Device;
import com.medvid.andriy.housemanager.dataset.DevicesRoom;
import com.medvid.andriy.housemanager.interfaces.ListDataFilter;
import com.medvid.andriy.housemanager.views.animated_expandble_list_view.widgets.AnimatedExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/10/2015.
 */
public class ExpandableListAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter
    implements ListDataFilter {
    private LayoutInflater mLayoutInflater;

    private List<DevicesRoom> mDevicesRoomsList = null;
    private List<DevicesRoom> mDevicesRoomsSearchList = null;
    private Context mContext = null;
    private boolean mIsSearchMode;
    private boolean mIsCollapsed = true; //Initially all groups are collapsed
    private boolean mIsExpanded = false;
    private AnimatedExpandableListView mExpandableListView = null;
    private boolean mSearchWasFailed = false;

    public ExpandableListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public ExpandableListAdapter(Context context, List<DevicesRoom> devicesRoomList,
                                 AnimatedExpandableListView expandableListView)    {
        mLayoutInflater = LayoutInflater.from(context);
        this.mDevicesRoomsList = new ArrayList<>(devicesRoomList);
        this.mDevicesRoomsSearchList = new ArrayList<>(mDevicesRoomsList);
        mContext = context;
        this.mExpandableListView = expandableListView;
    }

    public void setData(List<DevicesRoom> devicesRoomList) {
        this.mDevicesRoomsList = devicesRoomList;
        if(!mDevicesRoomsSearchList.isEmpty()) {
            mDevicesRoomsSearchList.clear();
        }

        this.mDevicesRoomsSearchList.addAll(mDevicesRoomsList);
    }

    public List<DevicesRoom> getData()  {
        return this.mDevicesRoomsList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDevicesRoomsList.get(groupPosition).getDevicesList().get(childPosition);
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
        int deviceNameIcResId = 0;
        int textColor;

        if(deviceItem.isDeviceSwitchedOn()) {
            deviceStatus = mContext.getString(R.string.switched_on);
            statusIcResId = R.drawable.lamp_orange;
            textColor = R.color.orange_bright;
            deviceNameIcResId = R.drawable.point_green;
        }   else    {
            deviceStatus = mContext.getString(R.string.switched_off);
            statusIcResId = R.drawable.lamp_gray;
            textColor = R.color.gray_unactive;
            deviceNameIcResId = R.drawable.ic_point_unselected;
        }

        childHolder.tv_device_status.setTextColor(mContext.getResources().getColor(textColor));
        childHolder.tv_device_status.setText(deviceStatus);
        childHolder.tv_device_status.
                setCompoundDrawablesWithIntrinsicBounds(0, 0, statusIcResId, 0);
        childHolder.tv_device_name.
                setCompoundDrawablesWithIntrinsicBounds(deviceNameIcResId, 0, 0, 0);
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

    @Override
    public boolean dataFilter(String searchString) {
        searchString = searchString.toLowerCase();
        DevicesRoom deviceRoom = null;
        boolean addSection = false;

        if(!mDevicesRoomsList.isEmpty()) {
            mDevicesRoomsList.clear();
        }

        if (searchString.isEmpty() || searchString.equals("")) {
            this.mIsSearchMode = false;

            mDevicesRoomsList.addAll(mDevicesRoomsSearchList);
            super.notifyDataSetChanged();
            if(mIsExpanded) {
                for (int i = 0; i < mDevicesRoomsList.size(); ++i) {
                    mExpandableListView.collapseGroupWithAnimation(i);
                }
                mIsCollapsed = true;
                mIsExpanded = false;
            }

            return true;
        } else {

            this.mIsSearchMode = true;
            boolean isSuccessSearch = false;

            for (DevicesRoom devicesRoom : mDevicesRoomsSearchList) {

                deviceRoom = new DevicesRoom(devicesRoom);
                deviceRoom.getDevicesList().clear();

                for (Device device : devicesRoom.getDevicesList()) {
                    if (device.getDeviceName().toLowerCase().contains(searchString)) {

                        deviceRoom.getDevicesList().add(device);
                        addSection = true;
                        isSuccessSearch = true;
                    }
                }
                if (addSection) {
                    mDevicesRoomsList.add(deviceRoom);
                    deviceRoom = null;
                    addSection = false;
                }
            }
            mSearchWasFailed = isSuccessSearch;
            super.notifyDataSetChanged();
            if(mIsCollapsed && isSuccessSearch || mSearchWasFailed && isSuccessSearch) {
                for (int i = 0; i < mDevicesRoomsList.size(); ++i) {
                    mExpandableListView.expandGroup(i);
                }
                mIsCollapsed = false;
                mIsExpanded = true;
            }
            return isSuccessSearch;
        }
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
