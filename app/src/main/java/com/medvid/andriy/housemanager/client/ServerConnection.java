package com.medvid.andriy.housemanager.client;

import android.util.Log;

import com.volodymyr.bereziuk.server.transmit.TransmitHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Андрій on 6/2/2015.
 */
public class ServerConnection {

    private static final String LOG_TAG = "ServerConnection.log";
    private static final String IP_ADDRESS = "192.168.33.101";
    private static final int PORT = 6666;

    public Socket socket = null;
    public ObjectInputStream in = null;
    public ObjectOutputStream out = null;
    public TransmitHelper mTransmitHelper = null;

    public ServerConnection()   {
        startConnection();
    }

    public void closeConnection()   {
        mTransmitHelper.closeConnection();
    }

    private void startConnection()    {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            Log.d(LOG_TAG, "Connected to " + IP_ADDRESS);

            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            //clientHandler = new ClientHandler(in, out, socket);
            //login = new Login(clientHandler);
            mTransmitHelper = new TransmitHelper(in, out, socket);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
