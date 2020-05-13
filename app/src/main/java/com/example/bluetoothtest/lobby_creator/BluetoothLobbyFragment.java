package com.example.bluetoothtest.lobby_creator;

import com.example.bluetoothtest.game.BluetoothGameManager;
import com.example.bluetoothtest.game.GameManager;

public class BluetoothLobbyFragment extends LobbyFragment {
    @Override
    protected GameManager createGameManager() {
        return new BluetoothGameManager(getContext(), room, gameFragment);
    }
}
