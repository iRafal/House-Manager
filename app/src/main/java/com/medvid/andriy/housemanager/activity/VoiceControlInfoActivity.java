package com.medvid.andriy.housemanager.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.utils.SlidrHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Андрій on 5/11/2015.
 */
public class VoiceControlInfoActivity extends BaseActivity {

    @InjectView(R.id.lv_commands)
        ListView lv_commands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_controll_info_layout);

        ButterKnife.inject(this);
        initSlidr();
        initView();
    }


    private void initView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.devices_list_item_layout, initCommandsList());
        lv_commands.setAdapter(adapter);
    }

    private List<String> initCommandsList()  {
        List<String> commandsList = new ArrayList<>();

        commandsList.add("switch on the lamp");
        commandsList.add("switch off the lamp");

        commandsList.add("switch on thermometer");
        commandsList.add("switch off thermometer");

        commandsList.add("switch on conditioner");
        commandsList.add("switch off conditioner");

        commandsList.add("switch on chandelier");
        commandsList.add("switch off chandelier");

        return commandsList;
    }

    private void initSlidr() {
        SlidrHelper.initSlidr(this);
    }
}
