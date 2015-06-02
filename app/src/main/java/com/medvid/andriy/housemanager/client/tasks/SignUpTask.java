package com.medvid.andriy.housemanager.client.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.medvid.andriy.housemanager.application.HouseManagerApplication;
import com.medvid.andriy.housemanager.client.ServerConnection;
import com.volodymyr.bereziuk.server.dto.User;

import java.io.IOException;

/**
 * Created by Андрій on 6/2/2015.
 */
public class SignUpTask extends AsyncTask<Void, Void, Void> {

    private Context mContext = null;

    public SignUpTask(Context context)   {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        User user = new User(1, "123", "123", 0, 0);

        HouseManagerApplication.mServerConnection.mTransmitHelper.addUser(user);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
