package com.medvid.andriy.housemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import com.medvid.andriy.housemanager.R;
import com.medvid.andriy.housemanager.activity.EntryActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.ButterKnife;

public class DialogBuilder {

    private static Context mContext = null;

    public static void setContext(Context context) {
        mContext = context;
    }

    public static void showSignOutDialog() {

        ViewHolder holder = new ViewHolder(R.layout.dialog_centre_layout);
        DialogPlus.Gravity dialogGravity = DialogPlus.Gravity.BOTTOM;

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.cancel_it_button:
                        Toast.makeText(mContext, "We're glad that you like it", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.submit_button:
                        //Toast.makeText(mContext, "We're glad that you love it", Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(mContext, EntryActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(mainIntent);
                        break;
                }
                dialog.dismiss();
            }
        };

        showNoFooterDialog(holder, dialogGravity, clickListener);
    }


    private static void showNoFooterDialog(Holder holder, DialogPlus.Gravity gravity,
                                          OnClickListener clickListener) {
        final DialogPlus dialog = new DialogPlus.Builder(mContext)
                .setContentHolder(holder)
                .setHeader(R.layout.dialog_header_layout)
                .setCancelable(true)
                .setGravity(gravity)
                .setOnClickListener(clickListener)
                .create();
        dialog.show();
    }

}
