package com.example.bluetoothtest;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class BluetoothConnectionService {
    private static final String TAG = "BluetoothConnectionServ";

    private static final String APP_NAME = "Bluetooth Test";

    //UUID is how the devices identify each other
    private static final UUID UUID_UNSECURE =
            UUID.fromString("a060d5d7-bf91-4856-a0ea-4d777fe5c601");

    //private static final BluetoothAdapter mBluetoothAdapter;
    private static BluetoothAdapter mBluetoothAdapter;
    Context mContext;

    private AcceptThread mAcceptInsecureThread;
    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressBar mProgressBar;

    private ConnectedThread mConnectedThread;

    static {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothConnectionService(Context context) {
        mContext = context;

    }

    /**
     * This is the thread that will sit there and wait for incoming connecitons
     */
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mBluetoothServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;

            try {
                //Create new listening server socket
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME, UUID_UNSECURE);

                Log.d(TAG, "AcceptThread: Setting up server with: " + UUID_UNSECURE);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // If socket sets to listen. Set socket to class var
            mBluetoothServerSocket = tmp;
        }

        public void run() {
            Log.d(TAG, "run: Accept Thread running");

            //Setting up our bluetooth socket
            BluetoothSocket socket = null;


            Log.d(TAG, "run: Only returns only successful connection or Exception throwm");

            //Program will sit and wait until connection is made
            try {
                socket = mBluetoothServerSocket.accept();
                Log.d(TAG, "run: Connection successful");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //If connection was established
            if (socket != null) {
                connected(socket, mmDevice);
            }


            Log.d(TAG, "run: End of Accept Thread");
        }

        public void cancel() {
            Log.d(TAG, "cancel: Cancelling Accept Thread");
            try {
                mBluetoothServerSocket.close();
            } catch (IOException e) {
                Log.d(TAG, "cancel: Thread Cancel failed " + e.getMessage());
            }
        }
    }

    /**
     * This class will initiate the bluetooth connection with the AcceptThread
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.d(TAG, "ConnectThread: Started thread");
            mmDevice = device;
            deviceUUID = uuid;
        }


        /**
         * Both devices are going to be sitting with their accept thread runinng until the ConnectThread.run() methode is called
         */
        public void run() {
            Log.d(TAG, "run: Started Connect Thread");

            BluetoothSocket tmp = null;
            try {
                Log.d(TAG, "run: Trying to create insecure rf comm socket using: " + UUID_UNSECURE);
                tmp = mmDevice.createRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                Log.d(TAG, "run: Unable to create rfComm socket: " + e.getMessage());
            }

            mmSocket = tmp;
            mBluetoothAdapter.cancelDiscovery(); // Cancel after conneciton because this is resource hungry
            try {
                mmSocket.connect();
                Log.d(TAG, "run: ConnectThread Successful");
            } catch (IOException e) {
                Log.d(TAG, "run: Connection failed " + e.getMessage());
                try {
                    mmSocket.close();
                } catch (IOException ex) {
                    Log.d(TAG, "run: Cannot close connection: " + ex.getMessage());
                }
            }

            connected(mmSocket, mmDevice);
        }



        public void cancel() {
            Log.d(TAG, "cancel: Cancelling Connect Thread");
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.d(TAG, "cancel: Thread Cancel failed " + e.getMessage());
            }

        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInput;
        private final OutputStream mmOutput;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;



            mProgressBar.setVisibility(View.INVISIBLE);
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                Log.d(TAG, "ConnectedThread: Unable to get input/output stream " + e.getMessage());
            }

            mmInput = tmpIn;
            mmOutput = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024]; // buffer store for stream
            int bytes; // bytes returned from inputstream read()


            // Keep listening to input stream until exception
            while (true) {
                try {
                    bytes = mmInput.read(buffer);
                    String incomingMessage = new String(buffer, 0 , bytes);
                } catch (IOException e) {
                    Log.d(TAG, "run: Cannot read input stream. Exiting loop " + e.getMessage());
                    break;
                }
            }
        }

        // Send data to remote device
        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: Writing to remote device " + text);
            try {
                mmOutput.write(bytes);
            } catch (IOException e) {
                Log.d(TAG, "write: Error writing to output stream");
                e.printStackTrace();
            }

        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This will initiate the accept thread
     */
    public synchronized void start() {

        // If the Connect Thread is not null. Then start fresh and make it null
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        // If there is no accept thread, then start one.
        if (mAcceptInsecureThread == null) {
            mAcceptInsecureThread = new AcceptThread();
            mAcceptInsecureThread.start();
        }
    }

    public void startClient(BluetoothDevice device, UUID uuid) {
        Log.d(TAG, "startClient: startClient: Started");
        mProgressBar.setVisibility(View.VISIBLE);

        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }

    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        Log.d(TAG, "connected: Starting:");

        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    public void write(byte[] out) {
        //Temp object
        ConnectedThread r;

        mConnectedThread.write(out);
    }



}
