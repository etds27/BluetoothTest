package com.example.bluetoothtest.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.bluetoothtest.HomeScreenFragment;
import com.example.bluetoothtest.MainActivity;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.Room;
import com.example.bluetoothtest.game.GameFragment;
import com.example.bluetoothtest.game.LocalGameManager;
import com.example.bluetoothtest.player.*;
import com.example.bluetoothtest.player.list.PlayerList;
import com.example.bluetoothtest.player.list.PlayerListAdapter;

public class BluetoothHostClientFragment extends Fragment {

/**
 * Class is responsible for the host button, along with the host frame
 */
    private static final String TAG = "BluetoothHostClientFrag";
    private Switch hostClientSwitch;

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

    public View view;

    private PlayerList playerListView;
    private PlayerListAdapter playerListAdapter;

    private Button startButton;


/*
    public BluetoothHostClientFragment(Context context) {
        mContext = context;

        currentRoom = new Room();
        currentRoom.generateHash();
    }
*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentRoom = Room.getInstance();
        currentRoom.generateHash();


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.bluetooth_host_client_layout, container, false);

        frameLayout = view.findViewById(R.id.bluetooth_host_client_frame);

        hostClientSwitch = view.findViewById(R.id.host_client_switch);

        playerListView = view.findViewById(R.id.players_in_party);
        currentRoom.addList(playerListView);

        playerListAdapter = (PlayerListAdapter) playerListView.getAdapter();
        Log.d(TAG, "onCreateView: playerListAdapter " + playerListAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        playerListView.setLayoutManager(llm);





        hostClientSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if ( ! b) {
                    placeHostFrame();
                } else {
                    placeClientFrame();
                }
            }
        });



        placeHostFrame();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void placeHostFrame() {
        cleanFrame();

        // Inflate the host layout to the frame

        LayoutInflater lm = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View hostView = lm.inflate(R.layout.host_frame_layout, frameLayout, true);

        ImageButton randomRoom = hostView.findViewById(R.id.generate_random_room_button);
        startBTConnectionAsHostButton = hostView.findViewById(R.id.start_bt_connection_as_host_button);
        hashView = hostView.findViewById(R.id.random_room_hash_text);

        startButton = view.findViewById(R.id.temp_start_button);

        Button randomizeOrder = view.findViewById(R.id.randomize_order_button);

        randomRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRoom = Room.getInstance();
                currentRoom.generateHash();
                update();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Starting Room");
                Room room = Room.getInstance();

                Host h = new Host("Ethan", null);
                int size = room.size();
                room.clear();
                playerListAdapter.notifyItemRangeRemoved(0, size);
                playerListAdapter.addItem(h);

                for (int i = 0; i < 4; i++) {
                    Player p = new Player("player" + i, null);
                    playerListAdapter.addItem(p);
                }

                ((MainActivity) getActivity()).setViewPage("CURRENT_GAME");
                GameFragment cgf = (GameFragment) ((MainActivity) getActivity()).getFragment("CURRENT_GAME");
                new LocalGameManager(getContext(), currentRoom, cgf);
            }
        });

        randomizeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRoom.randomizeOrder();
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

        LayoutInflater lm = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        //updateSelectButton(); // Make sure the button displays according to the selected state
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
        for (BluetoothDevice device : HomeScreenFragment.deviceListAdapter.mDevices) {
            bcc.startAsClient(device);
        }
        HomeScreenFragment.bluetoothConnectionClient = bcc;
    }

    /**
     * Create a room from the current typed group id
     * return the room
     * @return
     */
    private Room createRoom() {
        if (hashEntry.getText().toString().length() != 4) return null;
        currentRoom.setHash(hashEntry.getText().toString());
        return currentRoom;
    }


    public void setHostSelected(boolean selected) {
        this.selected = selected;
        playerListAdapter.setEditable(selected);
        playerListAdapter.notifyDataSetChanged();
    }


    private void connectBluetoothAsHost () {

        bcc = new BluetoothConnectionClient(mContext);
        bcc.setRoom(currentRoom);
        bcc.constructUuid();
        bcc.startAsHost();
        HomeScreenFragment.bluetoothConnectionClient = bcc;

    }


}
