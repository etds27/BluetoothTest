package com.example.bluetoothtest.lobby_creator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.example.bluetoothtest.R;
import com.example.bluetoothtest.game.BluetoothGameManager;
import com.example.bluetoothtest.game.GameManager;

public class BluetoothLobbyFragment extends LobbyFragment {
    @Override
    protected GameManager createGameManager() {
        return new BluetoothGameManager(getContext(), room, gameFragment);
    }

    @Override
    protected View inflateVariableFrame() {
        LayoutInflater lm = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return lm.inflate(R.layout.bluetooth_host_client_layout, mFrameLayout, true);
    }
}
