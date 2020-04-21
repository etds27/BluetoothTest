package com.example.bluetoothtest;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bluetoothtest.bluetooth.BluetoothConnectionClient;

import java.nio.charset.Charset;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements DeviceListAdapter.DeviceOnClickListener {
    private static final String TAG = "MainActivity";

    private BluetoothAdapter mBluetoothAdapter;
    private TextView btStatusView;
    private TextView btDiscoverabilityStatusView;
    private Button btEnableDisableDiscoverable;
    static public DeviceListAdapter deviceListAdapter;
    private LinkedList<BluetoothDevice> discoverableDevices;

    public HostClientSelectFrame hostClientSelectFrame;

    public static BluetoothConnectionClient bluetoothConnectionClient;



    private final BroadcastReceiver mBluetoothReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Get action of intent. In this case, it will probably be BLUETOOTH_STATE_CHANGE
            String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch(state) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "mBluetoothReceiver1: Bluetooth turned off");
                        btStatusView.setText("Off");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBluetoothReceiver1: Bluetooth turning off");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBluetoothReceiver1: Bluetooth turned on");
                        btStatusView.setText("On");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBluetoothReceiver1: Bluetooth turning on");
                        break;
                }

            }
        }
    };

    private final BroadcastReceiver mBluetoothReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Get action of intent. In this case, it will probably be BLUETOOTH_STATE_CHANGE
            String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, mBluetoothAdapter.ERROR);

                switch(state) {
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBluetoothReceiver2: Discoverability enabled");
                        btDiscoverabilityStatusView.setText("Discoverability Enabled");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBluetoothReceiver2: Discoverability enabled. Able to receive connecitons");
                        btDiscoverabilityStatusView.setText("Discoverability Enabled. Able to receive Connections");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBluetoothReceiver2: Discovreability Disabled.");
                        btDiscoverabilityStatusView.setText("Discoverability Disabled");
                        discoverableDevices = new LinkedList<>();
                        deviceListAdapter.notifyAll();
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBluetoothReceiver2: Connecting");
                        btDiscoverabilityStatusView.setText("Connecting");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBluetoothReceiver2: Connected");
                        btDiscoverabilityStatusView.setText("Connected");
                        break;
                }
            }
        }
    };

    private final BroadcastReceiver mBluetoothReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Get action of intent. In this case, it will probably be BluetoothDevice.ACTION_FOUND
            final String action = intent.getAction();
            //Log.d(TAG, "mBluetoothReceiver3: " + Arrays.toString(intent.getExtras().keySet().toArray()));



            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                Log.d(TAG, "mBluetoothReceiver3: ACTION FOUND");
                // Get the device passed throught the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


                if (device.getName() != null) {
                    // The device that was with the intent is added to the device list.
                    discoverableDevices.add(device);
                    Log.d(TAG, "mBluetoothReceiver3: Adding device to discoverable list" + device.getName() + ":" + device.getAddress());

                    //Update the device recyclerview
                    //deviceListAdapter.notifyDataSetChanged();
                    deviceListAdapter.notifyItemInserted(discoverableDevices.size() - 1);
                }
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBluetoothReceiver1);
        unregisterReceiver(mBluetoothReceiver2);
        unregisterReceiver(mBluetoothReceiver3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Map button in layout to java object.
        Button btOnOff = (Button) findViewById(R.id.bt_on_off_button);
        btStatusView = (TextView) findViewById(R.id.bluetooth_status);

        btEnableDisableDiscoverable = (Button) findViewById(R.id.bt_enable_discoverability_button);
        btDiscoverabilityStatusView = (TextView) findViewById(R.id.discoverability_status);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        btOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBluetooth();
            }
        });

        displayBTStatus(mBluetoothAdapter.getState());


        // Creating Device List
        discoverableDevices = new LinkedList<>();

        // Because this implements the OnClickListener, I can pass the instance of itself to the list adapter
        deviceListAdapter = new DeviceListAdapter(discoverableDevices, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);


        RecyclerView deviceList = findViewById(R.id.bt_device_recycler_view);
        deviceList.setLayoutManager(llm);
        deviceList.setAdapter(deviceListAdapter);


        // Create the functionality for ther host and client buttons
        Button hostButton = findViewById(R.id.designate_host_button);

        Button clientButton = findViewById(R.id.designate_client_button);

        FrameLayout hostClientFrame = findViewById(R.id.host_client_layout);


        hostClientSelectFrame = new HostClientSelectFrame(this, hostButton, clientButton, hostClientFrame);
        hostClientSelectFrame.placeHostFrame();

        Button hiButton = findViewById(R.id.hi_button);
        hiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothConnectionClient.write("HI!".getBytes(Charset.defaultCharset()));
            }
        });
    }


    public void displayBTStatus(int status) {
        switch(status) {
            case BluetoothAdapter.STATE_OFF:
                Log.d(TAG, "mBluetoothReceiver1: Bluetooth turned off");
            case BluetoothAdapter.STATE_TURNING_OFF:
                Log.d(TAG, "mBluetoothReceiver1: Bluetootk turning off");
                btStatusView.setText("Off");
                break;
            case BluetoothAdapter.STATE_ON:
                Log.d(TAG, "mBluetoothReceiver1: Bluetooth turned on");
            case BluetoothAdapter.STATE_TURNING_ON:
                Log.d(TAG, "mBluetoothReceiver1: Bluetooth turning on");
                btStatusView.setText("On");
                break;
        }
    }

    public void toggleBluetooth() {
        // mBluetooth adapter will still be null if no bluetooth
        // If bluetooth is enabled, then disable it
        // If it isnt enabled, then disable it
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "toggleBluetooth: Device doesnt have bluetooth");
        } else if (mBluetoothAdapter.isEnabled()) {

            mBluetoothAdapter.disable();

            IntentFilter BTIntentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBluetoothReceiver1, BTIntentFilter);
        } else {
            //This is like saying, "Hey phone, im going to change the bluetooth state" So that anyone listening for that message can act accordingly
            Intent toggleBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(toggleBTIntent);

            //This will now intercept changes to the bluetooth state
            IntentFilter BTIntentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBluetoothReceiver1, BTIntentFilter);
        }

        //Change labels to reflect bluetooth state
        displayBTStatus(mBluetoothAdapter.getState());
    }

    public void btEnableDisableDiscoverability(View view) {
        Log.d(TAG, "btEnableDisableDiscoverability: Making device discoverable for 30 seconds");

        Intent discoverabilityIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //Put extra is like addidng parameters to the specific Intent Type
        discoverabilityIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 30);
        discoverabilityIntent.putExtra("SERVER_HOST_CLIENT", true);

        startActivity(discoverabilityIntent);

        //This pushes our broadcast receiver above to check the state after enables
        IntentFilter discoverableIntentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBluetoothReceiver2, discoverableIntentFilter);



    }


    public void btDiscoverDevices(View view) {
        Log.d(TAG, "btDiscoverDevices: Discovering Devices");

        if (mBluetoothAdapter.isDiscovering()) {
            Log.d(TAG, "btDiscoverDevices: BT Adapter is discovering");
            mBluetoothAdapter.cancelDiscovery();
        }

        //Necessary for API 23+
        checkBTPermissions();

        mBluetoothAdapter.startDiscovery();
        IntentFilter discoverIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBluetoothReceiver3, discoverIntent);

    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    // Implemented from Device On Click
    @Override
    public void onDeviceClick(int position) {
        BluetoothDevice selectedDevice = discoverableDevices.get(position);
        Log.d(TAG, "onDeviceClick: Selected " + selectedDevice.getName());

        //BluetoothConnectionClient bluetoothConnectionClient = new BluetoothConnectionClient(getApplicationContext(), currentRoom);
    }
}

