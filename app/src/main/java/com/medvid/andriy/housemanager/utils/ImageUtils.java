package com.medvid.andriy.housemanager.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by Андрій on 5/19/2015.
 */
public class ImageUtils {

    private Context mContext = null;

    public ImageUtils(Context context) {
        this.mContext = context;
    }

    public Drawable getDrawableFromResource(int resId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mContext.getDrawable(resId);
        }
        return mContext.getResources().getDrawable(resId);
    }
}
