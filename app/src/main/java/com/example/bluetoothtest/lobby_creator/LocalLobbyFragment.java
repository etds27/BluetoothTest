package com.example.bluetoothtest.lobby_creator;

import com.example.bluetoothtest.game.GameManager;
import com.example.bluetoothtest.game.LocalGameManager;

public class LocalLobbyFragment extends LobbyFragment {

    @Override
    protected GameManager createGameManager() {
        return new LocalGameManager(getContext(), room, gameFragment);
    }
}
