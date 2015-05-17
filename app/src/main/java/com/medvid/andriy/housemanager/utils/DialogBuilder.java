package com.medvid.andriy.housemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
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

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogHeaderView =  inflater.inflate(R.layout.dialog_header_layout, null);
        TextView tv_dialog_header_title = (TextView) dialogHeaderView.findViewById(R.id.tv_dialog_header_title);
        tv_dialog_header_title.setText(mContext.getString(R.string.sign_out));

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.cancel_it_button:
                        break;
                    case R.id.submit_button:
                        Intent mainIntent = new Intent(mContext, EntryActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(mainIntent);
                        break;
                }
                dialog.dismiss();
            }
        };

        showNoFooterDialog(holder, dialogHeaderView, dialogGravity, clickListener);
    }


    private static void showNoFooterDialog(Holder holder, View headerView, DialogPlus.Gravity gravity,
                                          OnClickListener clickListener) {
        final DialogPlus dialog = new DialogPlus.Builder(mContext)
                .setContentHolder(holder)
                .setHeader(headerView)
                .setCancelable(true)
                .setGravity(gravity)
                .setOnClickListener(clickListener)
                .create();
        dialog.show();
    }

    public static void showOkDialog(String title, String message) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View dialogView =  inflater.inflate(R.layout.dialog_ok_layout, null);
        TextView tv_dialog_message = (TextView) dialogView.findViewById(R.id.title);
        tv_dialog_message.setText(message);
        ViewHolder holder = new ViewHolder(dialogView);

        View dialogHeaderView =  inflater.inflate(R.layout.dialog_header_layout, null);
        TextView tv_dialog_header_title = (TextView) dialogHeaderView.findViewById(R.id.tv_dialog_header_title);
        tv_dialog_header_title.setText(title);

        DialogPlus.Gravity dialogGravity = DialogPlus.Gravity.CENTER;

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                dialog.dismiss();
            }
        };

        showNoFooterDialog(holder, dialogHeaderView, dialogGravity, clickListener);
    }

}
