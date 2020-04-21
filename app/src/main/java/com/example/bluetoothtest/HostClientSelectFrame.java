package com.example.bluetoothtest;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bluetoothtest.bluetooth.BluetoothConnectionClient;

/**
 * Class is responsible for the host button, along with the host frame
 */
public class HostClientSelectFrame {
    private FrameLayout frameLayout;
    private Button designateHostButton;

    private ImageButton startBTConnectionAsHostButton;

    private Context mContext;
    private TextView hashView;
    private boolean selected;

    public Room currentRoom;


    private EditText hashEntry;
    private Button designateClientButton;
    private ImageButton connectBluetoothClient;

    public BluetoothConnectionClient bcc;



    public HostClientSelectFrame(Context context, Button hostButton, Button clientButton, FrameLayout frameLayout) {
        mContext = context;
        designateHostButton = hostButton;
        designateClientButton = clientButton;
        this.frameLayout = frameLayout;


        designateHostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeHostFrame();
            }
        });

        designateClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeClientFrame();
            }
        });



        currentRoom = new Room();
        currentRoom.generateHash();
    }

    public void placeHostFrame() {
        cleanFrame();

        // Inflate the host layout to the frame
        LayoutInflater lm = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View hostView = lm.inflate(R.layout.host_frame_layout, frameLayout, true);

        ImageButton randomRoom = hostView.findViewById(R.id.generate_random_room_button);
        startBTConnectionAsHostButton = hostView.findViewById(R.id.start_bt_connection_as_host_button);
        hashView = hostView.findViewById(R.id.random_room_hash_text);

        randomRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRoom = new Room();
                currentRoom.generateHash();
                update();
            }
        });

        startBTConnectionAsHostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectBluetoothAsHost();
            }
        });

        setHostSelected(true);
        update();
    }

    // Puts all of the items from the client layout xml into the frame layout
    public void placeClientFrame() {
        cleanFrame();

        // Inflate the host layout to the frame
        LayoutInflater lm = (LayoutInflater) mContext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View hostView = lm.inflate(R.layout.client_frame_layout, frameLayout, true);

        hashEntry = hostView.findViewById(R.id.hash_text_entry);
        connectBluetoothClient = hostView.findViewById(R.id.client_bluetooth_connect_button);

        connectBluetoothClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectBluetoothAsClient();
            }
        });

        setHostSelected(false);
        update();

    }

    private void cleanFrame() {
        frameLayout.removeAllViews();
    }


    public void update() {
        updateHostFrameHash(); // Sets correct text for room
        updateSelectButton(); // Make sure the button displays according to the selected state
    }

    private void updateHostFrameHash() {
        hashView.setText(currentRoom.getHash());
    }


    void updateSelectButton() {
        if (selected) {
            designateHostButton.setBackgroundColor(Color.GRAY);
            designateClientButton.setBackgroundColor(Color.WHITE);
        } else {
            designateHostButton.setBackgroundColor(Color.WHITE);
            designateClientButton.setBackgroundColor(Color.GRAY);
        }
    }


    private void connectBluetoothAsClient() {
        currentRoom = createRoom();
        if (currentRoom == null) return;

        bcc = new BluetoothConnectionClient(mContext);
        bcc.setRoom(currentRoom);
        bcc.constructUuid();


        // Attempting to connect to all devices
        for (BluetoothDevice device : MainActivity.deviceListAdapter.mDevices) {
            bcc.startAsClient(device);
        }

        MainActivity.bluetoothConnectionClient = bcc;
    }

    /**
     * Create a room from the current typed group id
     * return the room
     * @return
     */
    private Room createRoom() {
        if (hashEntry.getText().toString().length() != 4) return null;
        Room tempRoom = new Room();
        tempRoom.setHash(hashEntry.getText().toString());
        return tempRoom;
    }


    public void setHostSelected(boolean selected) {
        this.selected = selected;
    }


    private void connectBluetoothAsHost () {

        bcc = new BluetoothConnectionClient(mContext);
        bcc.setRoom(currentRoom);
        bcc.constructUuid();
        bcc.startAsHost();
        MainActivity.bluetoothConnectionClient = bcc;

    }
}
