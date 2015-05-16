package com.medvid.andriy.housemanager.utils;

import android.app.Activity;
import android.content.Context;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * Created by Андрій on 5/11/2015.
 */
public class SlidrHelper {
    public static void initSlidr(Activity activity) {
        SlidrConfig config = new SlidrConfig.Builder()
                //.primaryColor(getResources().getColor(R.color.green)) //Avtion bar color
                //.secondaryColor(getResources().getColor(R.color.gray)) //Status bar color
                .position(SlidrPosition.LEFT)
                .sensitivity(1f)
                .build();

        Slidr.attach(activity, config);
    }
}
